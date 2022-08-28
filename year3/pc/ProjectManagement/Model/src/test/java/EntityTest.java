import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.pm.model.Entity;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity entity;

    @BeforeEach
    void setUp() {
        entity = new Entity();
    }

    @Test
    void getSetId() {
        entity.setId(1);
        assertEquals(entity.getId(),1);
        entity.setId(-1);
        assertEquals(entity.getId(),-1);
    }
}