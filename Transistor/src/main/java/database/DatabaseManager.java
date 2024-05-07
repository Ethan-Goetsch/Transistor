package database;

import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseManager
{
    private static DatabaseManager instance;
    private static Connection connection;

    private DatabaseManager()
    {
        try
        {
            var userConfig = FileManager.readData(PathLocations.CREDENTIALS_FILE, UserConfig.class);
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(userConfig.url(), userConfig.username(), userConfig.password());
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

    public ResultSet executeStatement(String statementContent){
        try{
            var statement = connection.prepareStatement(statementContent);
            return statement.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args)
    {
        var instance = DatabaseManager.getInstance();
    }
}