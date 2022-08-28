package tasks.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tasks.model.ArrayTaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateServiceTest {
    // Annotations used: Tag, DisplayName, RepeatedTest, Timeout, ParameterizedTest, ValueSource

    DateService dateService;
    TasksService tasksService;
    ArrayTaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);
        dateService = new DateService(tasksService);
    }

    @AfterEach
    void tearDown() {}

    private Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(date);
    }

    // TC1_ECP
    // input: time = "12:00", date = "2022.03.14 11:11:11"
    // output: "2022.03.14 12:00:00"
    @Test
    @Tag("valid")
    @DisplayName("Valid time and non null date time test")
    void dateMergeWithTimeValidCaseNonNullDatetime() throws ParseException {
        assertEquals(
                dateService.getDateMergedWithTime("12:00", parseDate("2022.03.14 11:11:11")),
                parseDate("2022.03.14 12:00:00"),
                "getDateMergeWithTime should return: 2022.03.14 12:00:00"
        );
    }

    // TC2_ECP
    // input: time = "12:00", date = "2022.03.14 00:00:00"
    // output: "2022.03.14 12:00:00"
    @Tag("valid")
    @RepeatedTest(3)
    void dateMergeWithTimeValidCaseNullDatetime() throws ParseException {
        assertEquals(
                dateService.getDateMergedWithTime("12:00", parseDate("2022.03.14 00:00:00")),
                parseDate("2022.03.14 12:00:00"),
                "getDateMergeWithTime should return: 2022.03.14 12:00:00"
        );
    }

    // TC4_ECP
    // input: time = "500:00", date = "2022.03.14 00:00:00"
    // output: throws IllegalArgumentException
    @Test
    @Tag("invalid")
    @Timeout(5)
    void dateMergeWithTimeInvalidTimeException() throws ParseException {
        assertThrows(
                IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime("500:00", parseDate("2022.03.14 00:00:00")),
                "getDateMergeWithTime should throw IllegalArgumentException"
        );
    }

    // TC3_BVA
    // input: time = "10:00", date = "2022.03.14 11:11:11"
    // output: "2022.03.14 10:00:00"
    @Tag("valid")
    @ParameterizedTest
    @ValueSource(strings = { "2022.03.14 11:11:11", "2022.03.14 00:00:00", "2022.03.14 12:20:55" })
    void dateMergeWithTimeValidCaseZeroMinutes(String date) throws ParseException {
        assertEquals(
                dateService.getDateMergedWithTime("10:00", parseDate(date)),
                parseDate("2022.03.14 10:00:00"),
                "getDateMergeWithTime should return: 2022.03.14 10:00:00"
        );
    }

    // TC8_BVA
    // input: time = "00:12", date = "2022.03.14 11:11:11"
    // output: "2022.03.14 00:12:00"
    @Test
    @Tag("valid")
    void dateMergeWithTimeValidCaseHourZero() throws ParseException {
        assertEquals(
                dateService.getDateMergedWithTime("00:12", parseDate("2022.03.14 11:11:11")),
                parseDate("2022.03.14 00:12:00"),
                "getDateMergeWithTime should return: 2022.03.14 00:12:00"
        );
    }

    // TC1_BVA
    // input: time = "", date = "2022.03.14 11:11:11"
    // output: throws IllegalArgumentException
    @Test
    @Tag("invalid")
    void dateMergeWithTimeEmptyTimeException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime("", parseDate("2022.03.14 11:11:11")),
                "getDateMergeWithTime should throw IllegalArgumentException"
        );
    }

    // TC18_BVA
    // input: time = "20:61", date = "2022.03.14 11:11:11"
    // output: throws IllegalArgumentException
    @Test
    @Tag("invalid")
    void dateMergeWithTimeMinutesExceededException() throws ParseException {
        assertThrows(
                IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime("20:61", parseDate("2022.03.14 11:11:11")),
                "getDateMergeWithTime should throw IllegalArgumentException"
        );
    }
}
