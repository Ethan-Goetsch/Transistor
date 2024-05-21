package entities.transit;

import entities.Coordinate;
import entities.transit.shapes.TransitShape;

import java.time.LocalTime;

public record TransitNode(int id, String name, Coordinate coordinate, LocalTime arrivalTime, LocalTime departureTime, TransitShape shape)
{
    //LocalTime startTime = Localtime.parse(departureTime);
    public String getArrivalDescription() { return arrivalTime.toString(); }
    public String getDepartureDescription() { return departureTime.toString(); }
}