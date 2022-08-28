import domain.Test;
import domain.User;
import myjdbc.MyJdbcTemplate;
import networking.MotorcycleClientRpcWorker;
import networking.RpcConcurrentServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.database.ParticipantJdbcRepository;
import repositories.database.RaceJdbcRepository;
import repositories.database.TeamJdbcRepository;
import repositories.database.UserJdbcRepository;
import repositories.hibernate.MotorcycleSessionFactory;
import services.MotorcycleService;
import services.MotorcycleServiceImpl;
import validators.ParticipantValidator;
import validators.RaceValidator;
import validators.TeamValidator;
import validators.UserValidator;

public class StartServer {
    private static final Logger LOGGER = LogManager.getLogger(MotorcycleClientRpcWorker.class.getName());

    public static void main(String[] args) {
        RpcConcurrentServer server = null;
        try {
            LOGGER.info("creating server");
            server = getServerXmlConfig();
            server.start();
        } catch (Exception e) {
            LOGGER.warn("creating application context failed " + e);
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }

    static RpcConcurrentServer getServer() {
        LOGGER.info("creating application context");
        MyJdbcTemplate template = new MyJdbcTemplate("/Users/analazar/Intellij/motorcycle-client-server/MotorcycleServer/src/main/resources/bd.config");
        MotorcycleService service = new MotorcycleServiceImpl(
                new UserJdbcRepository(template, new UserValidator()),
                new RaceJdbcRepository(template, new RaceValidator()),
                new TeamJdbcRepository(template, new TeamValidator()),
                new ParticipantJdbcRepository(template, new ParticipantValidator())
        );
        return new RpcConcurrentServer(55555, service);
    }

    static RpcConcurrentServer getServerXmlConfig() {
        LOGGER.info("creating application context");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        return context.getBean(RpcConcurrentServer.class);
    }
}
