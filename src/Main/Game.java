package Main;

import java.util.ArrayList;
import java.util.Scanner;

import Subs.Person;

public class Game {
	
	
	public Scanner scan = new Scanner(System.in);
	
	/////////////////////////
	
	public int turn = 1;	
	
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
	
	private int cmdLimit = 5;
	
	///////////////////////
	
	public String[] procs = {"halfrations", "doublerations"};
	
	///////////////////////
	
	private int food = 50;
	
	private int lumber = 20;
	
	private int buildPower = 20;
		
	private int finance = 20;
	
	
	//////////////////////
	
	public int startingPopulation = 23;
	
	public double halfRations =  0.5;
	
	public double doubleRations = 2;
	
	public double foodMultiplier = 1;
	
	public ArrayList<Person> subs = new ArrayList<Person>();
	
	public Game() {
		
		System.out.println("Welcome, Gamer, to the city of Millex. \n You will be responsible for maintaining, cultivating, \n and enhancing this city and the lives of it's inhabitants. ");
		initPopulation(startingPopulation);
		scanInput();
	}
	
	private void initPopulation(int numSubs) {
		for(int i = 0; i<numSubs; i++) {
			subs.add(new Person());
		}
	}
	
	public void scanInput() {
		System.out.println("What would you like to do at this point? Type 'options' to view choices.");
		System.out.println("You may also check your stats by typing 'stats'.");
		System.out.println("You may also type 'proclamation' to initiate ration control.");
		String gamerChoice = scan.nextLine().toLowerCase();
		
		
		if (gamerChoice.equals(endTurnCmd)) {
			endTurn();
		}
		else if (gamerChoice.equals(viewCmds)) {
			System.out.println(" farm | lumber | build | finance.");		
		}
		
		else if (gamerChoice.equals(proclamationCmd) || gamerChoice.equals(procCmd)) {
			handleProclamations();
		}
		else if (gamerChoice.equals(statsCmd)) {
			printStats();	
		}
		else if (cmdLimit>cmdList.size()) {
			if (gamerChoice.equals(farmCmd)) {
				cmdList.add(0);	
			}
			if (gamerChoice.equals(lumberCmd)) {
				cmdList.add(1);	
			}
			if (gamerChoice.equals(buildCmd)) {
				cmdList.add(2);	
			}
			if (gamerChoice.equals(financeCmd)) {
				cmdList.add(3);	
			}
	
		}
		
		else {
			System.out.println("Sorry! You're out of commands for this turn!");
		}
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
		
		System.out.println(food + " food | " +
				lumber + " lumber | " +
				buildPower + " build power | " +
				finance + " gold\n" +
				"You have " + males + " males, and " + females + " females in your tribe.\n" +
				newPreg + " females are newly pregnant. " + midPreg + " females are mid-pregnancy. " + endPreg + " females are about to give birth."); 
	}
	
	public void endTurn() {
		turn ++;
		resolveCmds();
		
		eatFood();
		printStats();
		
		if (food <=0) {
			food=0;
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
		food+=15;
		System.out.println("You've successfully farmed! You have " + food + " food.");
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
		food-=3;
		System.out.println("You've successfully financed! You have " + finance + " gold.");
	}
	
	
	public void eatFood() {
		int foodC = 0;
		
		for (int i=0; i<subs.size(); i++) {
			foodC += subs.get(i).foodConsumed;
		}
		
		food -= foodC*foodMultiplier;
		foodMultiplier = 1;
	}

}
//City of Millex		
//turn based sim
//ran by subordinates
//users can type in commands for the townspeople to do
//actions -> gather food / farm / town meetings / build 
//supplies of food , homes