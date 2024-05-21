package entities.transit;

import entities.Coordinate;
import entities.transit.shapes.TransitShape;
import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.time.LocalTime;

public record TransitNode(int id, String name, Coordinate coordinate, String arrivalTime, String departureTime, TransitShape shape)
{
    //LocalTime startTime = Localtime.parse(departureTime);
    public LocalTime getArrivalTime() { return LocalTime.parse(arrivalTime); }
    public LocalTime getDepartureTime() { return LocalTime.parse(departureTime); }
}