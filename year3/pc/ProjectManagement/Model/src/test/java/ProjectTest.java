import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.Project;

import java.util.ArrayList;
import java.util.List;


/**
 * This test class tests the class Project from Model
 */
public class ProjectTest {

    private Project project;
    private List<Epic> epicList;


    /**
     * Some data that will be tested is initialized in this method, that is executed
     * before any other test method.
     */
    @Before
    public  void initData(){
        project = new Project();
        epicList = new ArrayList<>();
    }


    /**
     * Tests the get and set methods for the fields title and id
     */
    @Test
    public  void testGetters(){

        //test Id
        project.setId(2);
        Assert.assertEquals(2,project.getId());
        project.setId(10);
        Assert.assertEquals(10,project.getId());

        //test Title
        project.setTitle("Title");
        Assert.assertEquals("Title", project.getTitle());

    }


    /**
     * Tests the set and get methods for the Epic list field
     */
    @Test
    public void testEpics(){

        Assert.assertEquals(new ArrayList<>(), project.getEpics());
        Assert.assertEquals(0,epicList.size());
        for(int i =0 ;i <2; i++){
            epicList.add(new Epic());
        }
        project.setEpics(epicList);

        Assert.assertNotNull(project.getEpics());
        Assert.assertEquals(2,project.getEpics().size());
        epicList.get(0).setProject(project);
        Assert.assertEquals(project, epicList.get(0).getProject());
    }
}

