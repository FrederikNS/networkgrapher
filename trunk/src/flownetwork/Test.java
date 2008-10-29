package flownetwork;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			//if(args.length > 0) {
			if(true) {
				//String filename = args[0];
				//String filename = "Flownetworks/gridRand001-42.fln"; 
				//String filename = "Flownetworks/complRand000-237.fln";
				//String filename = "Flownetworks/123-4.fln";
				//String filename = "Flownetworks/par-4.fln";
				//String filename = "Flownetworks/random500-250016.fln";
				//String filename = "Flownetworks/proj2009.fln";
				String filename = "Flownetworks/gridrand004-122.fln";
				Network n = NetworkReader.readFile(filename);
				System.out.println("Network file: " + filename);
				n.printHeaderInfo();
				System.out.println();
				System.out.println("Maximum flow found has value " + n.calculateMaxFlow() + " units.");
			}
			else {
				System.out.println("Usage: Give the input FLN network as argument");
			}
		}
		catch (IOException e) {
			System.out.println("IO Exception.");
			if(e.getMessage()==null)
				e.printStackTrace();
			else
				System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Error!");
			if(e.getMessage()==null)
				e.printStackTrace();
			else
				System.out.println(e.getMessage());
		}
	}
}
