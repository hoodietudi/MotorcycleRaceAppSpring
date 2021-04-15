package repository;

import domain.Participant;
import domain.Race;
import domain.Team;
import domain.validators.ValidatorParticipant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ParticipantDBRepository implements ParticipantIRepository{

    private Logger log = (Logger) LogManager.getLogger(ParticipantDBRepository.class);
    private RaceIRepository raceRepo;
    private TeamIRepository teamRepo;
    private ValidatorParticipant validator = new ValidatorParticipant();
    private DBUtils dbUtils;

    public ParticipantDBRepository(Properties props, ValidatorParticipant validator,RaceIRepository raceRepo,TeamIRepository teamRepo){
        this.dbUtils = new DBUtils(props);
        this.validator = validator;
        this.raceRepo = raceRepo;
        this.teamRepo = teamRepo;
    }


    @Override
    public Participant findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Participant> findAll() {
        return null;
    }

    @Override
    public Participant save(Participant entity) {
        log.traceEntry("Saving participant {}",entity);
        validator.validate(entity);

        Connection con = dbUtils.getConnection();
        if(teamRepo.findOne(entity.getTeam()) == null)
        {
            Team team = new Team(1L,entity.getTeam());
            teamRepo.save(team);
        }
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Participants(firstName,lastName,engine,id_team,id_race) values (?,?,?,?,?)")){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setFloat(3,entity.getEngineSize());
            preparedStatement.setLong(4,teamRepo.findOne(entity.getTeam()).getId());
            preparedStatement.setLong(5,entity.getRace().getId());
            int result = preparedStatement.executeUpdate();
            log.trace("Saved {} instances",result);
            return null;
        } catch (SQLException e) {
            log.error(e);
            System.out.println("Error DB: " + e);
            return entity;
        }
        finally {
            try {
                log.traceExit();
                con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<Participant> filterByTeam(String teamName){
        List<Participant> filtered = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        long idTeam = teamRepo.findOne(teamName).getId();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Participants WHERE id_team LIKE ?")){
            preparedStatement.setLong(1,idTeam);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    Long id = rs.getLong("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    float enginecapacity = rs.getFloat("engine");
                    Long raceId = rs.getLong("id_race");
                    Race race = raceRepo.findOne(raceId);


                    filtered.add(new Participant(id,firstName,lastName, teamName, enginecapacity,race));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error DB: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return filtered;
    }

    @Override
    public Participant delete(Long aLong) throws IOException {
        return null;
    }

    @Override
    public Participant update(Participant entity) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }
}
