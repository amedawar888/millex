package Subs;

import Misc.Tools;

public class Person {

	public int foodConsumed = 1;
	
	private int age;
	private boolean pregnant = false;
	private int pregnancyStage = -1;
	public boolean gender = Tools.chance(0.5);
	
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
			}
		}
	}
	
	public int getPregnancyStage() {
		return pregnancyStage;
	}
	
	public void makePregnant() {
		if (!gender && !pregnant) {
			pregnant = true;
			pregnancyStage = 0;
		}
	}
}
