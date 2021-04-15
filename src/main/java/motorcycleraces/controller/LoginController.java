package motorcycleraces.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import motorcycleraces.domain.User;
import motorcycleraces.service.Service;

import java.io.IOException;

public class LoginController {
    private Service service;

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;
    @FXML
    private void initialize() {
    }
    public void setPage(Stage stage, Service service) {
        this.dialogStage = stage;
        this.service = service;
    }



    public void goToPageProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mainView.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Pagina Utilizatorului");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setWidth(650);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            MainController mainController = loader.getController();
            mainController.setPage(stage, user, service);
            dialogStage.close();
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleLogin(){
        String username;
        String password;
        username = textFieldUsername.getText();
        password = passwordField.getText();

        if(username != null && password != null){
            User user = service.searchUser(username,password);
            if(user == null){
                MessageAlert.showErrorMessage(null,"Invalid login credentials");
            }
            else{
                this.goToPageProfile(user);
            }
        }
    }
}
