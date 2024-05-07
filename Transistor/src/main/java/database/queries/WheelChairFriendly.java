package database.queries;

public class WheelChairFriendly extends QueryObject{
    @Override
    public String getStatement() {
        return "SELECT stop_id" + " FROM transitorgtfs.stops" +  "FROM trips" +
                " WHERE (stop_lon BETWEEN 5.642129667 AND 5.7533842) AND stop_lat BETWEEN 50.81581557 AND 50.9007405 and and wheelchair_boarding = '1';";
    }
}
