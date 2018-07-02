package Buildings;

import java.util.ArrayList;

import Resources.ResourceManager;

public class BuildingManager {
	
	private ResourceManager resources;

	private ArrayList<Building> homes = new ArrayList<Building>();
	private ArrayList<Farm> farms = new ArrayList<Farm>();
	private ArrayList<Lumberyard> lumberyards = new ArrayList<Lumberyard>();
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
		
		for (Farm farm : farms) {
			foodOut += farm.getFoodProduced();
		}
		
		resources.adjustResource("food", foodOut);
		
		return foodOut;
	}
	
	public int chopWood() {
		int lumberOut = 0;
		
		for (Lumberyard lumberyard : lumberyards) {
			lumberOut += lumberyard.getLumberProduced();
		}
		
		resources.adjustResource("lumber", lumberOut);
		
		return lumberOut;
	}
	
}
