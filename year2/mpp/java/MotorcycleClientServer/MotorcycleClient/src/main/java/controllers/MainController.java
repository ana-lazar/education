package controllers;

import domain.Participant;
import services.MotorcycleObserver;
import utils.MessageAlert;
import dtos.ParticipantDto;
import dtos.RaceDto;
import domain.Race;
import domain.User;
import services.MotorcycleService;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController implements MotorcycleObserver {
    private MotorcycleService service;
    private User user;
    private Stage mainStage;
    private Stage loginStage;

    private final ObservableList<RaceDto> racesModel = FXCollections.observableArrayList();
    private final ObservableList<ParticipantDto> participantsModel = FXCollections.observableArrayList();

    @FXML
    Label welcomeLabel;
    @FXML
    ListView<RaceDto> racesListView;
    @FXML
    ListView<ParticipantDto> participantsListView;
    @FXML
    ComboBox<String> racesComboBox;

    public void setService(MotorcycleService service) {
        this.service = service;
        service.addMotorcycleObserver(this);
        initCapacityComboBox();
    }

    public void setStage(Stage mainStage, Stage loginStage) {
        this.mainStage = mainStage;
        this.loginStage = loginStage;
    }

    public void setUser(User user) {
        this.user = user;
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
            List<RaceDto> races;
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
            List<ParticipantDto> participants = service.getRaceParticipantsByTeam(teamTextField.getText());
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
        service.removeMotorcycleObserver(this);
        service.logOut(this.user);
        loginStage.show();
    }

    @Override
    public void registeredParticipant(Participant participant) {
        Platform.runLater(() -> {
            updateRacesList(participant);
            updateParticipantsList(participant);
        });
    }

    private void updateParticipantsList(Participant participant) {
        if (participantsModel.size() != 0 && participant.getTeamId().equals(participantsModel.get(0).getParticipant().getTeamId())) {
            Race race = new Race();
            race.setId(participant.getRaceId());
            participantsModel.add(new ParticipantDto(race, participant));
        }
    }

    private void updateRacesList(Participant participant) {
        for (int i = 0; i < racesModel.size(); i++) {
            RaceDto race = racesModel.get(i);
            if (race.getRace().getId().equals(participant.getRaceId())) {
                race.setParticipantCount(race.getParticipantCount() + 1);
                racesModel.set(i, race);
                break;
            }
        }
    }
}
