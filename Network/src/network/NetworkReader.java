package network;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class NetworkReader {
	public static Network readFile(String filename) throws IOException, FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		StringTokenizer stok = new StringTokenizer(line, "; \n");
		// read the general information

		int num_nodes = Integer.parseInt(stok.nextToken().trim());
		int num_edges = Integer.parseInt(stok.nextToken().trim());
		int source_index = Integer.parseInt(stok.nextToken().trim()) ;
		int sink_index = Integer.parseInt(stok.nextToken().trim()) ;

		Node nodes[] = new Node[num_nodes];
		for(int i = 0; i < num_nodes; i++) //necesarry? Probably
			nodes[i] = new Node();

		HashSet<Edge> edges = new HashSet<Edge>();

		int node1, node2, capacity;
		// read the edges
		for (int i = 0; i < num_edges; i++) {
			line = br.readLine();
			stok = new StringTokenizer(line, "; \n");
			node1 = Integer.parseInt(stok.nextToken().trim());
			node2 = Integer.parseInt(stok.nextToken().trim());
			capacity = Integer.parseInt(stok.nextToken().trim());
 
			Edge e = new Edge(nodes[node1], nodes[node2], capacity);

			edges.add(e);
			nodes[node1].addOutgoingEdge(e);
			nodes[node2].addIngoingEdge(e);
		}
		br.close();
		return new Network(Arrays.asList(nodes), edges, nodes[source_index], nodes[sink_index]);
	}
}

