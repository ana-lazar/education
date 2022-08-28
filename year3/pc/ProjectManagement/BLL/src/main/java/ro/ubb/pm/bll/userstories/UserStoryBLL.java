package ro.ubb.pm.bll.userstories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.pm.bll.exceptions.InternalServerException;
import ro.ubb.pm.dal.UserStoriesRepository;
import ro.ubb.pm.model.UserStory;
import ro.ubb.pm.model.dtos.UserStoryDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserStoryBLL {

    private UserStoriesRepository userStoriesRepository;
    private UserStoryMapper userStoryMapper;

    @Autowired
    public void setUserStoriesRepository(UserStoriesRepository userStoriesRepository) {
        this.userStoriesRepository = userStoriesRepository;
    }

    @Autowired
    public void setUserStoryMapper(UserStoryMapper userStoryMapper) {
        this.userStoryMapper = userStoryMapper;
    }

    public List<UserStoryDTO> getAllUserStoriesBySprintId(int id){
        return userStoriesRepository.findAllBySprintId(id)
                .stream()
                .map(userStoryMapper::userStoryToUserStoryDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserStoryDTO addUserStory(UserStoryDTO userStoryDTO){
        UserStory userStory = userStoryMapper.userStoryDTOToUserStory(userStoryDTO);
        userStory = userStoriesRepository.save(userStory);
        userStoryDTO.setId(userStory.getId());
        return userStoryDTO;
    }

    @Transactional
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO) throws InternalServerException {
        UserStory userStory = userStoryMapper.userStoryDTOToUserStory(userStoryDTO);
        userStoriesRepository.update(
                userStory.getId(),
                userStory.getTitle(),
                userStory.getDescription(),
                userStory.getAssignedTo(),
                userStory.getCreatedBy(),
                userStory.getEpic(),
                userStory.getSprint(),
                userStory.getCreated(),
                userStory.getStatus());
        return userStoryMapper.userStoryToUserStoryDTO(userStoriesRepository.findById(userStory.getId()).orElseThrow(() -> new InternalServerException("Update went wrong")));
    }

    @Transactional
    public void deleteUserStory(int id){
        userStoriesRepository.deleteById(id);
    }

    public UserStory findUserStoryById(int userStoryId) {
        return userStoriesRepository.getById(userStoryId);
    }

}
