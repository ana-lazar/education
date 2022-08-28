import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.EpicDTO;
import ro.ubb.pm.model.dtos.ProjectDTO;

import java.time.LocalDate;

public class EpicDTOTest {

    private EpicDTO epicDTO;

    @Test
    public void testEpicDTO(){

        Assert.assertNull(epicDTO);

        epicDTO=  new EpicDTO();
        Assert.assertNotNull(epicDTO);

        //test id
        epicDTO.setId(1);
        Assert.assertEquals(String.valueOf(1), String.valueOf(epicDTO.getId()));

        //test Created
        LocalDate date=  LocalDate.parse("2019-10-10");
        epicDTO.setCreated(date);
        Assert.assertEquals(2019, epicDTO.getCreated().getYear());
        Assert.assertEquals(date, epicDTO.getCreated());

        //test title
        epicDTO.setTitle("My Title");
        Assert.assertEquals("My Title", epicDTO.getTitle());

        //test projectDTO
        Assert.assertNull(epicDTO.getProjectDTO());
        ProjectDTO projectDTO = new ProjectDTO();
        epicDTO.setProjectDTO(projectDTO);
        Assert.assertNotNull(epicDTO.getProjectDTO());
        Assert.assertEquals(projectDTO, epicDTO.getProjectDTO());
        projectDTO.setTitle("Title for project");
        Assert.assertEquals("Title for project", epicDTO.getProjectDTO().getTitle());


    }
}
