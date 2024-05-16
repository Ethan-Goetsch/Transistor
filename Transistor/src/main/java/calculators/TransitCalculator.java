package calculators;

import database.DatabaseManager;
import database.queries.GetAllShapesBetweenStops;
import database.queries.GetAllStopsForTrip;
import database.queries.GetShapeSequenceForTripAndStop;
import database.queries.GetTripBetweenTwoStopsQuery;
import entities.Path;
import entities.PathPoint;
import entities.PointType;
import entities.Trip;
import entities.transit.TransitNode;
import entities.transit.TransitTrip;
import entities.transit.shapes.TransitShape;

import java.util.ArrayList;
import java.util.List;

public class TransitCalculator
{
    public Trip calculateRoute(int originId, int destinationId)
    {
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(originId, destinationId));
        if (trip == null) return null;

        var nodes = DatabaseManager.executeAndReadQuery(new GetAllStopsForTrip(trip.id(), trip.originStopSequence(), trip.destinationStopSequence()));
        var path = getPathForTrip(trip, nodes);

        return new Trip(path, nodes);
    }

    private Path getPathForTrip(TransitTrip trip, List<TransitNode> nodes)
    {
        var points = new ArrayList<PathPoint>();
        var path = new Path(points);

        for (var i = 0; i < nodes.size() - 1; i++)
        {
            var startNode = nodes.get(i);
            var stopNode = nodes.get(i + 1);

            var startShapeSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(trip.id(), startNode.id()));
            var stopShapeSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(trip.id(), stopNode.id()));

            var shapesBetweenStops = DatabaseManager.executeAndReadQuery(new GetAllShapesBetweenStops(trip.id(),
                    startShapeSequence,
                    stopShapeSequence));

            points.addAll(shapesBetweenStops.stream()
                    .map(TransitShape::toPoint)
                    .toList());
        }

        return path;
    }
}