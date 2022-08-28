package repositories.interfaces;

import domain.entities.User;

import java.util.Optional;

// pastrez Repository si la UserRepository si la AbstractJdbcRepository ?
public interface UserRepository extends Repository<Integer, User> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
