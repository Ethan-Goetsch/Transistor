package entities.transit;

import java.time.LocalTime;

public record TransitTrip(int id, String name, int originStopSequence, int destinationStopSequence, LocalTime originDepartureTime, LocalTime destinationArrivalTime)
{
    public String getOriginDepartureDescription(){ return originDepartureTime.toString(); }
    public String getDestinationDepartureDescription(){ return destinationArrivalTime.toString(); }
}