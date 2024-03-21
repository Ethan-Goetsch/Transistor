package entities;

public record Route(Coordinate departure, Coordinate arrival, RouteCalculationResult result, TransportType transportType, String responseMessage)
{
}
