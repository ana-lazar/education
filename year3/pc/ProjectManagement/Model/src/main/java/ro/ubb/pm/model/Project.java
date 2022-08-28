package ro.ubb.pm.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "projects")
public class Project extends Entity implements Serializable {

    @NotNull
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Epic> epics = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Project() {}

    public Project(String title, List<Epic> epics) {
        this.title = title;
        this.epics = epics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Epic> getEpics() {
        return epics;
    }

    public void setEpics(List<Epic> epics) {
        this.epics = epics;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
