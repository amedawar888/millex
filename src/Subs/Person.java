package Subs;

import Misc.Tools;

public class Person {

	public int foodConsumed = 1;
	
	private int age;
	private boolean pregnant = false;
	private int pregnancyStage = -1;
	public boolean gender = Tools.chance(0.5);
	public boolean birth = false;
	
	public Person() {
		
	}
	
	public Person(int f) {
		foodConsumed = f;
	}
	
	public void step() {
		if (pregnant) {
			if (pregnancyStage < 2) {
				pregnancyStage++;
			}
			else if (pregnancyStage >= 2) {
				pregnancyStage = -1;
				pregnant = false;
				birth = true;
			
			}
		}
	}
	
	public int getPregnancyStage() {
		return pregnancyStage;
	}
	
	public boolean makePregnant() {
		if (!gender && !pregnant) {
			pregnant = true;
			pregnancyStage = 0;
			return true;
		}
		return false;
	}
}
