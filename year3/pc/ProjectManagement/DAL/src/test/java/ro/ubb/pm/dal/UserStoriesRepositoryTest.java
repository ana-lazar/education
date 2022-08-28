package ro.ubb.pm.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.UserStory;

import java.util.List;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class UserStoriesRepositoryTest {

    @Autowired
    private UserStoriesRepository userStoriesRepository;

    @Test
    public void testFindAllBySprintId(){

        //there are 2 rows in db
        List<UserStory> userStoryList = userStoriesRepository.findAllBySprintId(1);
        Assert.assertEquals(2, userStoryList.size());

        //invalid id
        userStoryList = userStoriesRepository.findAllBySprintId(-1);
        Assert.assertEquals(0, userStoryList.size());

    }
}
