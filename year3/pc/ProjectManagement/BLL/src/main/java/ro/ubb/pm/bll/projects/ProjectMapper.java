package ro.ubb.pm.bll.projects;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.ubb.pm.model.Project;
import ro.ubb.pm.model.dtos.ProjectDTO;

@Mapper
@DecoratedWith(ProjectMapperDecorator.class)
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO projectToProjectDTO(Project project);

    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "epics", ignore = true)
    Project projectDTOToProject(ProjectDTO projectDTO);
}
