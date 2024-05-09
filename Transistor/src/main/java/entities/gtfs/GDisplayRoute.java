package entities.gtfs;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class GDisplayRoute
{
    private int routeid;
    private int tripid;
    private String routeName;
    private List<GShapePoint> shapePoints;

    public GDisplayRoute(int routeid, int tripid, String routeName)
    {
        this.routeid = routeid;
        this.tripid = tripid;
        this.routeName = routeName;
        this.shapePoints = new ArrayList<>();
    }

    public void addShapePoint(GShapePoint shapePoint)
    {
        shapePoints.add(shapePoint);
    }

    public int getRouteid()
    {
        return routeid;
    }

    public void setRouteid(int routeid)
    {
        this.routeid = routeid;
    }

    public int getTripid()
    {
        return tripid;
    }

    public void setTripid(int tripid)
    {
        this.tripid = tripid;
    }

    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }

    public List<GShapePoint> getShapePoints()
    {
        return shapePoints;
    }

    public void setShapePoints(List<GShapePoint> shapePoints)
    {
        this.shapePoints = shapePoints;
    }

    @Override
    public String toString()
    {
        return routeid + " | " + tripid + " | " + routeName + " | " + shapePoints.size();
    }
}
