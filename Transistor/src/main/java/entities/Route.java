package entities;

import java.time.LocalTime;
import java.util.List;

public record Route(Coordinate departure, Coordinate arrival, Journey journey, String responseMessage)
{
    //What do you return by these?
    public String departureDescription() { return journey.getTrips().getFirst().getDepartureDescription(); }
    public String arrivalDescription() { return journey.getTrips().getLast().getArrivalDescription(); }
}