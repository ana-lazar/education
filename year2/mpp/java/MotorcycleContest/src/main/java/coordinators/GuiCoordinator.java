package coordinators;

import config.MotorcycleContestAutowireConfig;
import config.MotorcycleContestJavaConfig;
import controllers.LoginController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

import myjdbc.MyJdbcTemplate;

import services.MotorcycleContestService;

import java.io.IOException;

public class GuiCoordinator extends Application {
    MotorcycleContestService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        service = getServiceXmlConfig();
        initView(primaryStage);
        primaryStage.setTitle("LOG IN");
        ApplicationContext context = new ClassPathXmlApplicationContext("App.xml");
        primaryStage.setOnCloseRequest(we -> context.getBean(MyJdbcTemplate.class).close());
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        Scene loginScene = new Scene(loginLayout);
        primaryStage.setScene(loginScene);
        LoginController controller = loginLoader.getController();
        controller.setService(service);
    }

    static MotorcycleContestService getServiceXmlConfig(){
        ApplicationContext context = new ClassPathXmlApplicationContext("App.xml");
        return context.getBean(MotorcycleContestService.class);
    }

    static MotorcycleContestService getServiceJavaConfig(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MotorcycleContestJavaConfig.class);
        return context.getBean(MotorcycleContestService.class);
    }

    static MotorcycleContestService getServiceAutowireConfig(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MotorcycleContestAutowireConfig.class);
        return context.getBean(MotorcycleContestService.class);
    }
}
