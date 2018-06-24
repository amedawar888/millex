package Misc;

import Resources.ResourceManager;

public class ResourceCommand extends Command {
	
	public ResourceManager resources;
	public String resourceType;
	public int amount;
	
	public ResourceCommand(ResourceManager res, String s, String rtype, int a) {
		super(s);
		resources = res;
		resourceType = rtype;
		amount = a;
	}
	
	public String run() {
		boolean success = resources.adjustResource(resourceType, amount);
		if (success) {			
			return successStr;
		}
		return resourceType + " resource update failed.";
	}

}
