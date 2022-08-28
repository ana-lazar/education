package repositories.interfaces;

import domain.Participant;

import java.util.List;

public interface ParticipantRepository extends Repository<Integer, Participant> {
    List<Participant> findByTeam(Integer teamId);

    List<Participant> findByRace(Integer raceId);
}
