package database.queries;

import entities.Coordinate;

public class TransitMapData extends QueryObject
{
    private Coordinate maasLowerLeft;
    private Coordinate maasUpperRight;

    public TransitMapData()
    {
        this.maasLowerLeft = new Coordinate(50.826137, 5.653699);
        this.maasUpperRight = new Coordinate(50.878915, 5.730947);
    }

    public TransitMapData(Coordinate maasLowerLeft, Coordinate maasUpperRight)
    {
        this.maasLowerLeft = maasLowerLeft;
        this.maasUpperRight = maasUpperRight;
    }

    @Override
    public String getStatement()
    {

        return "SELECT\r\n" + //
                        "\troute_id,\r\n" + //
                        "\ttrip_id,\r\n" + //
                        "\troute_long_name,\r\n" + //
                        "\tshape_pt_sequence,\r\n" + //
                        "\tshape_pt_lat,\r\n" + //
                        "\tshape_pt_lon\r\n" + //
                        "FROM\r\n" + //
                        "\ttransitorgtfs.shapes LEFT JOIN transitorgtfs.trips USING(shape_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "WHERE trip_id IN (\r\n" + //
                        "\t\tSELECT\r\n" + //
                        "\t\t\ttrip_id\r\n" + //
                        "\t\tFROM (\r\n" + //
                        "\t\t\tSELECT\r\n" + //
                        "\t\t\t\t*,\r\n" + //
                        "\t\t\t\tROW_NUMBER() OVER(PARTITION BY route_id ORDER BY trip_id DESC) AS row_num\r\n" + //
                        "\t\t\tFROM\r\n" + //
                        "\t\t\t\ttransitorgtfs.stop_times LEFT JOIN transitorgtfs.stops USING (stop_id) LEFT JOIN transitorgtfs.trips USING (trip_id) LEFT JOIN transitorgtfs.routes USING (route_id)\r\n" + //
                        "\t\t\tWHERE\r\n" + //
                        "\t\t\t\t(stop_lat BETWEEN "+maasLowerLeft.getLatitude()+" AND "+maasUpperRight.getLatitude()+") AND (stop_lon BETWEEN "+maasLowerLeft.getLongitude()+" AND "+maasUpperRight.getLongitude()+") AND shape_id IS NOT NULL\r\n" + //
                        "\t\t) unique_trips_in_maas\r\n" + //
                        "\t\tWHERE row_num = 1\r\n" + //
                        ")\r\n" + //
                        "ORDER BY trip_id ASC, shape_pt_sequence ASC;";
    }
    
}
