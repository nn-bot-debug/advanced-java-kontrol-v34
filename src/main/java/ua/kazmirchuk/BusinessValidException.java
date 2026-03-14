package ua.kazmirchuk;

public class BusinessValidException extends RuntimeException {
    public BusinessValidException(String message) {
        super(message);
    }
}
