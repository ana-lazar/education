import org.junit.Assert;
import org.junit.Test;
import ro.ubb.pm.model.dtos.TaskDTO;
import ro.ubb.pm.model.dtos.UserDTO;
import ro.ubb.pm.model.dtos.UserStoryDTO;

import java.time.LocalDate;

public class TaskDTOTest {

    private TaskDTO taskDTO;

    @Test
    public void testTaskDTO(){
        Assert.assertNull(taskDTO);

        taskDTO = new TaskDTO();
        Assert.assertNotNull(taskDTO);

        //test created
        taskDTO.setCreated(LocalDate.parse("2020-09-10"));
        Assert.assertEquals(2020, taskDTO.getCreated().getYear());
        Assert.assertEquals(9, taskDTO.getCreated().getMonthValue());

        //test title
        taskDTO.setTitle("MyTask");
        Assert.assertEquals("MyTask", taskDTO.getTitle());

        //test description
        taskDTO.setDescription("TaskDesc");
        Assert.assertEquals("TaskDesc",taskDTO.getDescription());

        //test assignedToDTO
        Assert.assertNull(taskDTO.getAssignedToDTO());
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Tim");
        userDTO.setId(1);
        taskDTO.setAssignedToDTO(userDTO);
        Assert.assertNotNull(taskDTO.getAssignedToDTO());
        Assert.assertEquals(String.valueOf(1), String.valueOf(taskDTO.getAssignedToDTO().getId()));
        Assert.assertEquals("Tim",taskDTO.getAssignedToDTO().getFirstName());

        //test createdByDTO
        Assert.assertNull(taskDTO.getCreatedByDTO());
        taskDTO.setCreatedByDTO(userDTO);
        Assert.assertNotNull(taskDTO.getCreatedByDTO());
        Assert.assertEquals(String.valueOf(1), String.valueOf(taskDTO.getCreatedByDTO().getId()));
        Assert.assertEquals("Tim",taskDTO.getCreatedByDTO().getFirstName());


        //test userStoryDTO
        Assert.assertNull(taskDTO.getUserStoryDTO());
        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.setId(12);
        userStoryDTO.setDescription("MyDescription");
        taskDTO.setUserStoryDTO(userStoryDTO);
        Assert.assertNotNull(taskDTO.getUserStoryDTO());
        Assert.assertEquals(String.valueOf(12),String.valueOf(taskDTO.getUserStoryDTO().getId()));
        Assert.assertEquals("MyDescription",taskDTO.getUserStoryDTO().getDescription());


        //test id
        taskDTO.setId(1);
        Assert.assertEquals(String.valueOf(1), taskDTO.getId().toString());


    }


}
