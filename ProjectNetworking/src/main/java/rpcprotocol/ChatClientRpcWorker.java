package rpcprotocol;

import domain.Participant;
import domain.Race;
import domain.User;
import srv.IObserver;
import srv.IService;
import srv.ProjectException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;


public class ChatClientRpcWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ChatClientRpcWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }
    public void ParticipantAdded(Participant participant) throws ProjectException {
        System.out.println("Worker");
        Response resp=new Response.Builder().type(ResponseType.ADDPARTICIPANT).data(participant).build();
        System.out.println("Inscriere added  "+participant);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new ProjectException("Sending error: "+e);
        }

    }

//    public void messageReceived(Message message) throws ChatException {
//        MessageDTO mdto= DTOUtils.getDTO(message);
//        Response resp=new Response.Builder().type(ResponseType.NEW_MESSAGE).data(mdto).build();
//        System.out.println("Message received  "+message);
//        try {
//            sendResponse(resp);
//        } catch (IOException e) {
//            throw new ChatException("Sending error: "+e);
//        }
//    }
//
//    public void friendLoggedIn(User friend) throws ChatException {
//        UserDTO udto= DTOUtils.getDTO(friend);
//        Response resp=new Response.Builder().type(ResponseType.FRIEND_LOGGED_IN).data(udto).build();
//        System.out.println("Friend logged in "+friend);
//        try {
//            sendResponse(resp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void friendLoggedOut(User friend) throws ChatException {
//        UserDTO udto= DTOUtils.getDTO(friend);
//        Response resp=new Response.Builder().type(ResponseType.FRIEND_LOGGED_OUT).data(udto).build();
//        System.out.println("Friend logged out "+friend);
//        try {
//            sendResponse(resp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
  //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            User user=(User)request.data();

            try {
                server.searchUser(user.getUsername(),user.getPassword(), this);
                return okResponse;
            } catch (ProjectException e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.LOGOUT){
            System.out.println("Logout request");
           // LogoutRequest logReq=(LogoutRequest)request;
            User user=(User)request.data();
            try {
                server.logout(user, this);
                connected=false;
                return okResponse;

            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }


    @Override
    public void ParticipantAdded(Race race) throws ProjectException, RemoteException {

    }
}
