package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollment, Integer> {

    @Query("FROM Enrollment enrollment WHERE enrollment.user.id = :userId")
    List<Enrollment> findAllByUserId(int userId);
}
