package resolvers.Exceptions;

public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message) {
        super(message);
    }
}