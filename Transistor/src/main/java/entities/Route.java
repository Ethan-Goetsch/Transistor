package entities;

import java.util.List;

public record Route(Coordinate departure, Coordinate arrival, List<Trip> trips, String responseMessage)
{
    public String departureDescription() { return trips.getFirst().getDepartureDescription(); }
    public String arrivalDescription() { return trips.getFirst().getArrivalDescription(); }
}