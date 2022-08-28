package controllers;

import utils.MessageAlert;
import domain.Race;
import services.MotorcycleService;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegisterController {
    private MotorcycleService service;
    private Stage mainStage;

    public void setService(MotorcycleService service) {
        this.service = service;
        initComboBox();
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    ComboBox<Integer> capacityComboBox;

    @FXML
    public void initialize() {

    }

    private void initComboBox() {
        try {
            for (Race race : service.getRaces()) {
                capacityComboBox.getItems().add(race.getCapacity());
            }
            capacityComboBox.getSelectionModel().select(0);
        } catch (Exception exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }

    public void update(Event event) {
        initComboBox();
    }

    @FXML
    public void handleCancelButton(Event event) {
        mainStage.close();
    }

    @FXML
    TextField nameRegisterField;
    @FXML
    TextField teamRegisterField;

    @FXML
    public void handleAddButton(Event event) {
        if (nameRegisterField.getText().equals("")) {
            MessageAlert.showErrorMessage(null, "Make sure all the fields are filled.");
            return;
        }
        try {
            service.registerParticipant(nameRegisterField.getText(), teamRegisterField.getText(), capacityComboBox.getValue());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Participant", "Participant successfully added.");
            nameRegisterField.clear();
            teamRegisterField.clear();
        } catch (Exception exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }
}
