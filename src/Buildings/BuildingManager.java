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
	
	public int farm() {
		int foodOut = 0;
		
		for (Farm farm : farms) {
			foodOut += farm.getFoodProduced();
		}
		
		resources.addFood(foodOut);
		
		return foodOut;
	}
	
}
