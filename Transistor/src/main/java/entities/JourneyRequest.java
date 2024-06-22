package entities;

public record JourneyRequest(Coordinate departure, Coordinate arrival, TransportType transportType, TransitType transitType)
{
}