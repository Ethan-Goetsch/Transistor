package entities;

import java.time.LocalTime;

public record RouteCalculationRequest(Coordinate departure, Coordinate arrival, LocalTime departureTime, TransportType transportType)
{
}