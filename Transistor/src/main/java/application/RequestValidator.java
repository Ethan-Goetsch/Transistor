package application;

import entities.RouteRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidator
{
    private final String pattern = "\\d{4}[A-Z|a-z]{2}";
    private final int length = 6;

    public boolean isValidRequest(RouteRequest request)
    {
        return isValidInput(request.departure(), request.arrival());
    }

    private boolean isValidInput(String departurePostcode, String arrivalPostCode)
    {
        // Compile the pattern
        Pattern regex = Pattern.compile(pattern);

        // Match departure postcode against the pattern
        Matcher departureMatcher = regex.matcher(departurePostcode);

        // Match destination postcode against the pattern
        Matcher destinationMatcher = regex.matcher(arrivalPostCode);
        departurePostcode.toUpperCase();
        arrivalPostCode.toUpperCase();

        departurePostcode.trim();
        arrivalPostCode.trim();
        // Return true if both departure and destination postcodes match the pattern, false otherwise
        return departureMatcher.find() && destinationMatcher.find() && length == departurePostcode.length() && length ==arrivalPostCode.length();
    }
}