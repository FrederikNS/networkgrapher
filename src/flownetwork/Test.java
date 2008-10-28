package flownetwork;

public class Test {
	public static void main(String[] args) {
		try {
			//Network n = NetworkReader.readFile("c:\\testnetworks\\proj2009.fln");
			Network n = NetworkReader.readFile("FlowNetworks/Proj2009.fln");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
