package flownetwork;

public class Test {
	public static void main(String[] args) {
		try {
			//Network n = NetworkReader.readFile("c:\\testnetworks\\proj2009.fln");
			Network n = NetworkReader.readFile("FlowNetworks/Random20-10697.fln");
            int c = n.calculateMaxFlow();
            System.out.println(c+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
