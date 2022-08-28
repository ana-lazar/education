package services;

public class ServerException extends RuntimeException {
    public ServerException(String s) {
        super(s);
    }

    public ServerException(Exception e) {
        super(e);
    }
}