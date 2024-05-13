package database.queries;

import database.DatabaseManager;
import database.RadiusGenerator;
import entities.Coordinate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NearestBusStopsQuery extends ResultQuery<List<Integer>>
{
    private final double[] latBounds;
    private final double[] lonBounds;

    //From departure we generate intervals +-100 meters to each direction
    //Query: get all bus stops in that area. Get all busses that depart at those stops.
    public NearestBusStopsQuery(double[] latBounds, double[] lonBounds)
    {

        this.latBounds = latBounds;
        this.lonBounds = lonBounds;
    }
    @Override
    public String getStatement()
    {
        return "SELECT  DISTINCT stop_id " +
                "FROM transitorgtfs.stops " +
                "WHERE stop_lat >= ? " +
                "AND stop_lon >= ? " +
                "AND stop_lat <= ? " +
                "AND stop_lon <= ? ";
    }

    @Override
    public void applyParameters(PreparedStatement statement) throws SQLException
    {
        statement.setDouble(1, latBounds[0]);
        statement.setDouble(2, lonBounds[0]);

        statement.setDouble(3, latBounds[1]);
        statement.setDouble(4, lonBounds[1]);
    }

    @Override
    public List<Integer> readResult(ResultSet resultSet)
    {
        List<Integer> ids = new ArrayList<>();
        try
        {
            while (resultSet.next())
            {
                var id = resultSet.getInt(1);
                ids.add(id);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return ids;

//        getStopId(Coordinate coordinate)
//        ArrayList<Integer> IDs = new ArrayList<>();
//        double radius = 100;
//        var instance = DatabaseManager.getInstance();
//        while (IDs.isEmpty())
//        {
//            double[][] bounds = RadiusGenerator.getRadius(coordinate, radius);
//            ResultSet rs = instance.executeQuery(new NearestBusStopsQuery(bounds[0], bounds[1]).getStatement());
//            try
//            {
//                while (rs.next())
//                {
//                    int id = rs.getInt(1);
//                    IDs.add(id);
//                }
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            radius += 100;
//        }
//        return IDs;
    }
}