import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import server.ServicesImpl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartServer {

    // constante
    private static final int SERVER_LIFE_MINUTES = 2;
    private static final int VALIDATE_INTERVAL_SECONDS = 2;

    // pt. sincronizare - main thread opreste timeoutExecutor numai dupa ce timeoutExecutor finalizeaza task-ul
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springServer.xml");
        //ApplicationContext factoryJavaConfig = new AnnotationConfigApplicationContext(StartServer.class);
        System.out.println("Server - Spring Container loaded...");

        // preia o instanta Services
        ServicesImpl services = (ServicesImpl) factory.getBean("services");
        services.resetSpectacole();
        services.startValidationThread(VALIDATE_INTERVAL_SECONDS);

        // planifica un task sa inchida server-ul dupa o durata specificata
        ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
        timeoutExecutor.schedule(() -> {
            services.closeExecutors();
            ((ConfigurableApplicationContext) factory).close();
            System.out.println(SERVER_LIFE_MINUTES + " minutes have passed");
            latch.countDown();
        }, SERVER_LIFE_MINUTES, TimeUnit.MINUTES);

        // inchide executorul pentru task-ul de timeout
        try {
            latch.await();
            timeoutExecutor.shutdown();
            timeoutExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Server - Terminated.");
    }
}
