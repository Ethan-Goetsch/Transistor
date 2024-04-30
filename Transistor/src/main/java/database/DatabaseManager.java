package database;

import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager
{
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager()
    {
        try
        {
            var userConfig = FileManager.readData(PathLocations.CREDENTIALS_FILE, UserConfig.class);
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(userConfig.url(), userConfig.username(), userConfig.password());

            var statement = connection.prepareStatement(
                    "SELECT * " +
                        "FROM transitorgtfs.agency ");
            var result = statement.executeQuery();
            while (result.next())
            {
                var string = result.getString(1);
                System.out.println(string);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance()
    {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public static void main(String[] args)
    {
        var instance = DatabaseManager.getInstance();
    }
}