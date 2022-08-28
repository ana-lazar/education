package repositories.hibernate;

import domain.User;
import myjdbc.MyJdbcException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.interfaces.UserRepository;
import validators.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserOrmRepository implements UserRepository {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected UserValidator validator;

    public UserOrmRepository() {
    }

    public UserOrmRepository(UserValidator validator) {
        this.validator = validator;
    }

    public void setValidator(UserValidator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        LOGGER.traceEntry("find username {} and password {}", password);
        if (username == null || password == null) {
            LOGGER.warn("find failed: username or password is null");
            throw new MyJdbcException("Find failed: username or password is null");
        }
        User user = null;
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = session.createQuery("from User as u where u.username = :username and u.password = :password", User.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        if (user == null) {
            LOGGER.warn("find failed: username {} and password {} not found", username, password);
            throw new MyJdbcException("Find failed: user not found");
        }
        LOGGER.traceExit("find successful {}", user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findOne(Integer id) {
        LOGGER.traceEntry("find id {}", id);
        if (id == null) {
            LOGGER.warn("find failed: id is null");
            throw new MyJdbcException("Find failed: id is null");
        }
        User user = null;
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = session.createQuery("from user as u where u.id = :id", User.class)
                        .setParameter("id", id)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        if (user == null) {
            LOGGER.warn("find failed: id {} not found", id);
            throw new MyJdbcException("Find failed: id not found");
        }
        LOGGER.traceExit("find successful {}", user);
        return Optional.of(user);
    }

    @Override
    public Iterable<User> findAll() {
        LOGGER.info("entering find all");
        List<User> entities = null;
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                entities = session.createQuery("from user as u order by u.id asc", User.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        LOGGER.traceExit("find all successful {}", entities);
        return entities;
    }

    @Override
    public User save(User user) {
        LOGGER.traceEntry("saving/updating user {}", user);
        if (user == null) {
            LOGGER.warn("save failed: user was null");
            throw new IllegalArgumentException("Save failed: user must be not null");
        }
        validator.validate(user);
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(user);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return user;
    }

    @Override
    public User remove(Integer id) {
        LOGGER.traceEntry("removing entity with id {}", id);
        if (id == null) {
            LOGGER.warn("remove failed: id is null");
            throw new MyJdbcException("Remove failed: id is null");
        }
        Optional<User> entity = findOne(id);
        if (entity.isEmpty()){
            LOGGER.warn("remove failed: id {} not found", id);
            throw new MyJdbcException("Remove failed: id not found");
        }
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                User user = session.createQuery("from user as u where u = :id", User.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(user);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        LOGGER.traceExit("removal successful {}", id);
        return entity.get();
    }

    @Override
    public Integer size() {
        LOGGER.traceEntry("size");
        Integer size = null;
        try (Session session = MotorcycleSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                size = (int) session.createQuery("select count(u.id) from user as u")
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        LOGGER.traceExit("size {}", size);
        return size;
    }
}
