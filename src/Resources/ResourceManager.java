package Resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	
	private Map<String, Integer> resources = new HashMap<>();
	
	public ResourceManager(int[] resources) {
		this.resources.put("food", resources[0]);
		this.resources.put("lumber", resources[1]);
		this.resources.put("gold", resources[2]);
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
			resources.put(key, Math.max(res + amount, 0));
			return true;
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
}
