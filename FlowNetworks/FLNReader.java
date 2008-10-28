package flows;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class FLNReader {
	
	
	  public static void readNCF(String filename) {
		    File inFile = new File(filename);
		    if (!inFile.exists()) {
		      System.out.println("File not found");
		    }
		    try {
		      BufferedReader br = new BufferedReader(new FileReader(filename));
		      String line = br.readLine();
		      // read the general information
		      StringTokenizer stok = new StringTokenizer(line, "; \n");
		      int noOfNodes = Integer.parseInt(stok.nextToken().trim());
		      int noOfEdges = Integer.parseInt(stok.nextToken().trim());
		      int source = Integer.parseInt(stok.nextToken().trim()) ;
		      int sink = Integer.parseInt(stok.nextToken().trim()) ;
		      System.out.println("n="+noOfNodes+" m="+noOfEdges+" s="+source+" t="+sink);

		        int node1, node2, cap;
		         // read the edges
		        for (int i = 0; i < noOfEdges; i++) {
		          line = br.readLine();
		          stok = new StringTokenizer(line, "; \n");
		          node1 = Integer.parseInt(stok.nextToken().trim());
		          node2 = Integer.parseInt(stok.nextToken().trim());
		          cap = Integer.parseInt(stok.nextToken().trim());
		          System.out.println("edge {"+node1+";"+node2+") has capacity "+cap);

		        }
		        br.close();

		    }
		    catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
		  }

}
