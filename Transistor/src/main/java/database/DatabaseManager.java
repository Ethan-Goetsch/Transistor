package database;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import database.queries.NearestBusStopsQuery;
import database.queries.QueryObject;
import entities.Coordinate;
import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Integer> getStopId(Coordinate coordinate)
    {
        ArrayList<Integer> IDs = new ArrayList<>();
        double radius = 100;
        var instance = DatabaseManager.getInstance();
        while (IDs.isEmpty())
        {
            RadiusGenerator rg = new RadiusGenerator();
            double[][] bounds = rg.getRadius(coordinate, radius);
            ResultSet rs  = instance.executeStatement(new NearestBusStopsQuery(bounds[0], bounds[1]).getStatement());
            try
            {
                while (rs.next())
                {
                    int id = rs.getInt(1);
                    IDs.add(id);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            radius += 100;
        }

        return IDs;
    }
}