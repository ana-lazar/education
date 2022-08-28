import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.pm.model.Entity;
import ro.ubb.pm.model.Sprint;
import ro.ubb.pm.model.UserStory;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SprintTest {

    Sprint sprint;

    @BeforeEach
    void setUp() {
        sprint = new Sprint();
    }


    @Test
    void getSetTitle() {
        sprint.setTitle("title");
        assertEquals(sprint.getTitle(),"title");
        sprint.setTitle("");
        assertEquals(sprint.getTitle(),"");
    }

    @Test
    void getSetStartDate() {
        LocalDate localDate = LocalDate.now();
        sprint.setStartDate(localDate);
        assertEquals(sprint.getStartDate(), localDate);
    }

    @Test
    void getSetEndDate() {
        LocalDate localDate = LocalDate.now();
        sprint.setEndDate(localDate);
        assertEquals(sprint.getEndDate(), localDate);
    }

    @Test
    void getUserStories() {
        ArrayList<UserStory> userStories = new ArrayList<>();

        UserStory userStory1 = new UserStory();
        UserStory userStory2 = new UserStory();
        UserStory userStory3 = new UserStory();

        userStories.add(userStory1);
        userStories.add(userStory2);
        userStories.add(userStory3);

        sprint.setUserStories(userStories);

        for(int i=0; i<2; i++)
            assertEquals(userStories.get(i), sprint.getUserStories().get(i));
    }
}