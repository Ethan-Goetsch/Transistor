package entities.TransitGraphEntities;

import java.time.LocalTime;

public class TStopTimePoint implements Comparable<TStopTimePoint>
{
    private int sequence;
    private TStop stop;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int shapeDistTraveled;

    public TStopTimePoint(int sequence, TStop stop, LocalTime arrivalTime, LocalTime departureTime, int shapeDistTraveled)
    {
        this.sequence = sequence;
        this.stop = stop;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.shapeDistTraveled = shapeDistTraveled;
    }

    @Override
    public int compareTo(TStopTimePoint o)
    {
        return Integer.compare(sequence, o.getSequence());
    }

    public int getSequence()
    {
        return sequence;
    }

    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    public TStop getStop()
    {
        return stop;
    }

    public void setStop(TStop stop)
    {
        this.stop = stop;
    }

    public LocalTime getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public int getShapeDistTraveled()
    {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(int shapeDistTraveled)
    {
        this.shapeDistTraveled = shapeDistTraveled;
    }
}
