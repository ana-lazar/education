package ro.ubb.pm.bll.users;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.pm.dal.RolesRepository;
import ro.ubb.pm.model.Role;
import ro.ubb.pm.model.User;
import ro.ubb.pm.model.dtos.UserDTO;

public abstract class UserMapperDecorator implements UserMapper {

    private final UserMapper userMapper;

    @Autowired
    private RolesRepository rolesRepository;

    public UserMapperDecorator(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        if(userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        if(userDTO.getRoleId() != null) {
            Role userRole = rolesRepository.findById(userDTO.getRoleId()).orElse(null);
            user.setRole(userRole);
        }
        if(userDTO.getRoleTitle() != null) {
            Role userRole = rolesRepository.findByRoleTitle(userDTO.getRoleTitle());
            if(userRole != null)
                user.setRole(userRole);
        }
        return user;
    }
}
