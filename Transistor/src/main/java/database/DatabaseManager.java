package database;

import database.queries.ClosestStopsQuery;
import database.queries.QueryObject;
import database.queries.TestQuery;
import database.queries.TransitMapData;
import entities.UserConfig;
import file_system.FileManager;
import utils.PathLocations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.gtfs.*;

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

    public List<GDisplayRoute> getRoutesForTransitMap() throws SQLException
    {
        HashMap<Integer, GDisplayRoute> routesMap = new HashMap<>();
        ArrayList<GDisplayRoute> routes = new ArrayList<>();

        ResultSet resultSet = instance.executeStatement(new TransitMapData());
        int columns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next())
        {
            int route_id = 0;
            int trip_id = 0;
            String route_long_name = "";
            int shape_pt_sequence = 0;
            double shape_pt_lat = 0.0;
            double shape_pt_lon = 0.0;

            for (int i = 1; i <= columns; i++)
            {
                String colval = resultSet.getString(i);
                switch (i)
                {
                    case 1:
                        route_id = Integer.parseInt(colval);
                        break;
                    case 2:
                        trip_id = Integer.parseInt(colval);
                        break;
                    case 3:
                        route_long_name = colval;
                        break;
                    case 4:
                        shape_pt_sequence = Integer.parseInt(colval);
                        break;
                    case 5:
                        shape_pt_lat = Double.parseDouble(colval);
                        break;
                    case 6:
                        shape_pt_lon = Double.parseDouble(colval);
                        break;
                }
            }

            GShapePoint newShapePoint = new GShapePoint(shape_pt_sequence, shape_pt_lat, shape_pt_lon);

            if (!routesMap.containsKey(trip_id))
            {
                GDisplayRoute newRoute = new GDisplayRoute(route_id, trip_id, route_long_name);
                newRoute.addShapePoint(newShapePoint);
                routesMap.put(trip_id, newRoute);
            }
            else
            {
                routesMap.get(trip_id).addShapePoint(newShapePoint);
            }
        }

        for (GDisplayRoute route : routesMap.values())
        {
            routes.add(route);
        }
        return routes;
    }

    public static void newTest() throws SQLException
    {
        System.out.println("newtest");
        DatabaseManager dbm = DatabaseManager.getInstance();
        List<GDisplayRoute> routes = dbm.getRoutesForTransitMap();
        for (GDisplayRoute route : routes)
        {
            System.out.println(route.toString());
        }
    }

    public static void test() throws SQLException
    {
        var instance = DatabaseManager.getInstance();
        var result = instance.executeStatement(new ClosestStopsQuery(10, 50.83944169214228, 5.715266844267379));
        
        while (result.next())
        {
            var count = result.getMetaData().getColumnCount();
            var printData = "";
            for (int i = 1; i <= count; i++)
            {
                printData += result.getString(i) + " ";
            }
            System.out.println(printData);
        }
    }
    public static void main(String[] args) throws SQLException
    {
        newTest();
    }
}