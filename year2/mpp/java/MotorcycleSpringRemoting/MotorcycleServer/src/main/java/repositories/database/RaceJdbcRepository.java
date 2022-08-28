package repositories.database;

import domain.Race;
import validators.Validator;
import myjdbc.MyJdbcException;
import repositories.interfaces.RaceRepository;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import java.util.List;

public class RaceJdbcRepository extends AbstractJdbcRepository<Integer, Race> implements RaceRepository {
    public RaceJdbcRepository(MyJdbcTemplate template, Validator<Race> validator) {
        super(template, validator);
    }

    public RaceJdbcRepository() {
    }

    @Override
    ResultSetExtractor<Race> getResultSetExtractor() {
        return (set) -> {
            Integer id = set.getInt("id");
            Integer capacity = set.getInt("capacity");
            Race race = new Race(capacity);
            race.setId(id);
            return race;
        };
    }

    @Override
    String selectByIdCommand(Integer id) {
        return String.format("SELECT id, capacity FROM races WHERE id = '%d';", id);
    }

    @Override
    String selectAllCommand() {
        return "SELECT id, capacity FROM races;";
    }

    @Override
    String insertCommand(Race race) {
        return String.format("INSERT INTO races (capacity) VALUES ('%s');",
                race.getCapacity()
        );
    }

    @Override
    String updateCommand(Race race) {
        return String.format("UPDATE races SET capacity = '%d' WHERE id = '%d';",
                race.getCapacity(),
                race.getId()
        );
    }

    @Override
    Integer toId(Integer id) {
        return id;
    }

    @Override
    String deleteByIdCommand(Integer id) {
        return String.format("DELETE FROM races WHERE id = '%d';", id);
    }

    @Override
    String getTableName() {
        return "races";
    }

    @Override
    public Integer findIdByCapacity(Integer capacity) {
        System.out.println("find race with capacity " + capacity);
        if (capacity == null) {
            System.out.println("find failed: capacity is null");
            throw new MyJdbcException("Find failed: capacity is null");
        }
        List<Race> races = template.query(
                String.format("SELECT id, capacity FROM races WHERE capacity = '%d';", capacity),
                getResultSetExtractor()
        );
        if (races.isEmpty()) {
            System.out.println("find failed: capacity {} not found" + capacity);
            return -1;
        }
        System.out.println("find successful " + races);
        return races.get(0).getId();
    }

    @Override
    public List<Race> findByCapacityInterval(Integer capacity1, Integer capacity2) {
        System.out.println("find race with capacity between {} and {} " + capacity1 + " " + capacity2);
        if (capacity1 == null || capacity2 == null) {
            System.out.println("find failed: capacities are null");
            throw new MyJdbcException("Find failed: capacities are null");
        }
        List<Race> races = template.query(
                String.format("SELECT id, capacity FROM races WHERE capacity >= '%d' AND capacity <= '%d' ORDER BY capacity;", capacity1, capacity2),
                getResultSetExtractor()
        );
        if (races.isEmpty()) {
            System.out.println("find failed: capacity between {} and {} not found" + capacity1 + " " + capacity2);
            return races;
        }
        System.out.println("find successful " + races);
        return races;
    }
}
