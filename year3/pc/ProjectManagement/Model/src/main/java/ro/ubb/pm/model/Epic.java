package ro.ubb.pm.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "epics")
public class Epic extends Entity implements Serializable {

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "created")
    private LocalDate created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "epic", fetch = FetchType.LAZY)
    private List<Sprint> sprints = new ArrayList<>();

    @OneToMany(mappedBy = "epic", fetch = FetchType.LAZY)
    private List<UserStory> userStories = new ArrayList<>();

    public Epic() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }
}
