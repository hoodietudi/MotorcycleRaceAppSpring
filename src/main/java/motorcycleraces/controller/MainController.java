package motorcycleraces.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import motorcycleraces.domain.*;
import motorcycleraces.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Stage dialogStage;
    private Service service;
    private User user;

    private ObservableList<Race> tab1 = FXCollections.observableArrayList();
    private ObservableList<Participant> tab2 = FXCollections.observableArrayList();
    ObservableList<String> observableListTeams = FXCollections.observableArrayList();


    @FXML
    private TableView<Race> tableViewRaces;
    @FXML
    private TableColumn<Race,String> tableColumnRaceName;
    @FXML
    private TableColumn<Race,String> tableColumnEngine;
    @FXML
    private TableColumn<Race,String> tableColumnLocation;
    @FXML
    private TableColumn<Race,String> tableColumnParticipants;

    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldEngineSize;
    @FXML
    private TextField textFieldNrCylinders;
    @FXML
    private TextField textFieldPlacement;
    @FXML
    private TextField textFieldTeam;


    @FXML
    private TableView<Participant> tableViewParticipant;
    @FXML
    private TableColumn<Participant,String> tableColumnFirstName;
    @FXML
    private TableColumn<Participant,String> tableColumnLastName;
    @FXML
    private TableColumn<Participant, String> tableColumnEngineSize;
    @FXML
    private ComboBox<String> comboBoxTeam;




    public void setPage(Stage stage,User user,Service service){
        this.user = user;
        this.dialogStage = stage;
        this.service = service;
        initModel();
    }



    @FXML
    public void initialize() {
        tableColumnRaceName.setCellValueFactory(new PropertyValueFactory<Race, String>("Name"));
        tableColumnEngine.setCellValueFactory(new PropertyValueFactory<Race, String>("MaxEngineSize"));
        tableColumnLocation.setCellValueFactory(new PropertyValueFactory<Race, String>("Location"));
        tableColumnParticipants.setCellValueFactory(new PropertyValueFactory<Race, String>("NrParticipants"));

        tableViewRaces.setItems(tab1);


        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Participant,String>("FirstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Participant,String>("LastName"));
        tableColumnEngineSize.setCellValueFactory(new PropertyValueFactory<Participant, String>("EngineSize"));

        tableViewParticipant.setItems(tab2);


        comboBoxTeam.setItems(observableListTeams);
    }



    private void initModel() {
        List<Race> races = service.findAllRaces();
        tab1.setAll(races);
        tableViewRaces.setRowFactory(x -> new TableRow<Race>(){
            public void updateItem(Race item,boolean empty){

                super.updateItem(item,empty);

                if(item == null){
                    setStyle("");
                }

                else{
                    setStyle("");
                }
            }
        });
        System.out.println(service.findAllTeams());
        System.out.println(service.findAllRaces());
        List<String> teams = new ArrayList<>();
        for(Team team : service.findAllTeams()){
            teams.add(team.getName());
        }
        observableListTeams.setAll(teams);


    }

    public void handleRegister() {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String teamName = textFieldTeam.getText();
        float engineCapacity = Float.parseFloat(textFieldEngineSize.getText());


        if (!firstName.equals("") && !lastName.equals("") && engineCapacity > 0) {
            Race selectedRace = tableViewRaces.getSelectionModel().getSelectedItem();
            if (selectedRace != null) {
                try {
                    service.addParticipant(firstName, lastName, engineCapacity, teamName,selectedRace);
                    initModel();
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Register was succesful", "Thank you for registering to the race");
                } catch (Exception ex) {
                    MessageAlert.showErrorMessage(null, ex.getMessage());
                }
            } else {
                MessageAlert.showErrorMessage(null, "Please select a race!");
            }
        } else {
            MessageAlert.showErrorMessage(null, "Please complete all the fields!");
        }
    }


    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/loginView.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setWidth(600);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            LoginController loginController = loader.getController();
            loginController.setPage(stage, service);
            dialogStage.close();
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleSearch(){
        String selectedTeam = comboBoxTeam.getValue();
        List<Participant> filtered = service.findParticipants(selectedTeam);
        tab2.setAll(filtered);
    }

}
