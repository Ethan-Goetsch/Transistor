package calculators;

import entities.*;
import entities.TransitGraphEntities.GraphEntities.Edge;
import entities.TransitGraphEntities.GraphEntities.Node;
import entities.transit.TransitNode;
import entities.transit.shapes.StopShape;
import utils.Conversions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransitCalculator
{
    private final TransitGraphCalculator transitGraphCalculator;

    public TransitCalculator(TransitGraphCalculator transitGraphCalculator)
    {
        this.transitGraphCalculator = transitGraphCalculator;
    }

    public List<Trip> calculateRoute(int originId, int destinationId)
    {
        System.out.println("originid: " + originId);
        System.out.println("destinationid: " + destinationId);
        return transitGraphCalculator.getPathDijkstra(originId, destinationId, LocalTime.NOON)
                .getEdgeList()
                .stream()
                .map(this::convertEdgeToTrip)
                .collect(Collectors.toList());
    }

    private Trip convertEdgeToTrip(Edge edge)
    {
        var source = edge.getSource();
        var destination = edge.getDestination();

        var points = edge.getShape().getShapePoints()
                .stream()
                .map(shapePoint -> new PathPoint(shapePoint.getCoordinates(), PointType.Normal))
                .toList();

        var path = new Path(points, edge.getColor());
        var nodes = new ArrayList<TransitNode>();

        nodes.add(convertNodeToTransitNode(edge, source));
        nodes.add(convertNodeToTransitNode(edge, destination));

        return new Trip(path, nodes, TransportType.BUS);
    }

    private TransitNode convertNodeToTransitNode(Edge edge, Node node)
    {
        var stop = node.getStop();
        return new TransitNode(stop.getId(),
                stop.getName(),
                stop.getCoordinates(),
                Conversions.intToLocalTime(edge.getArrivalTime()),
                Conversions.intToLocalTime(edge.getDepartureTime()),
                new StopShape(stop.getCoordinates()));
    }
}