package coordinators;

import controllers.LoginController;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import services.MotorcycleService;
import services.MotorcycleServiceGrpcProxy;

import java.io.IOException;
import java.util.Properties;

public class GuiCoordinator extends Application {
    private MotorcycleService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        service = getService();
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

    static MotorcycleService getService() throws IOException {
        Resource resource = new ClassPathResource("client.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(props.getProperty("server.channel"))
                .usePlaintext()
                .build();
        return new MotorcycleServiceGrpcProxy(channel);
    }
}
