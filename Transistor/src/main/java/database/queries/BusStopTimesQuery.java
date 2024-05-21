package database.queries;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.List;

public class BusStopTimesQuery extends ResultQuery<List<LocalTime>>{

    private final int stopId;

    public BusStopTimesQuery(int stopId){

        this.stopId = stopId;
    }
    @Override
    public String getStatement() {
        return  "SELECT DISTINCT arrival_time FROM transitorgtfs.stop_times" +
                " WHERE stop_id = " + stopId;
    }

    @Override
    public List<LocalTime> readResult(ResultSet resultSet) {
        return null;
    }
}
