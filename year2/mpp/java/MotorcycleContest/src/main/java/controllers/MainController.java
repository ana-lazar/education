package controllers;

import domain.dtos.ParticipantInfo;
import domain.dtos.RaceInfo;

import domain.entities.Race;
import domain.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import services.MotorcycleContestService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import utils.MessageAlert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private MotorcycleContestService service;
    private Stage mainStage;

    private final ObservableList<RaceInfo> racesModel = FXCollections.observableArrayList();
    private final ObservableList<ParticipantInfo> participantsModel = FXCollections.observableArrayList();

    @FXML
    Label welcomeLabel;
    @FXML
    ListView<RaceInfo> racesListView;
    @FXML
    ListView<ParticipantInfo> participantsListView;
    @FXML
    ComboBox<String> racesComboBox;

    public void setService(MotorcycleContestService service) {
        this.service = service;
        initCapacityComboBox();
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setUser(User user) {
        welcomeLabel.setText("WELCOME, " + user.getName());
    }

    @FXML
    public void initialize() {
        racesListView.setItems(racesModel);
        participantsListView.setItems(participantsModel);
        racesListView.setStyle("-fx-border-color: black;");
        participantsListView.setStyle("-fx-border-color: black;");
        racesComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    initRacesList();
            }
        );
    }

    private void initCapacityComboBox() {
        racesComboBox.getItems().add("ALL");
        try {
            for (Race race : service.getRaces()) {
                racesComboBox.getItems().add(race.getCapacity().toString());
            }
            racesComboBox.getSelectionModel().select(0);
        } catch (Exception exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }

    private void initRacesList() {
        try {
            List<RaceInfo> races;
            if (racesComboBox.getValue().equals("ALL")) {
                races = service.getRaceInfos();
            }
            else {
                races = new ArrayList<>();
                races.add(service.getRaceByCapacity(Integer.valueOf(racesComboBox.getValue())));
            }
            racesModel.setAll(races);
            if (races.isEmpty()) {
                racesListView.setStyle("-fx-border-color: red;");
            } else {
                racesListView.setStyle("-fx-border-color: black;");
            }
        } catch (Exception exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }

    @FXML
    public void update(Event event) {
        initRacesList();
        initParticipantsList();
    }

    @FXML
    TextField teamTextField;

    private void initParticipantsList() {
        try {
            List<ParticipantInfo> participants = service.getRaceParticipantsByTeam(teamTextField.getText());
            participantsModel.setAll(participants);
            if (participants.isEmpty()) {
                participantsListView.setStyle("-fx-border-color: red;");
            } else {
                participantsListView.setStyle("-fx-border-color: black;");
            }
        } catch (Exception exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }

    @FXML
    public void handleSearchButton(Event event) {
        if (teamTextField.getText().equals("")) {
            MessageAlert.showErrorMessage(null, "Make sure the text field is filled.");
            return;
        }
        initParticipantsList();
    }

    @FXML
    public void handleComboBox(Event event) {
        initRacesList();
    }

    @FXML
    public void handleRegisterButton(Event event) {
        try {
            FXMLLoader registerLoader = new FXMLLoader();
            registerLoader.setLocation(getClass().getResource("/views/registerView.fxml"));
            AnchorPane registerLayout = registerLoader.load();
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.initModality(Modality.WINDOW_MODAL);
            Scene registerScene = new Scene(registerLayout);
            registerStage.setScene(registerScene);
            RegisterController registerController = registerLoader.getController();
            registerController.setService(service);
            registerController.setStage(registerStage);
            Platform.runLater(registerStage::show);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleLogOut(Event event) {
        mainStage.close();
    }
}
