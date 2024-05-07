package database.queries;

public class ClosestStopsQuery extends QueryObject
{

    private int stopNum;
    private double latitude;
    private double longtitude;

    public ClosestStopsQuery(int stopNum, double latitude, double longtitude)
    {
        this.stopNum = stopNum;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    @Override
    public String getStatement()
    {
        return "SELECT stop_id, stop_name, stop_lat, stop_lon,\r\n" + //
                        "\t( 3959 * acos( cos( radians("+latitude+") ) * cos( radians( stop_lat ) ) * cos( radians( stop_lon ) - radians("+longtitude+") ) + sin( radians("+latitude+") ) * sin( radians( stop_lat ) ) ) ) AS distance\r\n" + //
                        "FROM\r\n" + //
                        "    transitorgtfs.stops\r\n" + //
                        "ORDER BY\r\n" + //
                        "    distance\r\n" + //
                        "LIMIT\r\n" + //
                        "    "+stopNum+";";
    }
}