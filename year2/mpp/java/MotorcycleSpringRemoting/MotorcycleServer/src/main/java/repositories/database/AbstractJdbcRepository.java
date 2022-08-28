package repositories.database;

import domain.Entity;
import validators.Validator;
import repositories.interfaces.Repository;

import myjdbc.MyJdbcException;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJdbcRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    protected MyJdbcTemplate template;
    protected Validator<E> validator;

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
        System.out.println("find id " + id);
        if (id == null) {
            System.out.println("find failed: id is null");
            throw new MyJdbcException("Find failed: id is null");
        }
        List<E> entities = template.query(
                selectByIdCommand(id),
                getResultSetExtractor()
        );
        if (entities.isEmpty()) {
            System.out.println("find failed: id {} not found" + id);
            throw new MyJdbcException("Find failed: id not found");
        }
        System.out.println("find successful " + entities.get(0));
        return Optional.of(entities.get(0));
    }

    abstract String selectAllCommand();

    @Override
    public Iterable<E> findAll() {
        System.out.println("find all");
        List<E> entities = template.query(
                selectAllCommand(),
                getResultSetExtractor()
        );
        System.out.println("find all successful " + entities);
        return entities;
    }

    abstract String insertCommand(E entity);

    abstract String updateCommand(E entity);

    abstract ID toId(Integer id);

    @Override
    public E save(E user) {
        System.out.println("saving/updating user " + user);
        if (user == null) {
            System.out.println("save failed: user was null");
            throw new IllegalArgumentException("Save failed: user must be not null");
        }
        validator.validate(user);
        if (user.getId() == null) {
            System.out.println("inserting " + user);
            int id = template.insert(insertCommand(user));
            user.setId(toId(id));
            System.out.println("insert successful " + user);
            return user;
        }
        System.out.println("updating " + user);
        int lines = template.update(updateCommand(user));
        if (lines == 0) {
            System.out.println("update failed " + user);
            throw new MyJdbcException("Update failed: user not found");
        }
        System.out.println("update successful " + user);
        return user;
    }

    abstract String deleteByIdCommand(ID id);

    @Override
    public E remove(ID id) {
        System.out.println("removing entity with id " + id);
        if (id == null) {
            System.out.println("remove failed: id is null");
            throw new MyJdbcException("Remove failed: id is null");
        }
        Optional<E> entity = findOne(id);
        if (entity.isEmpty()){
            System.out.println("remove failed: id {} not found" + id);
            throw new MyJdbcException("Remove failed: id not found");
        }
        int lines = template.update(deleteByIdCommand(id));
        if (lines == 0) {
            System.out.println("removal failed {}" + id);
            throw new MyJdbcException("Removal failed: entity with id not found");
        }
        System.out.println("removal successful " + id);
        return entity.get();
    }

    abstract String getTableName();

    @Override
    public Integer size() {
        System.out.println("size");
        int size = template.query("SELECT COUNT(*) FROM " + getTableName(), (set) -> set.getInt(1)).get(0);
        System.out.println("size " + size);
        return size;
    }
}
