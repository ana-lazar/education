package tasks.model;

import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksOperationsTest {
    Task task;
    ArrayList<Task> tasks;
    TasksOperations tasksOps;

    @BeforeEach
    void setUp() throws ParseException {
        tasks = new ArrayList<>();
        for (int i = 10; i < 28; i++) {
            task = new Task("Task" + i, parseDate("2022.01." + i + " 10:00:00"), parseDate("2022.02." + i + " 10:00:00"), 1);
            task.setActive(true);
            tasks.add(task);
        }
        tasksOps = new TasksOperations(tasks);
    }

    @AfterEach
    void tearDown() {
        tasks.clear();
    }

    private Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(date);
    }

    // F02_TC01
    // input: start = null, end = "2022.01.12 00:00:00"
    // output: []
    @Test
    void incomingStartDateIsNull() throws ParseException {
        Iterable<Task> result = tasksOps.incoming(null, parseDate("2022.01.12 00:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, taskList.size(), "incoming should return empty list");
    }

    // F02_TC02
    // input: start = "2022.01.10 00:00:00", end = null
    // output: []
    @Test
    void incomingEndDateIsNull() throws ParseException {
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), null);
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, taskList.size(), "incoming should return empty list");
    }

    // F02_TC03
    // input: start = "2022.01.10 00:00:00", end = "2022.01.12 00:00:00"
    // output: []
    @Test
    void incomingEmptyTasksList() throws ParseException {
        tasksOps.tasks.clear();
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), parseDate("2022.01.12 00:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, taskList.size(), "incoming should return empty list");
    }

    // F02_TC04
    // input: start = "2022.01.10 00:00:00", end = "2022.02.28 00:00:00"
    // output: all tasks
    @Test
    void incomingNextTimeAfterEnd() throws ParseException {
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), parseDate("2022.01.01 00:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, taskList.size(), "incoming should return tasks list");
    }

    // F02_TC05
    // input: start = "2022.01.10 00:00:00", end = "2022.02.28 00:00:00"
    // output: all tasks
    @Test
    void incomingNextTimeBeforeEnd() throws ParseException {
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), parseDate("2022.02.28 00:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(18, taskList.size(), "incoming should return tasks list");
    }

    // F02_TC06
    // input: start = "2022.01.10 00:00:00", end = "2022.01.12 10:00:00"
    // output: [t1]
    @Test
    void incomingNextTimeEqualsEnd() throws ParseException {
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), parseDate("2022.01.10 10:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(1, taskList.size(), "incoming should return the first task");
    }

    // F02_TC07
    // input: start = "2022.01.10 00:00:00", end = "2022.01.12 10:00:00"
    // output: []
    @Test
    void incomingNextTimeIsNull() throws ParseException {
        for (Task task : tasksOps.tasks) {
            task.setActive(false);
        }
        Iterable<Task> result = tasksOps.incoming(parseDate("2022.01.10 00:00:00"), parseDate("2022.01.10 10:00:00"));
        List<Task> taskList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, taskList.size(), "incoming should return empty list");
    }
}
