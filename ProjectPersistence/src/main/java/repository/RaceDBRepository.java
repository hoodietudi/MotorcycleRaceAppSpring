package repository;

import domain.Race;
import domain.validators.ValidatorRace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RaceDBRepository implements RaceIRepository{

    public static final Logger log = LogManager.getLogger();
    private DBUtils dbUtils;
    private ValidatorRace validator;
    public RaceDBRepository(Properties props,ValidatorRace validator){
        this.dbUtils = new DBUtils(props);
        log.info("Initializing RaceDBRepository with props: {}",props);
        this.validator = validator;
    }


    @Override
    public Race findOne(Long idRace) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Races WHERE id = ?")){
            preparedStatement.setLong(1,idRace);
            try(ResultSet result = preparedStatement.executeQuery()){
                if(result.next()){
                    Long id = result.getLong("id");
                    String name = result.getString("name");
                    int nrParticipants = result.getInt("nrparticipants");
                    float maxEngineSize = result.getFloat("maxenginesize");
                    String location = result.getString("location");
                    return new Race(id,location,name,maxEngineSize,nrParticipants);
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
        return null;
    }

    @Override
    public List<Race> findAll() {
        List<Race> all = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Races")){
            try (ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    Long id = rs.getLong("id");
                    String nume = rs.getString("name");
                    float maxEngineSize = rs.getFloat("maxenginesize");
                    int nrParticipants = rs.getInt("nrparticipants");
                    String location = rs.getString("location");
                    all.add(new Race(id,location,nume,maxEngineSize,nrParticipants));
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
        return all;
    }

    @Override
    public Race save(Race entity) {
        return null;
    }

    @Override
    public Race delete(Long aLong) throws IOException {
        return null;
    }

    @Override
    public Race update(Race entity) {
        log.traceEntry("Updating race {}",entity);
        validator.validate(entity);

        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("UPDATE Races SET id = ?, name = ?, nrparticipants = ? WHERE id = ?")){
            preparedStatement.setLong(1,entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3,entity.getNrParticipants());
            preparedStatement.setLong(4,entity.getId());
            int result = preparedStatement.executeUpdate();
            log.trace("Updated {} instances",result);
            return null;
        }
        catch (SQLException e){
            log.error(e);
            System.out.println("Error DB: " + e);
            return entity;
        } finally {
            try {
                log.traceExit();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
