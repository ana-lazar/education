package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Task;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.UserStory;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {

    @Query("FROM Task task WHERE task.userStory.id = :userStoryId")
    List<Task> findAllByUserStoryId(int userStoryId);

    @Modifying
    @Query("update Task t set t.title = :title, " +
            " t.description = :description, " +
            " t.assignedTo = :assignedTo," +
            " t.createdBy = :createdBy," +
            " t.created = :created," +
            " t.userStory = :userStory where t.id = :id")
    void update(@Param(value = "id") int id,
                @Param(value = "title") String title,
                @Param(value = "description") String description,
                @Param(value = "assignedTo") User assignedTo,
                @Param(value = "createdBy") User createdBy,
                @Param(value = "created") LocalDate created,
                @Param(value = "userStory") UserStory userStory);
}
