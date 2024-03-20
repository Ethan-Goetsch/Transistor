package entities;

public class Coordinates
{
    private double latitude;
    private double longitude;
    
    public Coordinates(double latitude, double longtitude)
    {
        this.longitude = longtitude;
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

    
}
