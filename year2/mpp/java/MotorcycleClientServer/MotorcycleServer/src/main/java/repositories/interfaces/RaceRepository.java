package repositories.interfaces;

import domain.Race;

import java.util.List;

public interface RaceRepository extends Repository<Integer, Race> {
    Integer findIdByCapacity(Integer capacity);

    List<Race> findByCapacityInterval(Integer capacity1, Integer capacity2);
}
