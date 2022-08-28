package ro.ubb.pm.bll.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.pm.dal.RolesRepository;
import ro.ubb.pm.model.Role;

import java.util.List;

@Component
public class RoleBLL {

    public RolesRepository rolesRepository;

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Role> getAllRoles(){return rolesRepository.findAll();}
}
