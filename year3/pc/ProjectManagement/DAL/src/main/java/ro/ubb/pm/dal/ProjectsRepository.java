package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Project;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {
}
