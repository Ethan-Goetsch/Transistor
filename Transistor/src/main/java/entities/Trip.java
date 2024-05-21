package entities;

import entities.transit.TransitNode;

import java.time.LocalTime;
import java.util.List;

public record Trip(Path path, List<TransitNode> nodes)
{
    public String getDepartureDescription() { return nodes.getFirst().departureTime(); }
    public String getArrivalDescription() { return nodes.getLast().arrivalTime(); }
}