package resolvers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import resolvers.Exceptions.CallNotPossibleException;
import utils.PathLocations;
import entities.Coordinate;

public class APICaller
{
    public static Coordinate getCoordinates(String postcode) throws CallNotPossibleException
    {
        if (!CallRateAdmin.canRequest())
        {
            throw new CallNotPossibleException("Call is not possible for postcode: " + postcode + ". Too many requests!");
        }
        String response = "";
        try
        {
            // Create URL object
            URL url = new URL(PathLocations.API_URL);

            // Create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonData = getJsonData(postcode);

            try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream()))
            {
                byte[] dataBytes = jsonData.getBytes();
                outputStream.write(dataBytes);
                outputStream.flush();
            }

            response = getResponseContent(connection);

            // Disconnect the HttpURLConnection
            connection.disconnect();
        }
        catch (IOException e)
        {
            throw new CallNotPossibleException(e.getMessage());
        }

        return generateCoordinates(response);
    }

    // Probably rename to toCoordinates
    // Do we want a Json library for this or is too much?
    private static Coordinate generateCoordinates(String response)
    {
        double latitude = 0.0;
        double longitude = 0.0;

        Pattern latitudePattern = Pattern.compile("\"latitude\":\\s*\"([^\"]+)\"");
        Pattern longitudePattern = Pattern.compile("\"longitude\":\\s*\"([^\"]+)\"");

        Matcher latitudeMatcher = latitudePattern.matcher(response);
        if (latitudeMatcher.find())
        {
            latitude = Double.parseDouble(latitudeMatcher.group(1));
        }

        Matcher longitudeMatcher = longitudePattern.matcher(response);
        if (longitudeMatcher.find())
        {
            longitude = Double.parseDouble(longitudeMatcher.group(1));
        }

        return new Coordinate(latitude, longitude);
    }

    // Probably rename to toJsonData
    private static String getJsonData(String postcode)
    {
        return "{ \"postcode\": \"" + postcode + "\" }";
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null)
        {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}
