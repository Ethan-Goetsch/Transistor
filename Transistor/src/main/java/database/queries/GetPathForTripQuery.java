package database.queries;

import database.DatabaseExtensions;
import database.DatabaseManager;
import entities.Coordinate;
import entities.transit.TransitShape;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPathForTripQuery extends ResultQuery<List<TransitShape>>
{
    private final int tripId;

    public GetPathForTripQuery(int tripId)
    {
        this.tripId = tripId;
    }

    @Override
    public String getStatement()
    {
        return "SELECT shapes.shape_id, shapes.shape_pt_lat, shapes.shape_pt_lon " +
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
    public List<TransitShape> readResult(ResultSet resultSet)
    {
        try
        {
            List<TransitShape> shapes = new ArrayList<>();
            while (resultSet.next())
            {
                shapes.add(new TransitShape(resultSet.getInt(1), new Coordinate(resultSet.getInt(2), resultSet.getInt(3))));
            }
            return shapes;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        var trips = DatabaseManager.getInstance().executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578413, 2578366));
        for (Integer trip : trips)
        {
            DatabaseExtensions.printResults(DatabaseManager.getInstance().executeQuery(new GetPathForTripQuery(trip)));
            System.out.println("----------------------------------------------------------------");
        }
    }
}