package calculators;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Test
{
    public static class Node
    {
        public int id;
        public int stopId;
        public List<Edge> edges = new ArrayList<>();
        public List<Edge> shortestPath = new ArrayList<>();
        public LocalTime arrivalTime = LocalTime.MAX;

        public Node(int id, int stopId)
        {
            this.id = id;
            this.stopId = stopId;
        }
    }

    public static class Edge
    {
        public int tripId;
        public int source;
        public int destination;

        public LocalTime arrivalTime;
        public LocalTime departureTime;
        public int duration;

        public Edge(int tripId, int source, int destination, LocalTime arrivalTime, LocalTime departureTime, int duration)
        {
            this.tripId = tripId;
            this.source = source;
            this.destination = destination;

            this.arrivalTime = arrivalTime;
            this.departureTime = departureTime;

            this.duration = duration;
        }

        public LocalTime getDurationForTime(LocalTime currentTime)
        {
            return currentTime.isBefore(departureTime) ? departureTime.withMinute(duration): LocalTime.MAX;
        }

        @Override
        public String toString()
        {
            return source + " " + destination;
        }
    }

    public static List<Edge> TestAlgorithm(Node[] nodes, boolean[] settled, int originId, int destinationId, LocalTime startTime)
    {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(Comparator.comparing(node -> node.arrivalTime));
        pq.add(nodes[originId]);
        nodes[originId].arrivalTime = startTime;

        while (!pq.isEmpty())
        {
            var currentNode = pq.poll();

            if (currentNode.id == destinationId)
                return currentNode.shortestPath;

            if (settled[currentNode.id])
                continue;

            settled[currentNode.id] = true;
            for (var edge : currentNode.edges)
            {
                var neighbour = nodes[edge.destination];
                if (settled[neighbour.id]) continue;

                var newTime = edge.getDurationForTime(startTime);
                if (newTime.isBefore(neighbour.arrivalTime))
                {
                    neighbour.arrivalTime = newTime;
                    var newShortestPath = new ArrayList<Edge>(currentNode.shortestPath);
                    newShortestPath.add(edge);
                    neighbour.shortestPath = newShortestPath;
                }

                pq.add(neighbour);
            }
        }

        return null;
    }

    public static void main(String[] args)
    {
        Node origin = new Node(0, 0);
        Node node1 = new Node(1, 0);
        Node node2 = new Node(2, 0);
        Node node3 = new Node(3, 0);
        Node destination = new Node(4, 0);

        origin.edges.add(new Edge(0, origin.id, node1.id, LocalTime.now().plusMinutes(10), LocalTime.now().plusMinutes(15), 5));
        origin.edges.add(new Edge(0, origin.id, node3.id, LocalTime.now().plusMinutes(20), LocalTime.now().plusMinutes(25), 5));

        node1.edges.add(new Edge(0, node1.id, node2.id, LocalTime.now().plusMinutes(5), LocalTime.now().plusMinutes(10), 5));
        node1.edges.add(new Edge(0, node1.id, node3.id, LocalTime.now().plusMinutes(5), LocalTime.now().plusMinutes(10), 5));
        node2.edges.add(new Edge(0, node2.id, destination.id, LocalTime.now().plusMinutes(20), LocalTime.now().plusMinutes(25), 5));
        node3.edges.add(new Edge(0, node3.id, destination.id, LocalTime.now().plusMinutes(10), LocalTime.now().plusMinutes(15), 5));

        Node[] nodes = new Node[] {origin, node1, node2, node3, destination};
        boolean[] settled = new boolean[nodes.length];

        System.out.println(TestAlgorithm(nodes, settled, origin.id, destination.id, LocalTime.now()));
    }
}