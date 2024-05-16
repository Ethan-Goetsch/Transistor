package calculators;

import entities.*;
import entities.transit.TransitNode;
import entities.transit.shapes.PathShape;
import utils.Conversions;

import java.util.ArrayList;
import java.util.List;

public class AerialCalculator implements IRouteCalculator
{
    private static final int radiusEarthInKM = 6371;

    @Override
    public RouteType getRouteType()
    {
        return RouteType.AERIAL;
    }

    @Override
    public Trip calculateRoute(RouteCalculationRequest calculationRequest)
    {
        double distance = distanceToPoint(calculationRequest.departure(), calculationRequest.arrival());
        double time = Conversions.calculateTime(distance, calculationRequest.transportType());

        var points = new ArrayList<PathPoint>();
        points.add(new PathPoint(calculationRequest.departure(), PointType.Normal));
        points.add(new PathPoint(calculationRequest.arrival(), PointType.Normal));
        var path = new Path(points);

        List<TransitNode> nodes = new ArrayList<>();

        nodes.add(new TransitNode(-1, "Departure", calculationRequest.departure(), "00:00", "00:00", new PathShape(-1, calculationRequest.departure())));
        nodes.add(new TransitNode(-1, "Destination", calculationRequest.arrival(), String.valueOf(time), String.valueOf(time), new PathShape(-1, calculationRequest.arrival())));

        return new Trip(path, nodes);
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