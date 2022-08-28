package tasks.integration;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.text.ParseException;
import java.util.Objects;

public class TaskIntegrationTest {
    private Task task;
    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;

    @BeforeEach
    void setUp() throws ParseException {
        task = new Task("new task", Task.getDateFormat().parse("2021-02-12 10:10"));
        arrayTaskList = new ArrayTaskList();
        tasksService = new TasksService(arrayTaskList);
    }

    @AfterEach
    void tearDown() {
        task = null;
        arrayTaskList = null;
        tasksService = null;
    }

    @Test
    void getObservableListValid() throws ParseException {
        arrayTaskList.add(task);
        ObservableList<Task> result = tasksService.getObservableList();
        assert result.stream().filter(t -> Objects.equals(t.getTitle(), task.getTitle()) && t.getTime() == task.getTime()).count() == 1;
    }

    @Test
    void getObservableListInvalid() {
        ObservableList<Task> result = tasksService.getObservableList();
        assert (long) result.size() == 0;
    }
}
