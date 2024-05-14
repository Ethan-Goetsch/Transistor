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
        var tripsFromOriginToDestination = DatabaseManager.getInstance().GetTrip(2578413, 2578366);
        return new RouteCalculationResult(getPathForTrip(tripsFromOriginToDestination.get(0)), 0, 0);
    }

    private Path getPathForTrip(int tripId)
    {
        var transitShapes = DatabaseManager.getInstance().GetPath(tripId);
        var pathPoints = transitShapes.stream()
                .map(shape -> new PathPoint(shape.coordinate(), PointType.Normal))
                .toList();
        return new Path(pathPoints);
    }
}