package entities;

import entities.transit.TransitNode;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public record Trip(Path path, List<TransitNode> nodes, Color colour, TransportType type)
{
    public LocalTime getDepartureTime() { return nodes.getFirst().departureTime(); }
    public LocalTime getArrivalTime() { return nodes.getLast().arrivalTime(); }

    public String getDepartureDescription() { return getDepartureTime().toString(); }
    public String getArrivalDescription() { return getArrivalTime().toString(); }
    public double getTravelTime(){
        Duration travelTime = Duration.between(getDepartureTime(), getArrivalTime());
        return travelTime.toHours();
    }
}