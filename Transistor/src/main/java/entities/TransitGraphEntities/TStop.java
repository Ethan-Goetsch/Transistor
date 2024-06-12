package entities.TransitGraphEntities;

import entities.Coordinate;

public class TStop
{
    private int id;
    private String name;
    private Coordinate coordinates;

    public TStop(int id, String name, Coordinate coordinates)
    {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
