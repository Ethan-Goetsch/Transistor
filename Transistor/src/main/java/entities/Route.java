package entities;

import java.time.LocalTime;
import java.util.List;

public record Route(Coordinate departure, Coordinate arrival, Journey journey, String responseMessage)
{
    public LocalTime departureTime() { return journey.getTrips().getFirst().getDepartureTime(); }
    public LocalTime arrivalTime() { return journey.getTrips().getLast().getArrivalTime(); }

    public String departureDescription() { return journey.getTrips().getFirst().getDepartureDescription(); }
    public String arrivalDescription() { return journey.getTrips().getLast().getArrivalDescription(); }
}