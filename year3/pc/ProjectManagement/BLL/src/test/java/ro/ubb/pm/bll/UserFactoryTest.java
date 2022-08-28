package ro.ubb.pm.bll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.bll.users.UserFactory;
import ro.ubb.pm.bll.users.UserMapper;
import ro.ubb.pm.model.Role;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.dtos.UserDTO;

public class UserFactoryTest {

    private UserFactory userFactory;
    private UserMapper userMapper;
    private User userToTest;
    private UserDTO userDTOToTest;

    @Before
    public void initData(){

         userFactory = new UserFactory();
         userMapper = userFactory.userMapper();
         userDTOToTest = new UserDTO();
         userToTest = new User();
    }

    @Test
    public void testUserToUserDTO(){

        Role myRole= new Role();
        myRole.setId(10);
        myRole.setTitle("Developer");

        userToTest.setId(12);
        userToTest.setPassword("a");
        userToTest.setEmail("a@a.com");
        userToTest.setLastName("Pink");
        userToTest.setFirstName("Mark");
        userToTest.setRole(myRole);

        UserDTO localUserDTO = userMapper.userToUserDTO(userToTest);

        //test id
        Assert.assertEquals(String.valueOf(12),String.valueOf(localUserDTO.getId()));

        //test password
        Assert.assertEquals("a", localUserDTO.getPassword());

        //test email
        Assert.assertEquals("a@a.com",localUserDTO.getEmail());

        //test lastName
        Assert.assertEquals("Pink", localUserDTO.getLastName());

        //test firstName
        Assert.assertEquals("Mark", localUserDTO.getFirstName());

        //test roleId
        Assert.assertEquals(String.valueOf(10), String.valueOf(localUserDTO.getRoleId()));

        //test roleTitle
        Assert.assertEquals("Developer", localUserDTO.getRoleTitle());

    }

}
