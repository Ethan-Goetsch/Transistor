package entities.transit;

import entities.Coordinate;

public record TransitStop(int id, String name, Coordinate coordinate)
{
}