package file_system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class FileManager
{
    public static void saveData(String path, Object data)
    {
        var json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(data);
        storeData(json, path);
    }

    public static <T> T readData(String path, Class<T> type)
    {
        try
        {
            var json = readFile(path);
            return new Gson().fromJson(json, type);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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

    private static String readFile(String filePath) throws IOException
    {
        StringBuilder data = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null)
            data.append(line);

        return data.toString();
    }
}