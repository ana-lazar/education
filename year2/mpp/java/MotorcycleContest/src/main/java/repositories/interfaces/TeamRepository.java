package repositories.interfaces;

import domain.entities.Team;

public interface TeamRepository extends Repository<Integer, Team> {
    Integer findIdByName(String name);
}
