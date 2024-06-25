package calculators;

import database.DatabaseManager;
import database.queries.GetAllShapesBetweenStops;
import database.queries.GetAllStopsForTrip;
import database.queries.GetShapeSequenceForTripAndStop;
import database.queries.GetTripBetweenTwoStopsQuery;
import entities.*;
import entities.transit.TransitNode;
import entities.transit.TransitRoute;
import entities.transit.TransitTrip;
import entities.transit.shapes.TransitShape;
import database.queries.GetRouteForTripQuery;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DirectTransitCalculator extends TransitCalculator
{
    @Override
    public TransitType getTransitType()
    {
        return TransitType.Direct;
    }

    @Override
    public List<Trip> calculateRoute(int originId, int destinationId, LocalTime searchTime)
    {
        var trips = new ArrayList<Trip>();
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(originId, destinationId));

        if (trip == null) return null;
        var route = DatabaseManager.executeAndReadQuery(new GetRouteForTripQuery(trip.id()));

        var nodes = DatabaseManager.executeAndReadQuery(new GetAllStopsForTrip(trip.id(), trip.originStopSequence(), trip.destinationStopSequence()));
        var path = getPathForTrip(route, trip, nodes);

        trips.add(new Trip(trip.id(), path, nodes, TransportType.BUS));
        return trips;
    }

    private Path getPathForTrip(TransitRoute route, TransitTrip trip, List<TransitNode> nodes)
    {
        var points = new ArrayList<PathPoint>();
        var path = new Path(points, route.colour());

        for (var i = 0; i < nodes.size() - 1; i++)
        {
            var startNode = nodes.get(i);
            var stopNode = nodes.get(i + 1);

            var startShapeSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(trip.id(), startNode.id()));
            var stopShapeSequence = DatabaseManager.executeAndReadQuery(new GetShapeSequenceForTripAndStop(trip.id(), stopNode.id()));

            var shapesBetweenStops = DatabaseManager.executeAndReadQuery(new GetAllShapesBetweenStops(trip.id(),
                    startShapeSequence,
                    stopShapeSequence));

            if (shapesBetweenStops.isEmpty())
            {
                points.add(new PathPoint(startNode.coordinate(), PointType.Normal));
                points.add(new PathPoint(stopNode.coordinate(), PointType.Normal));
            }

            points.addAll(shapesBetweenStops.stream()
                    .map(TransitShape::toPoint)
                    .toList());
        }

        return path;
    }
}