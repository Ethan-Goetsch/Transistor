package entities.transit.shapes;

import entities.Coordinate;
import entities.PathPoint;
import entities.PointType;

public class StopShape extends TransitShape
{
    public StopShape(Coordinate coordinate)
    {
        super(-1, coordinate);
    }

    @Override
    public PathPoint toPoint()
    {
        return new PathPoint(getCoordinate(), PointType.Stop);
    }
}