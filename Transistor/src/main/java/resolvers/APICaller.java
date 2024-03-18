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

import utils.Coordinates;

public class APICaller
{
    private static final String API_URL = "https://computerscience.dacs.unimaas.nl/get_coordinates";
    // TODO restrict api calls

    public static void main(String[] args)
    {
        Coordinates cr = APICaller.call("6227BP");
        System.out.println(cr.getLatitude() + "/" + cr.getLongitude());
    }

    public static Coordinates call(String postcode)
    {
        String response = "";
        try
        {
            // Create URL object
            URL url = new URL(API_URL);

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
            e.printStackTrace();
        }
        System.out.println(response);
        return generateCoordinates(response);

    }

    private static Coordinates generateCoordinates(String response)
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

        return new Coordinates(latitude, longitude);
    }

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
