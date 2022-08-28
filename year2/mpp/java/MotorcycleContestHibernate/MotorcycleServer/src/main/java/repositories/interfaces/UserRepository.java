package repositories.interfaces;

import domain.User;

import java.util.Optional;

public interface UserRepository extends Repository<Integer, User> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
