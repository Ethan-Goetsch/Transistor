package database.queries;

import database.DatabaseManager;
import entities.Coordinate;
import entities.transit.TransitNode;
import entities.transit.shapes.StopShape;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GetAllStopsForTrip extends ResultQuery<List<TransitNode>>
{
    private final int tripId, originSequence, destinationSequence;

    public GetAllStopsForTrip(int tripId, int originStopSequence, int destinationStopSequence)
    {
        this.tripId = tripId;
        this.originSequence = originStopSequence;
        this.destinationSequence = destinationStopSequence;
    }

    @Override
    public String getStatement()
    {
        return "select  " +
                "   stops.stop_id, " +
                "   stop_name, " +
                "   stop_lat, " +
                "   stop_lon, " +
                "   arrival_time, " +
                "   departure_time " +
                "from transitorgtfs.stop_times " +
                "inner join transitorgtfs.stops " +
                "   on stop_times.stop_id  = stops.stop_id " +
                "where trip_id = ? " +
                "and stop_sequence between ? and ?";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, tripId);
        statement.setInt(2, originSequence);
        statement.setInt(3, destinationSequence);
    }

    @Override
    public List<TransitNode> readResult(ResultSet resultSet)
    {
        try
        {
            List<TransitNode> nodes = new ArrayList<>();
            while (resultSet.next())
            {
                var coordinate = new Coordinate(resultSet.getDouble(3), resultSet.getDouble(4));
                var arrivalTime = LocalTime.parse(resultSet.getString(5));
                var departureTime = LocalTime.parse(resultSet.getString(6));
                var node = new TransitNode(resultSet.getInt(1),
                        resultSet.getString(2),
                        coordinate,
                        arrivalTime,
                        departureTime,
                        new StopShape(coordinate));
                nodes.add(node);
            }
            return nodes;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        var test = new GetAllStopsForTrip(178414978, 0, 999);
        System.out.println(test.getStatement());
        //System.out.println(DatabaseManager.executeAndReadQuery(new GetAllStopsForTrip(178414978, 16, 18)));
    }
}