package rest.contest.myjdbc;

public class MyJdbcException extends RuntimeException {
    public MyJdbcException(Exception exception) {
        super(exception);
    }

    public MyJdbcException(String message) {
        super(message);
    }
}
