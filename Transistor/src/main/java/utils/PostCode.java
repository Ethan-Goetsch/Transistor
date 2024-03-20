package utils;

import entities.Coordinate;

public class PostCode
{
    String name;
    Coordinate cords;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Coordinate getCords()
    {
        return cords;
    }

    public void setCords(Coordinate cords)
    {
        this.cords = cords;
    }

    public PostCode(String name, Coordinate cords)
    {
        this.name = name;
        this.cords = cords;
    }

}
