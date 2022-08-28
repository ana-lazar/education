package ro.ubb.pm.bll;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.bll.epics.EpicBLL;
import ro.ubb.pm.model.Epic;

import java.util.List;

@SpringBootTest(classes = BLLTest.class)
@RunWith(SpringRunner.class)
public class EpicBLLTest {

    @Autowired
    EpicBLL epicBLL;

    private List<Epic> result;

    @Test
    public void testGetAllEpicsByProjectId(){

        //sending inexistent project id
        result = epicBLL.getAllEpicsByProjectId(-1000);
        Assert.assertEquals (0, result.size());

        //sending valid project id (there are 2 rows in db)
        result= epicBLL.getAllEpicsByProjectId(1);
        System.out.println(result);
        Assert.assertEquals(2, result.size());

    }
}