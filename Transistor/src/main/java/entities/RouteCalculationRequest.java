package entities;

public record RouteCalculationRequest(Coordinate departure, Coordinate arrival, TransportType transportType)
{
}
