package network;

import java.util.HashSet;

public class Node {
	private HashSet<Edge> edges_in;
	private HashSet<Edge> edges_out;
	private int level;
	
	public Node() {
		edges_in = new HashSet<Edge>();
		edges_out = new HashSet<Edge>();
		level = 0;
	}

	public Node(HashSet<Edge> edges_in, HashSet<Edge> edges_out) {
		this.edges_in = edges_in;
		this.edges_out = edges_out;
		level = 0;
	}	
	
	public HashSet<Edge> getIngoingEdges() {
		return edges_in;
	}
	
	public HashSet<Edge> getOutgoingEdges() {
		return edges_out;
	}
	
	public void addIngoingEdge(Edge e) {
		edges_in.add(e);
	}

	public void addOutgoingEdge(Edge e) {
		edges_out.add(e);
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
