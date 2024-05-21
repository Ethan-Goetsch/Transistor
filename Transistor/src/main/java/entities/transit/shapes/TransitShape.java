package entities.transit.shapes;

import entities.Coordinate;
import entities.PathPoint;
import entities.PointType;

public abstract class TransitShape
{
    private final int id;
    private final Coordinate coordinate;

    protected TransitShape(int id, Coordinate coordinate)
    {
        this.id = id;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    public abstract PathPoint toPoint();
}