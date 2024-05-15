package calculators;

import database.DatabaseManager;
import database.queries.GetPathForTripQuery;
import database.queries.GetShapeSequenceForTripAndStop;
import database.queries.GetTripBetweenTwoStopsQuery;
import entities.Path;
import entities.PathPoint;
import entities.PointType;
import entities.RouteCalculationResult;
import entities.transit.TransitNode;

public class TransitCalculator
{
    /*
    Get Start Node
    Get End Node
    Get All Nodes in between
    Get Departure Time. Get Arrival Time
    Get Colour for each edge
    Get Shapes for each edge
    Get Weight for each edge
     */
    public RouteCalculationResult calculateRoute(TransitNode originStop, TransitNode destinationStop)
    {
        var tripsFromOriginToDestination = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(originStop.id(), destinationStop.id()));
        if (tripsFromOriginToDestination == null) return null;

        return new RouteCalculationResult(getPathForTrip(tripsFromOriginToDestination.id(), originStop.id(), destinationStop.id()), 0, 0);
    }

    private Path getPathForTrip(int tripId, int originStopId, int destinationStopId)
    {
        var originSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(tripId, originStopId));
        var destinationSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(tripId, destinationStopId));

        var transitShapes = DatabaseManager.executeAndReadQuery(new GetPathForTripQuery(tripId, originSequence, destinationSequence));

        var pathPoints = transitShapes.stream()
                .map(shape -> new PathPoint(shape.coordinate(), PointType.Normal))
                .toList();
        return new Path(pathPoints);
    }
}