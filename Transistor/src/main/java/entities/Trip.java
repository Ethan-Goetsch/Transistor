package entities;

import entities.transit.TransitNode;

import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public record Trip(Path path, List<TransitNode> nodes, Color colour)
{
    public LocalTime getDepartureTime() { return nodes.getFirst().departureTime(); }
    public LocalTime getArrivalTime() { return nodes.getLast().arrivalTime(); }

    public String getDepartureDescription() { return getDepartureTime().toString(); }
    public String getArrivalDescription() { return getArrivalTime().toString(); }
}