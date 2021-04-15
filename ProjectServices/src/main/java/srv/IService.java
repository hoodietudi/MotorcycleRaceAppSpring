package srv;

import domain.Participant;
import domain.Race;
import domain.Team;
import domain.User;

import java.util.List;

public interface IService {
    public void logout(User user, IObserver client) throws ProjectException;
    public User searchUser(String username, String password, IObserver client) throws ProjectException;
    public List<Participant> findParticipants(String teamName) throws  ProjectException;
    public List<Race> findAllRaces() throws ProjectException;
    public void updateRace(Long idRace) throws ProjectException;
    public List<Team> findAllTeams() throws ProjectException;
    public Participant addParticipant(String firstname,String lastname,float engineCapacity,String teamname,Race race) throws ProjectException;
}
