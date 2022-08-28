package ro.ubb.pm.bll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.bll.projects.ProjectFactory;
import ro.ubb.pm.bll.projects.ProjectMapper;
import ro.ubb.pm.model.Project;
import ro.ubb.pm.model.dtos.ProjectDTO;

public class ProjectFactoryTest {

    private ProjectFactory projectFactory;
    private ProjectMapper projectMapper;
    private ProjectDTO projectDTOToTest;
    private Project projectToTest;

    @Before
    public void initData(){
        projectFactory =new ProjectFactory();
        projectMapper = projectFactory.projectMapper();
        projectDTOToTest = new ProjectDTO();
        projectToTest = new Project();

    }

    @Test
    public void testProjectToProjectDTO(){
        projectToTest.setTitle("Title.");
        projectToTest.setId(123);

        ProjectDTO localProjectDTO= projectMapper.projectToProjectDTO(projectToTest);

        //test id
        Assert.assertEquals(String.valueOf(123), String.valueOf( localProjectDTO.getId()));

        //test title
        Assert.assertEquals("Title.", localProjectDTO.getTitle());

    }

    @Test
    public void testProjectDTOToProject(){

        projectDTOToTest.setTitle("Game playing");
        projectDTOToTest.setId(72);

        Project localProject = projectMapper.projectDTOToProject(projectDTOToTest);

        //test id
        Assert.assertEquals(String.valueOf(72), String.valueOf( localProject.getId()));

        //test title
        Assert.assertEquals("Game playing", localProject.getTitle());

    }
}
