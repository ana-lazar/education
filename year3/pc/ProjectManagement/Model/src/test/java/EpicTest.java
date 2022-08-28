import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.Project;
import ro.ubb.pm.model.Sprint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * This test class tests the class Epic from Model
 */
public class EpicTest {

    private Epic epic;
    private Project project;
    private List<Sprint> sprintList;


    /**
     * Some data that will be tested is initialized in this method, that is executed
     * before any other test method.
     */
    @Before
    public void initData(){
        epic = new Epic();
        project=new Project();
        sprintList = new ArrayList<>();
    }


    /**
     * Tests the methods get and set from the Epic class
     * The methods get and set used for Project and UserStory are treated in another
     * method.
     */
    @Test
    public void testSettersGetters(){

        //test id
        epic.setId(1);
        Assert.assertEquals(1, epic.getId());
        Assert.assertNotEquals(3, epic.getId());
        epic.setId(-1);
        Assert.assertNotEquals(1, epic.getId());

        //test Created
        epic.setCreated(LocalDate.of(2019,10,1));
        Assert.assertNotEquals(null, epic.getCreated());
        Assert.assertEquals(2019, epic.getCreated().getYear());

        //test Title
        epic.setTitle("EpicName");
        Assert.assertEquals("EpicName", epic.getTitle());



    }


    /**
     * Tests the project attribute from the class
     */
    @Test
    public void testProject(){
        Assert.assertNull(epic.getProject());

        project.setId(2);
        project.setTitle("ProjectManagement");

        epic.setProject(project);
        Assert.assertEquals(2, epic.getProject().getId());
        Assert.assertEquals("ProjectManagement", epic.getProject().getTitle());
        project.setId(5);
        Assert.assertNotEquals(2, epic.getProject().getId());
        Assert.assertEquals(project, epic.getProject());

    }


    /**
     * Tests the Sprints list attribute
     */
    @Test
    public void testSprintsList(){

        epic.setSprints(null);
        Assert.assertNull(epic.getSprints());

        for(int i =0 ;i <4;i++){
            sprintList.add(new Sprint());
        }

        epic.setSprints(sprintList);
        Assert.assertEquals(4,epic.getSprints().size());
        Assert.assertNull(epic.getSprints().get(0).getTitle());

    }
}
