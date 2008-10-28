package flownetwork;

public class Test {
	public static void main(String[] args) {
		try {
			//if(args.length > 0) {
			if(true) {
				//String filename = args[0];
				String filename = "Flownetworks/grid20-2.fln"; 
				Network n = NetworkReader.readFile(filename);
				System.out.println("Network file: " + filename);
				n.printHeaderInfo();
				System.out.println();
				System.out.println("Maximum flow found has value " + n.calculateMaxFlow() + " units.");
			}
			else {
				System.out.println("Usage: Give the input FLN network as argument");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
