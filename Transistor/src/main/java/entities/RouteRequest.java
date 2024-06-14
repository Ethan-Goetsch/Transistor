package entities;

public record RouteRequest(String departure, String arrival, TransportType transportType, RouteType routeType) implements Request
{
}