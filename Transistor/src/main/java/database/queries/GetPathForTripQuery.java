package database.queries;

import entities.Coordinate;
import entities.transit.shapes.PathShape;
import entities.transit.shapes.TransitShape;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPathForTripQuery extends ResultQuery<List<TransitShape>>
{
    private final int tripId;
    private final int originSequence;
    private final int destinationSequence;

    public GetPathForTripQuery(int tripId, int originSequence, int destinationSequence)
    {
        this.tripId = tripId;
        this.originSequence = originSequence;
        this.destinationSequence = destinationSequence;
    }

    @Override
    public String getStatement()
    {
        return "SELECT " +
                "    shapes.shape_id, " +
                "    shapes.shape_pt_lat, " +
                "    shapes.shape_pt_lon " +
                "FROM " +
                "    transitorgtfs.trips " +
                "INNER JOIN " +
                "    transitorgtfs.shapes ON trips.shape_id = shapes.shape_id " +
                "WHERE " +
                "    trips.trip_id = ? " +
                "    AND shape_pt_sequence BETWEEN ? and ? " +
                "ORDER BY " +
                "    trips.trip_id, " +
                "    shapes.shape_pt_sequence";

//        return "SELECT shapes.shape_id, shapes.shape_pt_lat, shapes.shape_pt_lon " +
//                "FROM transitorgtfs.trips " +
//                "INNER JOIN transitorgtfs.shapes ON trips.shape_id = shapes.shape_id " +
//                "WHERE trips.trip_id = ? " +
//                "ORDER BY trips.trip_id, shapes.shape_pt_sequence ";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, tripId);
        statement.setInt(2, originSequence);
        statement.setInt(3, destinationSequence);
    }

    @Override
    public List<TransitShape> readResult(ResultSet resultSet)
    {
        try
        {
            List<TransitShape> shapes = new ArrayList<>();
            while (resultSet.next())
            {
                shapes.add(new PathShape(resultSet.getInt(1), new Coordinate(resultSet.getDouble(2), resultSet.getDouble(3))));
            }
            return shapes;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[])
    {
        var test = new GetPathForTripQuery(0, 0, 0);
        System.out.println(test.getStatement());
    }
}