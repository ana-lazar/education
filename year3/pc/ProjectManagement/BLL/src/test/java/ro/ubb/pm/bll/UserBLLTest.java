package ro.ubb.pm.bll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.bll.exceptions.ExceptionMessages;
import ro.ubb.pm.bll.exceptions.InvalidCredentialsException;
import ro.ubb.pm.bll.users.UserBLL;
import ro.ubb.pm.model.dtos.UserDTO;

@SpringBootTest(classes = BLLTest.class)
@RunWith(SpringRunner.class)
public class UserBLLTest {

    @Autowired
    UserBLL userBLL;

    private UserDTO userDTO;

    @Before
    public void initData(){
        userDTO = new UserDTO();
    }

    @Test
    public void testLogin() {

        //invalid user
        Throwable exception = Assert.assertThrows(InvalidCredentialsException.class, ()-> userBLL.login(userDTO));
        Assert.assertEquals(exception.getMessage(), ExceptionMessages.invalidEmailMessage + ExceptionMessages.invalidPasswordMessage);


        //invalid password
        userDTO.setEmail("cristina@yahoo.com");
        userDTO.setPassword("notmypass");
        exception = Assert.assertThrows(InvalidCredentialsException.class, ()-> userBLL.login(userDTO));
        Assert.assertEquals(exception.getMessage(), ExceptionMessages.incorrectPasswordMessage);

        //correct , but userMapper is null
//        userDTO.setPassword("cristina");
//        try{
//            userBLL.login(userDTO);
//        } catch (InvalidCredentialsException e) {
//            this.foundException = true;
//        }
//
//        Assert.assertFalse(foundException);

    }

}