package server;

public class InvalidResponseException extends Exception {
    public InvalidResponseException(String message) {
        super(message);
    }
}