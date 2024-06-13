package entities.TransitGraphEntities;

import entities.Coordinate;

public class TShapePoint implements Comparable<TShapePoint>
{
    private int sequence;
    private Coordinate coordinates;
    private int shapeDistTraveled;
    
    public TShapePoint(int sequence, Coordinate coordinates, int shapeDistTraveled)
    {
        this.sequence = sequence;
        this.coordinates = coordinates;
        this.shapeDistTraveled = shapeDistTraveled;
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

    public int getShapeDistTraveled()
    {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(int shapeDistTraveled)
    {
        this.shapeDistTraveled = shapeDistTraveled;
    }

    @Override
    public int compareTo(TShapePoint o)
    {
        return Integer.compare(sequence, o.getSequence());
    }
    
}
