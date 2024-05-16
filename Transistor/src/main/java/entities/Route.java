package entities;

import java.util.List;

public record Route(Coordinate departure, Coordinate arrival, List<Trip> trips, String responseMessage)
{
}