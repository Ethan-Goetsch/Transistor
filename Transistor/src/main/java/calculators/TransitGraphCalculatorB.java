package calculators;

import database.DatabaseManager;
import database.queries.GetAllTripsMaasQuery;
import entities.TransitGraphEntities.GraphEntities.Edge;
import entities.TransitGraphEntities.GraphEntities.Node;
import entities.TransitGraphEntities.GraphEntities.TransitGraphPath;
import entities.TransitGraphEntities.TShape;
import entities.TransitGraphEntities.TStop;
import entities.TransitGraphEntities.TStopTimePoint;
import entities.TransitGraphEntities.TTrip;
import entities.TransportType;
import utils.Conversions;

import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

public class TransitGraphCalculatorB
{
    private Map<Integer, Node> nodes;// stop id to node
    private int edgeCount = 0;

    public TransitGraphCalculatorB()
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
        LocalTime rArrivalTime = Conversions.intToLocalTime(returnList.get(returnList.size() - 1).getArrivalTime());
        int rDuration = destination.getShortestTime() - departureTime;

        TransitGraphPath returnPath = new TransitGraphPath(rDepartureTime, rArrivalTime, rDuration, returnList);
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

    // private void updateShortestPathDijkstra(Node current, Node adjacent, List<Edge> edges)
    // {
    //     int newShortestTime = Integer.MAX_VALUE;

    //     Edge bestEdge = null;
    //     for (Edge edge : edges)
    //     {
    //         int earliestPossibleArrivalTime = edge.getPossibleArrivalTime(current.getShortestTime());

    //         if (earliestPossibleArrivalTime < newShortestTime)
    //         {
    //             newShortestTime = earliestPossibleArrivalTime;
    //             bestEdge = edge;
    //         }
    //     }

    //     if (newShortestTime < adjacent.getShortestTime())
    //     {
    //         adjacent.setShortestTime(newShortestTime);

    //         List<Edge> newShortestPath = new ArrayList<Edge>();
    //         for (Edge edge : current.getShortestPath())
    //         {
    //             newShortestPath.add(edge);
    //         }
    //         newShortestPath.add(bestEdge);
    //         adjacent.setShortestPath(newShortestPath);
    //     }

    // }

    private void updateShortestPathDijkstra(Node current, Node adjacent, List<Edge> edges) {
        int newShortestTime = Integer.MAX_VALUE;
        int shortestPretendTime = Integer.MAX_VALUE;

        int currentTripId = current.getShortestPath().isEmpty() ? 0 : current.getShortestPath().getLast().getTripid();

        Edge bestEdge = null;
        boolean isWalkingTransfer = false;

        // Check bus connections
        for (Edge edge : edges) {
            int earliestPossibleArrivalTime = edge.getPossibleArrivalTime(current.getShortestTime());
            int pretendTime = earliestPossibleArrivalTime;

            if (edge.getTripid() == currentTripId) {
                pretendTime -= (60 * 4);
            }

            if (pretendTime < shortestPretendTime) {
                newShortestTime = earliestPossibleArrivalTime;
                shortestPretendTime = pretendTime;
                bestEdge = edge;
                isWalkingTransfer = false;
            }
        }
        System.out.println("Shortest time by bus: " + newShortestTime);

        // Check walking transfer
        int walkingTime = calculateWalkingTime(current.getStop(), adjacent.getStop());
        int walkingArrivalTime = current.getShortestTime() + walkingTime;

        if (walkingArrivalTime < shortestPretendTime) {
            newShortestTime = walkingArrivalTime;
            shortestPretendTime = walkingArrivalTime;
            bestEdge = createWalkingTransferEdge(current, adjacent, walkingTime);
            isWalkingTransfer = true;
        }
        System.out.println();
        System.out.println("Shortest time by walking: " + shortestPretendTime);

        if (newShortestTime < adjacent.getShortestTime()) {
            adjacent.setShortestTime(newShortestTime);

            List<Edge> newShortestPath = new ArrayList<>(current.getShortestPath());
            newShortestPath.add(bestEdge);
            adjacent.setShortestPath(newShortestPath);
        }


    }

    private Edge createWalkingTransferEdge(Node from, Node to, int walkingTime) {
        int departureTime = from.getShortestTime();
        int arrivalTime = departureTime + walkingTime;
        return new Edge(departureTime, arrivalTime, from, to);
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

    // private void buildWalkingEdges()
    // {
    //     for (Node node1 : nodes.values())
    //     {
    //         for (Node node2 : nodes.values())
    //         {
    //             if (node1.getStop().getId() != node2.getStop().getId())
    //             {
    //                 TShapePoint shapePoint1 = new TShapePoint(0, node1.getStop().getCoordinates(), 0);
    //                 TShapePoint shapePoint2 = new TShapePoint(1, node2.getStop().getCoordinates(), 1);
    //                 List<TShapePoint> shapePointList = new ArrayList<TShapePoint>();
    //                 shapePointList.add(shapePoint1);
    //                 shapePointList.add(shapePoint2);

    //                 TShape shape = new TShape(-1, shapePointList);

    //                 int departureTime =
    //                 int arrivalTime = Conversions.localTimeToInt(s2.getArrivalTime());
    //                 Node source = nodes.get(s1.getStop().getId());
    //                 Node destination = nodes.get(s2.getStop().getId());
    //                 int tripid = trip.getId();
    //                 int routeid = trip.getRouteid();
    //                 String routeShortName = trip.getRouteShortName();
    //                 String routeLongName = trip.getRouteLongName();
    //                 TShape shape = trip.getShape();
    //                 int shapeDistTraveledStart = s1.getShapeDistTraveled();
    //                 int shapeDistTraveledEnd = s2.getShapeDistTraveled();
    //             }
    //         }
    //     }
    // }

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

    private int calculateWalkingTime(TStop from, TStop to) {
        // Use AerialCalculator to get the distance between stops
        AerialCalculator calculator = new AerialCalculator();
        double distance = calculator.distanceToPoint(from.getCoordinates(), to.getCoordinates());
        System.out.println("Distance: " + distance);
        System.out.println("time: " + distance / TransportType.FOOT.getSpeedInKilometersPerSecond());
        // Convert distance to walking time (assuming average walking speed)
        return (int) (distance / TransportType.FOOT.getSpeedInKilometersPerSecond());
    }

    private void debug()
    {
        Node dbgNode = nodes.get(2578413);
        System.out.println(dbgNode.getAdjacent().keySet().size());
        for (var edgelist : dbgNode.getAdjacent().values())
        {
            for (Edge edge : edgelist)
            {
                if (edge.getRouteShortName().equals("5"))
                {
                    System.out.println("b5: " + Conversions.intToLocalTime(edge.getDepartureTime()) + " | " + Conversions.intToLocalTime(edge.getArrivalTime()));
                }
                if (edge.getRouteShortName().equals("8"))
                {
                    System.out.println("b8: " + Conversions.intToLocalTime(edge.getDepartureTime()) + " | " + Conversions.intToLocalTime(edge.getArrivalTime()));
                }
            }
        }
        System.out.println("xd");
        System.out.println("xd2");
    }

    // OLD
    // 6229EM apart hotel randwyck stopid: 2578129
    // 6211CM maastricht markt stopid: 2578366

    // 6229EM: 2578130
    // 6211CM: 2578384
    public static void main(String[] args)
    {
        int originid = 2578130;
        int destinationid = 2578384;
        System.out.println("testing graph...");
        System.out.println("fetching trips...");

        var graph = new TransitGraphCalculatorB();
        System.out.println("fetched trips");
        System.out.println("built graph with " + graph.nodes.size() + "nodes and " + graph.edgeCount + "edges");

        System.out.println("testing finding path from stop " + originid + " to stop " + destinationid + " starting at 12:00");

        var transitGraphPath = graph.getPathDijkstra(originid, destinationid, LocalTime.of(12, 0, 0));
        var path = transitGraphPath.getEdgeList();
        System.out.println("path size: " + path.size());
        for (int i = 0; i < path.size(); i++)
        {
            Edge edge = path.get(i);
            System.out.println(edge.getSource().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getDepartureTime()).toString() + " | bus: " + edge.getRouteShortName() + " sid: " + edge.getSource().getStop().getId());
            if (i == path.size() - 1)
            {
                System.out.println(edge.getDestination().getStop().getName() + " @ " + Conversions.intToLocalTime(edge.getArrivalTime()).toString() + " | bus: " + edge.getRouteShortName() + " sid: " + edge.getDestination().getStop().getId());
            }
        }
        //graph.debug();
    }
}