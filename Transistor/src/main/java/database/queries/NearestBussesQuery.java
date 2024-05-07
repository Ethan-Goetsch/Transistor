package database.queries;

public class NearestBussesQuery extends QueryObject{
    private final double[] latBounds;
    private final double[] lonBounds;

    //From departure we generate intervals +-100 meters to each direction
    //Query: get all bus stops in that area. Get all busses that depart at those stops.
    public NearestBussesQuery(double[] latBounds, double[] lonBounds){

        this.latBounds = latBounds;
        this.lonBounds = lonBounds;
    }
    @Override
    public String getStatement() {

        String statement = "(SELECT  DISTINCT trip_id " +
                "FROM transitorgtfs.stop_times " +
                "WHERE stop_id IN (" +
                "SELECT stop_id FROM transitorgtfs.stops " +
                "WHERE stop_lat >= " + latBounds[0] +
                " AND stop_lon >= " + lonBounds[0] +
                " AND stop_lat <= " + latBounds[1] +
                " AND stop_lon <= " + lonBounds[1] +
                "))";
        return statement;
    }
}
