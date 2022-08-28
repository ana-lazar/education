package ro.ubb.pm.bll.epics;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.ubb.pm.model.Epic;
import ro.ubb.pm.model.dtos.EpicDTO;

@Mapper
@DecoratedWith(EpicMapperDecorator.class)
public interface EpicMapper {

    EpicMapper INSTANCE = Mappers.getMapper(EpicMapper.class);

    @Mapping(source = "project", target = "projectDTO")
    EpicDTO epicToEpicDTO(Epic epic);

    @Mapping(target = "sprints", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "userStories", ignore = true)
    Epic epicDTOToEpic(EpicDTO epicDTO);
}
