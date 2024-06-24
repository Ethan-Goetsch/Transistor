package entities;

import entities.transit.TransitNode;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record Trip(int tripId, Path path, List<TransitNode> nodes, TransportType type)
{
    public LocalTime getDepartureTime() { return nodes.getFirst().arrivalTime(); }
    public LocalTime getArrivalTime() { return nodes.getLast().arrivalTime(); }

    public String getDepartureDescription() { return getDepartureTime().toString(); }
    public String getArrivalDescription() { return getArrivalTime().toString(); }

    public double getTravelTimeHours()
    {
        Duration travelTime = Duration.between(getDepartureTime(), getArrivalTime());
        return travelTime.getSeconds() / 3600.0;
    }

    public Trip merge(Trip other)
    {
        var mergedPoints = new ArrayList<PathPoint>(path.points());
        mergedPoints.addAll(other.path.points());
        var mergedPath = new Path(mergedPoints, path.colour());
        var mergedNodes = new ArrayList<TransitNode>(nodes);
        mergedNodes.addAll(other.nodes);
        return new Trip(tripId, mergedPath, mergedNodes, type);
    }
}