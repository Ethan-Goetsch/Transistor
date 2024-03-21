package calculators;

import entities.TransportType;
import utils.Conversions;
import entities.Coordinate;

public class RouteCalculator extends Conversions
{
    private static final int radiusEarthInM = 6371000;

    public double Distance2P(Coordinate point1, Coordinate point2)
    {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());
        return Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*radiusEarthInM;
    }

    public double calculateTime(TransportType type, Coordinate point1, Coordinate point2)
    {
        double distance = Distance2P(point1, point2);
        return calculateTime(type, distance);
    }

    public double calculateTime(TransportType type, double distance)
    {
        double speed = type.getSpeedInMetersPerSecond();
        return distance/speed;
    }
}