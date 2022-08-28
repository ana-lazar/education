package ro.ubb.pm.bll.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.pm.bll.exceptions.ExceptionMessages;
import ro.ubb.pm.bll.exceptions.InvalidCredentialsException;
import ro.ubb.pm.bll.validator.ValidatorUser;
import ro.ubb.pm.dal.*;
import ro.ubb.pm.model.*;
import ro.ubb.pm.model.dtos.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserBLL {

    private UsersRepository usersRepository;
    private ValidatorUser validatorUser;
    private UserMapper userMapper;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setValidatorUser(ValidatorUser validatorUser) {
        this.validatorUser = validatorUser;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDTO login(UserDTO userDTO) throws InvalidCredentialsException {

        User receivedUser = userMapper.userDTOToUser(userDTO);
        validatorUser.validate(receivedUser);
        User userFound = usersRepository.findByEmail(receivedUser.getEmail());
        if(userFound == null)
            throw new InvalidCredentialsException(ExceptionMessages.nonExistentUserMessage);
        if(!userFound.getPassword().equals(receivedUser.getPassword()))
            throw new InvalidCredentialsException(ExceptionMessages.incorrectPasswordMessage);

        return userMapper.userToUserDTO(userFound);
    }

    public List<UserDTO> getAllByProject(int projectId){
        return usersRepository.findAllByProject(projectId)
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }
}
