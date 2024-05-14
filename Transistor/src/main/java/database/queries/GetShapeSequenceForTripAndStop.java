package database.queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetShapeSequenceForTripAndStop extends ResultQuery<Integer>
{
    private final int tripId;
    private final int stopId;

    public GetShapeSequenceForTripAndStop(int tripId, int stopId)
    {
        this.tripId = tripId;
        this.stopId = stopId;
    }

    @Override
    public String getStatement()
    {
        return "SELECT " +
                "    shapes.shape_pt_sequence, " +
                "    MIN(SQRT(POWER(shapes.shape_pt_lat - stops.stop_lat, 2) + POWER(shapes.shape_pt_lon - stops.stop_lon, 2))) AS distance " +
                "FROM " +
                "    transitorgtfs.shapes " +
                "JOIN " +
                "    transitorgtfs.trips ON trips.shape_id = shapes.shape_id " +
                "JOIN " +
                "    transitorgtfs.stop_times ON stop_times.trip_id = trips.trip_id " +
                "JOIN " +
                "    transitorgtfs.stops ON stops.stop_id = stop_times.stop_id " +
                "WHERE " +
                "    trips.trip_id = ? " +
                "    AND stops.stop_id = ? " +
                "GROUP BY " +
                "    shape_pt_sequence " +
                "ORDER BY " +
                "    distance ASC " +
                "LIMIT 1";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, tripId);
        statement.setInt(2, stopId);
    }

    @Override
    public Integer readResult(ResultSet resultSet)
    {
        try
        {
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}