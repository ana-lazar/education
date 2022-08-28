package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Sprint;

import java.time.LocalDate;

@Repository
public interface SprintsRepository extends JpaRepository<Sprint, Integer> {
    @Query("FROM Sprint WHERE :currentDate between startDate and endDate")
    Sprint getCurrentSprint(LocalDate currentDate);
}
