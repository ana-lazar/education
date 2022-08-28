import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.pm.model.Task;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.UserStory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
    }

    @Test
    void getSetTitle() {
        task.setTitle("title");
        assertEquals(task.getTitle(),"title");
        task.setTitle("");
        assertEquals(task.getTitle(),"");
    }


    @Test
    void getSetDescription() {
        task.setDescription("desc");
        assertEquals(task.getDescription(),"desc");
        task.setDescription("");
        assertEquals(task.getDescription(),"");
    }

    @Test
    void getSetAssignedTo() {
        User user = new User();
        task.setAssignedTo(user);
        assertEquals(task.getAssignedTo(), user);
    }

    @Test
    void getSetCreatedBy() {
        User user = new User();
        task.setCreatedBy(user);
        assertEquals(task.getCreatedBy(), user);
    }

    @Test
    void getSetCreated() {
        LocalDate localDate = LocalDate.now();
        task.setCreated(localDate);
        assertEquals(task.getCreated(), localDate);
    }

    @Test
    void getSetUserStory() {
        UserStory userStory = new UserStory();
        task.setUserStory(userStory);
        assertEquals(task.getUserStory(),userStory);
    }
}