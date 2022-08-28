package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {

    @Query("FROM Role role WHERE role.title = :roleTitle")
    Role findByRoleTitle(String roleTitle);
}
