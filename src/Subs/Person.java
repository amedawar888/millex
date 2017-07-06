package Subs;

import java.util.ArrayList;

import Misc.Tools;

public class Person {

	public int foodConsumed = 1;
	
	public int trade = 0;
	
	//Age is measured in turns (seasons). 0-16 = child; 16-40 = adult; 40+ elder.
	private int age;
	private boolean pregnant = false;
	private int pregnancyStage = -1;
	public boolean gender = Tools.chance(0.5);
	public boolean birth = false;
	
	private String[] allTraits = {"entrepeneurial", "servantile", "imaginitive", "intelligent", "dumb", "selfish", "selfless", "charismatic", "loner", "violent"};
	public ArrayList<String> traits = new ArrayList<String>();
	
	private ArrayList<Person> children = new ArrayList<Person>();
	private ArrayList<Person> parents = new ArrayList<Person>();
	
	
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
