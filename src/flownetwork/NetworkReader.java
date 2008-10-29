package flownetwork;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TreeSet;

/* LIST OF ERRORS TAKEN INTO ACCOUNT:
 * +Source = Sink
 * +Source/Sink/Edge node out of range
 * +Too few/many edges (error/warning)
 * +Negative values
 * +Multiple edges between two nodes
 * +Nodes without edges (warning)
 * +Misformatted input
 * +File not found/IO error
 * +Edge from a node to itself 
 * +Edge from sink (warning)
 */

public class NetworkReader {
	public static Network readFile(String filename) throws IOException, FileNotFoundException, Exception {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		StringTokenizer stok = new StringTokenizer(line, "; \n");
		// read the general information

		int num_nodes = Integer.parseInt(stok.nextToken().trim());
		int num_edges = Integer.parseInt(stok.nextToken().trim());
		int source_index = Integer.parseInt(stok.nextToken().trim());
		int sink_index = Integer.parseInt(stok.nextToken().trim());
		
		if(num_nodes <= 1) error("Too few nodes/negative amount to make a network: "+num_nodes);
		if(num_edges <= 1) error("Too few edges/negative amount to make a network: "+num_edges);
		if(source_index < 0 || source_index >= num_nodes) error("Source node out of bound: "+source_index);
		if(sink_index < 0 || sink_index >= num_nodes) error("Sink node out of bound: "+sink_index);
		if(source_index == sink_index) error("The source is the same as the sink. The capacity is infinite.");

		Node nodes[] = new Node[num_nodes];
		for(int i = 0; i < num_nodes; i++) //necesarry? Probably
			nodes[i] = new Node(i);

		int node1, node2, capacity;
		// read the edges
		for (int i = 0; i < num_edges; i++) {
			line = br.readLine();
			if(line==null) error("Too few edges in the file. Terminated at " + (i+1) + ", expected " + num_edges + " edges.");		
			stok = new StringTokenizer(line, "; \n");
			try {
				node1 = Integer.parseInt(stok.nextToken().trim());
				node2 = Integer.parseInt(stok.nextToken().trim());
				capacity = Integer.parseInt(stok.nextToken().trim());
			}
			catch (NumberFormatException e) {
				error("Following line is not properly formatted in order to define an edge (should be source;target;capacity): " + line);
				throw new Exception();
			}
			catch (NoSuchElementException e) {
				error("Misformatted input: " + line);
				throw new Exception();
			}
			
			if(node1 < 0 || node2 < 0 || capacity < 0) error("Negative values in edge: " + line);
			if(node1 == node2) error("Edge from a node to itself: " + line);
			if(node1 == sink_index) warning("Edge from sink: " + line);
			if(node1 >= num_nodes || node2 >= num_nodes) error("Node value out of bound in edge: " + line);
			if(nodes[node1].containsEdgeTo(nodes[node2])) error("The edge "+line+" defines a secondary edge between two nodes. There can be only one.");
			nodes[node1].addOutgoingEdge(nodes[node2], capacity);
			nodes[node2].addIngoingEdge(nodes[node1], capacity);
		}
		if(stok.hasMoreTokens()) warning("There could be more edges in the file than the header specifies.");
		TreeSet<Integer> lone_nodes = new TreeSet<Integer>();
		for(int i = 0; i < num_nodes; i++)
			if(nodes[i].numEdges() == 0) 
				lone_nodes.add(i);
		if(lone_nodes.size() > 0) {
			String warn = "Following nodes have no outbound or inbound edges: "; 
			for(int i : lone_nodes) {
				warn += i + ", ";
			}
			warning(warn);
		}
		br.close();
		return new Network(Arrays.asList(nodes), nodes[source_index], nodes[sink_index]);
	}
	
	public static void error(String s) throws Exception {
		throw new Exception(s);
	}
	
	public static void warning(String s) throws Exception {
		System.out.println("Warning: "+s);
	}
}

