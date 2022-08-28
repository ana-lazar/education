package ro.ubb.pm.bll;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.bll.tasks.TaskBLL;
import ro.ubb.pm.model.dtos.TaskDTO;

import java.util.List;

@SpringBootTest(classes = BLLTest.class)
@RunWith(SpringRunner.class)
public class TaskBLLTest {
    @Autowired
    TaskBLL taskBLL;

    private List<TaskDTO> result;

    @Test
    public void testGetAllTasksForASprint(){

        //send valid user story id, that does not have data in db
        result= taskBLL.getAllTasksForAUserStory(1);
        Assert.assertEquals(0, result.size());

        //invalid sprint id(there is no user story with this id)
        result= taskBLL.getAllTasksForAUserStory(-1);
        Assert.assertEquals(0, result.size());

        //send valid user story id, with 2 rows in db
        result= taskBLL.getAllTasksForAUserStory(2);
        Assert.assertEquals(2, result.size());



    }
}
