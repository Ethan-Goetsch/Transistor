package database.queries;

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
        return "SELECT destination.trip_id, origin_stop.stop_name, destination_stop.stop_name\n" +
                "    FROM transitorgtfs.stop_times AS origin\n" +
                "    INNER JOIN transitorgtfs.stop_times AS destination\n" +
                "        ON destination.trip_id = origin.trip_id\n" +
                "        AND destination.stop_id = 2578366\n" +
                "    INNER JOIN transitorgtfs.stops AS origin_stop\n" +
                "        ON origin_stop.stop_id = origin.stop_id\n" +
                "    INNER JOIN transitorgtfs.stops AS destination_stop\n" +
                "        ON destination_stop.stop_id = destination.stop_id\n" +
                "    WHERE origin.stop_id = 2578413\n" +
                "        AND origin.stop_sequence < destination.stop_sequence;";
    }
}