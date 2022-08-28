package services;

import domain.dtos.ParticipantInfo;
import domain.dtos.RaceInfo;
import domain.entities.Participant;
import domain.entities.Race;
import domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.interfaces.ParticipantRepository;
import repositories.interfaces.RaceRepository;
import repositories.interfaces.TeamRepository;
import repositories.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MotorcycleContestService {
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;
    private final TeamRepository teamRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public MotorcycleContestService(UserRepository userRepository, RaceRepository raceRepository, TeamRepository teamRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.teamRepository = teamRepository;
        this.participantRepository = participantRepository;
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public List<RaceInfo> getRacesBetween(Integer capacity1, Integer capacity2) {
        List<RaceInfo> races = new ArrayList<>();
        for (Race race : raceRepository.findByCapacityInterval(capacity1, capacity2)) {
            List<Participant> participants = participantRepository.findByRace(race.getId());
            races.add(new RaceInfo(race, participants.size()));
        }
        return races;
    }

    public RaceInfo getRaceByCapacity(Integer capacity) {
        Integer raceId = raceRepository.findIdByCapacity(capacity);
        Optional<Race> race = raceRepository.findOne(raceId);
        List<Participant> participants = participantRepository.findByRace(raceId);
        return new RaceInfo(race.get(), participants.size());
    }

    public List<RaceInfo> getRaceInfos() {
        List<RaceInfo> races = new ArrayList<>();
        for (Race race : raceRepository.findAll()) {
            List<Participant> participants = participantRepository.findByRace(race.getId());
            races.add(new RaceInfo(race, participants.size()));
        }
        return races;
    }

    public Iterable<Race> getRaces() {
        return raceRepository.findAll();
    }

    public List<ParticipantInfo> getRaceParticipantsByTeam(String teamName) {
        List<ParticipantInfo> participants = new ArrayList<>();
        Integer teamId = teamRepository.findIdByName(teamName);
        if (teamId == 0) {
            return participants;
        }
        for (Participant participant : participantRepository.findByTeam(teamId)) {
            Optional<Race> race = raceRepository.findOne(participant.getRaceId());
            race.ifPresent(value -> participants.add(new ParticipantInfo(value, participant)));
        }
        return participants;
    }

    public void registerParticipant(String name, String teamName, Integer capacity) {
        Integer teamId = teamRepository.findIdByName(teamName);
        Integer raceId = raceRepository.findIdByCapacity(capacity);
        participantRepository.save(new Participant(name, raceId, teamId));
    }
}
