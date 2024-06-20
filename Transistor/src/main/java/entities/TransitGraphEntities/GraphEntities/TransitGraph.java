package entities.TransitGraphEntities.GraphEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import database.DatabaseManager;
import database.queries.GetAllTripsMaasQuery;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TStop;
import entities.TransitGraphEntities.TStopTimePoint;
import entities.TransitGraphEntities.TTrip;
import utils.Conversions;

public class TransitGraph
{
    private Map<Integer, Node> nodes;// stop id to node
    private int edgeCount = 0;

    public TransitGraph()
    {
        this.nodes = new HashMap<Integer, Node>();
    }

    public TransitGraph(List<TTrip> trips)
    {
        this.nodes = new HashMap<Integer, Node>();
        buildFromTripsList(trips);
    }

    public List<Edge> getPathDijkstra(int originStopID, int desinationStopID, int departureTime)
    {
        resetGraph();
        Node source = nodes.get(originStopID);
        Node destination = nodes.get(desinationStopID);

        dijkstra(source, destination, departureTime);
        return destination.getShortestPath();
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
        Edge bestEdge = null;
        for (Edge edge : edges)
        {
            int earliestPossibleArrivalTime = edge.getPossibleArrivalTime(current.getShortestTime());
            if (earliestPossibleArrivalTime < newShortestTime)
            {
                newShortestTime = earliestPossibleArrivalTime;
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

        Edge newEdge = new Edge(departureTime, arrivalTime, source, destination, tripid, routeid, routeShortName, routeLongName, shape);

        if (!source.getAdjacent().containsKey(destination))
        {
            source.getAdjacent().put(destination, new ArrayList<Edge>());
        }
        source.getAdjacent().get(destination).add(newEdge);
        edgeCount++;
    }

    // 6229EM apart hotel randwyck stopid: 2578129
    // 6211CM maastricht markt stopid: 2578364
    public static void main(String[] args)
    {
        System.out.println("testing graph...");
        System.out.println("fetching trips...");
        var trips = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasQuery());
        System.out.println("fetched trips");
        TransitGraph graph = new TransitGraph(trips);
        System.out.println("built graph with " + graph.nodes.size() + "nodes and " + graph.edgeCount + "edges");

        System.out.println("testing finding path from stop 2578129 (near apart hotel randwyck) to stop 2578364 (near maastricht markt) starting at 12:00");

        var path = graph.getPathDijkstra(2578129, 2578364, 60 * 60 * 12);
        System.out.println("path size: " + path.size());
        for (int i = 0; i < path.size(); i++)
        {
            Edge edge = path.get(i);
            System.out.println(edge.getSource().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getDepartureTime()).toString());
            if (i == path.size() - 1)
            {
                System.out.println(edge.getDestination().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getArrivalTime()).toString());
            }
        }
    }
}