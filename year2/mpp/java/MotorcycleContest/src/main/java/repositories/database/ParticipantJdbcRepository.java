package repositories.database;

import domain.entities.Participant;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.interfaces.ParticipantRepository;

import myjdbc.MyJdbcException;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import java.util.List;

@Component
public class ParticipantJdbcRepository extends AbstractJdbcRepository<Integer, Participant> implements ParticipantRepository {
    @Autowired
    public ParticipantJdbcRepository(MyJdbcTemplate template, Validator<Participant> validator) {
        super(template, validator);
    }

    @Override
    ResultSetExtractor<Participant> getResultSetExtractor() {
        return (set) -> {
            Integer id = set.getInt("id");
            String name = set.getString("name");
            Integer raceId = set.getInt("raceId");
            Integer teamId = set.getInt("teamId");
            Participant participant = new Participant(name, raceId, teamId);
            participant.setId(id);
            return participant;
        };
    }

    @Override
    String selectByIdCommand(Integer id) {
        return String.format("SELECT id, name, raceId, teamId FROM participants WHERE id = '%d';", id);
    }

    @Override
    String selectAllCommand() {
        return "SELECT id, name, raceId, teamId FROM participants;";
    }

    @Override
    String insertCommand(Participant participant) {
        return String.format("INSERT INTO participants (name, raceId, teamId) VALUES ('%s', '%d', '%d');",
                participant.getName(),
                participant.getRaceId(),
                participant.getTeamId()
        );
    }

    @Override
    String updateCommand(Participant participant) {
        return String.format("UPDATE participants SET name = '%s', raceId = '%d', teamId = '%d' WHERE id = '%d';",
                participant.getName(),
                participant.getRaceId(),
                participant.getTeamId(),
                participant.getId()
        );
    }

    @Override
    Integer toId(Integer id) {
        return id;
    }

    @Override
    String deleteByIdCommand(Integer id) {
        return String.format("DELETE FROM participants WHERE id = '%d';", id);
    }

    @Override
    String getTableName() {
        return "participants";
    }

    @Override
    public List<Participant> findByTeam(Integer teamId) {
        LOGGER.traceEntry("find participants with team {} ", teamId);
        if (teamId == null) {
            LOGGER.warn("find failed: team is null");
            throw new MyJdbcException("Find failed: team is null");
        }
        List<Participant> participants = template.query(
                String.format("SELECT id, name, raceId, teamId FROM participants WHERE teamId = '%d';", teamId),
                getResultSetExtractor()
        );
        if (participants.isEmpty()) {
            LOGGER.warn("find failed: team {} not found", teamId);
            return participants;
        }
        LOGGER.traceExit("find successful {}", participants.get(0));
        return participants;
    }

    @Override
    public List<Participant> findByRace(Integer raceId) {
        LOGGER.traceEntry("find participants with race {} ", raceId);
        if (raceId == null) {
            LOGGER.warn("find failed: race is null");
            throw new MyJdbcException("Find failed: race is null");
        }
        List<Participant> participants = template.query(
                String.format("SELECT id, name, raceId, teamId FROM participants WHERE raceId = '%d';", raceId),
                getResultSetExtractor()
        );
        if (participants.isEmpty()) {
            LOGGER.warn("find failed: race {} not found", raceId);
            return participants;
        }
        LOGGER.traceExit("find successful {}", participants.get(0));
        return participants;
    }
}
