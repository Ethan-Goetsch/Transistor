package utils;

public class Coordinates
{
    private double longtitude;
    private double latitude;

    public Coordinates(double longtitude, double latitude)
    {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public double getLongtitude()
    {
        return longtitude;
    }

    public void setLongtitude(double longtitude)
    {
        this.longtitude = longtitude;
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
