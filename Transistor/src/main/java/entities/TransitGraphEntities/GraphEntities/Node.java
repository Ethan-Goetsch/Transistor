package entities.TransitGraphEntities.GraphEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.TransitGraphEntities.TStop;

public class Node implements Comparable<Node>
{
    private TStop stop;
    private int shortestTime;
    private Map<Node, List<Edge>> adjacent;
    private List<Edge> shortestPath;

    public Node(TStop stop)
    {
        this.stop = stop;
        this.shortestTime = Integer.MAX_VALUE;
        this.adjacent = new HashMap<Node, List<Edge>>();
        this.shortestPath = new ArrayList<Edge>();
    }

    public Node(TStop stop, int shortestTime, Map<Node, List<Edge>> adjacent, List<Edge> shortestPath)
    {
        this.stop = stop;
        this.shortestTime = shortestTime;
        this.adjacent = adjacent;
        this.shortestPath = shortestPath;
    }

    @Override
    public int compareTo(Node o)
    {
        return Integer.compare(shortestTime, o.getShortestTime());
    }

    public TStop getStop()
    {
        return stop;
    }

    public void setStop(TStop stop)
    {
        this.stop = stop;
    }

    public int getShortestTime()
    {
        return shortestTime;
    }

    public void setShortestTime(int shortestTime)
    {
        this.shortestTime = shortestTime;
    }

    public Map<Node, List<Edge>> getAdjacent()
    {
        return adjacent;
    }

    public void setAdjacent(Map<Node, List<Edge>> adjacent)
    {
        this.adjacent = adjacent;
    }

    public List<Edge> getShortestPath()
    {
        return shortestPath;
    }

    public void setShortestPath(List<Edge> shortestPath)
    {
        this.shortestPath = shortestPath;
    }
}
