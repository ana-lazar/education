import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.pm.model.Entity;
import ro.ubb.pm.model.RememberMeToken;

import static org.junit.jupiter.api.Assertions.*;

class RememberMeTokenTest {

    RememberMeToken token;

    @BeforeEach
    void setUp() {
        token = new RememberMeToken();
    }


    @Test
    void getSetEmail() {
        token.setEmail("email");
        assertEquals(token.getEmail(),"email");
        token.setEmail("");
        assertEquals(token.getEmail(),"");
    }

    @Test
    void getSetPassword() {
        token.setPassword("pass");
        assertEquals(token.getPassword(),"pass");
        token.setPassword("");
        assertEquals(token.getPassword(),"");
    }
}