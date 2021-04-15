package motorcycleraces.repository;
import motorcycleraces.domain.User;
import motorcycleraces.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserDBRepository implements UserIRepository{

    private DBUtils dbUtils;



    private static final Logger log= LogManager.getLogger();

    public UserDBRepository(Properties props) {
        this.dbUtils = new DBUtils(props);
    }

    @Override
    public User searchForUser(String username, String password) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Users where username = ? and password = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try(ResultSet result = preparedStatement.executeQuery()){
                if(result.next()){
                    User user = new User(username, password);
                    return user;
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
    public User save(User elem) {
        //to do
        return elem;
    }

    @Override
    public User delete(Long aLong) throws IOException {
        return null;
    }

    @Override
    public User update(User entity) {
        log.traceEntry("Deleting User with ID {}",entity.getId());
        Connection connection = dbUtils.getConnection();
        //try(PreparedStatement prepSt = connection.prepareStatement("delete from User")){

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


    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;

    }
}