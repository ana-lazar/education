package ro.ubb.pm.bll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.ubb.pm.bll.roles.RoleBLL;
import ro.ubb.pm.dal.RolesRepository;
import ro.ubb.pm.model.Role;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RoleBLL.class)
@ExtendWith(MockitoExtension.class)
class RoleBLLTest {

    List<Role> allRoles;

    @MockBean
    private RolesRepository roleRepository;

    @InjectMocks
    RoleBLL roleBLL;


    @BeforeEach
    void setUp() {

        // Mock some repository data in order not to have to connect to the real database
        Role role1 = new Role();
        role1.setId(1);

        Role role2 = new Role();
        role1.setId(2);

        Role role3 = new Role();
        role1.setId(3);

        allRoles = new ArrayList<>();
        allRoles.add(role1);
        allRoles.add(role2);
        allRoles.add(role3);

        // mock the behaviour for get all
        Mockito.when(roleRepository.findAll()).thenReturn(allRoles);
    }

    @Test
    void getAllRoles() {
        assertEquals(roleBLL.getAllRoles().size(),3);
        assertEquals(roleBLL.getAllRoles(),allRoles);
    }
}