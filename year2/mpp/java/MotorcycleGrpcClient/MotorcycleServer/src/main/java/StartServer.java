import networking.GrpcServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
    private static final Logger LOGGER = LogManager.getLogger(StartServer.class.getName());

    public static void main(String[] args) throws InterruptedException {
        GrpcServer server = null;
        try {
            LOGGER.info("creating server");
            server = getServerXmlConfig();
            server.start();
            server.blockUntilShutdown();
        } catch (Exception e) {
            LOGGER.warn("creating application context failed " + e);
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }

    static GrpcServer getServerXmlConfig() {
        LOGGER.info("creating application context");
        ApplicationContext context = new ClassPathXmlApplicationContext("App.xml");
        return context.getBean(GrpcServer.class);
    }
}
