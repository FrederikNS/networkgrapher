package flownetwork;

import java.util.HashMap;

public class Node {
	private HashMap<Node, Integer> edges_in;
	private HashMap<Node, Integer> edges_out;
	private int level;
	
	public Node() {
		edges_in = new HashMap<Node, Integer>();
		edges_out = new HashMap<Node, Integer>();
		level = 0;
	}
	
	public HashMap<Node, Integer> getIngoingEdges() {
		return edges_in;
	}
	
	public HashMap<Node, Integer> getOutgoingEdges() {
		return edges_out;
	}
	
	public void addIngoingEdge(Node n, int capacity) {
		edges_in.put(n, capacity);
	}

	public void addOutgoingEdge(Node n, int capacity) {
		edges_out.put(n, capacity);
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
