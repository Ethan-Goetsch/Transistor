package entities;

public record AccessibilityRequest(String postalCode, boolean disabledPersonSetting) implements Request
{

}