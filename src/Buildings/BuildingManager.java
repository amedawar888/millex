package Buildings;

import java.util.ArrayList;

import Resources.ResourceManager;

public class BuildingManager {
	
	private ResourceManager resources;

	private ArrayList<Building> homes = new ArrayList<Building>();
	private ArrayList<Building> farms = new ArrayList<Building>();
	private ArrayList<Building> lumberyards = new ArrayList<Building>();
	private ArrayList<Building> markets = new ArrayList<Building>();
	
	public BuildingManager(ResourceManager res) {
		resources = res;
	}
	
	public int farm() {
		
	}
	
}
