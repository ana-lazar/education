import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.EpicDTO;
import ro.ubb.pm.model.dtos.SprintDTO;

import java.time.LocalDate;

public class SprintDTOTest {

    private SprintDTO sprintDTO;

    @Test
    public void testSprintDTO(){

        Assert.assertNull(sprintDTO);

        sprintDTO = new SprintDTO();
        Assert.assertNotNull(sprintDTO);

        //test id
        sprintDTO.setId(1);
        Assert.assertEquals(String.valueOf(1), String.valueOf(sprintDTO.getId()));

        //test title
        sprintDTO.setTitle("My title for sprint");
        Assert.assertEquals("My title for sprint", sprintDTO.getTitle());

        //test endDate
        LocalDate date = LocalDate.parse("2019-09-10");
        sprintDTO.setEndDate(date);
        Assert.assertEquals(date, sprintDTO.getEndDate());

        //test startDate
        date= LocalDate.parse("2019-08-10");
        sprintDTO.setStartDate(date);
        Assert.assertEquals(date, sprintDTO.getStartDate());

        //test epicDTO
        Assert.assertNull(sprintDTO.getEpicDTO());
        EpicDTO epicDTO = new EpicDTO();
        sprintDTO.setEpicDTO(epicDTO);

        //test epicDTO title
        epicDTO.setTitle("I am the title.");
        Assert.assertEquals("I am the title.", sprintDTO.getEpicDTO().getTitle());

        //test epicDTO id
        epicDTO.setId(12);
        Assert.assertEquals(String.valueOf(12), String.valueOf(sprintDTO.getEpicDTO().getId()));

        Assert.assertEquals(epicDTO, sprintDTO.getEpicDTO());




    }
}
