package entities;

public class Coordinate
{
    private double latitude;
    private double longitude;
    
    public Coordinate(double latitude, double longitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    @Override
    public String toString()
    {
        return latitude + " " + longitude;
    }
}