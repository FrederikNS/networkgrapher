package flownetwork;

public class Test {
	public static void main(String[] args) {
		try {
			if(args.length > 0) {
				Network n = NetworkReader.readFile(args[0]);
				System.out.println("Max flow: " + n.calculateMaxFlow());
			}
			else {
				System.out.println("Usage: Give the input FLN network as argument");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
