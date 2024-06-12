package entities.TransitGraphEntities;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

class Edge 
{
    private int tripid;
    private int routeid;
    private String name;

    private LocalTime startTime;
    private LocalTime endTime;

    private Node originNode;
    private Node destinationNode;

    public Edge(int tripid, int routeid, String name, LocalTime startTime, LocalTime endTime, Node originNode, Node destinationNode)
    {
        this.tripid = tripid;
        this.routeid = routeid;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.originNode = originNode;
        this.destinationNode = destinationNode;
    }

    // in seconds
    public LocalTime getArrivalTime(LocalTime currentTime)
    {
        return null;
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


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public LocalTime getStartTime()
    {
        return startTime;
    }


    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }


    public LocalTime getEndTime()
    {
        return endTime;
    }


    public void setEndTime(LocalTime endTime)
    {
        this.endTime = endTime;
    }


    public Node getOriginNode()
    {
        return originNode;
    }


    public void setOriginNode(Node originNode)
    {
        this.originNode = originNode;
    }


    public Node getDestinationNode()
    {
        return destinationNode;
    }


    public void setDestinationNode(Node destinationNode)
    {
        this.destinationNode = destinationNode;
    }

    
}

class Node implements Comparable<Node>
{
    private int stopid;
    private double latitude;
    private double longitude;
    private String name;
    public HashMap<Node, ArrayList<Edge>> adjacent;

    private LocalTime shortestTime;
    public ArrayList<Edge> shortestPath;

    public Node(int stopid, double latitude, double longitude, String name, HashMap<Node, ArrayList<Edge>> adjacent, LocalTime shortestTime, ArrayList<Edge> shortestPath)
    {
        this.stopid = stopid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.adjacent = adjacent;
        this.shortestTime = shortestTime;
        this.shortestPath = shortestPath;
    }
    
    @Override
    public int compareTo(Node other)
    {
        return this.shortestTime.compareTo(other.shortestTime);
    }

    public int getStopid()
    {
        return stopid;
    }

    public void setStopid(int stopid)
    {
        this.stopid = stopid;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public HashMap<Node, ArrayList<Edge>> getAdjacent()
    {
        return adjacent;
    }

    public void setAdjacent(HashMap<Node, ArrayList<Edge>> adjacent)
    {
        this.adjacent = adjacent;
    }

    public LocalTime getShortestTime()
    {
        return shortestTime;
    }

    public void setShortestTime(LocalTime shortestTime)
    {
        this.shortestTime = shortestTime;
    }

    public ArrayList<Edge> getShortestPath()
    {
        return shortestPath;
    }

    public void setShortestPath(ArrayList<Edge> shortestPath)
    {
        this.shortestPath = shortestPath;
    }
}

public class TransitGraph
{
    //stop id to node
    public HashMap<Integer, Node> nodes;
    public int lastNodeID;
    public int lastEdgeID;

    public TransitGraph()
    {
        this.nodes = new HashMap<Integer, Node>();
        this.lastNodeID = 0;
        this.lastEdgeID = 0;
    }

    public void addNode()
    {
        
    }

    public void addEdge(int stop, int nodeid2, int weight)
    {

    }

    public List<Edge> getShortestTime(int sourceid, int destid, int tdep)
    {
        Node source = nodes.get(sourceid);
        Node dest = nodes.get(destid);

        calcPathDjk(source, tdep, destid);

        return dest.shortestPath;
    }

    public void calcPathDjk(Node source, int tdep, int destid)
    {
        //source.distance = tdep;
        HashSet<Node> settled = new HashSet<Node>();
        PriorityQueue<Node> unsettled = new PriorityQueue<Node>();
        unsettled.add(source);

        while (!unsettled.isEmpty())
        {
            Node current = unsettled.poll();
            for (Entry<Node, ArrayList<Edge>> entry : current.adjacent.entrySet())
            {
                Node adjacent = entry.getKey();
                ArrayList<Edge> edges = entry.getValue();

                if (!settled.contains(adjacent))
                {
                    updatePathDist(current, adjacent, edges);
                    unsettled.add(adjacent);
                }
            }
            settled.add(current);
            // if (current.id == destid)
            // {
            //     break;    
            // }
        }
    }

    public void updatePathDist(Node current, Node adjacent, ArrayList<Edge> edges)
    {


    }
}
