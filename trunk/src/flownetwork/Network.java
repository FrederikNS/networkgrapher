package flownetwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Network {
    private HashSet<Node> nodes;
    private Node source, sink;

    public Network(Collection<Node> nodes, Node source, Node sink) {
        this.nodes = new HashSet<Node>(nodes);
        this.source = source;
        this.sink = sink;
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
            n.resetLNEdges();
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
                    if (n.getCapacityOfEdgeTo(d) > 0) {
                    	if(n.getLevel() + 1 < d.getLevel()) {
                    		d.setLevel(n.getLevel() + 1);
                            queue.add(d);
                    	}
                    	if(n.getLevel() < d.getLevel())
                    		n.addLNEdge(d);
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
    
    public void printLN() {
    	for(Node n : nodes) {
    		if(n.getLevel() != Integer.MAX_VALUE) {
    			System.out.print("Node " + n.getEnumeration() + ": ");
    			for(Node e : n.getLNEdges()) {
    				System.out.print(e.getEnumeration() + ", ");
    			}
    			System.out.println();
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public int augmentLNPath(Node n, int min_capacity) {
        if (n == sink) {
            return min_capacity;
        }
        if(min_capacity == 0) return 0;
        
        int added_load = 0;
        ArrayList<Node> lnedges = new ArrayList<Node>(n.getLNEdges());
        //Collections.shuffle(lnedges);
        for (Node e : lnedges) {
        	int e_cap = n.getCapacityOfEdgeTo(e);
        	if(e_cap > 0) {
	            int max_path_load = augmentLNPath(e, Math.min(min_capacity, e_cap));
	        	min_capacity -= max_path_load;
	        	e_cap -= max_path_load;
	            n.addLoadToEdgeTo(e, max_path_load);
	            added_load += max_path_load;
	            if (max_path_load == 0) {
	                n.removeLNEdgeTo(e);
	            }
        	}
        	if(e_cap == 0) 
                n.removeLNEdgeTo(e);
        }
        return added_load;
    }

    public boolean augmentLNPaths() {
    	int initial_maxflow = getMaxFlow();
    	augmentLNPath(source, Integer.MAX_VALUE);
		if(initial_maxflow != getMaxFlow())
			return true;
		return false;
    }
    
    public void printHeaderInfo() {
    	System.out.println("Network size: "+numNodes()+" nodes, "+numEdges()+" edges, source "+source.getEnumeration()+", sink "+sink.getEnumeration()+".");
    }

    int calculateMaxFlow() {
        int i = 1;
        for (Node n : nodes) {
            n.resetLNEdges();
        }
        calculateLeveledNetwork();
        if(sink.getLevel() != Integer.MAX_VALUE) {
        	System.out.println("Leveled network in iteration " + i + ":");
        	printLeveledNetwork();
        	i++;
        }
        while (augmentLNPaths()) {
            calculateLeveledNetwork();
            if(sink.getLevel() != Integer.MAX_VALUE) {
            	System.out.println("Leveled network in iteration " + i + ":");
            	//printLeveledNetwork();
            }
            i++;
        }
        System.out.println("\nIterations: " + (i-2) + ".");
        return getMaxFlow();
    }
    
    public int getMaxFlow() {
    	int maxflow = 0;
    	for(Node n : source.getOutgoingEdges()) {
    		maxflow += source.getLoadToEdge(n);
    	}
    	return maxflow;
    }
}
