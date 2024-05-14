package database;

import database.queries.*;
import entities.Coordinate;
import entities.UserConfig;
import entities.transit.TransitShape;
import entities.transit.TransitStop;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean execute(QueryObject query)
    {
        return query.execute(connection);
    }

    public boolean execute(String query)
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

    public<T> T executeAndReadQuery(ResultQuery<T> query)
    {
        return query.readResult(query.executeQuery(connection));
    }

    public ResultSet executeQuery(QueryObject query)
    {
        return query.executeQuery(connection);
    }

    public ResultSet executeQuery(String query)
    {
        try
        {
            var statement = connection.prepareStatement(query);
            return statement.executeQuery();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> GetTrip(int originStopId, int destinationStopId)
    {
        return executeAndReadQuery(new GetTripBetweenTwoStopsQuery(originStopId, destinationStopId));
    }

    public List<TransitShape> GetPath(int tripId)
    {
        return executeAndReadQuery(new GetPathForTripQuery(tripId));
    }

    public List<TransitStop> getStop(Coordinate coordinate, double radius)
    {
        return executeAndReadQuery(new NearestBusStopsQuery(coordinate, radius));
    }
}