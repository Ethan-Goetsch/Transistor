package database.queries;

import database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPathForTripQuery extends ResultQuery<List<Integer>>
{
    private final int tripId;

    public GetPathForTripQuery(int tripId)
    {
        this.tripId = tripId;
    }

    @Override
    public String getStatement()
    {
        return "SELECT trips.trip_id, shapes.shape_pt_sequence, shapes.shape_pt_lat, shapes.shape_pt_lon " +
                "FROM transitorgtfs.trips " +
                "INNER JOIN transitorgtfs.shapes ON trips.shape_id = shapes.shape_id " +
                "WHERE trips.trip_id = ? " +
                "ORDER BY trips.trip_id, shapes.shape_pt_sequence ";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, tripId);
    }

    @Override
    public List<Integer> readResult(ResultSet resultSet)
    {
        try
        {
            List<Integer> paths = new ArrayList<>();
            while (resultSet.next())
            {
                paths.add(resultSet.getInt(1));
            }
            return paths;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        System.out.println(DatabaseManager.getInstance().executeAndReadQuery(new GetPathForTripQuery(176974587)));
    }
}