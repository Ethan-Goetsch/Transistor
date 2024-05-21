package entities.transit.shapes;

import entities.Coordinate;
import entities.PathPoint;
import entities.PointType;

public class PathShape extends TransitShape
{
    public PathShape(int id, Coordinate coordinate)
    {
        super(id, coordinate);
    }

    @Override
    public PathPoint toPoint()
    {
        return new PathPoint(getCoordinate(), PointType.Normal);
    }
}