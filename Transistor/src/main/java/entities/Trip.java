package entities;

import entities.transit.TransitNode;

import java.util.List;

public record Trip(Path path, List<TransitNode> nodes, String colour, TransportType type)
{
    public String getDepartureDescription() { return nodes.getFirst().departureTime(); }
    public String getArrivalDescription() { return nodes.getLast().arrivalTime(); }
}