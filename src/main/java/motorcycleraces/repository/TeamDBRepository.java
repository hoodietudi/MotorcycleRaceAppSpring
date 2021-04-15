package motorcycleraces.repository;

import motorcycleraces.domain.Participant;
import motorcycleraces.domain.Team;

import java.io.IOException;

import motorcycleraces.domain.validators.ValidatorTeam;
import motorcycleraces.utils.DBUtils;
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


public class TeamDBRepository implements TeamIRepository {

    private Logger log = LogManager.getLogger(TeamDBRepository.class);
    private ValidatorTeam validator;
    private DBUtils dbUtils;

    public TeamDBRepository(Properties props, ValidatorTeam validator) {
        this.dbUtils = new DBUtils(props);
        this.validator = validator;
    }

    @Override
    public Team findOne(String teamName) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from teams where name = ?")) {
            preparedStatement.setString(1, teamName);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {

                    Long id = result.getLong("id");
                    String name = result.getString("name");

                    Team team = new Team(id,name);
                    return team;
                }
            }
        } catch (SQLException e) {
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
    public Team findOne(Long idTeam) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Teams where id = ?")) {
            preparedStatement.setLong(1, idTeam);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    String name = result.getString("name");
                    Team team = new Team(id, name);
                    return team;
                }
            }
        } catch (SQLException e) {
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
    public List<Team> findAll() {
        List<Team> all = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Teams")) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    Team team = new Team(id, name);
                    all.add(team);
                }
            }
        } catch (SQLException e) {
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

    /*public long getMaxId() {
        log.traceEntry("Getting max id {}");
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("select max(id) AS maxid from Participanti")) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("maxid");
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }*/



    @Override
    public Team save(Team entity) {
        log.traceEntry("Saving team {}", entity);
        validator.validate(entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Teams(name) values (?)")) {
            preparedStatement.setString(1, entity.getName());
            int result = preparedStatement.executeUpdate();
            log.trace("Saved {} instances", result);
            return null;
        } catch (SQLException e) {
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
    public Team delete(Long aLong) throws IOException {
        return null;
    }

    @Override
    public Team update(Team entity) {
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
