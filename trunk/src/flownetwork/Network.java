package flownetwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Network {

    private HashSet<Node> nodes;
    private Node source, sink;
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
    
    public int numNodes() {
    	return nodes.size();
    }
    
    public int numEdges() {
    	int num_edges = 0;
    	for(Node n : nodes) {
    		num_edges += n.getOutgoingEdges().size();
    	}
    	return num_edges;
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
                //OPTIMIZATION: Iterate through the outgoing and ingoing edges
                //separately to avoid concatenating the list in each iteration
                for (Node d : n.getEdges()) {
                    if (n.getCapacityOfEdgeTo(d) > 0 && d.getLevel() > n.getLevel() + 1) {
                        d.setLevel(n.getLevel() + 1);
                        n.addLNEdge(d);
                        queue.add(d);
                    }
                }
            }
        }
    }
    
    public void printLeveledNetwork() {
    	ArrayList<TreeSet<Integer>> level_nodes = new ArrayList<TreeSet<Integer>>(sink.getLevel());
    	for(int i = 0; i < sink.getLevel(); i++)
    		level_nodes.add(new TreeSet<Integer>());
    	for(Node n : nodes)
    		if(n.getLevel() < sink.getLevel())
    			level_nodes.get(n.getLevel()).add(n.getEnumeration());
    	for(int i = 0; i < sink.getLevel(); i++) {
    		System.out.print("Level "+i+" contains nodes: ");
    		Iterator<Integer> n = level_nodes.get(i).iterator();
    		do {
    			System.out.print(n.next());
    			if(n.hasNext()) System.out.print(", ");
    			else System.out.println(".");
    		} while(n.hasNext());
    	}
    	System.out.println("Level "+sink.getLevel()+" contains nodes: "+sink.getEnumeration()+".\n");
    }
    
    @SuppressWarnings("unchecked")
	public int augmentLNPath(Node n, int min_capacity) {
        if (n == sink) {
            return min_capacity;
        }
        if (n.getLNEdges().size() == 0) {
            return 0;
        }
        HashSet<Node> lnedges = (HashSet<Node>)n.getLNEdges().clone();
        for (Node e : lnedges) {
            int c = augmentLNPath(e, Math.min(min_capacity, n.getCapacityOfEdgeTo(e)));
            if (c != 0) {
            	min_capacity = Math.min(c, min_capacity);
                n.addLoadToEdgeTo(e, min_capacity);
                if (n.getCapacityOfEdgeTo(n) == 0) {
                    n.removeLNEdgeTo(e);
                }
                return min_capacity;
            } else {
                n.removeLNEdgeTo(e);
            }
        }
        return 0;
    }

    public boolean augmentLNPaths() {
        int to_add = augmentLNPath(source, Integer.MAX_VALUE);
        if (to_add == 0) {
            return false;
        }
        while (to_add > 0) {
            maxflow += to_add;
            to_add = augmentLNPath(source, Integer.MAX_VALUE);
        }
        return true;
    }
    
    public void printHeaderInfo() {
    	System.out.println("Network size: "+numNodes()+" nodes, "+numEdges()+" edges, source "+source.getEnumeration()+", sink "+sink.getEnumeration()+".");
    }

    int calculateMaxFlow() {
        maxflow = 0;
        int i = 1;
        boolean loop = true;
        for (Node n : nodes) {
            n.resetLNEdges();
        }
        while (loop) {
            calculateLeveledNetwork();
            if(sink.getLevel() != Integer.MAX_VALUE) {
            	System.out.println("Leveled network in iteration " + i + ":");
            	i++;
            	printLeveledNetwork();
            }
            System.gc();
            loop = augmentLNPaths();
        }
        return maxflow;
    }
}
