package entities.TransitGraphEntities;

import java.time.LocalTime;

public class TStopTimePoint
{
    private int sequence;
    private TStop stop;
    private LocalTime arrivalTime;
    private LocalTime departurTime;

    public TStopTimePoint(int sequence, TStop stop, LocalTime arrivalTime, LocalTime departurTime)
    {
        this.sequence = sequence;
        this.stop = stop;
        this.arrivalTime = arrivalTime;
        this.departurTime = departurTime;
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

    public LocalTime getDeparturTime()
    {
        return departurTime;
    }

    public void setDeparturTime(LocalTime departurTime)
    {
        this.departurTime = departurTime;
    }
}
