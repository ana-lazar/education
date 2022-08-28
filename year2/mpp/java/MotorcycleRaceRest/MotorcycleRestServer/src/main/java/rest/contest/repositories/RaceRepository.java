package rest.contest.repositories;

import rest.contest.domain.Race;

import java.util.List;
import java.util.Optional;

public interface RaceRepository {
    Optional<Race> findOne(Integer id);

    Iterable<Race> findAll();

    Race save(Race entity);

    Race remove(Integer id);

    Integer size();

    Integer findIdByCapacity(Integer capacity);

    List<Race> findByCapacityInterval(Integer capacity1, Integer capacity2);
}
