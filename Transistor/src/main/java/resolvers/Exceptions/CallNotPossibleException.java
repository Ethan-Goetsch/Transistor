package resolvers.Exceptions;

public class CallNotPossibleException extends Exception{
    public CallNotPossibleException() {
        super(); // Call the constructor of the superclass (Exception)
    }
    public CallNotPossibleException(String message) {
        super(message); // Call the constructor of the superclass (Exception) with the provided message
    }
}
