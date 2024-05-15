package entities.transit;

public record TransitTrip(int id, String name, int originStopSequence, int destinationStopSequence, int originDepartureTime)
{
}