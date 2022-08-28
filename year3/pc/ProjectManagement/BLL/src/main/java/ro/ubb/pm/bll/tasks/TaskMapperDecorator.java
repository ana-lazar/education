package ro.ubb.pm.bll.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.pm.bll.users.UserMapper;
import ro.ubb.pm.bll.userstories.UserStoryMapper;
import ro.ubb.pm.dal.TasksRepository;
import ro.ubb.pm.model.Task;
import ro.ubb.pm.model.dtos.*;

public abstract class TaskMapperDecorator implements TaskMapper {

    private final TaskMapper taskMapper;
    private UserMapper userMapper;
    private UserStoryMapper userStoryMapper;
    private TasksRepository tasksRepository;

    public TaskMapperDecorator(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserStoryMapper(UserStoryMapper userStoryMapper) {
        this.userStoryMapper = userStoryMapper;
    }

    @Autowired
    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public TaskDTO taskToTaskDTO(Task task) {

        TaskDTO taskDTO = taskMapper.taskToTaskDTO(task);
        UserDTO assignedToDTO = userMapper.userToUserDTO(task.getAssignedTo());
        UserDTO createdByDTO = userMapper.userToUserDTO(task.getCreatedBy());
        UserStoryDTO userStoryDTO = userStoryMapper.userStoryToUserStoryDTO(task.getUserStory());

        taskDTO.setAssignedToDTO(assignedToDTO);
        taskDTO.setCreatedByDTO(createdByDTO);
        taskDTO.setUserStoryDTO(userStoryDTO);
        return taskDTO;
    }

    @Override
    public Task taskDTOToTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setAssignedTo(userMapper.userDTOToUser(taskDTO.getAssignedToDTO()));
        task.setCreatedBy(userMapper.userDTOToUser(taskDTO.getCreatedByDTO()));
        task.setUserStory(userStoryMapper.userStoryDTOToUserStory(taskDTO.getUserStoryDTO()));
        return task;
    }
}
