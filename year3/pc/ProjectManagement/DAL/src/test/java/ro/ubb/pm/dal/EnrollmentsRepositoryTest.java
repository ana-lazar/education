package ro.ubb.pm.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.Enrollment;

import java.util.List;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class EnrollmentsRepositoryTest {

    @Autowired
    private EnrollmentsRepository enrollmentsRepository;


    @Test
    public void testFindAllByUserId(){

        int userId= 1;
        List<Enrollment> result=enrollmentsRepository.findAllByUserId(userId);
        Assert.assertEquals(1, result.size());

        result =enrollmentsRepository.findAllByUserId(1782);
        Assert.assertEquals(0,result.size());

    }
}
