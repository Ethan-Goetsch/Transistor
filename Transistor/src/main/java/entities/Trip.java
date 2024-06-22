package entities;

import entities.transit.TransitNode;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public record Trip(Path path, List<TransitNode> nodes, TransportType type)
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
}