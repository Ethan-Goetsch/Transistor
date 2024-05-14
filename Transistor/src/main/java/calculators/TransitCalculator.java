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
        var tripsFromOriginToDestination = DatabaseManager.getInstance().getTrip(originStop.id(), destinationStop.id());
        if (tripsFromOriginToDestination.isEmpty()) return null;
        return new RouteCalculationResult(getPathForTrip(tripsFromOriginToDestination.getFirst(), originStop.id(), destinationStop.id()), 0, 0);
    }

    private Path getPathForTrip(int tripId, int originStopId, int destinationStopId)
    {
        var originSequence = DatabaseManager.getInstance().getSequence(tripId, originStopId);
        var destinationSequence = DatabaseManager.getInstance().getSequence(tripId, destinationStopId);
        var transitShapes = DatabaseManager.getInstance().getPath(tripId, originSequence, destinationSequence);
        var pathPoints = transitShapes.stream()
                .map(shape -> new PathPoint(shape.coordinate(), PointType.Normal))
                .toList();
        return new Path(pathPoints);
    }
}