package Resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

	private int food = 0;
	private int lumber = 0;
	private int trade = 0;
	
	private Map<String, Integer> resources = new HashMap<>();
	
	public ResourceManager(int f) {
		resources.put("food", f);
	}
	
	public int getFood() {
		return food;
	}
	
	public int getLumber() {
		return lumber;
	}
	
	public int getTrade() {
		return trade;
	}
	
	public void addFood(int f) {
		food += f;
	}
	
	public void removeFood(int f) {
		food = Math.max(food-f, 0);
	}
	
	public int getResource(String key) {
		try {
			return resources.get(key);
		} catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public boolean adjustResource(String key, int amount) {
		try {
			int res = resources.get(key);
			resources.put(key, res + amount);
			return true;
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
}
