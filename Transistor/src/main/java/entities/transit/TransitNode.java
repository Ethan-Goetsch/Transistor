package entities.transit;

import entities.Coordinate;
import entities.transit.shapes.TransitShape;

public record TransitNode(int id, String name, Coordinate coordinate, String arrivalTime, String departureTime, TransitShape shape)
{
}