package database.queries;

import database.DatabaseManager;
import entities.transit.TransitTrip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTripBetweenTwoStopsQuery extends ResultQuery<TransitTrip>
{
    private final int originStopId;
    private final int destinationStopId;

    public GetTripBetweenTwoStopsQuery(int originStopId, int destinationStopId)
    {
        this.originStopId = originStopId;
        this.destinationStopId = destinationStopId;
    }

    @Override
    public String getStatement()
    {
        return "SELECT DISTINCT destination.trip_id" +
                "    FROM transitorgtfs.stop_times AS origin " +
                "    INNER JOIN transitorgtfs.stop_times AS destination " +
                "        ON destination.trip_id = origin.trip_id " +
                "        AND destination.stop_id = ? " +
                "    INNER JOIN transitorgtfs.stops AS origin_stop " +
                "        ON origin_stop.stop_id = origin.stop_id " +
                "    INNER JOIN transitorgtfs.stops AS destination_stop " +
                "        ON destination_stop.stop_id = destination.stop_id " +
                "    WHERE origin.stop_id = ? " +
                "        AND origin.stop_sequence < destination.stop_sequence;";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, destinationStopId);
        statement.setInt(2, originStopId);
    }

    @Override
    public TransitTrip readResult(ResultSet resultSet)
    {
        try
        {
            resultSet.next();
            return new TransitTrip(resultSet.getInt(1));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        System.out.println(DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578413, 2578366)));
    }
}