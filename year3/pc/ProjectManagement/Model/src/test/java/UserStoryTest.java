import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.model.*;
import ro.ubb.pm.model.enums.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This test class tests the class Role from Model
 */
public class UserStoryTest {

    private UserStory userStory;
    private User user;
    private  Sprint sprint;
    private  List<Task> taskList;
    private LocalDate date;

    /**
     * Some data that will be tested is initialized in this method, that is executed
     * before any other test method.
     */
    @Before
    public void initData(){
        userStory = new UserStory();
        user= new User();
        sprint = new Sprint();
        taskList = new ArrayList<>();
        date= LocalDate.of(2020,10,10);

    }

    /**
     * Tests the get and set methods for these fields: title, id, status, created,
     * description
     */
    @Test
    public void testGettersSetters(){

        //test title
        userStory.setTitle("InitialTitle");
        Assert.assertEquals("InitialTitle",userStory.getTitle());

        //test status
        userStory.setStatus(Status.DONE);
        Assert.assertNotNull(userStory.getStatus());
        Assert.assertEquals(Status.DONE, userStory.getStatus());

        //test created
        userStory.setCreated(LocalDate.MIN);
        Assert.assertEquals(1,userStory.getCreated().getMonthValue());
        userStory.setCreated(date);
        Assert.assertEquals(date, userStory.getCreated());

        //test id
        userStory.setId(1);
        Assert.assertEquals(1,userStory.getId());

        //test description
        userStory.setDescription("NoDescription");
        Assert.assertEquals("NoDescription",userStory.getDescription());

    }


    /**
     * Tests the get and set methods for AssignedTo and CreatedBy fields
     */
    @Test
    public void testUsers(){

        //test AssignedTo
        userStory.setAssignedTo(user);
        Assert.assertNotNull(userStory.getAssignedTo());
        user.setFirstName("Rick");
        Assert.assertNotEquals("Rock", userStory.getAssignedTo().getFirstName());
        Assert.assertEquals(0,userStory.getAssignedTo().getId());

        //test CreatedBy
        Assert.assertNull(userStory.getCreatedBy());
        userStory.setCreatedBy(user);
        Assert.assertEquals("Rick",userStory.getCreatedBy().getFirstName());
    }



    /**
     * Tests the Sprint field
     */
    @Test
    public void testSprint(){
        Assert.assertNull(userStory.getSprint());
        userStory.setSprint(sprint);
        Assert.assertNotNull(userStory.getSprint());
        Assert.assertEquals(sprint, userStory.getSprint());

        sprint.setEndDate(LocalDate.MAX);
        Assert.assertEquals(sprint, userStory.getSprint());
    }


    /**
     * Tests the Task list field
     */
    @Test
    public void testTasks(){

        Assert.assertEquals(0, userStory.getTasks().size());

        for(int i =0; i< 3; i++){
            taskList.add(new Task());
        }
        userStory.setTasks(taskList);
        Assert.assertEquals(3,userStory.getTasks().size());
        Assert.assertNotNull(userStory.getTasks().get(1));

    }
}
