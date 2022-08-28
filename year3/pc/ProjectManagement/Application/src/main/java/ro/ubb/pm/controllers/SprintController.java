package ro.ubb.pm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.pm.bll.exceptions.ResourceNotFoundException;
import ro.ubb.pm.bll.sprints.SprintBLL;
import ro.ubb.pm.model.dtos.SprintDTO;

@RestController
@CrossOrigin
@RequestMapping(SprintController.BASE_URL)
public class SprintController {

    protected static final String BASE_URL = "project_management/sprints";

    private final SprintBLL sprintBLL;

    public SprintController(SprintBLL sprintBLL) {
        this.sprintBLL = sprintBLL;
    }

    /**
     * get current sprint based on startDate and endDate
     * @return ResponseEntity<SprintDTO>
     */
    @RequestMapping(value = "/get-current-sprint", method = RequestMethod.GET)
    public ResponseEntity<SprintDTO> getCurrentSprint() throws ResourceNotFoundException {
        return new ResponseEntity<>(sprintBLL.getCurrentSprintDTO(), HttpStatus.OK);
    }
}
