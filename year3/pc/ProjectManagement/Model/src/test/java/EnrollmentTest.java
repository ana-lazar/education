import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.pm.model.Enrollment;
import ro.ubb.pm.model.Project;
import ro.ubb.pm.model.User;

public class EnrollmentTest {

    private  Enrollment enrollment;

    @Before
    public void initData(){
        enrollment = new Enrollment();
        enrollment.setId(1);
    }

    @Test
    public void testEnrollmentProperties(){

        //testing the id property
        Assert.assertEquals(1, enrollment.getId());

        //testing the project property
        Assert.assertNull(enrollment.getProject());
        Project project = new Project();
        project.setTitle("HugeProject");
        enrollment.setProject(project);
        Assert.assertNotNull(enrollment.getProject());
        Assert.assertEquals(project, enrollment.getProject());
        Assert.assertEquals("HugeProject", enrollment.getProject().getTitle());


        //testing the user property
        Assert.assertNull(enrollment.getUser());
        User user = new User();
        user.setLastName("Philips");
        enrollment.setUser(user);
        Assert.assertNotNull(enrollment.getUser());
        Assert.assertEquals(user, enrollment.getUser());
        Assert.assertEquals("Philips", enrollment.getUser().getLastName());


    }
}
