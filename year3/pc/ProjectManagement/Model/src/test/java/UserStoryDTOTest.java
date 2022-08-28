import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.SprintDTO;
import ro.ubb.pm.model.dtos.UserDTO;
import ro.ubb.pm.model.dtos.UserStoryDTO;
import ro.ubb.pm.model.enums.Status;

import java.time.LocalDate;

public class UserStoryDTOTest {

    private  UserStoryDTO userStoryDTO;
    private UserDTO userDTO;

    @Test
    public void testUserStoryDTO(){
        Assert.assertNull(userStoryDTO);
        userStoryDTO = new UserStoryDTO();
        Assert.assertNotNull(userStoryDTO);

        //test id
        userStoryDTO.setId(1);
        Assert.assertEquals(String.valueOf(1), String.valueOf(userStoryDTO.getId()));

        //test title
        userStoryDTO.setTitle("User title.");
        Assert.assertEquals("User title.", userStoryDTO.getTitle());

        //test  created
        LocalDate myDate= LocalDate.parse("2018-09-10");
        userStoryDTO.setCreated(myDate);
        Assert.assertEquals(2018, userStoryDTO.getCreated().getYear());
        Assert.assertEquals(myDate, userStoryDTO.getCreated());

        //test description
        userStoryDTO.setDescription("An interesting description. You can check it out.");
        Assert.assertEquals("An interesting description. You can check it out.",
                userStoryDTO.getDescription());

        //test status
        Status status = Status.TO_DO;
        userStoryDTO.setStatus(status.name());
        Assert.assertEquals("TO_DO", userStoryDTO.getStatus());

        //test sprintDTO
        Assert.assertNull(userStoryDTO.getSprintDTO());
        SprintDTO sprintDTO = new SprintDTO();
        userStoryDTO.setSprintDTO(sprintDTO);
        Assert.assertNotNull(userStoryDTO.getSprintDTO());
        sprintDTO.setId(121);
        Assert.assertEquals(String.valueOf(121), String.valueOf(userStoryDTO.getSprintDTO().getId()));

        //test assignedTo
        userStoryDTO.setAssignedTo(userDTO);
        Assert.assertNull(userStoryDTO.getAssignedTo());
        userDTO = new UserDTO();
        userDTO.setPassword("empty-pass1");
        userStoryDTO.setAssignedTo(userDTO);
        Assert.assertEquals("empty-pass1", userStoryDTO.getAssignedTo().getPassword());

        //test createdBy
        Assert.assertNull(userStoryDTO.getCreatedBy());
        userStoryDTO.setCreatedBy(userDTO);
        Assert.assertEquals("empty-pass1", userStoryDTO.getCreatedBy().getPassword());


    }
}
