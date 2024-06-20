package entities.TransitGraphEntities.GraphEntities;

import java.time.LocalTime;
import java.util.List;

public class TransitGraphPath
{
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int duration;
    
    List<Edge> edgeList;

    public TransitGraphPath(LocalTime departureTime, LocalTime arrivalTime, int duration, List<Edge> edgeList)
    {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.edgeList = edgeList;
    }

    public LocalTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public List<Edge> getEdgeList()
    {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList)
    {
        this.edgeList = edgeList;
    }
}
