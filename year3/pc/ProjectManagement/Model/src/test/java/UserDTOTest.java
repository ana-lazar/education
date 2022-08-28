import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.UserDTO;

public class UserDTOTest {

    private UserDTO userDTO;

    @Test
    public void testProperties(){

        Assert.assertNull(userDTO);
        userDTO = new UserDTO();
        Assert.assertNotNull(userDTO);

        //test email
        userDTO.setEmail("myemail@em.com");
        Assert.assertEquals("myemail@em.com", userDTO.getEmail());

        //test firstName
        userDTO.setFirstName("Smith");
        Assert.assertEquals("Smith",userDTO.getFirstName());

        //test password
        userDTO.setPassword("Secret Password");
        Assert.assertEquals("Secret Password", userDTO.getPassword());

        //test id
        userDTO.setId(1);
        Assert.assertEquals(String.valueOf(1),String.valueOf( userDTO.getId()));

        //test lastName
        userDTO.setLastName("Thor");
        Assert.assertEquals("Thor", userDTO.getLastName());

        //test roleId
        userDTO.setRoleId(1);
        Assert.assertEquals(String.valueOf(1),String.valueOf(userDTO.getRoleId()));

        //test roleTitle
        Assert.assertNull(userDTO.getRoleTitle());
        userDTO.setRoleTitle("Role1");
        Assert.assertEquals("Role1",userDTO.getRoleTitle());

    }
}
