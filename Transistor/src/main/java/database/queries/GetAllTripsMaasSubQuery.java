package database.queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseManager;
import entities.Coordinate;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TShapePoint;
import entities.TransitGraphEntities.TStop;
import entities.TransitGraphEntities.TStopTimePoint;
import entities.TransitGraphEntities.TTrip;

public class GetAllTripsMaasSubQuery extends ResultQuery<Map<Integer, TShape>>
{
    private final Coordinate maasLowerLeft;
    private final Coordinate maasUpperRight;
    
    public GetAllTripsMaasSubQuery(Coordinate maasLowerLeft, Coordinate maasUpperRight)
    {
        this.maasLowerLeft = maasLowerLeft;
        this.maasUpperRight = maasUpperRight;
    }

    public GetAllTripsMaasSubQuery()
    {
        this.maasLowerLeft = new Coordinate(50.826137, 5.653699);
        this.maasUpperRight = new Coordinate(50.878915, 5.730947);
    }

    @Override
    public Map<Integer, TShape> readResult(ResultSet resultSet)
    {
        Map<Integer, TShape> shapesMap = new HashMap<Integer, TShape>(); // shape_id to TShape

        try
        {
            int columns = resultSet.getMetaData().getColumnCount();
            int iter = 0;

            while (resultSet.next())
            {
                int shape_id = -1;
                int shape_pt_sequence = -1;
                double shape_pt_lat = 0.0;
                double shape_pt_lon = 0.0;
                int shape_dist_traveled = 0;

                for (int i = 1; i <= columns; i++)
                {
                    String colval = resultSet.getString(i);

                    switch (i)
                    {
                        case 1:
                            shape_id = Integer.parseInt(colval);
                            break;
                        case 2:
                            shape_pt_sequence = Integer.parseInt(colval);
                            break;
                        case 3:
                            shape_pt_lat = Double.parseDouble(colval);
                            break;
                        case 4:
                            shape_pt_lon = Double.parseDouble(colval);
                            break;
                        case 5:
                            shape_dist_traveled = Integer.parseInt(colval);
                            break;
                    }
                }

                if (!shapesMap.containsKey(shape_id))
                {
                    shapesMap.put(shape_id, new TShape(shape_id, new ArrayList<TShapePoint>()));
                }
                TShape newShape = shapesMap.get(shape_id);

                TShapePoint newShapePoint = new TShapePoint(shape_pt_sequence, new Coordinate(shape_pt_lat, shape_pt_lon), shape_dist_traveled);
                newShape.getShapePoints().add(newShapePoint);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return shapesMap;
    }

    @Override
    public String getStatement()
    {
        return "SELECT *\r\n" + //
                        "FROM transitorgtfs.shapes\r\n" + //
                        "WHERE shape_id IN\r\n" + //
                        "(\r\n" + //
                        "\tSELECT DISTINCT shape_id\r\n" + //
                        "\tFROM transitorgtfs.stop_times LEFT JOIN transitorgtfs.stops USING (stop_id) LEFT JOIN transitorgtfs.trips USING (trip_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "\tWHERE (stop_lat BETWEEN ? AND ?) AND (stop_lon BETWEEN ? AND ?) AND route_type = 3\r\n" + //
                        ")\r\n" + //
                        "ORDER BY shape_id ASC, shape_pt_sequence ASC;";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setDouble(1, maasLowerLeft.getLatitude());
        statement.setDouble(2, maasUpperRight.getLatitude());

        statement.setDouble(3, maasLowerLeft.getLongitude());
        statement.setDouble(4, maasUpperRight.getLongitude());
    }

    public static void main(String[] args)
    {
        var map = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasSubQuery());
        System.out.println(map.values().size());
    }
    
}
