package service;

import domain.Participant;
import domain.Race;
import domain.Team;
import domain.User;
import repository.ParticipantIRepository;
import repository.RaceIRepository;
import repository.TeamIRepository;
import repository.UserIRepository;
import srv.IObserver;
import srv.IService;
import srv.ProjectException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    private UserIRepository repoUser;
    private TeamIRepository repoTeam;
    private ParticipantIRepository repoParticipant;
    private RaceIRepository repoRace;
    private Map<Long, IObserver> loggedClients;

    public Service(UserIRepository repoUser, TeamIRepository repoTeam, ParticipantIRepository repoParticipant,RaceIRepository repoRace) {
        this.repoUser = repoUser;
        this.repoTeam = repoTeam;
        this.repoParticipant = repoParticipant;
        this.repoRace = repoRace;
        loggedClients = new ConcurrentHashMap<>();
    }

    public User searchUser(String username, String password, IObserver client) throws ProjectException {
        User user = repoUser.searchForUser(username, password);
        if (user == null)
            throw new ProjectException("Nu exista acest cont !");

        if(!user.getPassword().equals(password))
            throw  new ProjectException("Parola gresita!");
        loggedClients.put(user.getId(),client);
        return user;
    }

    public synchronized Participant addParticipant(String firstname, String lastname, float engineCapacity, String teamname, Race race){
        Participant participant = new Participant(firstname,lastname,teamname, engineCapacity,race);
        System.out.println(race.getId());
        updateRace(race.getId());
        return repoParticipant.save(participant);
    }

    public synchronized List<Participant> findParticipants(String teamName){
        return repoParticipant.filterByTeam(teamName);
    }

    public synchronized List<Race> findAllRaces(){
        return repoRace.findAll();
    }

    public synchronized void updateRace(Long idRace){
        Race race = repoRace.findOne(idRace);
        if(race != null){
            Race updatedRace = new Race(idRace,race.getLocation(), race.getName(), race.getMaxEngineSize(), race.getNrParticipants()+1);
            repoRace.update(updatedRace);
        }
    }

    public synchronized List<Team> findAllTeams(){
        return repoTeam.findAll();
    }

    public synchronized void logout(User user, IObserver client)  {
        IObserver localClient=loggedClients.remove(user.getId());
//        if (localClient==null)
//            throw new ChatException("User "+user.getId()+" is not logged in.");
        //notifyFriendsLoggedOut(user);
    }

    private final int defaultThreadsNo=5;
    private void notifyRegister(Race r) throws ProjectException {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(long i : loggedClients.keySet()){
            System.out.println(i);
            //Oficiu o = repoOficiu.findOne(str);
            IObserver chatClient = loggedClients.get(i);
            if (chatClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying " +i+ " new participant");
                        chatClient.ParticipantAdded(r);
                    } catch (ProjectException | RemoteException e) {
                        System.err.println("Error notifying " + e);
                    }
                });
        }

        executor.shutdown();
    }
}
