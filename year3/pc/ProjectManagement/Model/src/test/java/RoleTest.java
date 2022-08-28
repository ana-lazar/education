import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.model.Role;


/**
 * This test class tests the class Role from Model
 */
public class RoleTest {

    private Role role;

    /**
     * Some data that will be tested is initialized in this method, that is executed
     * before any other test method.
     */
    @Before
    public void initData(){
        role = new Role();
    }

    /**
     * Tests the get and set methods for the fields title and id
     */
    @Test
    public void testGetterSetter(){

        //test Title
        role.setTitle("RoleTitle");
        Assert.assertEquals("RoleTitle",role.getTitle());
        role.setTitle("");
        Assert.assertEquals("",role.getTitle());

        //test Id
        Assert.assertEquals(0,role.getId());
        role.setId(2);
        Assert.assertEquals(2,role.getId());

    }

}
