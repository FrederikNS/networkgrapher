package flownetwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        }

        LinkedList<Node> queue = new LinkedList<Node>();
        source.setLevel(0);
        queue.add(getSource());
        
        while (!queue.isEmpty()) {
            Node n = queue.poll();
//            System.out.print("\nBFS " + n.getEnumeration() + "; ");
            if (n.getLevel() < sink.getLevel()) {
                //OPTIMIZATION: Iterate through the outgoing and ingoing edges
                //separately to avoid concatenating the list in each iteration
                for (Node d : n.getEdges()) {
                    if (n.getCapacityOfEdgeTo(d) > 0) {
                    	if(d.getLevel() > n.getLevel() + 1) {
//                    		System.out.print(d.getEnumeration() + ", ");
                    		d.setLevel(n.getLevel() + 1);
                            queue.add(d);
                    	}
                        n.addLNEdge(d);
                    }
                }
            }
//            System.out.println();
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
        
        int added_load = 0;
        ArrayList<Node> lnedges = (ArrayList<Node>)n.getLNEdges().clone();
        Collections.shuffle(lnedges);
        for (Node e : lnedges) {
//        	System.out.println("Went into: " + n.getEnumeration() + ", " + e.getEnumeration());
            int max_path_load = augmentLNPath(e, Math.min(min_capacity, n.getCapacityOfEdgeTo(e)));
//        	System.out.println(n.getEnumeration() + ", " + e.getEnumeration() + " got max path load: " + max_path_load);
        	min_capacity -= max_path_load;
            n.addLoadToEdgeTo(e, max_path_load);
            added_load += max_path_load;
            if (n.getCapacityOfEdgeTo(e) == 0) {
//            	System.out.println("Removed: " + n.getEnumeration() + ", " + e.getEnumeration());
                n.removeLNEdgeTo(e);
            }
        }
        return added_load;
    }

    public boolean augmentLNPaths() {
    	boolean changed = false;
//    	int c = 0;
    	do {
        	int initial_maxflow = getMaxFlow();
    		System.out.println("Added load: " +  augmentLNPath(source, Integer.MAX_VALUE) + "\n");
    		if(initial_maxflow != getMaxFlow()) {
    			changed = true;
//    			c++;
//    			System.out.print(c);
    		}
    		else break;
    	} while (true);
    	return changed;
    }
    
    public void printHeaderInfo() {
    	System.out.println("Network size: "+numNodes()+" nodes, "+numEdges()+" edges, source "+source.getEnumeration()+", sink "+sink.getEnumeration()+".");
    }

    int calculateMaxFlow() {
        int i = 0;
        for (Node n : nodes) {
            n.resetLNEdges();
        }
        calculateLeveledNetwork();
        printLN();
        while (augmentLNPaths()) {
            calculateLeveledNetwork();
//            printLN();
            /*if(sink.getLevel() != Integer.MAX_VALUE) {
            	System.out.println("Leveled network in iteration " + i + ":");
            	printLeveledNetwork();
            }*/
            i++;
        }
        System.out.println("\nIterations: " + i + ".");
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
