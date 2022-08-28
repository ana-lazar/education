package ro.ubb.pm.bll.sprints;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.pm.bll.epics.EpicMapper;
import ro.ubb.pm.bll.projects.ProjectMapper;
import ro.ubb.pm.dal.SprintsRepository;
import ro.ubb.pm.model.Sprint;
import ro.ubb.pm.model.dtos.EpicDTO;
import ro.ubb.pm.model.dtos.SprintDTO;

public abstract class SprintMapperDecorator implements SprintMapper {

    private final SprintMapper sprintMapper;
    private EpicMapper epicMapper;
    private ProjectMapper projectMapper;

    @Autowired
    private SprintsRepository sprintsRepository;

    @Autowired
    public void setEpicMapper(EpicMapper epicMapper) {
        this.epicMapper = epicMapper;
    }

    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public SprintMapperDecorator(SprintMapper sprintMapper) {
        this.sprintMapper = sprintMapper;
    }

    @Override
    public SprintDTO sprintToSprintDTO(Sprint sprint) {
        SprintDTO sprintDTO = sprintMapper.sprintToSprintDTO(sprint);
        EpicDTO epicDTO = epicMapper.epicToEpicDTO(sprint.getEpic());
        sprintDTO.setEpicDTO(epicDTO);
        return sprintDTO;
    }

    @Override
    public Sprint sprintDTOToSprint(SprintDTO sprintDTO) {
        return sprintsRepository.findById(sprintDTO.getId()).orElse(null);
    }
}
