package ro.ubb.pm.bll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.ubb.pm.bll.userstories.UserStoryBLL;
import ro.ubb.pm.dal.UserStoriesRepository;
import ro.ubb.pm.model.Sprint;
import ro.ubb.pm.model.UserStory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserStoryBLL.class)
@ExtendWith(MockitoExtension.class)
class UserStoryBLLTest {

    /*List<UserStory> allUserStories;

    @MockBean
    private UserStoriesRepository userStoriesRepository;

    @InjectMocks
    UserStoryBLL userStoryBLL;

    @BeforeEach
    void setUp() {
        // Mock some repository data in order not to have to connect to the real database
        UserStory userstory1 = new UserStory();
        userstory1.setId(1);
        Sprint sprint1 = new Sprint();
        sprint1.setId(1);
        userstory1.setSprint(sprint1);

        UserStory userstory2 = new UserStory();
        userstory2.setId(2);
        Sprint sprint2 = new Sprint();
        sprint2.setId(1);
        userstory2.setSprint(sprint2);

        UserStory userstory3 = new UserStory();
        userstory3.setId(3);
        Sprint sprint3 = new Sprint();
        sprint3.setId(3);
        userstory3.setSprint(sprint3);

        allUserStories = new ArrayList<>();
        allUserStories.add(userstory1);
        allUserStories.add(userstory2);
        allUserStories.add(userstory3);

        // mock the behaviour for get all stories, getAllUserStoriesBySprintId, and get by id
        Mockito.when(userStoriesRepository.findAll()).thenReturn(allUserStories);
        Mockito.when(userStoriesRepository.getById(Mockito.any(Integer.class))).thenAnswer(index -> allUserStories.get((Integer)index.getArgument(0) - 1));
        Mockito.when(userStoriesRepository.findAllBySprintId(Mockito.any(Integer.class))).thenAnswer(index ->
                allUserStories.stream().filter( story -> story.getSprint().getId() == (Integer) index.getArgument(0)
                ).collect(Collectors.toList()));

    }

    @Test
    void getAllUserStoriesBySprintId() {
        assertEquals(userStoryBLL.getAllUserStoriesBySprintId(1).size(),2);
        assertEquals(userStoryBLL.getAllUserStoriesBySprintId(3).size(),1);

        // empty list
        assertEquals(userStoryBLL.getAllUserStoriesBySprintId(2).size(),0);
    }

    @Test
    void getAllUserStories() {
        assertEquals(userStoryBLL.getAllUserStories().size(),3);
        assertEquals(userStoryBLL.getAllUserStories(),allUserStories);
    }

    @Test
    void findUserStoryById() {
        assertEquals(1, userStoryBLL.findUserStoryById(1).getId());

        // check if userStoryBll getById rethrows when exception encountered in repository
        try {
            userStoryBLL.findUserStoryById(10);
            fail();
        }
        catch(Exception ignored){
        }
    }*/
}