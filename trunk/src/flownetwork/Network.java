package flownetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class Network {

    private HashSet<Node> nodes;
    private Node source,  sink;
    int maxflow;

    public Network(Collection<Node> nodes, Node source, Node sink) {
        this.nodes = new HashSet<Node>(nodes);
        this.source = source;
        this.sink = sink;
        maxflow = 0;
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public Node getSource() {
        return source;
    }

    public Node getSink() {
        return sink;
    }

    public void calculateLeveledNetwork() {
        for (Node n : nodes) {
            n.setLevel(Integer.MAX_VALUE);
        }

        LinkedList<Node> queue = new LinkedList<Node>();
        source.setLevel(0);
        queue.add(getSource());

        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (n.getLevel() < sink.getLevel()) {
                int level = n.getLevel() + 1;
                //OPTIMIZATION: Iterate through the outgoing and ingoing edges
                //separately to avoid concatenating the list in each iteration
                for (Node d : n.getEdges()) {
                    if (n.getCapacityOfEdgeTo(d) > 0 && d.getLevel() > level) {
                        d.setLevel(level);
                        n.addLNEdge(d);
                        queue.add(d);
                    }
                }
            }
        }
    //System.out.println(""+sink.getLevel());
    }

    public int LNHax(Node n, int min_capacity) {
        int c;
        if (n.getLNEdges().size() == 0) {
            return 0;
        }
        if (n == sink) {
            return min_capacity;
        }
        for (Node e : n.getLNEdges()) {
            c = LNHax(e, Math.min(min_capacity, n.getCapacityOfEdgeTo(e)));
            if (c != 0) {
                min_capacity = Math.min(c, min_capacity);
                n.addLoadToEdgeTo(e, min_capacity);
                if (n.getCapacityOfEdgeTo(n) == 0) {
                    n.removeLNEdgeTo(e);
                }
                return min_capacity;
            }
        }
        return 0;
    }

    public boolean augmentLNPaths() {
        int to_add = LNHax(source, Integer.MAX_VALUE);
        if (to_add == 0) {
            return false;
        }
        while (to_add > 0) {
            to_add = LNHax(source, Integer.MAX_VALUE);
            maxflow += to_add;
        }
        return true;
    }

    int calculateMaxFlow() {
        maxflow = 0;
        boolean loop = true;

        for (Node n : nodes) {
            n.resetLNEdges();
        }
        while (loop) {
            calculateLeveledNetwork();
            loop = augmentLNPaths();
        }
        return maxflow;
    }
}
