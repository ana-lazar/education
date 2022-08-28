package ro.ubb.pm.dal;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.Task;

import java.util.List;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class TasksRepositoryTest {

    @Autowired
    private TasksRepository tasksRepository;

    @Test
    public void testFindAllByUserStoryId(){

        //there are 2 rows
        List<Task> result= tasksRepository.findAllByUserStoryId(2);
        Assert.assertEquals(2, result.size());

        //invalid id
        result= tasksRepository.findAllByUserStoryId(-2);
        Assert.assertEquals(0, result.size());
    }
}
