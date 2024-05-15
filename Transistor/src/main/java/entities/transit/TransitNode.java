package entities.transit;

import entities.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TransitNode
{
    private final int id;
    private final Coordinate coordinate;
    private final String name;
    private double departureTime;
    private double arrivalTime;
    private final List<TransitEdge> edges;
    private final TransitShape shape;

    public TransitNode(int id, Coordinate coordinate, String name, TransitShape shape)
    {
        this.id = id;
        this.coordinate = coordinate;
        this.name = name;
        this.departureTime = Double.MAX_VALUE;
        this.arrivalTime = Double.MAX_VALUE;
        this.edges = new ArrayList<>();
        this.shape = shape;
    }

    public int id()
    {
        return id;
    }

    public Coordinate coordinate()
    {
        return coordinate;
    }

    public String name()
    {
        return name;
    }

    public double arrivalTime()
    {
        return arrivalTime;
    }
    public double departureTime() { return departureTime; }

    public List<TransitEdge> edges()
    {
        return edges;
    }

    public TransitShape shape()
    {
        return shape;
    }

    public void setArrivalTime(double time)
    {
        this.arrivalTime = time;
    }
    public void setDepartureTime(double time) { this.departureTime = time; }
    public void addEdge(TransitEdge edge) { this.edges.add(edge); }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TransitNode) obj;
        return this.id == that.id &&
                Objects.equals(this.coordinate, that.coordinate) &&
                Objects.equals(this.name, that.name) &&
                this.arrivalTime == that.arrivalTime &&
                Objects.equals(this.edges, that.edges) &&
                Objects.equals(this.shape, that.shape);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, coordinate, name, arrivalTime, edges, shape);
    }

    @Override
    public String toString()
    {
        return "TransitNode[" +
                "id=" + id + ", " +
                "coordinate=" + coordinate + ", " +
                "name=" + name + ", " +
                "arrivalTime=" + arrivalTime + ", " +
                "edges=" + edges + ", " +
                "shape=" + shape + ']';
    }

}