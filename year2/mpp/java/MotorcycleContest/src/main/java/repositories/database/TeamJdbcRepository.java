package repositories.database;

import domain.entities.Team;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.interfaces.TeamRepository;

import myjdbc.MyJdbcException;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import java.util.List;

@Component
public class TeamJdbcRepository extends AbstractJdbcRepository<Integer, Team> implements TeamRepository {
    @Autowired
    public TeamJdbcRepository(MyJdbcTemplate template, Validator<Team> validator) {
        super(template, validator);
    }

    @Override
    ResultSetExtractor<Team> getResultSetExtractor() {
        return (set) -> {
            Integer id = set.getInt("id");
            String name = set.getString("name");
            Team team = new Team(name);
            team.setId(id);
            return team;
        };
    }

    @Override
    String selectByIdCommand(Integer id) {
        return String.format("SELECT id, name FROM teams WHERE id = '%d';", id);
    }

    @Override
    String selectAllCommand() {
        return "SELECT id, name FROM teams;";
    }

    @Override
    String insertCommand(Team team) {
        return String.format("INSERT INTO teams (name) VALUES ('%s');",
                team.getName()
        );
    }

    @Override
    String updateCommand(Team team) {
        return String.format("UPDATE teams SET name = '%s' WHERE id = '%d';",
                team.getName(),
                team.getId()
        );
    }

    @Override
    Integer toId(Integer id) {
        return id;
    }

    @Override
    String deleteByIdCommand(Integer id) {
        return String.format("DELETE FROM teams WHERE id = '%d';", id);
    }

    @Override
    String getTableName() {
        return "teams";
    }

    @Override
    public Integer findIdByName(String name) {
        LOGGER.traceEntry("find team with name {} ", name);
        if (name == null) {
            LOGGER.warn("find failed: name is null");
            throw new MyJdbcException("Find failed: name is null");
        }
        List<Team> teams = template.query(
                String.format("SELECT id, name FROM teams WHERE name = '%s';", name),
                getResultSetExtractor()
        );
        if (teams.isEmpty()) {
            LOGGER.warn("find failed: name {} not found", name);
            return 0;
        }
        LOGGER.traceExit("find successful {}", teams.get(0));
        return teams.get(0).getId();
    }
}
