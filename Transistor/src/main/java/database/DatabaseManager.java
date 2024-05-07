package database;

import database.queries.NearestBusStopsQuery;
import database.queries.NearestBussesQuery;
import database.queries.QueryObject;
import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalTime;

public class DatabaseManager
{
    private static DatabaseManager instance;
    private static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private DatabaseManager()
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

    public static DatabaseManager getInstance()
    {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public ResultSet executeStatement(QueryObject query)
    {
        return executeStatement(query.getStatement());
    }

    public ResultSet executeStatement(String statementContent)
    {
        try
        {
            var statement = connection.prepareStatement(statementContent);
            return statement.executeQuery();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args)
    {
        var instance = DatabaseManager.getInstance();
        ResultSet rs = instance.executeStatement(new NearestBusStopsQuery(new double[]{51.9307,51.932576}, new double[]{4.40,4.403}).getStatement());
        try{
            while ( rs.next() ) {
                int arrival = rs.getInt(1);
                System.out.println(arrival);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}