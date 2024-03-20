package calculators;

import entities.TransportType;
import utils.TransportationType;
import static utils.TransportationTypeCreated.*;

public class RouteCalculator
{
    private static final int radiusEarthInM = 6371000;

    public double Distance2P(double lat1, double lon1, double lat2, double lon2)
    {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        return Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*radiusEarthInM;
    }
//    public double KilometerTimeTaken(double distance)
//    {
//        double kiloPerHour=(distance/1000)/5;
//        return kiloPerHour;
//    }
//    public double MeterTimeTaken(double distance)
//    {
//        double MeterPerSecond=distance/1.4;
//        return MeterPerSecond;
//    }

    public double calculateTime(TransportType type, double lat1, double lon1, double lat2, double lon2)
    {
        double distance = Distance2P( lat1, lon1, lat2, lon2);
        return calculateTime(type, distance);
    }

    public double calculateTime(TransportType type, double distance)
    {
        double speed = type.getSpeedInMetersPerSecond();
        return distance/speed;
    }
}