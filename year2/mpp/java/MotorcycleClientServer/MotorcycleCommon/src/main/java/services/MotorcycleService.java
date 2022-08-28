package services;

import dtos.RaceDto;
import dtos.ParticipantDto;
import domain.Race;
import domain.User;

import java.util.List;
import java.util.Optional;

public interface MotorcycleService  {
    Optional<User> authenticate(String username, String password) throws Exception;

    RaceDto getRaceByCapacity(Integer capacity) throws Exception;

    List<RaceDto> getRaceInfos() throws Exception;

    Iterable<Race> getRaces() throws Exception;

    List<ParticipantDto> getRaceParticipantsByTeam(String teamName) throws Exception;

    void registerParticipant(String name, String teamName, Integer capacity) throws Exception;

    void logOut(User user);

    void addMotorcycleObserver(MotorcycleObserver observer);

    void removeMotorcycleObserver(MotorcycleObserver observer);
}
