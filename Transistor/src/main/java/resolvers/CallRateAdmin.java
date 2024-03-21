package resolvers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.PathLocations;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class CallRateAdmin
{
    private static Bucket bucket;

    public static boolean canRequest()
    {
        bucket = loadBucket();
        if (bucket == null || bucket.getTokenBuckets().isEmpty())
        {
            bucket = new Bucket();
        }

        var isAuthorized = bucket.getTokenBuckets()
                .stream()
                .anyMatch(TokenBucket::tryConsume);

        saveBucket();
        return isAuthorized;
    }

    private static Bucket loadBucket()
    {
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        var json = readData(PathLocations.TOKEN_BUCKET);
        return gson.fromJson(json, Bucket.class);
    }

    private static void saveBucket()
    {
        var gson = new Gson();
        var json = gson.toJson(bucket);
        storeData(json, PathLocations.TOKEN_BUCKET);
    }

    private static void storeData(String data, String filePath)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(data);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static String readData(String filePath)
    {
        StringBuilder data = new StringBuilder();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null)
                data.append(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return data.toString();
    }
}