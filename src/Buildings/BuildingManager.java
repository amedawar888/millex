package Buildings;

import java.util.ArrayList;

import Resources.ResourceManager;

public class BuildingManager {
	
	private ResourceManager resources;

	private ArrayList<Building> homes = new ArrayList<Building>();
	private ArrayList<Farm> farms = new ArrayList<Farm>();
	private ArrayList<Building> lumberyards = new ArrayList<Building>();
	private ArrayList<Building> markets = new ArrayList<Building>();
	
	public BuildingManager(ResourceManager res) {
		resources = res;
	}
	
	public boolean buildFarm() {
		if (resources.getResource("food") < 40 || resources.getResource("lumber") < 20) {
			return false;
		}
		farms.add(new Farm(5));
		resources.adjustResource("food", -40);
		resources.adjustResource("lumber", -20);
		return true;
	}
	
	public int farm() {
		int foodOut = 0;
		
		for (Farm farm : farms) {
			foodOut += farm.getFoodProduced();
		}
		
		resources.adjustResource("food", foodOut);
		
		return foodOut;
	}
	
}
