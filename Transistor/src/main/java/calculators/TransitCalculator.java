package calculators;

import entities.*;
import entities.TransitGraphEntities.GraphEntities.Edge;
import entities.TransitGraphEntities.GraphEntities.Node;
import entities.transit.TransitNode;
import entities.transit.shapes.StopShape;
import utils.Conversions;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransitCalculator
{
    public List<Trip> calculateRoute(int originId, int destinationId)
    {
        return new TransitGraphCalculator().getPathDijkstra(originId, destinationId, LocalTime.now())
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

        var path = new Path(points, Color.BLUE);
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