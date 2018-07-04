package Buildings;

public class Building {

	private int buildTime, buildAmount = 0;
	private boolean built = false;
	
	public Building(int time) {
		buildTime = time;
	}

	public boolean isBuilt() {
		return built;
	}

	public int getBuildTime() {
		return buildTime;
	}

	public int getBuildAmount() {
		return buildAmount;
	}
	
	public boolean build() {
		buildAmount++;
		if (buildAmount >= buildTime) {
			built = true;
		}
		return built;
	}
	
}
//input from player
//keeping track of time
//keeping track of buildings, etc