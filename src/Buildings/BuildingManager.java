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
	
	private String buildHelper(ArrayList<Building> buildings, String buildType) {
		int buildingsInProgress = 0;
		int buildingsBuilt = 0;
		
		for (Building building : buildings) {
			if (!building.isBuilt()) {
				buildingsInProgress++;
				if (building.build()) {
					buildingsBuilt++;
				}
			}
		}
		
		return "Building progress: " + buildingsBuilt + " " + buildType + "(s) finished construction. " +
			(buildingsInProgress - buildingsBuilt) + " " + buildType + "(s) still in progress.";
	}
	
	public String build() {
		return buildHelper(farms, "farm") + "\n" + buildHelper(lumberyards, "lumberyard");
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
	
	public boolean buildLumberyard() {
		if (resources.getResource("food") < 20 || resources.getResource("lumber") < 45) {
			return false;
		}
		lumberyards.add(new Lumberyard(7));
		resources.adjustResource("food", -20);
		resources.adjustResource("lumber", -45);
		return true;
	}
	
	public int farm() {
		int foodOut = 0;
		
		for (Building farm : farms) {
			if (farm.isBuilt()) {				
				foodOut += ((Farm)farm).getFoodProduced();
			}
		}
		
		resources.adjustResource("food", foodOut);
		
		return foodOut;
	}
	
	public int chopWood() {
		int lumberOut = 0;
		
		for (Building lumberyard : lumberyards) {
			if (lumberyard.isBuilt()) {				
				lumberOut += ((Lumberyard)lumberyard).getLumberProduced();
			}
		}
		
		resources.adjustResource("lumber", lumberOut);
		
		return lumberOut;
	}
	
}
