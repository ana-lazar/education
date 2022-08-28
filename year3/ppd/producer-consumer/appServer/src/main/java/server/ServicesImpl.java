package server;

import model.Spectacol;
import model.Vanzare;
import model.Verificare;
import persistence.repository.SpectacolRepo;
import persistence.repository.VanzareRepo;
import persistence.repository.VerificareRepo;
import service.IServices;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ServicesImpl implements IServices {

    // constante
    private final int LOCURI_SALA = 100;
    private final int NUM_THREADS = 10;

    // repos
    private SpectacolRepo spectacolRepo;
    private VanzareRepo vanzareRepo;
    private VerificareRepo verificareRepo;

    // thread-pool
    private ExecutorService executorTasks = Executors.newFixedThreadPool(NUM_THREADS);
    private ScheduledExecutorService executorValidare = Executors.newSingleThreadScheduledExecutor();

    // sincronizare operatii pe repository
    private final ReentrantLock repositoryLock = new ReentrantLock();

    public ServicesImpl(SpectacolRepo spectacolRepo, VanzareRepo vanzareRepo, VerificareRepo verificareRepo) {
        this.spectacolRepo = spectacolRepo;
        this.vanzareRepo = vanzareRepo;
        this.verificareRepo = verificareRepo;
    }

    public void resetSpectacole() {
        spectacolRepo.findAll()
                .forEach(spectacol -> {
                    spectacol.setLocuriRamase(LOCURI_SALA);
                    spectacol.setTotal(0.0d);
                    spectacol.setLocuriVandute(new ArrayList<>());
                    spectacolRepo.save(spectacol);
                });
        vanzareRepo.deleteAll();
    }

    public void startValidationThread(int validate_seconds) {
        executorValidare.scheduleAtFixedRate(this::validate, 0, validate_seconds, TimeUnit.SECONDS);
    }

    public void validate() {
        for (Spectacol spectacol : spectacolRepo.findAll()) {
            boolean corect = true;
            // verificarea corespondentei corecte intre locurile libere si vanzarile facute
            List<Integer> locuriVandute = spectacol.getLocuriVandute();
            //???
            long locuriDistincte = locuriVandute.stream().distinct().count();
            if (locuriDistincte != locuriVandute.size())
                corect = false;

            //verificare intre sumele incasate per vanzare si soldul total
            Double soldTotal = locuriVandute.size() * spectacol.getPretBilet();
            if (!soldTotal.equals(spectacol.getTotal())) {
                corect = false;
            }
            Verificare verificare = new Verificare(spectacol, corect);
            verificareRepo.save(verificare);
        }
    }

    @Override
    public boolean cumpara(Long idSpectacol, List<Integer> listaBilete) {
        Future<Boolean> taskResult = executorTasks.submit(() -> {
            try {
                // blocheaza repo-ul
                repositoryLock.lock();

                // verifica daca TOATE biletele cerute sunt disponibile
                Optional<Spectacol> spectacolDb = spectacolRepo.findById(idSpectacol);
                if (spectacolDb.isEmpty()) {
                    System.out.println("spectacol cu id " + idSpectacol + " nu exista!");
                    return false;
                }
                Spectacol spectacol = spectacolDb.get();
                List<Integer> bileteCumparate = spectacol.getLocuriVandute();

                // daca CEL PUTIN 1 LOC din cele cerute a fost vandut deja, respinge cererea
                if (bileteCumparate.stream().anyMatch(listaBilete::contains)) {
                    System.out.println("bilet deja vandut!");
                    return false;
                } else {
                    // adauga vanzare
                    Vanzare vanzare = new Vanzare(LocalDate.now());
                    vanzare.setLocuriVandute(listaBilete);
                    vanzareRepo.save(vanzare);
                    // actualizeaza spectacol (sold, locuri vandute)
                    bileteCumparate.addAll(listaBilete);
                    Double total = spectacol.getPretBilet() * listaBilete.size();
                    Double newTotal = spectacol.getTotal() + total;
                    spectacol.setTotal(newTotal);
                    spectacol.setLocuriVandute(bileteCumparate);
                    spectacol.addVanzare(vanzare);
                    spectacolRepo.save(spectacol);
                    return true;
                }
            } finally {
                // elibereaza repo-ul
                repositoryLock.unlock();
            }
        });

        try {
            // blocheaza pana se primeste valoarea din future
            return taskResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeExecutors() {
        executorTasks.shutdown();
        executorValidare.shutdown();

        try {
            executorTasks.awaitTermination(2, TimeUnit.SECONDS);
            executorValidare.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
