package motorcycleraces.repository;

import motorcycleraces.domain.User;
import motorcycleraces.repository.UserDBRepository;
import motorcycleraces.repository.UserIRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainDB {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        UserIRepository userRepo=new UserDBRepository(props);
        userRepo.save(new User("Tesla","Model S"));
        System.out.println("Toate masinile din db");



    }
}
