package ro.ubb.pm.bll.userstories;
import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.pm.bll.epics.EpicMapper;
import ro.ubb.pm.bll.sprints.SprintMapper;
import ro.ubb.pm.bll.users.UserMapper;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.UserStory;
import ro.ubb.pm.model.dtos.SprintDTO;
import ro.ubb.pm.model.dtos.UserDTO;
import ro.ubb.pm.model.dtos.UserStoryDTO;
import ro.ubb.pm.model.enums.Status;

public abstract class UserStoryMapperDecorator implements UserStoryMapper {

    private final UserStoryMapper userStoryMapper;
    private UserMapper userMapper;
    private EpicMapper epicMapper;
    private SprintMapper sprintMapper;


    @Autowired
    public void setEpicMapper(EpicMapper epicMapper) {
        this.epicMapper = epicMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setSprintMapper(SprintMapper sprintMapper) {
        this.sprintMapper = sprintMapper;
    }

    public UserStoryMapperDecorator(UserStoryMapper userStoryMapper)
    {
        this.userStoryMapper = userStoryMapper;
    }


    @Override
    public UserStoryDTO userStoryToUserStoryDTO(UserStory userStory) {

        UserStoryDTO userStoryDTO = userStoryMapper.userStoryToUserStoryDTO(userStory);
        userStoryDTO.setStatus(userStory.getStatus().name());
        UserDTO assignedToUserDTO = userMapper.userToUserDTO(userStory.getAssignedTo());
        userStoryDTO.setAssignedTo(assignedToUserDTO);

        UserDTO createdByUserDTO = userMapper.userToUserDTO(userStory.getCreatedBy());
        userStoryDTO.setCreatedBy(createdByUserDTO);

        SprintDTO sprintDTO = sprintMapper.sprintToSprintDTO(userStory.getSprint());
        userStoryDTO.setSprintDTO(sprintDTO);

        return userStoryDTO;
    }

    @Override
    public UserStory userStoryDTOToUserStory(UserStoryDTO userStoryDTO) {
        UserStory userStory = userStoryMapper.userStoryDTOToUserStory(userStoryDTO);
        Epic epic = epicMapper.epicDTOToEpic(userStoryDTO.getSprintDTO().getEpicDTO());
        userStory.setEpic(epic);
        userStory.setSprint(sprintMapper.sprintDTOToSprint(userStoryDTO.getSprintDTO()));
        userStory.setStatus(Status.valueOf(userStoryDTO.getStatus()));
        userStory.setCreatedBy(userMapper.userDTOToUser(userStoryDTO.getCreatedBy()));
        userStory.setAssignedTo(userMapper.userDTOToUser(userStoryDTO.getAssignedTo()));
        return userStory;
    }
}
