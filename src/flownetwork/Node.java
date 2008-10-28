package flownetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Node {
	private HashMap<Node, Capacity> edges_in;
	private HashMap<Node, Capacity> edges_out;
	private HashSet<Node> ln_out_edges;
	private int level;
	
	public Node() {
		edges_in = new HashMap<Node, Capacity>();
		edges_out = new HashMap<Node, Capacity>();
		ln_out_edges = new HashSet<Node>();
		level = 0;
	}
	
	public Collection<Node> getIngoingEdges() {
		return edges_in.keySet();
	}
	
	public Collection<Node> getOutgoingEdges() {
		return edges_out.keySet();
	}
	
	public Collection<Node> getEdges() {
		Collection<Node> edges = new HashSet<Node>();
		edges.addAll(edges_in.keySet());
		edges.addAll(edges_out.keySet());
		return edges;
	}
	
	public int getCapacityOfEdgeTo(Node n) {
		if(edges_out.containsKey(n)) {
			return edges_out.get(n).getRemainingCapacity();
		}
		if(edges_in.containsKey(n)) {
			return edges_in.get(n).getLoad();
		}
		return 0;
	}
	
	public void addIngoingEdge(Node n, int capacity) {
		edges_in.put(n, new Capacity(capacity));
	}

	public void addOutgoingEdge(Node n, int capacity) {
		edges_out.put(n, new Capacity(capacity));
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isEdgePartOfLeveledNetwork(Node n) {
		if(getCapacityOfEdgeTo(n) > 0) {
			if(getLevel() == n.getLevel()+1) return true;
		}
		return false;
	}
	
	public void resetLNEdges() {
		ln_out_edges = new HashSet<Node>();
	}
	
	public void addLNEdge(Node n) {
		ln_out_edges.add(n);
	}
        
        public void removeLNEdgeTo(Node e) {
            ln_out_edges.remove(e);
        }
	
	public HashSet<Node> getLNEdges() {
		return ln_out_edges;
	}

	public void addLoadToEdgeTo(Node e, int l) {
		if(edges_in.containsKey(e))
			edges_in.get(e).addLoad(-l);
		if(edges_out.containsKey(e))
			edges_out.get(e).addLoad(l);
	}
}
