package controllers;

import com.google.common.hash.Hashing;

import domain.entities.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import services.MotorcycleContestService;

import utils.MessageAlert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {
    MotorcycleContestService service;

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    @FXML
    public void initialize() {
        usernameField.setPromptText("username");
        passwordField.setPromptText("password");
    }

    public void setService(MotorcycleContestService service) {
        this.service = service;
    }

    private void showMainWindow(User user) {
        try {
            FXMLLoader mainLoader = new FXMLLoader();
            mainLoader.setLocation(getClass().getResource("/views/mainView.fxml"));
            AnchorPane mainLayout = mainLoader.load();
            Stage mainStage = new Stage();
            mainStage.setTitle("Welcome!");
            mainStage.initModality(Modality.WINDOW_MODAL);
            Scene mainScene = new Scene(mainLayout);
            mainStage.setScene(mainScene);
            MainController mainController = mainLoader.getController();
            mainController.setService(service);
            mainController.setStage(mainStage);
            mainController.setUser(user);
            mainStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleLogIn(ActionEvent event) throws SQLException {
        String password = Hashing.sha256()
                .hashString(passwordField.getText(), StandardCharsets.UTF_8)
                .toString();
        Optional<User> user = service.authenticate(usernameField.getText(), password);
        if (user.isEmpty()) {
            usernameField.clear();
            passwordField.clear();
            usernameField.setPromptText("username");
            passwordField.setPromptText("password");
            MessageAlert.showErrorMessage(null, "Username or password wrong.");
        }
        else {
            usernameField.clear();
            passwordField.clear();
            Platform.runLater(() -> showMainWindow(user.get()));
        }
    }
}
