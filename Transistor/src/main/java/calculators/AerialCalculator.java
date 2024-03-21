package calculators;

import entities.RouteCalculationRequest;
import entities.RouteCalculationResult;
import entities.TransportType;
import utils.Conversions;
import entities.Coordinate;

public class AerialCalculator implements ICalculator
{
    private static final int radiusEarthInKM = 6371;

    public double distanceToPoint(Coordinate point1, Coordinate point2)
    {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());
        return Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))* radiusEarthInKM;
    }

    public double calculateTime(TransportType type, Coordinate point1, Coordinate point2)
    {
        double distance = distanceToPoint(point1, point2);
        return calculateTime(type, distance);
    }

    public double calculateTime(TransportType type, double distance)
    {
        double speed = type.getSpeedInMetersPerSecond();
        return distance/speed;
    }

    @Override
    public RouteCalculationResult calculateRoute(RouteCalculationRequest calculationRequest) {
        double distance = distanceToPoint(calculationRequest.departure(), calculationRequest.arrival());
        double time = calculateTime(calculationRequest.transportType(), distance);
        time = Conversions.secondsToMinutes(time);
        return new RouteCalculationResult(distance, time);
    }
}