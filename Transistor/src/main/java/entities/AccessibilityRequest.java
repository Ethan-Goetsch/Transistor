package entities;

public record AccessibilityRequest(String postalCode, boolean disabledPersonSetting, int locationNumberSensitivity, TransportType transportType) implements Request
{

}