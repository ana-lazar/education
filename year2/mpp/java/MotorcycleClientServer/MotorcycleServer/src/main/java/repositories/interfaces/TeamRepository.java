package repositories.interfaces;

import domain.Team;

public interface TeamRepository extends Repository<Integer, Team> {
    Integer findIdByName(String name);
}
