package database;

import database.queries.*;
import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager
{
    private static DatabaseManager instance;
    private static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                var userConfig = FileManager.readData(PathLocations.CREDENTIALS_FILE, UserConfig.class);
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(userConfig.url(), userConfig.username(), userConfig.password());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static Connection getConnection(String pathFile)
    {
        if (connection == null)
        {
            try
            {
                var userConfig = FileManager.readData(pathFile, UserConfig.class);
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(userConfig.url(), userConfig.username(), userConfig.password());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static boolean execute(QueryObject query)
    {
        return query.execute(connection);
    }

    public static boolean execute(String query)
    {
        try
        {
            var statement = connection.prepareStatement(query);
            return statement.execute();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static<T> T executeAndReadQuery(ResultQuery<T> query)
    {
        return query.readResult(query.executeQuery(getConnection()));
    }

    public static ResultSet executeQuery(QueryObject query)
    {
        return query.executeQuery(getConnection());
    }

    public static ResultSet executeQuery(String query)
    {
        try
        {
            var statement = getConnection().prepareStatement(query);
            return statement.executeQuery();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}