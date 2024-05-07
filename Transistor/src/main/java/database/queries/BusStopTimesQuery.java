package database.queries;

public class BusStopTimesQuery extends QueryObject{

    private final int stopId;

    public BusStopTimesQuery(int stopId){

        this.stopId = stopId;
    }
    @Override
    public String getStatement() {
        return  "SELECT DISTINCT arrival_time FROM transitorgtfs.stop_times" +
                " WHERE stop_id = " + stopId;
    }
}
