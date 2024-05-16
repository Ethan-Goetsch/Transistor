package database.queries;

import entities.Coordinate;
import entities.transit.shapes.PathShape;
import entities.transit.shapes.TransitShape;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllShapesBetweenStops extends ResultQuery<List<TransitShape>>
{
    private final int tripId, startSequence, stopSequence;

    public GetAllShapesBetweenStops(int tripId, int startSequence, int stopSequence)
    {
        this.tripId = tripId;
        this.startSequence = startSequence;
        this.stopSequence = stopSequence;
    }

    @Override
    public String getStatement()
    {
        return "SELECT " +
                "   shapes.shape_id, " +
                "   shape_pt_lat, " +
                "   shape_pt_lon " +
                "FROM transitorgtfs.shapes " +
                "INNER JOIN transitorgtfs.trips on trips.shape_id = shapes.shape_id " +
                "WHERE " +
                "   trips.trip_id = ? " +
                "   and shape_pt_sequence > ? and shape_pt_sequence < ?";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, tripId);
        statement.setInt(2, startSequence);
        statement.setInt(3, stopSequence);
    }

    @Override
    public List<TransitShape> readResult(ResultSet resultSet)
    {
        try
        {
            List<TransitShape> shapes = new ArrayList<>();
            while (resultSet.next())
            {
                var coordinate = new Coordinate(resultSet.getDouble(2), resultSet.getDouble(3));
                var node = new PathShape(resultSet.getInt(1), coordinate);
                shapes.add(node);
            }
            return shapes;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}