package Resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	
	private Map<String, Integer> resources = new HashMap<>();
	
	public ResourceManager(int f) {
		resources.put("food", f);
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
