package ro.ubb.pm.bll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.ubb.pm.bll.sprints.SprintBLL;
import ro.ubb.pm.dal.SprintsRepository;
import ro.ubb.pm.model.Sprint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/*
@SpringBootTest(classes = SprintBLL.class)
@ExtendWith(MockitoExtension.class)
class SprintBLLTest {


    List<Sprint> allSprints;

    @MockBean
    private SprintsRepository sprintRepository;

    @InjectMocks
    SprintBLL sprintBLL;


    @BeforeEach
    void setUp() {

        // Mock some repository data in order not to have to connect to the real database
        Sprint sprint1 = new Sprint("sprint1", LocalDate.now(), LocalDate.now(), new ArrayList<>());
        sprint1.setId(1);
        Sprint sprint2 = new Sprint("sprint2", LocalDate.now(), LocalDate.now(), new ArrayList<>());
        sprint2.setId(2);
        Sprint sprint3 = new Sprint("sprint3", LocalDate.now(), LocalDate.now(), new ArrayList<>());
        sprint3.setId(3);

        allSprints = new ArrayList<>();
        allSprints.add(sprint1);
        allSprints.add(sprint2);
        allSprints.add(sprint3);

        // mock the behaviour for get all sprints and get by id
        Mockito.when(sprintRepository.findAll()).thenReturn(allSprints);
        Mockito.when(sprintRepository.getById(Mockito.any(Integer.class))).thenAnswer(index -> allSprints.get((Integer)index.getArgument(0) - 1));

    }

    @Test
    void getAllSprints() {
        assertEquals(sprintBLL.getAllSprints().size(),3);
        assertEquals(sprintBLL.getAllSprints(), allSprints);
    }

    @Test
    void getSprintById() {
        assertEquals(sprintBLL.getSprintById(1).getTitle(), "sprint1");
        assertEquals(sprintBLL.getSprintById(2).getTitle(), "sprint2");
        assertEquals(sprintBLL.getSprintById(3).getTitle(), "sprint3");

        // check if sprintBLL getById rethrows when exception encountered in repository
        try {
            sprintBLL.getSprintById(10);
            fail();
        }
        catch(Exception ignored){
        }
    }
}*/
