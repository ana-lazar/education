package ro.ubb.pm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.pm.bll.exceptions.InternalServerException;
import ro.ubb.pm.bll.userstories.UserStoryBLL;
import ro.ubb.pm.model.dtos.UserStoryDTO;

import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(UserStoryController.BASE_URL)
public class UserStoryController {

    protected static final String BASE_URL = "project_management/user-stories";

    private final UserStoryBLL userStoryBLL;

    public UserStoryController(UserStoryBLL userStoryBLL) {
        this.userStoryBLL = userStoryBLL;
    }

    /**
     * get all user stories that are associated with the sprint id
     * @param sprintId - int
     * @return ResponseEntity<List<UserStoryDTO>>
     */
    @RequestMapping(value = "/get-all-by-sprint/{sprintId}", method = RequestMethod.GET)
    public ResponseEntity<List<UserStoryDTO>> getAllUserStoriesBySprint(@PathVariable int sprintId) {
        return new ResponseEntity<>(userStoryBLL.getAllUserStoriesBySprintId(sprintId), HttpStatus.OK);
    }

    @RequestMapping(value = "/create")
    public ResponseEntity<UserStoryDTO> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        return new ResponseEntity<>(userStoryBLL.addUserStory(userStoryDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{userStoryId}")
    public ResponseEntity<UserStoryDTO> updateUserStory(@PathVariable int userStoryId, @RequestBody UserStoryDTO userStoryDTO) throws InternalServerException {
        return new ResponseEntity<>(userStoryBLL.updateUserStory(userStoryDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{userStoryId}")
    public ResponseEntity<String> deleteUserStory(@PathVariable int userStoryId) {
        userStoryBLL.deleteUserStory(userStoryId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
