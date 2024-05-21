package database.queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.DatabaseManager;
import entities.Coordinate;
import entities.gtfs.GDisplayRoute;
import entities.gtfs.GShapePoint;

public class TransitMapDataQuery extends ResultQuery<List<GDisplayRoute>>
{
    private final Coordinate maasLowerLeft;
    private final Coordinate maasUpperRight;

    public TransitMapDataQuery()
    {
        this.maasLowerLeft = new Coordinate(50.826137, 5.653699);
        this.maasUpperRight = new Coordinate(50.878915, 5.730947);
    }

    public TransitMapDataQuery(Coordinate maasLowerLeft, Coordinate maasUpperRight)
    {
        this.maasLowerLeft = maasLowerLeft;
        this.maasUpperRight = maasUpperRight;
    }

    @Override
    public String getStatement()
    {

        return "SELECT\r\n" + //
                        "\troute_id,\r\n" + //
                        "\ttrip_id,\r\n" + //
                        "\troute_long_name,\r\n" + //
                        "\tshape_pt_sequence,\r\n" + //
                        "\tshape_pt_lat,\r\n" + //
                        "\tshape_pt_lon\r\n" + //
                        "FROM\r\n" + //
                        "\ttransitorgtfs.shapes LEFT JOIN transitorgtfs.trips USING(shape_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "WHERE trip_id IN (\r\n" + //
                        "\t\tSELECT\r\n" + //
                        "\t\t\ttrip_id\r\n" + //
                        "\t\tFROM (\r\n" + //
                        "\t\t\tSELECT\r\n" + //
                        "\t\t\t\t*,\r\n" + //
                        "\t\t\t\tROW_NUMBER() OVER(PARTITION BY route_id ORDER BY trip_id DESC) AS row_num\r\n" + //
                        "\t\t\tFROM\r\n" + //
                        "\t\t\t\ttransitorgtfs.stop_times LEFT JOIN transitorgtfs.stops USING (stop_id) LEFT JOIN transitorgtfs.trips USING (trip_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "\t\t\tWHERE\r\n" + //
                        "\t\t\t\t(stop_lat BETWEEN ? AND ?) AND (stop_lon BETWEEN ? AND ?) AND shape_id IS NOT NULL AND route_type = 3\r\n" + //
                        "\t\t) unique_trips_in_maas\r\n" + //
                        "\t\tWHERE row_num = 1\r\n" + //
                        ")\r\n" + //
                        "ORDER BY trip_id ASC, shape_pt_sequence ASC;";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setDouble(1, maasLowerLeft.getLatitude());
        statement.setDouble(2, maasUpperRight.getLatitude());

        statement.setDouble(3, maasLowerLeft.getLongitude());
        statement.setDouble(4, maasUpperRight.getLongitude());
    }

    @Override
    public List<GDisplayRoute> readResult(ResultSet resultSet)
    {
        HashMap<Integer, GDisplayRoute> routesMap = new HashMap<>();
        ArrayList<GDisplayRoute> routesWithShape = new ArrayList<>();

        try
        {
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
                routesWithShape.add(route);
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        List<GDisplayRoute> routesWithShapeAndStops = DatabaseManager.executeAndReadQuery(new TransitMapDataSubQuery(maasLowerLeft, maasUpperRight));

        for (GDisplayRoute route : routesWithShapeAndStops)
        {
            for (GDisplayRoute shapeRoute : routesWithShape)
            {
                if (shapeRoute.getRouteid() == route.getRouteid())
                {
                    route.setShapePoints(shapeRoute.getShapePoints());
                }
            }
        }

        return routesWithShapeAndStops;
    }
    public static void main(String[] args)
    {
        List<GDisplayRoute> routes = DatabaseManager.executeAndReadQuery(new TransitMapDataQuery());
        for (GDisplayRoute gDisplayRoute : routes)
        {
            System.out.println(gDisplayRoute.toString());    
        }
    }
}
