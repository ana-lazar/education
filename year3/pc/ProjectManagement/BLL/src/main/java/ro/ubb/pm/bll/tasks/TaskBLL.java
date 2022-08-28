package ro.ubb.pm.bll.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.pm.bll.exceptions.InternalServerException;
import ro.ubb.pm.dal.TasksRepository;
import ro.ubb.pm.model.Task;
import ro.ubb.pm.model.dtos.TaskDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskBLL {

    private TasksRepository tasksRepository;
    private TaskMapper taskMapper;

    @Autowired
    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> getAllTasksForAUserStory(int userStoryId){
        return tasksRepository.findAllByUserStoryId(userStoryId)
                .stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO){

        Task task = taskMapper.taskDTOToTask(taskDTO);
        task =tasksRepository.save(task);
        taskDTO.setId(task.getId());
        return taskDTO;
    }

    @Transactional
    public TaskDTO updateTask(TaskDTO taskDTO) throws InternalServerException {

        Task task = taskMapper.taskDTOToTask(taskDTO);
        tasksRepository.update(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignedTo(),
                task.getCreatedBy(),
                task.getCreated(),
                task.getUserStory());
        taskMapper.taskToTaskDTO(tasksRepository.findById(task.getId()).orElseThrow(() -> new InternalServerException("Update went wrong")));
        return taskDTO;
    }

    @Transactional
    public void deleteTask(int id){
        tasksRepository.deleteById(id);
    }
}
