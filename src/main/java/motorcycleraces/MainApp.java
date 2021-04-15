package motorcycleraces;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import motorcycleraces.controller.LoginController;
import motorcycleraces.domain.validators.ValidatorParticipant;
import motorcycleraces.domain.validators.ValidatorRace;
import motorcycleraces.domain.validators.ValidatorTeam;
import motorcycleraces.repository.ParticipantDBRepository;
import motorcycleraces.repository.RaceDBRepository;
import motorcycleraces.repository.TeamDBRepository;
import motorcycleraces.repository.UserDBRepository;
import motorcycleraces.service.Service;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    Service service;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        ValidatorParticipant validatorParticipant = new ValidatorParticipant();
        ValidatorTeam validatorTeam = new ValidatorTeam();
        ValidatorRace validatorRace = new ValidatorRace();
        TeamDBRepository repoTeam = new TeamDBRepository(props,validatorTeam);
        RaceDBRepository repoRace = new RaceDBRepository(props,validatorRace);
        ParticipantDBRepository repoParticipant = new ParticipantDBRepository(props,validatorParticipant,repoRace,repoTeam);
        UserDBRepository repoUser = new UserDBRepository(props);


        service = new Service(repoUser,repoTeam,repoParticipant,repoRace);

        initView(primaryStage);
        primaryStage.setWidth(600);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }
    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane layout = loginLoader.load();
        primaryStage.setScene(new Scene(layout));
        LoginController loginController = loginLoader.getController();
        loginController.setPage(primaryStage,service);
    }
}
