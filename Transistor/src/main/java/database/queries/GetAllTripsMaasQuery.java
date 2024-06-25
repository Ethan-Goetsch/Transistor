package database.queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DatabaseManager;
import entities.Coordinate;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TShapePoint;
import entities.TransitGraphEntities.TStop;
import entities.TransitGraphEntities.TStopTimePoint;
import entities.TransitGraphEntities.TTrip;
import entities.gtfs.GDisplayRoute;
import entities.gtfs.GShapePoint;

public class GetAllTripsMaasQuery extends ResultQuery<List<TTrip>>
{
    private final Coordinate maasLowerLeft;
    private final Coordinate maasUpperRight;

    public GetAllTripsMaasQuery(Coordinate maasLowerLeft, Coordinate maasUpperRight)
    {
        this.maasLowerLeft = maasLowerLeft;
        this.maasUpperRight = maasUpperRight;
    }

    public GetAllTripsMaasQuery()
    {
        this.maasLowerLeft = new Coordinate(50.826137, 5.653699);
        this.maasUpperRight = new Coordinate(50.878915, 5.730947);
    }

    @Override
    public List<TTrip> readResult(ResultSet resultSet)
    {
        Map<Integer, TTrip> tripsMap = new HashMap<Integer, TTrip>(); // trip_id to TTrip
        Map<Integer, TStop> stopsMap = new HashMap<Integer, TStop>(); // stop_id to TStop

        try
        {
            int columns = resultSet.getMetaData().getColumnCount();
            int iter = 0;

            while (resultSet.next())
            {
                int route_id = 0;
                int trip_id = 0;
                int stop_id = 0;
                int stop_sequence = 0;
                LocalTime arrival_time = LocalTime.of(0, 0, 0);
                LocalTime departure_time = LocalTime.of(0, 0, 0);
                int shape_dist_traveled = -1;
                String stop_name = "";
                double stop_lat = 0.0;
                double stop_lon = 0.0;
                int shape_id = -1;
                String route_short_name = "";
                String route_long_name = "";

                for (int i = 1; i <= columns; i++)
                {
                    String colval = resultSet.getString(i);
                    colval = normaliseIfTime(colval);

                    switch (i)
                    {
                        case 1:
                            route_id = Integer.parseInt(colval);
                            break;
                        case 2:
                            trip_id = Integer.parseInt(colval);
                            break;
                        case 3:
                            stop_id = Integer.parseInt(colval);
                            break;
                        case 4:
                            stop_sequence = Integer.parseInt(colval);
                            break;
                        case 5:
                            arrival_time = LocalTime.parse(colval);
                            break;
                        case 6:
                            departure_time = LocalTime.parse(colval);
                            break;
                        case 7:
                            if (colval != null)
                            {
                                shape_dist_traveled = Integer.parseInt(colval);
                            }
                            break;
                        case 8:
                            stop_name = colval;
                            break;
                        case 9:
                            stop_lat = Double.parseDouble(colval);
                            break;
                        case 10:
                            stop_lon = Double.parseDouble(colval);
                            break;
                        case 11:
                            if (colval != null)
                            {
                                shape_id = Integer.parseInt(colval);
                            }
                            break;
                        case 12:
                            route_short_name = colval;
                            break;
                        case 13:
                            route_long_name = colval;
                            break;
                    }
                }

                if (!stopsMap.containsKey(stop_id))
                {
                    stopsMap.put(stop_id, new TStop(stop_id, stop_name, new Coordinate(stop_lat, stop_lon)));
                }
                TStop newStop = stopsMap.get(stop_id);

                if (!tripsMap.containsKey(trip_id))
                {
                    tripsMap.put(trip_id, new TTrip(trip_id, route_id, route_short_name, route_long_name, new ArrayList<TStopTimePoint>(), new TShape(shape_id, new ArrayList<TShapePoint>())));    
                }
                TTrip newTrip = tripsMap.get(trip_id);
                
                TStopTimePoint newStopTimePoint = new TStopTimePoint(stop_sequence, newStop, arrival_time, departure_time, shape_dist_traveled);

                newTrip.getStopTimePoints().add(newStopTimePoint);

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        List<TTrip> tripsList = new ArrayList<TTrip>(tripsMap.values());
        Map<Integer, TShape> shapesMap = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasSubQuery(maasLowerLeft, maasUpperRight));
        
        for (TTrip trip : tripsList)
        {
            TShape shapeToSet = shapesMap.get(trip.getShape().getId());
            if (shapeToSet == null)
            {
                shapeToSet = generateShapeFromStopPoints(trip);
            }
            trip.setShape(shapeToSet);
        }

        // remove trips without a shape
        //tripsList.removeIf(trip -> (trip.getShape().getShapePoints() == null || trip.getShape().getShapePoints().isEmpty()));
        tripsList.removeIf(trip -> trip.getShape().getId() == -1);

        return tripsList;
    }

    private TShape generateShapeFromStopPoints(TTrip trip)
    {
        TShape newShape = new TShape(-1, new ArrayList<TShapePoint>());
        for (TStopTimePoint stopPoint : trip.getStopTimePoints())
        {
            stopPoint.setShapeDistTraveled(stopPoint.getSequence());
            TShapePoint newShapePoint = new TShapePoint(stopPoint.getSequence(), stopPoint.getStop().getCoordinates(), stopPoint.getShapeDistTraveled());  
            newShape.getShapePoints().add(newShapePoint);  
        }

        return newShape;
    }

    @Override
    public String getStatement()
    {
        return "SELECT route_id, trip_id, stop_id, stop_sequence, arrival_time, departure_time, shape_dist_traveled, stop_name, stop_lat, stop_lon, shape_id, route_short_name, route_long_name\r\n" + //
                        "FROM transitorgtfs.stop_times LEFT JOIN transitorgtfs.stops USING (stop_id) LEFT JOIN transitorgtfs.trips USING (trip_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "WHERE trip_id IN (\r\n" + //
                        "\t\t\t\t\tSELECT DISTINCT trip_id\r\n" + //
                        "\t\t\t\t\tFROM transitorgtfs.stop_times LEFT JOIN transitorgtfs.stops USING (stop_id) LEFT JOIN transitorgtfs.trips USING (trip_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "\t\t\t\t\tWHERE (stop_lat BETWEEN ? AND ?) AND (stop_lon BETWEEN ? AND ?) AND route_type = 3\r\n" + //
                        "\t\t\t\t)\r\n" + //
                        "ORDER BY trip_id ASC, stop_sequence ASC;";
    }

    private String normaliseIfTime(String string)
    {
        if (string == null)
        {
            return null;    
        }

        String regex24 = "^24:\\d{2}:\\d{2}$";
        Pattern pattern24 = Pattern.compile(regex24);
        Matcher matcher24 = pattern24.matcher(string);

        String regex25 = "^25:\\d{2}:\\d{2}$";
        Pattern pattern25 = Pattern.compile(regex25);
        Matcher matcher25 = pattern25.matcher(string);

        if (matcher24.matches())
        {
            return "00" + string.substring(2);
        }
        else if (matcher25.matches())
        {
            return "01" + string.substring(2);
        }
        else
        {
            return string;
        }
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
        List<TTrip> trips = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasQuery());
        for (TTrip tTrip : trips)
        {
            System.out.println(tTrip.toString());    
        }
        System.out.println(trips.size());
    }
}