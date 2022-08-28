package coordinators;

import controllers.LoginController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

import services.MotorcycleService;

import java.io.IOException;

public class GuiCoordinator extends Application {
    private MotorcycleService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:App.xml");
        service = (MotorcycleService) factory.getBean("motorcycleService");
        initView(primaryStage);
        primaryStage.setTitle("LOG IN");
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        Scene loginScene = new Scene(loginLayout);
        primaryStage.setScene(loginScene);
        LoginController controller = loginLoader.getController();
        controller.setLoginStage(primaryStage);
        controller.setService(service);
    }
}
