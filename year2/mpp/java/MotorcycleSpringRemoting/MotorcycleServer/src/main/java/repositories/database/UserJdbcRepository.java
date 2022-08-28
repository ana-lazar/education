package repositories.database;

import domain.User;
import validators.Validator;
import repositories.interfaces.UserRepository;
import myjdbc.MyJdbcException;
import myjdbc.MyJdbcTemplate;
import myjdbc.ResultSetExtractor;

import java.util.List;
import java.util.Optional;

public class UserJdbcRepository extends AbstractJdbcRepository<Integer, User> implements UserRepository {
    public UserJdbcRepository(MyJdbcTemplate template, Validator<User> validator) {
        super(template, validator);
    }

    public UserJdbcRepository() {
    }

    @Override
    public ResultSetExtractor<User> getResultSetExtractor() {
        return (set) -> {
            Integer id = set.getInt("id");
            String name = set.getString("name");
            String username = set.getString("username");
            String password = set.getString("password");
            User user = new User(name, username, password);
            user.setId(id);
            return user;
        };
    }

    @Override
    String selectByIdCommand(Integer id) {
        return String.format("SELECT id, name, username, password FROM users WHERE id = '%d';", id);
    }

    @Override
    String selectAllCommand() {
        return "SELECT id, name, username, password FROM users;";
    }

    @Override
    String insertCommand(User user) {
        return String.format("INSERT INTO users (name, username, password) VALUES ('%s', '%s', '%s');",
                user.getName(),
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    String updateCommand(User user) {
        return String.format("UPDATE users SET name = '%s', username = '%s', password = '%s' WHERE id = '%d';",
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getId()
        );
    }

    @Override
    Integer toId(Integer id) {
        return id;
    }

    @Override
    String deleteByIdCommand(Integer id) {
        return String.format("DELETE FROM users WHERE id = '%d';", id);
    }

    @Override
    String getTableName() {
        return "users";
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        System.out.println("find user with username {} & password {}" + username +   " " + password);
        if (username == null || password == null) {
            System.out.println("find failed: username or password is null");
            throw new MyJdbcException("Find failed: username or password is null");
        }
        List<User> users = template.query(
                String.format("SELECT id, name, username, password FROM users WHERE username = '%s' AND password = '%s';", username, password),
                getResultSetExtractor()
        );
        if (users.isEmpty()) {
            System.out.println("find failed: username {} & password {} not found" + username + " " + password);
            return null;
        }
        System.out.println("find successful " + users.get(0));
        return users.get(0);
    }
}
