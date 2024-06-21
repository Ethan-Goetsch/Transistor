package calculators;

import database.DatabaseManager;
import database.queries.GetAllTripsMaasQuery;
import entities.TransitGraphEntities.GraphEntities.Edge;
import entities.TransitGraphEntities.GraphEntities.Node;
import entities.TransitGraphEntities.GraphEntities.TransitGraphPath;
import entities.TransportType;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TShapePoint;
import entities.TransitGraphEntities.TStop;
import entities.TransitGraphEntities.TStopTimePoint;
import entities.TransitGraphEntities.TTrip;
import utils.Conversions;

import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

public class TransitGraphCalculator
{
    private Map<Integer, Node> nodes;// stop id to node
    private int edgeCount = 0;

    public TransitGraphCalculator()
    {
        this.nodes = new HashMap<Integer, Node>();
        buildFromTripsList(DatabaseManager.executeAndReadQuery(new GetAllTripsMaasQuery()));
    }

    // not thread safe
    public TransitGraphPath getPathDijkstra(int originStopID, int destinationStopID, LocalTime DepartureTime)
    {
        resetGraph();
        Node source = nodes.get(originStopID);
        Node destination = nodes.get(destinationStopID);
        int departureTime = Conversions.localTimeToInt(DepartureTime);

        if (source == null)
        {
            System.out.println("no source node with such id in graph");
            return null;
        }
        if (destination == null)
        {
            System.out.println("no destination node with such id in graph");
            return null;    
        }
        if (originStopID == destinationStopID)
        {
            TShape dummyShape = new TShape(-1, new ArrayList<TShapePoint>());
            Edge dummyEdge = new Edge(departureTime, departureTime, source, destination);
            dummyEdge.setShape(dummyShape);
            TransitGraphPath dummyPath = new TransitGraphPath(DepartureTime, Conversions.intToLocalTime(departureTime+1), 1, new ArrayList<Edge>());
            dummyPath.getEdgeList().add(dummyEdge);
            
            return dummyPath;
        }

        dijkstra(source, destination, departureTime);

        if (destination.getShortestTime() == Integer.MAX_VALUE)
        {
            System.out.println("no path");
            return null;    
        }

        List<Edge> returnList = new ArrayList<Edge>();
        for (Edge edge : destination.getShortestPath())
        {
            returnList.add(edge);    
        }

        LocalTime rDepartureTime = Conversions.intToLocalTime(returnList.get(0).getDepartureTime());
        LocalTime rArrivalTime = Conversions.intToLocalTime(returnList.getLast().getArrivalTime());
        int rDuration = destination.getShortestTime() - departureTime;

        TransitGraphPath returnPath = new TransitGraphPath(rDepartureTime, rArrivalTime, rDuration, returnList);
        System.out.println(returnPath.getDepartureTime());
        return returnPath;
    }

    private void dijkstra(Node source, Node destination, int departureTime)
    {
        source.setShortestTime(departureTime);
        Set<Node> settled = new HashSet<Node>();
        PriorityQueue<Node> unsettled = new PriorityQueue<Node>();
        unsettled.add(source);
        while (!unsettled.isEmpty())
        {
            Node current = unsettled.poll();
            for (Entry<Node, List<Edge>> entry : current.getAdjacent().entrySet())
            {
                Node adjacent = entry.getKey();
                List<Edge> edges = entry.getValue();
                if (!settled.contains(adjacent))
                {
                    updateShortestPathDijkstra(current, adjacent, edges);
                    unsettled.add(adjacent);
                }
            }
            settled.add(current);

            if (current.getStop().getId() == destination.getStop().getId())
            {
                break;
            }
        }
    }

    private void updateShortestPathDijkstra(Node current, Node adjacent, List<Edge> edges)
    {
        int newShortestTime = Integer.MAX_VALUE;
        int shortestPretendTime = Integer.MAX_VALUE;

        int currentTripid = 0;
        if (!current.getShortestPath().isEmpty())
        {
            if (current.getShortestPath().getLast().getTripid() != -1)
            {
                currentTripid = current.getShortestPath().getLast().getTripid();
            }
        }

        Edge bestEdge = null;
        for (Edge edge : edges)
        {
            int earliestPossibleArrivalTime = edge.getPossibleArrivalTime(current.getShortestTime());
            int pretendTime = earliestPossibleArrivalTime;

            if (edge.getTripid() == currentTripid)
            {
                pretendTime -= (60*4);    
            }

            if (pretendTime < shortestPretendTime)
            {
                newShortestTime = earliestPossibleArrivalTime;
                shortestPretendTime = pretendTime;
                bestEdge = edge;
            }
        }

        if (newShortestTime < adjacent.getShortestTime())
        {
            adjacent.setShortestTime(newShortestTime);

            List<Edge> newShortestPath = new ArrayList<Edge>();
            for (Edge edge : current.getShortestPath())
            {
                newShortestPath.add(edge);
            }
            newShortestPath.add(bestEdge);
            adjacent.setShortestPath(newShortestPath);
        }

    }

    private void resetGraph()
    {
        for (Node node : nodes.values())
        {
            node.setShortestTime(Integer.MAX_VALUE);
            node.setShortestPath(new ArrayList<Edge>());
        }
    }

    private void buildFromTripsList(List<TTrip> trips)
    {
        System.out.println("building graph...");
        for (TTrip trip : trips)
        {
            for (TStopTimePoint stopTimePoint : trip.getStopTimePoints())
            {
                buildNode(stopTimePoint.getStop());
            }
        }

        for (TTrip trip : trips)
        {
            List<TStopTimePoint> stomTimePoints = trip.getStopTimePoints();
            for (int i = 1; i < stomTimePoints.size(); i++)
            {
                buildEdge(trip, stomTimePoints.get(i - 1), stomTimePoints.get(i));
            }
        }

        buildWalkingEdges();
    }

    private void buildWalkingEdges()
    {
        for (Node node1 : nodes.values())
        {
            for (Node node2 : nodes.values())
            {
                if (node1.getStop().getId() != node2.getStop().getId())
                {
                    buildWalkingEdge(node1, node2);
                }
            }
        }
    }

    private void buildNode(TStop stop)
    {
        if (!nodes.containsKey(stop.getId()))
        {
            nodes.put(stop.getId(), new Node(stop));
        }
    }

    private void buildEdge(TTrip trip, TStopTimePoint s1, TStopTimePoint s2)
    {
        int departureTime = Conversions.localTimeToInt(s1.getDepartureTime());
        int arrivalTime = Conversions.localTimeToInt(s2.getArrivalTime());
        Node source = nodes.get(s1.getStop().getId());
        Node destination = nodes.get(s2.getStop().getId());
        int tripid = trip.getId();
        int routeid = trip.getRouteid();
        String routeShortName = trip.getRouteShortName();
        String routeLongName = trip.getRouteLongName();
        TShape shape = trip.getShape();
        int shapeDistTraveledStart = s1.getShapeDistTraveled();
        int shapeDistTraveledEnd = s2.getShapeDistTraveled();

        Edge newEdge = new Edge(departureTime, arrivalTime, source, destination, tripid, routeid, routeShortName, routeLongName, shape, shapeDistTraveledStart, shapeDistTraveledEnd);

        if (!source.getAdjacent().containsKey(destination))
        {
            source.getAdjacent().put(destination, new ArrayList<Edge>());
        }
        source.getAdjacent().get(destination).add(newEdge);
        edgeCount++;
    }

    private void buildWalkingEdge(Node node1, Node node2)
    {
        List<TShapePoint> shapePointList = new ArrayList<TShapePoint>();
        TShapePoint shapePoint1 = new TShapePoint(0, node1.getStop().getCoordinates(), 0);
        TShapePoint shapePoint2 = new TShapePoint(1, node2.getStop().getCoordinates(), 1);
        shapePointList.add(shapePoint1);
        shapePointList.add(shapePoint2);

        TShape shape = new TShape(-1, shapePointList);

        int departureTime = 0;
        int arrivalTime = 1;
        Node source = node1;
        Node destination = node2;
        int tripid = -1;
        int routeid = -1;
        String routeShortName = "walk";
        String routeLongName = "walkchester";
        //shape
        int shapeDistTraveledStart = 0;
        int shapeDistTraveledEnd = 1;

        Edge newWalkingEdge = new Edge(departureTime, arrivalTime, source, destination, tripid, routeid, routeShortName, routeLongName, shape, shapeDistTraveledStart, shapeDistTraveledEnd, TransportType.FOOT);

        if (newWalkingEdge.getPossibleArrivalTime(0) > (60*6))
        {
            return;
        }

        if (!source.getAdjacent().containsKey(destination))
        {
            source.getAdjacent().put(destination, new ArrayList<Edge>());
        }

        source.getAdjacent().get(destination).add(newWalkingEdge);
        edgeCount++;
    }

    // testing postcode pairs
    // 6229EM: 2578130
    // 6211CM: 2578384
    //
    // 6211AL
    // 6216NV
    // 
    // 6217KM 1104
    // 6225DR 2081
    //
    // 6215KA 688
    // 6211PK 181
    // 
    // 6223BV 1769
    // 6217BX 1006
    //
    // 6228CS 2542
    // 6217EP 1029
    //
    // OLD
    // 6229EM apart hotel randwyck stopid: 2578129
    // 6211CM maastricht markt stopid: 2578366
    public static void main(String[] args)
    {
        int originid = 2578130;
        int destinationid = 2578384;
        System.out.println("testing graph...");
        System.out.println("fetching trips...");

        var graph = new TransitGraphCalculator();
        System.out.println("built graph with " + graph.nodes.size() + "nodes and " + graph.edgeCount + "edges");

        System.out.println("testing finding path from stop " + originid + " to stop " + destinationid + " starting at 12:00");

        var transitGraphPath = graph.getPathDijkstra(originid, destinationid, LocalTime.of(12, 0, 0));
        var path = transitGraphPath.getEdgeList();
        System.out.println("path size: " + path.size());
        for (int i = 0; i < path.size(); i++)
        {
            Edge edge = path.get(i);
            System.out.println(edge.getSource().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getDepartureTime()).toString() + " | bus: " + edge.getRouteShortName() + " sid: " + edge.getSource().getStop().getId() + " sp: " + edge.getShape().getShapePoints().size());
            if (i == path.size() - 1)
            {
                System.out.println(edge.getDestination().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getArrivalTime()).toString() + " | bus: " + edge.getRouteShortName() + " sid: " + edge.getDestination().getStop().getId()+ " sp: " + edge.getShape().getShapePoints().size());
            }
        }
        //graph.debug();
    }
}