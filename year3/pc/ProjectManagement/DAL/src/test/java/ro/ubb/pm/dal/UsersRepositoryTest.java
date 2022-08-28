package ro.ubb.pm.dal;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.User;

import java.util.List;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByEmail(){

        //valid email
        User user= usersRepository.findByEmail("cristina@yahoo.com");
        Assert.assertNotNull(user);
        Assert.assertEquals("Hendre",user.getLastName());


        //invalid email
        user= usersRepository.findByEmail("cristina.com");
        Assert.assertNull(user);
    }

    @Test
    public void testFindAllByProject(){

        //valid project id (there are 10 users)
        List<User> users= usersRepository.findAllByProject(1);
        Assert.assertEquals(10, users.size());

        //invalid project id
        users= usersRepository.findAllByProject(-11);
        Assert.assertEquals(0, users.size());
    }
}
