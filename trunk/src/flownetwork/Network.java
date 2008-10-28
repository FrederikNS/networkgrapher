package flownetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
	
	public void levelNodes() {
		for(Node n : nodes) {
			n.setLevel(Integer.MAX_VALUE);
		}
		
		LinkedList<Node> queue = new LinkedList<Node>();
		source.setLevel(0);
		queue.add(getSource());
		
		while(!queue.isEmpty()) {
			Node n = queue.poll();
			if(n.getLevel() < sink.getLevel()) {
				int level = n.getLevel() + 1;
				for(Node d : n.getOutgoingEdges().keySet()) {
					if(d.getLevel() > level) {
						d.setLevel(level);
						queue.add(d);
					}
				}
			}
		}
		//System.out.println(""+sink.getLevel());
		for(Node n : nodes) System.out.println(""+n.getLevel());
		System.out.println("Size: "+nodes.size());
	}
}
