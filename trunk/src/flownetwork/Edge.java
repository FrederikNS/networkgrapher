package flownetwork;

public class Edge {
	private Node node_source;
	private Node node_destination;
	private int capacity;
	private int load;
	
	public Edge(Node node_source, Node node_destination, int capacity) {
		this.node_source = node_source;
		this.node_destination = node_destination;
		this.capacity = capacity;
		this.load = 0;
	}
	
	public Node getSource() {
		return node_source;
	}
	
	public Node getDestionation() {
		return node_destination;
	}
	
	public void increaseLoad(int load) {
		this.load += load;
	}
	
	public int getLoad() {
		return load;
	}
	
	public int getInitialCapacity() {
		return capacity;
	}
	
	public int getCurrentCapacity() {
		return getInitialCapacity() - getLoad();
	}
	
	public boolean isClogged() {
		return getCurrentCapacity() == 0; 
	}
}