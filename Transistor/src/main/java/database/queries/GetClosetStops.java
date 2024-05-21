package database.queries;

import entities.Coordinate;
import entities.transit.TransitNode;
import entities.transit.TransitStop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetClosetStops extends ResultQuery<List<TransitStop>>
{
    private final Coordinate coordinate;
    private final int limit;

    public GetClosetStops(Coordinate coordinate)
    {
        this.coordinate = coordinate;
        this.limit = 1;
    }

    public GetClosetStops(Coordinate coordinate, int limit)
    {
        this.coordinate = coordinate;
        this.limit = limit;
    }

    @Override
    public String getStatement()
    {
        return "SELECT " +
                "    stop_id, " +
                "    stop_lat, " +
                "    stop_lon, " +
                "    SQRT(POWER(stop_lat - ?, 2) + POWER(stop_lon - ?, 2)) AS distance " +
                "FROM " +
                "    transitorgtfs.stops " +
                "ORDER BY " +
                "    distance ASC " +
                "LIMIT ?";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setDouble(1, coordinate.getLatitude());
        statement.setDouble(2, coordinate.getLongitude());
        statement.setInt(3, limit);
    }

    @Override
    public List<TransitStop> readResult(ResultSet resultSet)
    {
        try
        {
            var stops = new ArrayList<TransitStop>();
            while (resultSet.next())
            {
                stops.add(new TransitStop(resultSet.getInt(1),
                        new Coordinate(resultSet.getDouble(2), resultSet.getDouble(3))));
            }
            return stops;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}