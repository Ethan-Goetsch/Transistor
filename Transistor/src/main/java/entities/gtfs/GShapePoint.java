package entities.gtfs;

public class GShapePoint
{
    int sequenceNum;
    double latitude;
    double longitude;
    
    public GShapePoint(int sequenceNum, double latitude, double longitude)
    {
        this.sequenceNum = sequenceNum;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getSequenceNum()
    {
        return sequenceNum;
    }

    public void setSequenceNum(int sequenceNum)
    {
        this.sequenceNum = sequenceNum;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
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


}
