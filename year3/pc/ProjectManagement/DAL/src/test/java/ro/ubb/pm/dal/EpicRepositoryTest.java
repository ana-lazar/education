package ro.ubb.pm.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.Epic;

import java.util.List;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class EpicRepositoryTest {

    @Autowired
    private EpicsRepository epicsRepository;


    @Test
    public void testFindAllByProjectId(){

        //there are 2 rows is db
        List<Epic> result = epicsRepository.findAllByProjectId(1);
        Assert.assertEquals(2,result.size());

        //invalid project id
        result =epicsRepository.findAllByProjectId(-1);
        Assert.assertEquals(0,result.size());
    }
}
