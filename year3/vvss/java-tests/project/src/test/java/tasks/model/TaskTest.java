package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Objects;

class TaskTest {
    private Task task1;

    @BeforeEach
    void setUp() {
        try {
            task1 = new Task("new task",Task.getDateFormat().parse("2021-02-12 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() { }

    @Test
    void testTaskCreation() throws ParseException {
       assert Objects.equals(task1.getTitle(), "new task");
       assert task1.getFormattedDateStart().equals(Task.getDateFormat().format(Task.getDateFormat().parse("2021-02-12 10:10")));
    }

    @Test
    void isRepeatedFalse() {
        assert !task1.isRepeated();
    }

    @Test
    void isActiveFalse() {
        assert !task1.isActive();
    }
}