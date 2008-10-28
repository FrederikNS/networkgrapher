package flownetwork;

public class Test {
	public static void main(String[] args) {
		try {
			Network n = NetworkReader.readFile("c:\\testnetworks\\proj2009.fln");
			n.levelNodes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
