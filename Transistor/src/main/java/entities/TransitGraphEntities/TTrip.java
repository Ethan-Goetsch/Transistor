package entities.TransitGraphEntities;

import java.util.List;

public class TTrip
{
    private int id;
    private int routeid;
    private String routeShortName; // bus number
    private String routeLongName;

    private List<TStopTimePoint> stopTimePoints;
    private TShape shape;

    public TTrip(int id, int routeid, String routeShortName, String routeLongName, List<TStopTimePoint> stopTimePoints, TShape shape)
    {
        this.id = id;
        this.routeid = routeid;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.stopTimePoints = stopTimePoints;
        this.shape = shape;
    }

    @Override
    public String toString()
    {
        return id + " | " + routeid + " | " + routeShortName + " | " + routeLongName + " | " + stopTimePoints.size() + " | " + shape.getId() + " | " + shape.getShapePoints().size();
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

    public String getRouteShortName()
    {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName)
    {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName()
    {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName)
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

    public TShape getShape()
    {
        return shape;
    }

    public void setShape(TShape shape)
    {
        this.shape = shape;
    }
}
