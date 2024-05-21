package entities.exceptions;

public class NetworkErrorException extends Exception {
    public NetworkErrorException(String message) {
        super(message);
    }
}