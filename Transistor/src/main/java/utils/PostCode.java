package utils;

import entities.Coordinates;

public class PostCode
{
    String name;
    Coordinates cords;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Coordinates getCords()
    {
        return cords;
    }

    public void setCords(Coordinates cords)
    {
        this.cords = cords;
    }

    public PostCode(String name, Coordinates cords)
    {
        this.name = name;
        this.cords = cords;
    }

}
