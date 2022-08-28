package ro.ubb.pm.bll.users;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.dtos.UserDTO;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.title", target = "roleTitle")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    User userDTOToUser(UserDTO userDTO);
}
