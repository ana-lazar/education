package repositories.database;

import domain.Entity;
import validators.Validator;
import repositories.interfaces.Repository;

import myjdbc.MyJdbcException;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJdbcRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    protected MyJdbcTemplate template;
    protected Validator<E> validator;
    protected static final Logger LOGGER = LogManager.getLogger();

    public AbstractJdbcRepository(MyJdbcTemplate template, Validator<E> validator) {
        this.validator = validator;
        this.template = template;
    }

    public AbstractJdbcRepository() {
    }

    public void setTemplate(MyJdbcTemplate template) {
        this.template = template;
    }

    public void setValidator(Validator<E> validator) {
        this.validator = validator;
    }

    abstract ResultSetExtractor<E> getResultSetExtractor();

    abstract String selectByIdCommand(ID id);

    @Override
    public Optional<E> findOne(ID id) {
        LOGGER.traceEntry("find id {}", id);
        if (id == null) {
            LOGGER.warn("find failed: id is null");
            throw new MyJdbcException("Find failed: id is null");
        }
        List<E> entities = template.query(
                selectByIdCommand(id),
                getResultSetExtractor()
        );
        if (entities.isEmpty()) {
            LOGGER.warn("find failed: id {} not found", id);
            throw new MyJdbcException("Find failed: id not found");
        }
        LOGGER.traceExit("find successful {}", entities.get(0));
        return Optional.of(entities.get(0));
    }

    abstract String selectAllCommand();

    @Override
    public Iterable<E> findAll() {
        LOGGER.traceEntry("find all");
        List<E> entities = template.query(
                selectAllCommand(),
                getResultSetExtractor()
        );
        LOGGER.traceExit("find all successful {}", entities);
        return entities;
    }

    abstract String insertCommand(E entity);

    abstract String updateCommand(E entity);

    abstract ID toId(Integer id);

    @Override
    public E save(E user) {
        LOGGER.traceEntry("saving/updating user {}", user);
        if (user == null) {
            LOGGER.warn("save failed: user was null");
            throw new IllegalArgumentException("Save failed: user must be not null");
        }
        validator.validate(user);
        if (user.getId() == null) {
            LOGGER.info("inserting {}", user);
            int id = template.insert(insertCommand(user));
            user.setId(toId(id));
            LOGGER.traceExit("insert successful {}", user);
            return user;
        }
        LOGGER.info("updating {}", user);
        int lines = template.update(updateCommand(user));
        if (lines == 0) {
            LOGGER.warn("update failed {}", user);
            throw new MyJdbcException("Update failed: user not found");
        }
        LOGGER.traceExit("update successful {}", user);
        return user;
    }

    abstract String deleteByIdCommand(ID id);

    @Override
    public E remove(ID id) {
        LOGGER.traceEntry("removing entity with id {}", id);
        if (id == null) {
            LOGGER.warn("remove failed: id is null");
            throw new MyJdbcException("Remove failed: id is null");
        }
        Optional<E> entity = findOne(id);
        if (entity.isEmpty()){
            LOGGER.warn("remove failed: id {} not found", id);
            throw new MyJdbcException("Remove failed: id not found");
        }
        int lines = template.update(deleteByIdCommand(id));
        if (lines == 0) {
            LOGGER.warn("removal failed {}", id);
            throw new MyJdbcException("Removal failed: entity with id not found");
        }
        LOGGER.traceExit("removal successful {}", id);
        return entity.get();
    }

    abstract String getTableName();

    @Override
    public Integer size() {
        LOGGER.traceEntry("size");
        int size = template.query("SELECT COUNT(*) FROM " + getTableName(), (set) -> set.getInt(1)).get(0);
        LOGGER.traceExit("size {}", size);
        return size;
    }
}
