package Misc;

import Resources.ResourceManager;
import Buildings.BuildingManager;

public class Command {
	public String successStr;
	
	public Command(String s) {
		successStr = s;
	}
	
	public String run() {
		return successStr;
	}
}
