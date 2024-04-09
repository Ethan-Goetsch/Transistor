package calculators;

import com.graphhopper.ResponsePath;
import entities.*;
import utils.Conversions;

public class AerialCalculator implements IRouteCalculator
{
    private static final int radiusEarthInKM = 6371;

    @Override
    public RouteType getRouteType()
    {
        return RouteType.AERIAL;
    }

    @Override
    public RouteCalculationResult calculateRoute(RouteCalculationRequest calculationRequest)
    {
        double distance = distanceToPoint(calculationRequest.departure(), calculationRequest.arrival());
        double time = Conversions.calculateTime(distance, calculationRequest.transportType());
        return new RouteCalculationResult(null, distance, time);
    }

    private double distanceToPoint(Coordinate point1, Coordinate point2)
    {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());
        return Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))* radiusEarthInKM;
    }
}