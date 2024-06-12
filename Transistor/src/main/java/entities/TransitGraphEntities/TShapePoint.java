package entities.TransitGraphEntities;

import entities.Coordinate;

public class TShapePoint
{
    private int sequence;
    private Coordinate coordinates;

    public TShapePoint(int sequence, Coordinate coordinates)
    {
        this.sequence = sequence;
        this.coordinates = coordinates;
    }

    public int getSequence()
    {
        return sequence;
    }

    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    public Coordinate getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates)
    {
        this.coordinates = coordinates;
    }
}
