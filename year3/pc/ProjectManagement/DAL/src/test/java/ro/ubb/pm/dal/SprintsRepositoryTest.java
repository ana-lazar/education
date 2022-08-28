package ro.ubb.pm.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.Sprint;

import java.time.LocalDate;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class SprintsRepositoryTest {

    @Autowired
    private  SprintsRepository sprintsRepository;

    @Test
    public void testGetCurrentSprint(){

        //existent date
        LocalDate date= LocalDate.parse("2021-11-07");
        Sprint sprint= sprintsRepository.getCurrentSprint(date);
        Assert.assertNotNull(sprint);

        //invalid date
        date =LocalDate.parse("1999-10-10");
        sprint= sprintsRepository.getCurrentSprint(date);
        Assert.assertNull(sprint);
    }
}
