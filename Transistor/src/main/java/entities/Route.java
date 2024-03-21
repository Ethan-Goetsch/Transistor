package entities;

public record Route(Coordinate departure, Coordinate arrival, double distance, double time, TransportType transportType, String responseMessage)
{
}
