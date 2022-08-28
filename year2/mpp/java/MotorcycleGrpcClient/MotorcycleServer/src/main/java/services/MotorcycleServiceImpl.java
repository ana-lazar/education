package services;

import dtos.ParticipantDto;
import dtos.RaceDto;
import domain.Participant;
import domain.Race;
import domain.User;
import repositories.interfaces.ParticipantRepository;
import repositories.interfaces.RaceRepository;
import repositories.interfaces.TeamRepository;
import repositories.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MotorcycleServiceImpl implements MotorcycleService {
    private UserRepository userRepository;
    private RaceRepository raceRepository;
    private TeamRepository teamRepository;
    private ParticipantRepository participantRepository;

    private List<MotorcycleObserver> observers = new ArrayList<>();

    public MotorcycleServiceImpl(UserRepository userRepository, RaceRepository raceRepository, TeamRepository teamRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.teamRepository = teamRepository;
        this.participantRepository = participantRepository;
    }

    public MotorcycleServiceImpl() {
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setRaceRepository(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setParticipantRepository(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public synchronized Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public synchronized RaceDto getRaceByCapacity(Integer capacity) {
        Integer raceId = raceRepository.findIdByCapacity(capacity);
        Optional<Race> race = raceRepository.findOne(raceId);
        List<Participant> participants = participantRepository.findByRace(raceId);
        return new RaceDto(race.get(), participants.size());
    }

    public synchronized List<RaceDto> getRaceInfos() {
        List<RaceDto> races = new ArrayList<>();
        for (Race race : raceRepository.findAll()) {
            List<Participant> participants = participantRepository.findByRace(race.getId());
            races.add(new RaceDto(race, participants.size()));
        }
        return races;
    }

    public synchronized Iterable<Race> getRaces() {
        return raceRepository.findAll();
    }

    public synchronized List<ParticipantDto> getRaceParticipantsByTeam(String teamName) {
        List<ParticipantDto> participants = new ArrayList<>();
        Integer teamId = teamRepository.findIdByName(teamName);
        if (teamId == 0) {
            return participants;
        }
        for (Participant participant : participantRepository.findByTeam(teamId)) {
            Optional<Race> race = raceRepository.findOne(participant.getRaceId());
            race.ifPresent(value -> participants.add(new ParticipantDto(value, participant)));
        }
        return participants;
    }

    public synchronized void registerParticipant(String name, String teamName, Integer capacity) {
        Integer teamId = teamRepository.findIdByName(teamName);
        Integer raceId = raceRepository.findIdByCapacity(capacity);
        Participant participant = new Participant(name, raceId, teamId);
        participantRepository.save(participant);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(() -> {
            observers.forEach(obs -> {
                try {
                    obs.registeredParticipant(participant);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        });
        executor.shutdown();
    }

    @Override
    public void logOut(User user) {
        // notify friends
    }

    @Override
    public void addMotorcycleObserver(MotorcycleObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeMotorcycleObserver(MotorcycleObserver observer) {
        this.observers.remove(observer);
    }
}
