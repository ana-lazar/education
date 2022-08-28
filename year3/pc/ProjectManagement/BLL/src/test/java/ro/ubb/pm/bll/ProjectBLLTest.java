package ro.ubb.pm.bll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.ubb.pm.bll.projects.ProjectBLL;
import ro.ubb.pm.dal.ProjectsRepository;
import ro.ubb.pm.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProjectBLL.class)
@ExtendWith(MockitoExtension.class)
class ProjectBLLTest {

    List<Project> allProjects;

    @MockBean
    private ProjectsRepository projectsRepository;

    Project project1, project2, project3;

    @InjectMocks
    ProjectBLL projectBLL;

    @BeforeEach
    void setUp() {
        // Mock some repository data in order not to have to connect to the real database
        project1 = new Project();
        project1.setId(1);

        project2 = new Project();
        project2.setId(2);

        project3 = new Project();
        project3.setId(3);

        allProjects = new ArrayList<>();

        allProjects.add(project1);
        allProjects.add(project2);
        allProjects.add(project3);

        // mock the behaviour for get all sprints and get by id
        Mockito.when(projectsRepository.findAll()).thenReturn(allProjects);
        Mockito.when(projectsRepository.findById(Mockito.any(Integer.class))).thenAnswer(index ->
                ((Integer)index.getArgument(0)-1<0 || (Integer)index.getArgument(0)>allProjects.size())? Optional.empty() : Optional.of(allProjects.get((Integer) index.getArgument(0) - 1))
        );

    }

    @Test
    void getProjectById() {
        assertEquals(project1,projectBLL.getProjectById(1));
        assertEquals(project2,projectBLL.getProjectById(2));
        assertEquals(project3,projectBLL.getProjectById(3));

        // check null for arg not present
       assertNull(projectBLL.getProjectById(10));

    }
}
