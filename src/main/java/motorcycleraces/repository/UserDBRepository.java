package motorcycleraces.repository;
import motorcycleraces.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class UserDBRepository implements UserIRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public UserDBRepository(Properties props) {
        logger.info("Initializing UserDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
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
    public Iterable<User> findAll() {
        return null;

    }
}