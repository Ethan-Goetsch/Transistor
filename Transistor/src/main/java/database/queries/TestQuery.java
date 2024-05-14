package database.queries;

import database.DatabaseExtensions;
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
//        return "SELECT DISTINCT destination.trip_id, origin_stop.stop_name, destination_stop.stop_name" +
//                "    FROM transitorgtfs.stop_times AS origin" +
//                "    INNER JOIN transitorgtfs.stop_times AS destination" +
//                "        ON destination.trip_id = origin.trip_id" +
//                "        AND destination.stop_id = 2578366" +
//                "    INNER JOIN transitorgtfs.stops AS origin_stop" +
//                "        ON origin_stop.stop_id = origin.stop_id" +
//                "    INNER JOIN transitorgtfs.stops AS destination_stop" +
//                "        ON destination_stop.stop_id = destination.stop_id" +
//                "    WHERE origin.stop_id = 2578413" +
//                "        AND origin.stop_sequence < destination.stop_sequence;";

//        return "SELECT DISTINCT shape_id " +
//                "FROM transitorgtfs.trips " +
//                "WHERE trip_id = 178421643";

//        return "SELECT " +
//                "    trips.trip_id, " +
//                "    shapes.shape_pt_sequence, " +
//                "    shapes.shape_pt_lat, " +
//                "    shapes.shape_pt_lon " +
//                "FROM " +
//                "    transitorgtfs.trips " +
//                "INNER JOIN " +
//                "    transitorgtfs.shapes ON trips.shape_id = shapes.shape_id " +
//                "WHERE " +
//                "    trips.trip_id IN (" +
//                "        SELECT DISTINCT destination.trip_id " +
//                "        FROM transitorgtfs.stop_times AS origin " +
//                "        INNER JOIN transitorgtfs.stop_times AS destination ON destination.trip_id = origin.trip_id " +
//                "            AND destination.stop_id = 2578366" +
//                "        INNER JOIN transitorgtfs.stops AS origin_stop ON origin_stop.stop_id = origin.stop_id " +
//                "        INNER JOIN transitorgtfs.stops AS destination_stop ON destination_stop.stop_id = destination.stop_id " +
//                "        WHERE origin.stop_id = 2578413 " +
//                "            AND origin.stop_sequence < destination.stop_sequence" +
//                "    )" +
//                "ORDER BY " +
//                "    trips.trip_id, " +
//                "    shapes.shape_pt_sequence;";

        return "SELECT trips.trip_id, shapes.shape_pt_sequence, shapes.shape_pt_lat, shapes.shape_pt_lon " +
                "FROM transitorgtfs.trips " +
                "INNER JOIN transitorgtfs.shapes ON trips.shape_id = shapes.shape_id " +
                "WHERE trip_id = 176974587 " +
                "ORDER BY trips.trip_id, shapes.shape_pt_sequence ";
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
        DatabaseExtensions.printResults(DatabaseManager.getInstance().executeQuery(new TestQuery()));
    }
}