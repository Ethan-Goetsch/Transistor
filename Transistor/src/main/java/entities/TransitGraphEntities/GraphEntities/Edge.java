package entities.TransitGraphEntities.GraphEntities;

import java.util.ArrayList;

import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TShapePoint;

public class Edge
{
    private int departureTime; // time needs to be int to account for overnight trips
    private int arrivalTime;
    private Node source;
    private Node destination;

    private int tripid;
    private int routeid;
    private String routeShortName;
    private String routeLongName;

    private TShape shape;
    private int shapeDistTraveledStart;
    private int shapeDistTraveledEnd;
    
    public Edge(int departureTime, int arrivalTime, Node source, Node destination, int tripid, int routeid, String routeShortName, String routeLongName, TShape shape)
    {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.source = source;
        this.destination = destination;
        this.tripid = tripid;
        this.routeid = routeid;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.shape = shape;
    }

    // TODO: MAKE THIS CONSIDER OVERNIGHT STUFF / TRIPS THAT PASS THE 0 HOUR, I TRIED AND FAILED MY ATTEMPT IS COMMENTED BELOW
    public int getPossibleArrivalTime(int currentTime)
    {
        int earliestPossibleArrivalTime = Integer.MAX_VALUE;

        int edgeDuration = arrivalTime - departureTime;
        if (edgeDuration < 0 || currentTime > departureTime)
        {
            return earliestPossibleArrivalTime;
        }
        else
        {
            earliestPossibleArrivalTime = currentTime + (departureTime - currentTime) + edgeDuration;
        }

        return earliestPossibleArrivalTime;
    }
    // public int getPossibleArrivalTime(int currentTime)
    // {
    //     int normalCurrentTime = currentTime % 60*60*24;
    //     int normalArrivalTime = arrivalTime;
    //     int normalDepartureTime = departureTime;
    //     if (arrivalTime < departureTime)
    //     {
    //         normalArrivalTime += 60*60*24;    
    //     }
    //     int edgeDuration = normalArrivalTime - departureTime;
    //     if (normalCurrentTime > departureTime)
    //     {
    //         normalDepartureTime += 60*60*24;
    //     }


    //     return (currentTime + ((normalDepartureTime - normalCurrentTime) + edgeDuration));   
    // }

    public int getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(int departureTime)
    {
        this.departureTime = departureTime;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public Node getSource()
    {
        return source;
    }

    public void setSource(Node source)
    {
        this.source = source;
    }

    public Node getDestination()
    {
        return destination;
    }

    public void setDestination(Node destination)
    {
        this.destination = destination;
    }

    public int getTripid()
    {
        return tripid;
    }

    public void setTripid(int tripid)
    {
        this.tripid = tripid;
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

    public TShape getShape()
    {
        TShape returnShape = new TShape(shape.getId(), new ArrayList<TShapePoint>());
        for (TShapePoint shapePoint : shape.getShapePoints())
        {
            if (shapePoint.getShapeDistTraveled() >= shapeDistTraveledStart && shapePoint.getShapeDistTraveled() <= shapeDistTraveledEnd)
            {
                returnShape.getShapePoints().add(shapePoint);    
            }
        }
        return returnShape;
    }

    public void setShape(TShape shape)
    {
        this.shape = shape;
    }
}