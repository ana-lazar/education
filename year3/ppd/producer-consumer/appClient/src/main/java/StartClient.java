import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.IServices;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartClient {

    // constante
    private static final int SECONDS_BETWEEN_REQ = 2; // secunde intre request-uri
    private static final int NR_SPECTACOLE = 3;
    private static final int NR_LOCURI = 100;
    private static final int MAX_BILETE_PER_REQ = 5;

    public static void main(String[] args) {

        // instantiza cate un Services Proxy pentru fiecare client
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:springClient.xml");
        IServices service = (IServices) factory.getBean("service");
        System.out.println("Client - Obtained reference to remote Server's Services");

        // porneste task-ul care face cereri repetate de vanzare
        Runnable taskCereriRepetate = () -> {
            boolean go = true;
            while (go) {
                try {
                    simulareCumparare(service);
                    Thread.sleep(SECONDS_BETWEEN_REQ * 1000);
                } catch (InterruptedException | RemoteException e) {
                    go = false;
                    System.out.println("Request Thread - Stopped. Reason: " + e.getMessage());
                }
            }
        };
        Thread requestThread = new Thread(taskCereriRepetate);
        requestThread.start();

        // asteapta terminarea thread-ului cu cereri
        try {
            requestThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Client - Terminated.");
    }

    public static void simulareCumparare(IServices service) throws RemoteException {
        // genereaza aleator biletele
        Random rand = new Random();
        long idSpectacol = rand.nextInt(NR_SPECTACOLE) + 1;
        int nrBilete = rand.nextInt(MAX_BILETE_PER_REQ) + 1;
        List<Integer> bileteDeCumparat = new ArrayList<>();
        while (nrBilete > 0) {
            Integer bilet = rand.nextInt(NR_LOCURI);
            if (!bileteDeCumparat.contains(bilet)) {
                bileteDeCumparat.add(bilet);
                nrBilete--;
            }
        }

        // apeleaza service-ul remote cu datele generate
        boolean vanzare_ok = service.cumpara(idSpectacol, bileteDeCumparat);
        String verdict = vanzare_ok ? "ACCEPTAT" : "RESPINS";
        System.out.println("Cerere bilete: ID_Spect " + idSpectacol + "; bilete " + bileteDeCumparat.toString() + "; " + verdict);
    }
}
