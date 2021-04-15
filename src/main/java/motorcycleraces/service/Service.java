package motorcycleraces.service;

import motorcycleraces.domain.*;
import motorcycleraces.repository.ParticipantIRepository;
import motorcycleraces.repository.RaceIRepository;
import motorcycleraces.repository.TeamIRepository;
import motorcycleraces.repository.UserIRepository;

import java.util.List;

public class Service {
    private UserIRepository repoUser;
    private TeamIRepository repoTeam;
    private ParticipantIRepository repoParticipant;
    private RaceIRepository repoRace;

    public Service(UserIRepository repoUser, TeamIRepository repoTeam, ParticipantIRepository repoParticipant,RaceIRepository repoRace) {
        this.repoUser = repoUser;
        this.repoTeam = repoTeam;
        this.repoParticipant = repoParticipant;
        this.repoRace = repoRace;
    }

    public User searchUser(String username, String password){
        return repoUser.searchForUser(username, password);
    }

    public Participant addParticipant(String firstname,String lastname,float engineCapacity,String teamname,Race race){
        Participant participant = new Participant(firstname,lastname,teamname, engineCapacity,race);
        System.out.println(race.getId());
        updateRace(race.getId());
        return repoParticipant.save(participant);
    }

    public List<Participant> findParticipants(String teamName){
        return repoParticipant.filterByTeam(teamName);
    }

    public List<Race> findAllRaces(){
        return repoRace.findAll();
    }

    public void updateRace(Long idRace){
        Race race = repoRace.findOne(idRace);
        if(race != null){
            Race updatedRace = new Race(idRace,race.getLocation(), race.getName(), race.getMaxEngineSize(), race.getNrParticipants()+1);
            repoRace.update(updatedRace);
        }
    }

    public List<Team> findAllTeams(){
        return repoTeam.findAll();
    }
}
