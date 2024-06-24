package calculators;

import entities.*;
import entities.transit.TransitNode;
import entities.transit.shapes.PathShape;
import utils.Conversions;

import java.awt.*;
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
        var distance = distanceToPoint(calculationRequest.departure(), calculationRequest.arrival());
        var time = Conversions.calculateTime(distance, calculationRequest.transportType());

        var color = Color.GREEN;
        var arrivalTime = calculationRequest.departureTime().plusSeconds((long)(time * 3600));

        var points = new ArrayList<PathPoint>();
        points.add(new PathPoint(calculationRequest.departure(), PointType.Normal));
        points.add(new PathPoint(calculationRequest.arrival(), PointType.Normal));
        var path = new Path(points, color);

        List<TransitNode> nodes = new ArrayList<>();

        nodes.add(new TransitNode(-1, "Departure", calculationRequest.departure(), calculationRequest.departureTime(), new PathShape(-1, calculationRequest.departure())));
        nodes.add(new TransitNode(-1, "Destination", calculationRequest.arrival(), arrivalTime, new PathShape(-1, calculationRequest.arrival())));

        return new Trip(-1, path, nodes, calculationRequest.transportType());
    }

    public double distanceToPoint(Coordinate point1, Coordinate point2)
    {

        double latDistance = Math.toRadians(point2.getLatitude() - point1.getLatitude());
        double lonDistance = Math.toRadians(point2.getLongitude() - point1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(point1.getLatitude())) * Math.cos(Math.toRadians(point2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radiusEarthInKM * c;

    }
}