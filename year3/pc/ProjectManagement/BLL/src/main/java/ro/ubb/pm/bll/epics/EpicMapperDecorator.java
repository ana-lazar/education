package ro.ubb.pm.bll.epics;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.pm.bll.projects.ProjectMapper;
import ro.ubb.pm.dal.EpicsRepository;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.dtos.EpicDTO;
import ro.ubb.pm.model.dtos.ProjectDTO;

public abstract class EpicMapperDecorator implements EpicMapper {

    private final EpicMapper epicMapper;
    private ProjectMapper projectMapper;

    @Autowired
    private EpicsRepository epicsRepository;

    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public EpicMapperDecorator(EpicMapper epicMapper) {
        this.epicMapper = epicMapper;
    }

    @Override
    public EpicDTO epicToEpicDTO(Epic epic) {
        EpicDTO epicDTO = epicMapper.epicToEpicDTO(epic);
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(epic.getProject());
        epicDTO.setProjectDTO(projectDTO);
        return epicDTO;
    }

    @Override
    public Epic epicDTOToEpic(EpicDTO epicDTO) {
        return epicsRepository.findById(epicDTO.getId()).orElse(null);
    }
}
