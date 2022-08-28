package tasks.services;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class TasksServiceTest {
    @Mock
    private ArrayTaskList arrayTaskList;

    @Mock
    private Task task;

    @InjectMocks
    private TasksService tasksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() { }

    @Test
    void getObservableListValid() throws ParseException {
        Mockito.when(task.getTitle()).thenReturn("new task");
        Mockito.when(task.getTime()).thenReturn(Task.getDateFormat().parse("2021-02-12 10:10"));
        Mockito.when(arrayTaskList.getAll()).thenReturn(Collections.singletonList(task));
        ObservableList<Task> result = tasksService.getObservableList();
        assert result.stream().filter(t -> Objects.equals(t.getTitle(), task.getTitle()) && t.getTime() == task.getTime()).count() == 1;
    }

    @Test
    void getObservableListInvalid() {
        Mockito.when(arrayTaskList.getAll()).thenReturn(new ArrayList<>());
        ObservableList<Task> result = tasksService.getObservableList();
        assert (long) result.size() == 0;
    }
}
