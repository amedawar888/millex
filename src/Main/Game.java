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

public class Game {
	
	
	public ResourceManager Resources = new ResourceManager(50);
	public BuildingManager Buildings = new BuildingManager(Resources);
	
	public Scanner scan = new Scanner(System.in);
	
	/////////////////////////
	
	public int turn = 1;
	
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
	
	public double halfRations =  0.5;
	
	public double doubleRations = 2;
	
	public double foodMultiplier = 1;
	
	public double feastCost = 0.25;
	
	public ArrayList<Person> subs = new ArrayList<Person>();
	
	public Game() {
		cmds.put("stats", new CommandOptions("printStats", false));
		cmds.put("options", new CommandOptions("printOptions", false));
		cmds.put("proclaim", new CommandOptions("startProclamation", false));
		cmds.put("collect", new CommandOptions("startCollect", true));
		cmds.put("build", new CommandOptions("startBuild", true));
		cmds.put("end turn", new CommandOptions("endTurn", false));
		
		System.out.println("Welcome, Gamer, to the city of Millex. \n You will be responsible for maintaining, cultivating, \n and enhancing this city and the lives of it's inhabitants. ");
		initPopulation(startingPopulation);
		scanInput();
	}
	
	private void initPopulation(int numSubs) {
		for(int i = 0; i<numSubs; i++) {
			subs.add(new Person());
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
					System.out.println("Sorry! You're out of commands for this turn!");
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
		turn ++;
		resolveCmds();
		handleSubs();
		
		//check if sub is giving birth -> add persons to subset and set givingbirth to false
		popGrowth();
		eatFood();
		printStats();
		
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
		System.out.println("Next Turn: It is now turn "+turn+".");
	}

	public void handleSubs() {
		int newPeoples = 0;
		for (Person sub : subs) { //for element in array -> ":"
			sub.step();
			if (sub.birth) {
				newPeoples++;
				sub.birth = false;
			}
		}
		for (int i=0; i < newPeoples; i++) {
			subs.add(new Person());
		}
	}
	
		
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
	
	
	public void popGrowth() {
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
	
	public void eatFood() {
		int foodC = 0;
		
		for (int i=0; i<subs.size(); i++) {
			foodC += subs.get(i).foodConsumed;
		}
		
		Resources.adjustResource("food", -(int)(foodC*foodMultiplier));
		foodMultiplier = 1;
	}
	
	public ArrayList<Person> getSubsByGender(boolean gen){
		ArrayList<Person> subList = new ArrayList<Person>();
		
		for (Person sub : subs){
			if (sub.gender==gen) {
				subList.add(sub);
				
			}
		}
		
		return subList;
	}
}