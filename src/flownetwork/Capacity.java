package flownetwork;

public class Capacity {
	int capacity, load;
	
	public Capacity(int c) {
		capacity = c;
		load = 0;
	}
	
	int getInitialCapacity() {
		return capacity;
	}
	
	int getRemainingCapacity() {
		return getInitialCapacity() - getLoad();
	}
	
	int getLoad() {
		return load;
	}

	public void addLoad(int l) {
		load += l;
	}
}
