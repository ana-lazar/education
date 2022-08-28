package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.Sprint;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.UserStory;
import ro.ubb.pm.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserStoriesRepository extends JpaRepository<UserStory, Integer> {

    @Query("FROM UserStory userStory WHERE userStory.sprint.id = :id")
    List<UserStory> findAllBySprintId(int id);

    @Modifying
    @Query("update UserStory userStory set userStory.title = :title, userStory.description = :description," +
            " userStory.assignedTo = :assignedTo, userStory.createdBy = :createdBy, userStory.epic = :epic," +
            " userStory.sprint = :sprint, userStory.created = :created, userStory.status = :status where userStory.id = :id")
    void update(@Param(value = "id") Integer id,
                @Param(value = "title") String title,
                @Param(value = "description") String description,
                @Param(value = "assignedTo") User assignedTo,
                @Param(value = "createdBy") User createdBy,
                @Param(value = "epic") Epic epic,
                @Param(value = "sprint") Sprint sprint,
                @Param(value = "created") LocalDate created,
                @Param(value = "status") Status status
                );
}
