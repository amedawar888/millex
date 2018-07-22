//City of Millex		
//turn based sim
//ran by subordinates
//users can type in commands for the townspeople to do
//actions -> gather food / farm / town meetings / build 
//supplies of food , homes

package Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Buildings.BuildingManager;
import Misc.CommandOptions;
import Resources.ResourceManager;
import Subs.Person;

public class Game implements Runnable {
	
	
	public ResourceManager Resources = new ResourceManager(new int[]{50,20,100});
	public BuildingManager Buildings = new BuildingManager(Resources);
	
	public Scanner scan = new Scanner(System.in);
	
	/////////////////////////
	
	public int turn = 0, year = 1;
	
	public String[] seasons = {"spring", "summer", "fall", "winter"};
	
	public Map<String, CommandOptions> cmds = new HashMap<>();
	
	private int numCmds = 0;
	
	public String endTurnCmd = "end turn";
	
	public String viewCmds = "options";
	
	public String farmCmd = "farm";
	
	public String lumberCmd = "lumber";
	
	public String buildCmd = "build";
	
	public String financeCmd = "finance";
	
	public String statsCmd = "stats";
	
	public String procCmd = "proc";
	public String proclamationCmd = "proclamation";
	
	private ArrayList<Integer> cmdList = new ArrayList<Integer>();
	
	private final int CMDLIMIT = 5;
	
	///////////////////////
	
	public String[] procs = {"halfrations", "doublerations", "feast"};
	
	///////////////////////
	
	private int lumber = 20;
	
	private int buildPower = 20;
		
	private int finance = 20;
	
	
	//////////////////////
	
	public int startingPopulation = 23;
	
	private int deathCount = 0;
	
	public double halfRations =  0.5;
	
	public double doubleRations = 2;
	
	public double foodMultiplier = 1;
	
	public double feastCost = 0.25;
	
	public ArrayList<Person> subs = new ArrayList<Person>();
	
	public Game() {
		cmds.put("stats", new CommandOptions("printStats", false));
		cmds.put("options", new CommandOptions("printOptions", false));
//		cmds.put("proclaim", new CommandOptions("startProclamation", false));
		cmds.put("collect", new CommandOptions("startCollect", true));
		cmds.put("build", new CommandOptions("startBuild", true));
		cmds.put("end turn", new CommandOptions("endTurn", false));
	}
	
	@Override
	public void run() {
		System.out.println("Welcome, Gamer, to the city of Millex. \n You will be responsible for maintaining, cultivating, \n and enhancing this city and the lives of it's inhabitants. ");
		initPopulation(startingPopulation);
		scanInput();
	}
	
	private void initPopulation(int numSubs) {
		for(int i = 0; i<numSubs; i++) {
			Person person = new Person(16 + (int)Math.ceil(Math.random()*8));
			subs.add(person);
		}
	}
	
	private void callMethodFromCommand(String methodStr) {
		try {
			Method method = Game.class.getDeclaredMethod(methodStr);
			method.setAccessible(true);
			try {
				method.invoke(this);
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void scanInput() {
		System.out.println("What would you like to do at this point? Type 'options' to view choices.");
		System.out.println("You may also check your stats by typing 'stats'.");
		System.out.println("You may also type 'proclamation' to initiate ration control.");
		String gamerChoice = scan.nextLine().toLowerCase();
		
		CommandOptions cmd = cmds.get(gamerChoice);
		if (cmd != null) {
			if (cmd.requiresCommand) {
				if (numCmds >= CMDLIMIT) {
					System.out.println("Unfortunately sir, you're out of commands for this turn.");
				}
				else {
					callMethodFromCommand(cmd.method);
				}
			}
			else {
				callMethodFromCommand(cmd.method);
			}
		}
		else {
			System.out.println("Sorry, I don't recognize that command. Try 'options' to see your available options.");
		}
		
		System.out.println();
		scanInput();
	}
	
	private void startCollect() {
		System.out.println("What would you like your subjects to collect? Enter 'cancel' to exit without issuing a command.");
		String input = scan.nextLine().toLowerCase();
		boolean reset = false,
				issueCmd = false;
		
		if (input.equals("options")) {
			System.out.println("Available options: scavenge | hunt | chop wood");
			reset = true;
		}
		else if (input.equals("cancel")) {
			System.out.println("Cancelling collect command.");
		}
		else {
			if (input.equals("scavenge")) {
				scavenge();
				issueCmd = true;
			}
			else if (input.equals("hunt")) {
				hunt();
				issueCmd = true;
			}
			else if (input.equals("chop wood")) {
				chopWood();
				issueCmd = true;
			}
			else {
				System.out.println("I don't recognize that command. Try 'options' to view available commands");
				reset = true;
			}
		}
		
		if (issueCmd) {
			numCmds++;
			if (numCmds>=CMDLIMIT) {
				System.out.println("You're now out of commands.");
			}
			else {				
				System.out.println("Would you like to issue another collection command?");
				boolean answer = yesOrNoInput();
				if (answer) {
					reset = true;
				}
			}
		}
		
		if (reset) {
			startCollect();
		}
	}
	
	private void startBuild() {
		System.out.println("What would you like your subjects to build? Enter 'cancel' to exit without issuing a command.");
		String input = scan.nextLine().toLowerCase();
		boolean reset = false,
				issueCmd = false;
		
		if (input.equals("options")) {
			System.out.println("Available options: farm | lumberyard");
			reset = true;
		}
		else if (input.equals("cancel")) {
			System.out.println("Cancelling build command.");
		}
		else {
			if (input.equals("farm")) {
				System.out.println("Are you sure you want to build a farm? It will cost 20 lumber and 40 food to build.");
				boolean issue = yesOrNoInput();
				if (issue) {
					boolean built = buildFarm();
					if (built) {
						issueCmd = true;
						System.out.println("A new farm is now under construction.");
					}
					else {
						System.out.println("Sir, you do not have enough resources to build a farm.");
					}
				}
				else {
					System.out.println("Canceling farm build.");
					reset = true;
				}
			}
			else if (input.equals("lumberyard")) {
				System.out.println("Are you sure you want to build a lumberyard? It will cost 45 lumber and 20 food to build.");
				boolean issue = yesOrNoInput();
				if (issue) {
					boolean built = buildLumberyard();
					if (built) {						
						issueCmd = true;
						System.out.println("A new lumberyard is now under construction.");
					}
					else {
						System.out.println("Sir, you do not have enough resources to build a lumberyard.");
					}
				}
				else {
					System.out.println("Canceling lumberyard build.");
					reset = true;
				}
			}
			else {
				System.out.println("I don't recognize that command. Try 'options' to view available commands");
				reset = true;
			}
		}
		
		if (issueCmd) {
			numCmds++;
			if (numCmds>=CMDLIMIT) {
				System.out.println("You're now out of commands.");
			}
			else {				
				System.out.println("Would you like to issue another build command?");
				boolean answer = yesOrNoInput();
				if (answer) {
					reset = true;
				}
			}
		}
		
		if (reset) {
			startBuild();
		}
	}
	
	private void confirmBuild() {
		
	}
	
	private void handleProclamations() {
		System.out.println("Type the proclamation you want to issue.");
		printProcs();
		
		String gamerChoiceProc = scan.nextLine().toLowerCase();
		
		if (gamerChoiceProc.equals(procs[0])) {
			foodMultiplier = 0.5;
			System.out.println("You have successfully halved your rations! Would you like to declare another proclamation?\n Yes | no.");
			boolean yesOrNoResp = yesOrNoInput();
			if (yesOrNoResp) {
				handleProclamations();
			}
		}
		else if (gamerChoiceProc.equals(procs[1])) {
			foodMultiplier = 2.0;
			System.out.println("You have successfully doubled your rations! Would you like to declare another proclamation?\n Yes | no.");
			boolean yesOrNoResp = yesOrNoInput();
			if (yesOrNoResp) {
				handleProclamations();
			}
		}
		else if (gamerChoiceProc.equals(procs[2])) {
			feastCost = 0.25;
			System.out.println("You have successfully chosen to feast! Would you like to declare another proclamation?\n Yes | no.");
			boolean yesOrNoResp = yesOrNoInput();
			if (yesOrNoResp) {
				handleProclamations();
			}
		}
		else if (gamerChoiceProc.equals("cancel")) {
			System.out.println("No proclamations issued.");
			return;
		}
		else {
			System.out.println("I'm sorry I couldn't recognize that. Try again.");
			handleProclamations();
		}
		
	}
	
	private boolean yesOrNoInput() {
		String userInput = scan.nextLine().toLowerCase();
		
		if (userInput.equals("yes")) {
			return true;
		}
		else if (userInput.equals("no")) {
			return false;
		}
		else {
			System.out.println("Input not recognized. Please input yes or no.");
			return yesOrNoInput();
		}
	}
	
	private void printProcs() {
		String outString = "";
		for (int i=0; i<procs.length; i++) {
			outString += procs[i];
			if (i<procs.length-1) {
				outString += " | ";
			}
		}
		System.out.println(outString);
		
	}
	
	private void printOptions() {
		String outStr = "Available commands: ";
		for (String key : cmds.keySet()) {
			outStr += key + " | ";
		}
		System.out.println(outStr);
	}
	
	private void printStats() {
		int males = 0;
		int females = 0;
		int newPreg = 0, midPreg = 0, endPreg = 0;
		
		for (int i=0; i<subs.size(); i++) {
			Person sub = subs.get(i);
			if (sub.gender) {
				males++;
			}
			else {
				females++;
				int pregStage = sub.getPregnancyStage();
				if (pregStage == 0) {
					newPreg++;
				}
				else if (pregStage == 1) {
					midPreg++;
				}
				else if (pregStage == 2) {
					endPreg++;
				}
			}
		}
		
		System.out.println(Resources.getResource("food") + " food | " +
				lumber + " lumber | " +
				buildPower + " build power | " +
				finance + " gold\n" +
				"You have " + males + " males, and " + females + " females in your tribe.\n" +
				newPreg + " females are newly pregnant. " + midPreg + " females are mid-pregnancy. " + endPreg + " females are about to give birth."); 
	}
	
	public void endTurn() {
		advanceTime();
		resolveCmds();
		handleSubs();
		
		handleSubs();
		//check if sub is giving birth -> add persons to subset and set givingbirth to false
		popGrowth();
		
		handleBuildings();
		
		eatFood();
		printStats();
		
		numCmds = 0;
		
		if (Resources.getResource("food") <=0) {
			Resources.adjustResource("food", 0);
			System.out.println("you're out of fucking food, breh.");
		}
		if (lumber <=0) {
			lumber=0;
			System.out.println("you're out of fucking lumber, broski.");
		}
		if (buildPower <=0) {
			buildPower=0;
			System.out.println("you're out of fucking building power, brotha.");
		}
		if (finance <=0) {
			finance=0;
			System.out.println("you're out of fucking moniez, nigga.");
		}
		cmdList.clear();
		
		if (deathCount > 0) {
			System.out.println("Aweeee fuck, you've lost " + deathCount + " niggas");
			deathCount = 0;
		}
		
		System.out.println();
	}

	public void advanceTime() {
		turn ++;
		if (turn >= 4) {
			year++;
			turn = 0;
		}
		System.out.println(seasons[turn] + " - Year " + year);
	}
	
	/*
	 * Collect commands
	 */
	private void scavenge() {
		int food = 5 + (int)(Math.ceil(Math.random()*5));
		Resources.adjustResource("food", food);
		System.out.println("You've successfully scavenged " + food + " food! You have " + Resources.getResource("food") + " food.");
	}
	
	private void hunt() {
		int food = (int)Math.ceil((Math.random()*20));
		Resources.adjustResource("food", food);
		System.out.println("You've successfully hunted " + food + " food! You have " + Resources.getResource("food") + " food.");
	}
	
	private void chopWood() {
		int lumber = 10 + (int)(Math.ceil(Math.random()*15));
		Resources.adjustResource("lumber", lumber);
		System.out.println("You've successfully collected " + lumber + " lumber! You have " + Resources.getResource("lumber") + " lumber.");
	}
	
	/*
	 * Build commands
	 */
	
	private boolean buildFarm() {
		System.out.println();
		System.out.println("Building farm...");
		return Buildings.buildFarm();
	}
	
	private boolean buildLumberyard() {
		System.out.println();
		System.out.println("Building lumberyard...");
		return Buildings.buildLumberyard();
	}
	
	/*
	 * Building Handling
	 */
	private void handleBuildings() {
		int farmedFood = Buildings.farm();
		int choppedLumber = Buildings.chopWood();
		if (farmedFood > 0) {			
			System.out.println("You farmed " + farmedFood + " food.");
		}
		if (choppedLumber > 0) {
			System.out.println("You chopped " + choppedLumber + " lumber with lumberyards.");
		}
		
		System.out.println(Buildings.build());
	}
	
	// End Building Handling
	
	/*
	 * Population Handling
	 */
	private void handleSubs() {
		int newPeoples = 0;
		ArrayList<Person> deadPeople = new ArrayList<Person>();
		for (Person sub : subs) { //for element in array -> ":"
			sub.step();
			if (sub.birth) {
				newPeoples++;
				sub.birth = false;
			}
			if (sub.isDead()) {
				deadPeople.add(sub);
				deathCount++;
			}
		}
		subs.removeAll(deadPeople);
		
		for (int i=0; i < newPeoples; i++) {
			subs.add(new Person());
		}
	}
	
	private void popGrowth() {
		int numPregs = 0;
		int numNewPregs = 0;
		double foodPopRat = Math.min((double) Resources.getResource("food") / (double) subs.size(), 2); //cap is var -> feast affects it
		if (foodPopRat >= 0.5) {
			numPregs = (int)Math.ceil((0.25+(Math.random()*0.5))*(double)subs.size()*(foodPopRat/10));
			ArrayList<Person> females = getSubsByGender(false);
			for (Person sub : females) {
				if (numNewPregs >= numPregs) {
					break;
				}
				if (sub.makePregnant()) {
					numNewPregs++;
				}
				
			}
			
		}
	}
	
	private void eatFood() {
		int foodC = 0;
		
		for (int i=0; i<subs.size(); i++) {
			foodC += subs.get(i).foodConsumed;
		}
		
		Resources.adjustResource("food", -(int)(foodC*foodMultiplier));
		foodMultiplier = 1;
		
		double foodRatio = Math.min(1, (double)Resources.getResource("food") / foodC);
		for (Person sub : subs) {
			sub.eatFood(foodRatio*sub.foodConsumed);
		}
	}
	
	private ArrayList<Person> getSubsByGender(boolean gen){
		ArrayList<Person> subList = new ArrayList<Person>();
		
		for (Person sub : subs){
			if (sub.gender==gen) {
				subList.add(sub);
				
			}
		}
		
		return subList;
	}
	
	// End Population Handling
	
	/*
	 * Legacy Stuff
	 */
	public void resolveCmds() {
		for (int i=0; i<cmdList.size(); i++) {
			if (cmdList.get(i)==0) {
				farm();
			}
			else if (cmdList.get(i)==1) {
				lumber();
			}
			else if (cmdList.get(i)==2) {
				build();
			}
			else if (cmdList.get(i)==3) {
				finance();
			}
		}
	}
		
	public void farm() {
		Resources.adjustResource("food", 15);
		System.out.println("You've successfully farmed! You have " + Resources.getResource("food") + " food.");
	}
	public void lumber() {
		lumber+=5;
		System.out.println("You've successfully acquired lumber! You have " + lumber + " lumber.");
	}
	public void build() {
		buildPower+=15;
		finance-=2;
		lumber-=2;
		System.out.println("You can build! You have " + buildPower + " buid power. What would like to build?");
	}
	public void finance() {
		finance+=10;
		Resources.adjustResource("food", -3);
		System.out.println("You've successfully financed! You have " + finance + " gold.");
	}
	
	// End Legacy Stuff
}