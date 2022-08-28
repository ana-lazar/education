package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    @Query("FROM User user WHERE user.email = :email")
    User findByEmail(String email);

    @Query("FROM User user left join user.enrollments en WHERE en.project.id = :projectId")
    List<User> findAllByProject(int projectId);
}
