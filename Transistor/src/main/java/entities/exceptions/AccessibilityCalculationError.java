package entities.exceptions;

public class AccessibilityCalculationError extends Exception{
    public AccessibilityCalculationError() {
        super(); // Call the constructor of the superclass (Exception)
    }
    public AccessibilityCalculationError(String message) {
        super(message); // Call the constructor of the superclass (Exception) with the provided message
    }
}
