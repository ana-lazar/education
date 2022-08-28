package ro.ubb.pm.bll.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.pm.dal.ProjectsRepository;
import ro.ubb.pm.model.Project;

@Component
public class ProjectBLL {

    ProjectsRepository projectsRepository;

    @Autowired
    public void setProjectsRepository(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public Project getProjectById(int projectId){return projectsRepository.findById(projectId).orElse(null);}
}
