package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

public class ArrayTaskListTest {
    @Mock
    private Task task;

    @InjectMocks
    private ArrayTaskList arrayTaskList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() { }

    @Test
    void addTaskValid() {
        Mockito.when(task.getTitle()).thenReturn("new task");
        arrayTaskList.add(task);
        assert arrayTaskList.getAll().stream().anyMatch(t -> Objects.equals(t.getTitle(), task.getTitle()));
    }

    @Test
    void removeTaskValid() {
        Mockito.when(task.getTitle()).thenReturn("new task");
        arrayTaskList.add(task);
        assert arrayTaskList.getAll().stream().anyMatch(t -> Objects.equals(t.getTitle(), task.getTitle()));
        arrayTaskList.remove(task);
        assert arrayTaskList.getAll().stream().noneMatch(t -> Objects.equals(t.getTitle(), task.getTitle()));
    }
}
