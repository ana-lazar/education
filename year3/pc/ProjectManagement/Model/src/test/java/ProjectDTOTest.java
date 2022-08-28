import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.ProjectDTO;

public class ProjectDTOTest {
    ProjectDTO projectDTO;

    @Test
    public void testProjectDTO(){
        Assert.assertNull(projectDTO);

        projectDTO = new ProjectDTO();
        Assert.assertNotNull(projectDTO);

        //test id
        projectDTO.setId(12);
        Assert.assertEquals(String.valueOf(12),String.valueOf(projectDTO.getId()));

        //test title
        projectDTO.setTitle("My title.");
        Assert.assertEquals("My title.", projectDTO.getTitle());

    }
}
