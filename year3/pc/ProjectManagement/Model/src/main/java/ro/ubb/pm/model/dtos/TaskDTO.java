package ro.ubb.pm.model.dtos;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TaskDTO {

    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private UserDTO assignedToDTO;

    @NotNull
    private UserDTO createdByDTO;

    @NotNull
    private LocalDate created;

    private UserStoryDTO userStoryDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getAssignedToDTO() {
        return assignedToDTO;
    }

    public void setAssignedToDTO(UserDTO assignedToDTO) {
        this.assignedToDTO = assignedToDTO;
    }

    public UserDTO getCreatedByDTO() {
        return createdByDTO;
    }

    public void setCreatedByDTO(UserDTO createdByDTO) {
        this.createdByDTO = createdByDTO;
    }

    public UserStoryDTO getUserStoryDTO() {
        return userStoryDTO;
    }

    public void setUserStoryDTO(UserStoryDTO userStoryDTO) {
        this.userStoryDTO = userStoryDTO;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }


}
