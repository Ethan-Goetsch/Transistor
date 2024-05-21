package resolvers;

import entities.Coordinate;
import entities.exceptions.*;
import utils.PathLocations;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APICaller {

    public static Coordinate getCoordinates(String postcode) throws CallNotPossibleException, PostcodeNotFoundException, InvalidCoordinateException, NetworkErrorException, RateLimitExceededException
    {
        if (!CallRateAdmin.canRequest()) {
            throw new CallNotPossibleException("Call is not possible for postcode: " + postcode + ". Too many requests!");
        }

        String response = "";
        try {
            // Create URL object
            URL url = new URL(PathLocations.API_URL);

            // Create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonData = getJsonData(postcode);

            try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream())) {
                byte[] dataBytes = jsonData.getBytes();
                outputStream.write(dataBytes);
                outputStream.flush();
            }

            if (connection.getResponseCode() == 429) {
                throw new RateLimitExceededException("Rate limit exceeded for postcode: " + postcode);
            }

            response = getResponseContent(connection);
            // Disconnect the HttpURLConnection
            connection.disconnect();
        } catch (SocketTimeoutException e) {
            throw new NetworkErrorException("Network timeout occurred for postcode: " + postcode);
        } catch (IOException e) {
            throw new NetworkErrorException("Network error occurred for postcode: " + postcode + ". " + e.getMessage());
        }

        // Check if the response contains coordinates
        if (response.contains("\"latitude\"") && response.contains("\"longitude\"")) {
            return generateCoordinates(response);
        } else {
            throw new PostcodeNotFoundException("Coordinates not found for postcode: " + postcode);
        }
    }

    private static Coordinate generateCoordinates(String response) throws InvalidCoordinateException {
        double latitude = 0.0;
        double longitude = 0.0;

        Pattern latitudePattern = Pattern.compile("\"latitude\":\\s*\"([^\"]+)\"");
        Pattern longitudePattern = Pattern.compile("\"longitude\":\\s*\"([^\"]+)\"");

        Matcher latitudeMatcher = latitudePattern.matcher(response);
        if (latitudeMatcher.find()) {
            try {
                latitude = Double.parseDouble(latitudeMatcher.group(1));
            } catch (NumberFormatException e) {
                throw new InvalidCoordinateException("Invalid latitude format in response: " + response);
            }
        }

        Matcher longitudeMatcher = longitudePattern.matcher(response);
        if (longitudeMatcher.find()) {
            try {
                longitude = Double.parseDouble(longitudeMatcher.group(1));
            } catch (NumberFormatException e) {
                throw new InvalidCoordinateException("Invalid longitude format in response: " + response);
            }
        }

        return new Coordinate(latitude, longitude);
    }

    private static String getJsonData(String postcode) {
        return "{ \"postcode\": \"" + postcode + "\" }";
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}