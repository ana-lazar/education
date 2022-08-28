package ro.ubb.pm.bll.sprints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.pm.bll.exceptions.ResourceNotFoundException;
import ro.ubb.pm.dal.SprintsRepository;
import ro.ubb.pm.model.Sprint;
import ro.ubb.pm.model.dtos.SprintDTO;

import java.time.LocalDate;

@Component
public class SprintBLL {

    private SprintsRepository sprintsRepository;

    private SprintMapper sprintMapper;

    @Autowired
    public void setSprintsRepository(SprintsRepository sprintsRepository){
        this.sprintsRepository= sprintsRepository;
    }

    @Autowired
    public void setSprintMapper(SprintMapper sprintMapper) {
        this.sprintMapper = sprintMapper;
    }

    public Sprint getSprintById(int sprintId) {
        return sprintsRepository.getById(sprintId);
    }

    /*public Sprint getCurrentSprint() throws ResourceNotFoundException {
        Sprint currentSprint = sprintsRepository.getCurrentSprint(LocalDate.now());
        if(currentSprint == null)
            throw new ResourceNotFoundException("There is no active sprint at this time");

        return currentSprint;
    }*/

    public SprintDTO getCurrentSprintDTO() throws ResourceNotFoundException {
        Sprint currentSprint = sprintsRepository.getCurrentSprint(LocalDate.now());
        if(currentSprint == null)
            throw new ResourceNotFoundException("There is no active sprint at this time");

        return sprintMapper.sprintToSprintDTO(currentSprint);
    }
}