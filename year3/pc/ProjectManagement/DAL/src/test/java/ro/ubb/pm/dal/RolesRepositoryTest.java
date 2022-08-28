package ro.ubb.pm.dal;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.ubb.pm.model.Role;

@SpringBootTest(classes = DALTest.class)
@RunWith(SpringRunner.class)
public class RolesRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Test
    public void testFindByRoleTitle(){

        //invalid title
        Role role = rolesRepository.findByRoleTitle("Invalid title");
        Assert.assertNull(role);

        //valid title
        role= rolesRepository.findByRoleTitle("Team Member");
        Assert.assertEquals(3, role.getId());
        role= rolesRepository.findByRoleTitle("Scrum Master");
        Assert.assertEquals(1, role.getId());
        role= rolesRepository.findByRoleTitle("Product Owner");
        Assert.assertEquals(2, role.getId());
    }
}
