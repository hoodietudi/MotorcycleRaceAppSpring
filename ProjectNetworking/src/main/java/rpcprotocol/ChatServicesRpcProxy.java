package rpcprotocol;

import domain.Participant;
import domain.Race;
import domain.Team;
import domain.User;
import srv.IObserver;
import srv.IService;
import srv.ProjectException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ChatServicesRpcProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private BlockingQueue<Object> objects = new LinkedBlockingQueue<>();

    private volatile boolean finished;
    public ChatServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }



    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws ProjectException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ProjectException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ProjectException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ProjectException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
        if (response.type()== ResponseType.ADDPARTICIPANT){
            Race r= (Race) response.data();
            try {
                client.ParticipantAdded(r);
            } catch (ProjectException | RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.ADDPARTICIPANT;
        //return false;
    }

    @Override
    public User searchUser(String username, String parola, IObserver client) throws ProjectException {
        initializeConnection();
        User user = new User(username,parola);

        Request req=new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return user;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ProjectException(err);
        }
        return null;
    }

    @Override
    public List<Participant> findParticipants(String teamName) throws ProjectException {
        return null;
    }

    @Override
    public List<Race> findAllRaces() throws ProjectException {
        return null;
    }

    @Override
    public void updateRace(Long idRace) throws ProjectException {

    }

    @Override
    public List<Team> findAllTeams() throws ProjectException {
        return null;
    }

    @Override
    public Participant addParticipant(String firstname, String lastname, float engineCapacity, String teamname, Race race) throws ProjectException {
        //initializeConnection();
        Participant p = new Participant(firstname,lastname,teamname,engineCapacity,race);
        Request req=new Request.Builder().type(RequestType.ADDPARTICIPANT).data(p).build();
        sendRequest(req);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ProjectException(err);
        }
        return (Participant)response.data();

    }

    @Override
    public void logout(User user, IObserver client) throws ProjectException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ProjectException(err);
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
