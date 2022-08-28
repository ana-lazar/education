package rest.contest.repositories;

import rest.contest.domain.Race;
import rest.contest.myjdbc.ResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rest.contest.myjdbc.MyJdbcException;
import rest.contest.myjdbc.MyJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class RaceJdbcRepository implements RaceRepository {
    private MyJdbcTemplate template;

    @Autowired
    public RaceJdbcRepository(MyJdbcTemplate template) {
        this.template = template;
    }

    public RaceJdbcRepository() {
    }

    ResultSetExtractor<Race> getResultSetExtractor() {
        return (set) -> {
            Integer id = set.getInt("id");
            Integer capacity = set.getInt("capacity");
            Race race = new Race(capacity);
            race.setId(id);
            return race;
        };
    }

    String selectByIdCommand(Integer id) {
        return String.format("SELECT id, capacity FROM races WHERE id = '%d';", id);
    }

    String selectAllCommand() {
        return "SELECT id, capacity FROM races;";
    }

    String insertCommand(Race race) {
        return String.format("INSERT INTO races (capacity) VALUES ('%s');",
                race.getCapacity()
        );
    }

    String updateCommand(Race race) {
        return String.format("UPDATE races SET capacity = '%d' WHERE id = '%d';",
                race.getCapacity(),
                race.getId()
        );
    }

    Integer toId(Integer id) {
        return id;
    }

    String deleteByIdCommand(Integer id) {
        return String.format("DELETE FROM races WHERE id = '%d';", id);
    }

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

    @Override
    public Optional<Race> findOne(Integer id) {
        if (id == null) {
            throw new MyJdbcException("Find failed: id is null");
        }
        List<Race> entities = template.query(
                selectByIdCommand(id),
                getResultSetExtractor()
        );
        if (entities.isEmpty()) {
            throw new MyJdbcException("Find failed: id not found");
        }
        return Optional.of(entities.get(0));
    }

    @Override
    public Iterable<Race> findAll() {
        return template.query(
                selectAllCommand(),
                getResultSetExtractor()
        );
    }

    @Override
    public Race save(Race race) {
        if (race == null) {
            throw new IllegalArgumentException("Save failed: race must be not null");
        }
        if (race.getId() == null) {
            int id = template.insert(insertCommand(race));
            race.setId(toId(id));
            return race;
        }
        int lines = template.update(updateCommand(race));
        if (lines == 0) {
            throw new MyJdbcException("Update failed: race not found");
        }
        return race;
    }

    @Override
    public Race remove(Integer id) {
        if (id == null) {
            throw new MyJdbcException("Remove failed: id is null");
        }
        Optional<Race> race = findOne(id);
        if (race.isEmpty()){
            throw new MyJdbcException("Remove failed: id not found");
        }
        int lines = template.update(deleteByIdCommand(id));
        if (lines == 0) {
            throw new MyJdbcException("Removal failed: race with id not found");
        }
        return race.get();
    }

    @Override
    public Integer size() {
        int size = template.query("SELECT COUNT(*) FROM " + getTableName(), (set) -> set.getInt(1)).get(0);
        return size;
    }
}
