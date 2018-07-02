package Subs;

import java.util.ArrayList;

import Misc.Tools;

public class Person {

	public int foodConsumed = 1;
	
	public int trade = 0;
	
	//Age is measured in turns (seasons). 0-16 = child; 16-40 = adult; 40+ elder.
	private int age;
	private double deathThreshold = 0.01;
	private boolean dead = false;
	private boolean pregnant = false;
	private int pregnancyStage = -1;
	public boolean gender = Tools.chance(0.5);
	public boolean birth = false;
	
	private String[] allTraits = {"entrepeneurial", "servantile", "imaginitive", "intelligent", "dumb", "selfish", "selfless", "charismatic", "loner", "violent"};
	public ArrayList<String> traits = new ArrayList<String>();
	
	private ArrayList<Person> children = new ArrayList<Person>();
	private ArrayList<Person> parents = new ArrayList<Person>();
	
	
	public Person() {
		age = 0;
	}
	
	public Person(int a) {
		age = a;
		deathThreshold += (Math.random()/1000)*age;
	}

	public boolean isChild() {
		return age <= 16;
	}
	
	public boolean isElder() {
		return age >= 40;
	}
	
	public boolean isAdult() {
		return !isChild() && !isElder();
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void step() {
		age++;
		double badLuck = Math.random();
		deathThreshold += (Math.random()/1000);
		if (badLuck <= deathThreshold) {
			dead = true;
		}
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
		if (!gender && !pregnant && isAdult() && !dead) {
			pregnant = true;
			pregnancyStage = 0;
			return true;
		}
		return false;
	}
}
