package calculators;

import database.DatabaseManager;
import entities.Path;
import entities.PathPoint;
import entities.PointType;
import entities.RouteCalculationResult;
import entities.transit.TransitStop;

public class TransitCalculator
{
    public RouteCalculationResult calculateRoute(TransitStop originStop, TransitStop destinationStop)
    {
        var tripsFromOriginToDestination = DatabaseManager.getInstance().GetTrip(originStop.id(), destinationStop.id());
        return new RouteCalculationResult(getPathForTrip(tripsFromOriginToDestination.get(0)), 0, 0);
    }

    private Path getPathForTrip(int tripId)
    {
        var pathsFromOriginToDestination = DatabaseManager.getInstance().GetPath(tripId);
        var points = pathsFromOriginToDestination.stream()
                .findFirst()
                .map(shape -> new PathPoint(shape.coordinate(), PointType.Normal))
                .stream().toList();
        return new Path(points);
    }
}