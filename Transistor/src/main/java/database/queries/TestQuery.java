package database.queries;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestQuery extends QueryObject
{
    /*
    TODO:
    1. GET COORDINATE AND QUERY STOP ID FOR COORDINATES
    2. FIND WHAT YOU CAN USE TRIPS FOR. IDEALLY EACH OF THE STOPS ALONG (I THINK)
     */
    @Override
    public String getStatement()
    {
//        return "SELECT DISTINCT destination.trip_id, origin_stop.stop_name, destination_stop.stop_name\n" +
//                "    FROM transitorgtfs.stop_times AS origin\n" +
//                "    INNER JOIN transitorgtfs.stop_times AS destination\n" +
//                "        ON destination.trip_id = origin.trip_id\n" +
//                "        AND destination.stop_id = 2578366\n" +
//                "    INNER JOIN transitorgtfs.stops AS origin_stop\n" +
//                "        ON origin_stop.stop_id = origin.stop_id\n" +
//                "    INNER JOIN transitorgtfs.stops AS destination_stop\n" +
//                "        ON destination_stop.stop_id = destination.stop_id\n" +
//                "    WHERE origin.stop_id = 2578413\n" +
//                "        AND origin.stop_sequence < destination.stop_sequence;";

//        return "SELECT DISTINCT shape_id " +
//                "FROM transitorgtfs.trips " +
//                "WHERE trip_id = 178421643";

        return "SELECT DISTINCT * " +
                "FROM transitorgtfs.shapes " +
                "WHERE shape_id = 1129986";
//
//        return "SELECT DISTINCT destination.trip_id, destination.stop_sequence, destination.arrival_time " +
//                "    FROM transitorgtfs.stop_times AS origin " +
//                "    INNER JOIN transitorgtfs.stop_times AS destination " +
//                "        ON destination.trip_id = origin.trip_id " +
//                "        AND destination.stop_id = 2578366 " +
//                "    INNER JOIN transitorgtfs.stops AS origin_stop " +
//                "        ON origin_stop.stop_id = origin.stop_id " +
//                "    INNER JOIN transitorgtfs.stops AS destination_stop " +
//                "        ON destination_stop.stop_id = destination.stop_id " +
//                "    WHERE origin.stop_id = 2578413 " +
//                "        AND origin.stop_sequence < destination.stop_sequence;";
    }

    public static void main(String[] args) throws SQLException
    {
        var instance = DatabaseManager.getInstance();
        ResultSet resultSet = instance.executeStatement(new TestQuery());
        try
        {
            while (resultSet.next())
            {
                var printData = "";
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                {
                    printData += resultSet.getString(i) + " ";
                }
                System.out.println(printData);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}