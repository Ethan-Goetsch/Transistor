package entities.TransitGraphEntities;

import java.util.List;

public class TTrip
{
    private int id;
    private int routeid;
    private int routeShortName; // bus number
    private int routeLongName;

    private List<TStopTimePoint> stopTimePoints;
    private List<TShapePoint> shapePoints;
    
    public TTrip(int id, int routeid, int routeShortName, int routeLongName, List<TStopTimePoint> stopTimePoints, List<TShapePoint> shapePoints)
    {
        this.id = id;
        this.routeid = routeid;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.stopTimePoints = stopTimePoints;
        this.shapePoints = shapePoints;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRouteid()
    {
        return routeid;
    }

    public void setRouteid(int routeid)
    {
        this.routeid = routeid;
    }

    public int getRouteShortName()
    {
        return routeShortName;
    }

    public void setRouteShortName(int routeShortName)
    {
        this.routeShortName = routeShortName;
    }

    public int getRouteLongName()
    {
        return routeLongName;
    }

    public void setRouteLongName(int routeLongName)
    {
        this.routeLongName = routeLongName;
    }

    public List<TStopTimePoint> getStopTimePoints()
    {
        return stopTimePoints;
    }

    public void setStopTimePoints(List<TStopTimePoint> stopTimePoints)
    {
        this.stopTimePoints = stopTimePoints;
    }

    public List<TShapePoint> getShapePoints()
    {
        return shapePoints;
    }

    public void setShapePoints(List<TShapePoint> shapePoints)
    {
        this.shapePoints = shapePoints;
    }
}
