package entities.TransitGraphEntities.GraphEntities;

import java.awt.Color;
import java.util.ArrayList;

import entities.TransportType;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TShapePoint;
import utils.ColorUtils;
import utils.DistanceUtils;

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

    private Color color;
    private TransportType transportType;

    public Edge(int departureTime, int arrivalTime, Node source, Node destination, int tripid, int routeid, String routeShortName, String routeLongName, TShape shape, int shapeDistTraveledStart, int shapeDistTraveledEnd, TransportType transportType)
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
        this.shapeDistTraveledStart = shapeDistTraveledStart;
        this.shapeDistTraveledEnd = shapeDistTraveledEnd;
        this.color = ColorUtils.intToColor(routeid);
        this.transportType = transportType;
    }

    public Edge(int departureTime, int arrivalTime, Node source, Node destination, int tripid, int routeid, String routeShortName, String routeLongName, TShape shape, int shapeDistTraveledStart, int shapeDistTraveledEnd)
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
        this.shapeDistTraveledStart = shapeDistTraveledStart;
        this.shapeDistTraveledEnd = shapeDistTraveledEnd;
        this.color = ColorUtils.intToColor(routeid);
        this.transportType = TransportType.BUS;
    }

    public Edge(int departureTime, int arrivalTime, Node source, Node destination) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.source = source;
        this.destination = destination;
        this.tripid = -1; // Special ID for walking transfers
        this.routeid = -1;
        this.routeShortName = "WALK";
        this.routeLongName = "Walking transfer";
        this.shape = null;
        this.shapeDistTraveledStart = 0;
        this.shapeDistTraveledEnd = 0;
        this.color = Color.GRAY;
        this.transportType = TransportType.FOOT;
    }
    // TODO: MAKE SURE THIS WORKS
    public int getPossibleArrivalTime(int currentTime)
    {
        if (transportType == TransportType.FOOT)
        {
            return getWalkingPossibleArrivalTime(currentTime);    
        }
        
        int normalCurrentTime = currentTime % (60*60*24);
        int normalArrivalTime = arrivalTime;
        int normalDepartureTime = departureTime;
        if (arrivalTime < departureTime)
        {
            normalArrivalTime += (60*60*24);
        }
        int edgeDuration = normalArrivalTime - departureTime;
        if (normalCurrentTime > departureTime)
        {
            normalDepartureTime += (60*60*24);
        }


        return (currentTime + ((normalDepartureTime - normalCurrentTime) + edgeDuration));
    }

    private int getWalkingPossibleArrivalTime(int currentTime)
    {
        double distanceM = DistanceUtils.gcdistanceMeters(source.getStop().getCoordinates(), destination.getStop().getCoordinates());
        double walkingSpeedMPS = 1.0;
        double durationS = distanceM/walkingSpeedMPS;

        int retval = currentTime + (int)durationS;        

        return retval;
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

    public void setShape(TShape shape)
    {
        this.shape = shape;
    }

    public int getShapeDistTraveledStart()
    {
        return shapeDistTraveledStart;
    }

    public void setShapeDistTraveledStart(int shapeDistTraveledStart)
    {
        this.shapeDistTraveledStart = shapeDistTraveledStart;
    }

    public int getShapeDistTraveledEnd()
    {
        return shapeDistTraveledEnd;
    }

    public void setShapeDistTraveledEnd(int shapeDistTraveledEnd)
    {
        this.shapeDistTraveledEnd = shapeDistTraveledEnd;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public TransportType getTransportType()
    {
        return transportType;
    }

    public void setTransportType(TransportType transportType)
    {
        this.transportType = transportType;
    }
}