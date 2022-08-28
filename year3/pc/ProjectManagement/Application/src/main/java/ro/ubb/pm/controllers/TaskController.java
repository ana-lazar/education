package ro.ubb.pm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.pm.bll.exceptions.InternalServerException;
import ro.ubb.pm.bll.tasks.TaskBLL;
import ro.ubb.pm.model.dtos.TaskDTO;

import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(TaskController.BASE_URL)
public class TaskController {

    protected static final String BASE_URL = "project_management/tasks";

    private final TaskBLL taskBLL;

    public TaskController(TaskBLL taskBLL) {
        this.taskBLL = taskBLL;
    }

    /**
     * Retrieves all the tasks associated to an user story.
     * @param userStoryId - int
     *                    - id of the sprint
     * @return ResponseEntity<List<TaskDTO>> - all
     */
    @RequestMapping(value = "/get-all-by-user-story/{userStoryId}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserStoryId(@PathVariable int userStoryId) {
        return new ResponseEntity<>(taskBLL.getAllTasksForAUserStory(userStoryId), HttpStatus.OK);
    }

    @RequestMapping(value = "/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskBLL.addTask(taskDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable int taskId, @RequestBody TaskDTO taskDTO) throws InternalServerException {
        return new ResponseEntity<>(taskBLL.updateTask(taskDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        taskBLL.deleteTask(taskId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
