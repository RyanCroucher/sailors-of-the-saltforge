package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import main.Constants.ShipModel;

/**
 * The GameEnvironment class implements most of the logic flow of the game
 * and maintains the current state of the game.
 * 
 * @author Ryan Croucher rcr69
 * @author Steven Johnson sjo139
 *
 */
public class GameEnvironment {
	
	//String curScreen;
	//ArrayList<Item>;
	
	/**
	 * An array of all of the routes between island pairs in the game.
	 */
	private static Route[] routes;
	
	private static Island[] islands;
	
	private static Island curIsland;
	
	/**
	 * The scanner is created once when the game begins, and handles all console input.
	 */
	private static Scanner scanner;
	
	/**
	 * Measures hours since the game started. (24 hours in one day).
	 */
	private static int hoursSinceStart = 0;
	
	/**
	 * Set during character creation, game ends when this day is reached.
	 */
	private static int gameDuration;
	
	/**
	 * When GUIMode is turned on, the console no longer expects input.
	 */
	private static Boolean GUIMode = false;
	
	/**
	 * Prints given string to the console.
	 * @param s The input string to be printed.
	 */
	public static void logToConsole(String s) {
		System.out.println(s);
	}
	
	/**
	 * Prints a prompt to the console to indicate that the user should enter something.
	 */
	public static void logToConsolePrompt() {
		System.out.print(">>> ");
	}
	
	/**
	 * Displays given string inside a large, visually commanding 'banner'.
	 * @param s The given string to go inside the banner.
	 * @return The banner string.
	 */
	public static String getBanner(String s) {
		
		int n = s.length();
		int banner_length = 50;
		
		String banner = "";
		
		for (int i = 0; i < banner_length; i++) {
			banner += "*";
		}
		
		banner += "\n";
		
		String midline = "";
		
		for (int i = 0; i < (banner_length - n - 2) / 2; i++) {
			midline += "*";
		}
		
		midline += " " + s + " ";
		int difference = banner_length - midline.length();
		
		for (int i = 0; i < difference; i++) {
			midline += "*";
		}
		
		banner += midline + "\n";
		
		for (int i = 0; i < banner_length; i++) {
			banner += "*";
		}
		
		return banner;
		
	}
	
	/**
	 * Validates the given name.
	 * Validates the length, composition and use of spaces in the given name.
	 * @param name The potential name to validate.
	 * @return Whether the given name is valid or not.
	 */
	public static Boolean isValidName(String name) {
		Boolean valid = true;
		
		//remove leading and trailing whitespace
		name = name.strip();
		
		if (name.length() < 3 || name.length() > 15) {
			valid = false;
			logToConsole("Name invalid: choose a name between 3 and 15 characters.");
		}
		
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
				valid = false;
				logToConsole("Name invalid: please use only english characters.");
				break;
			}
		}
		
		for (int i = 1; i < name.length() - 1; i++) {
			if (name.charAt(i) == ' ' && (name.charAt(i-1) == ' ' || name.charAt(i + 1) == ' ')) {
				valid = false;
				logToConsole("Name invalid: please use only single spaces between words.");
				break;
			}
		}
		
		return valid;
	}
	
	/**
	 * Prompts the player for a character name. Loops until a valid name is given.
	 * @return The validated name.
	 */
	private static String consoleChooseCharacterName() {
		
		
		Boolean validName = false;
		String name = "";
		
		do {
			
			name = consoleGetInput("What is your name, proud dwarf merchant?", false);
			
			validName = isValidName(name);
			
			
		} while (!validName);

		return name;
		
	}
	
	/**
	 * Walks the player through the character creation process.
	 * Prompts the player to choose a name, and then greets that player by character name.
	 */
	private static void consoleCreateCharacter() {
		
		logToConsole(Constants.PRIMER);
		
		String name = consoleChooseCharacterName();
		Player.setName(name);

		
		logToConsole("Well met, " + Player.getName());
		
	}
	
	/**
	 * Prompts the player to choose the duration of the game, during the initial setup of the game.
	 * Loops until a valid game duration between 20 and 50 is given.
	 */
	public static void consoleChooseGameDuration() {
		
		logToConsole("How many days do you need to save the Saltforge?");
		
		int duration = 0;
		
		do {
			try {
				duration = Integer.parseInt(consoleGetInput("Enter a number between 20 and 50", false));
			}
			catch (NumberFormatException e) {
				logToConsole("Number is not valid");
				duration = 0;
			}
			
		} while (!(duration >= Constants.MIN_GAME_DURATION && duration <= Constants.MAX_GAME_DURATION));
		
		gameDuration = duration;
		logToConsole("Old Saltbeard has given you " + duration + " days to save the Saltforge from collapse.");
		
	}
	
	/**
	 * Prompts the player to choose their starting ship.
	 * The player must choose one of four options, then the ship object will be instantiated and assigned to the Player object.
	 */
	private static void consoleChooseShip() throws IllegalArgumentException {
		
		logToConsole(Player.getName() + ", Old Saltbeard will grant you one ship of your choosing:\n");
		
		
		ArrayList<String> shipOptions = new ArrayList<String>();
		
		String merchantman = "Merchantman      (Cargo capacity: Medium, hull: Medium, speed: Medium, weapons: Medium, wages: Medium)";
		String cutter      = "Elven Cutter     (Cargo capacity: Medium, hull: Medium, speed: Fast,   weapons: Low,    wages: Medium)";
		String sloop       = "Tunian War-Sloop (Cargo capacity: Medium, hull: High,   speed: Medium, weapons: High,   wages: High)";
		String barge       = "Dwarven Barge    (Cargo capacity: High,   hull: High,   speed: Slow,   weapons: Low,    wages: Low)";
		
		shipOptions.add(merchantman);
		shipOptions.add(cutter);
		shipOptions.add(sloop);
		shipOptions.add(barge);
		
		for (int i = 0; i < shipOptions.size(); i++) {
			logToConsole((i + 1) + ". " + shipOptions.get(i));
		}
		
		logToConsole("");
		
		int choice = 0;
		
		while (choice < 1 || choice > 4) {
			try {
				choice = Integer.parseInt(consoleGetInput("Enter a number between 1 and 4.", false));
			} catch (NumberFormatException e) {
				logToConsole("Please enter a valid number.");
			}
		}
		
		Ship playerShip;
		
		switch (choice) {
			case 1:
				playerShip = new Ship(ShipModel.MERCHANTMAN);
				break;
			case 2:
				playerShip = new Ship(ShipModel.CUTTER);
				break;
			case 3:
				playerShip = new Ship(ShipModel.SLOOP);
				break;
			case 4:
				playerShip = new Ship(ShipModel.BARGE);
				break;
			default:
				throw new IllegalArgumentException("Invalid ship choice.");
		}
		
		Player.setShip(playerShip);
		
		String selectedShip = shipOptions.get(choice - 1);
		
		logToConsole("You have picked " + selectedShip);
	}
	
	/**
	 * Prompts the user to enter something in the console.
	 * @param prompt The prompt to display to the player.
	 * @param anyKey If true, no "&gt;&gt;&gt; " string will be displayed.
	 * @return The string input by the Player.
	 */
	public static String consoleGetInput(String prompt, Boolean anyKey) {

		String returnString = "";
		
		logToConsole(prompt);
		
		if (!anyKey) {
			logToConsolePrompt();
		}
		
		
		try {
			returnString = scanner.nextLine();
		}
		catch (NoSuchElementException | IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		
		logToConsole("");
		
		return returnString;
	}

	/**
	 * Creates island objects and uses them to populate routes with island-pairs.
	 */
	private static void setupIslandsAndRoutes() {
		
		Island saltForge = new Island(Constants.ISLAND_SALTFORGE, Constants.ISLAND_SALTFORGE_DESCRIPTION);
		Island tunia = new Island(Constants.ISLAND_TUNIA, Constants.ISLAND_TUNIA_DESCRIPTION);
		Island sandyFields = new Island(Constants.ISLAND_SANDYFIELDS, Constants.ISLAND_SANDYFIELDS_DESCRIPTION);
		Island skullHaven = new Island(Constants.ISLAND_SKULLHAVEN, Constants.ISLAND_SKULLHAVEN_DESCRIPTION);
		Island seaNomads = new Island(Constants.ISLAND_SEANOMADS, Constants.ISLAND_SEANOMADS_DESCRIPTION);
		
		islands = new Island[] {saltForge, tunia, sandyFields, skullHaven, seaNomads};
		
		Island[] tranquilExpansePair = {saltForge, sandyFields};
		Island[] basaltSpiresPair = {saltForge, tunia};
		Island[] aroundBasaltPair = {saltForge, tunia};
		Island[] shipwreckerPair = {sandyFields, skullHaven};
		Island[] oysterBayPair = {tunia, seaNomads};
		Island[] jackalSeaPairOne = {sandyFields, skullHaven};
		Island[] jackalSeaPairTwo = {seaNomads, skullHaven};
		
		ArrayList<Island[]> tranquilExpansePairList = new ArrayList<Island[]>();
		tranquilExpansePairList.add(tranquilExpansePair);
		
		ArrayList<Island[]> basaltSpiresPairList = new ArrayList<Island[]>();
		basaltSpiresPairList.add(basaltSpiresPair);
		
		ArrayList<Island[]> aroundBasaltPairList = new ArrayList<Island[]>();
		aroundBasaltPairList.add(aroundBasaltPair);
		
		ArrayList<Island[]> shipwreckerPairList = new ArrayList<Island[]>();
		shipwreckerPairList.add(shipwreckerPair);
		
		ArrayList<Island[]> oysterBayPairList = new ArrayList<Island[]>();
		oysterBayPairList.add(oysterBayPair);
		
		ArrayList<Island[]> jackalSeaPairList = new ArrayList<Island[]>();
		jackalSeaPairList.add(jackalSeaPairOne);
		jackalSeaPairList.add(jackalSeaPairTwo);
		
		Route tranquilExpanse = new Route("The Tranquil Expanse", Constants.ROUTE_TRANQUIL_EXPANSE_DESCRIPTION, 20, 24, tranquilExpansePairList);
		Route basaltSpires = new Route("Through the Basalt Spires", Constants.ROUTE_BASALT_SPIRES_DESCRIPTION, 40, 6, basaltSpiresPairList);
		Route aroundBasaltSpires = new Route("Around the Basalt Spires", Constants.ROUTE_AROUND_BASALT_DESCRIPTION, 20, 12, aroundBasaltPairList);
		Route shipwreckerShoals = new Route("The Shipwrecker Shoals", Constants.ROUTE_SHIPWRECKER_SHOALS_DESCRIPTION, 20, 24, shipwreckerPairList);
		Route oysterBay = new Route("Oyster Bay", Constants.ROUTE_OYSTER_BAY_DESCRIPTION, 20, 6, oysterBayPairList);
		Route jackalSea = new Route("The Sea of Jackals", Constants.ROUTE_JACKAL_SEA_DESCRIPTION, 60, 12, jackalSeaPairList);
		
		routes = new Route[] {tranquilExpanse, basaltSpires, aroundBasaltSpires, shipwreckerShoals, oysterBay, jackalSea};
	
		
		curIsland = saltForge;
	}
	
	/**
	 * Initializes all of the necessary objects and states to be used in the game.
	 */
	public static void setupGame() {
		
		scanner = new Scanner(System.in);
		
		setupIslandsAndRoutes();
		
	}
	
	/**
	 * Completes a graceful exit of the program.
	 */
	public static void exitGame() {
		scanner.close();
	}
	
	/**
	 * Get the max duration of the game, in days.
	 * @return Max duration of the game, in days.
	 */
	public static int getGameDuration() {
		return gameDuration;
	}
	
	/**
	 * Set the max duration of the game, in days.
	 */
	public static void setGameDuration(int days) {
		gameDuration = days;
	}
	
	public static void consoleIslandWelcome(Island island) {
		logToConsole("");
		logToConsole(getBanner(island.getIslandName()));
		logToConsole("");
		logToConsole(island.getIslandDescription());
		logToConsole("");
	}
	
	public static void consolePresentIslandOptions(Island island) {
		
		int curOptionNumber = 1;
		
		//maps console option to object array of {Route, destination Island}
		HashMap<Integer, Object[]> travelOptions = new HashMap<Integer, Object[]>();
		
		ArrayList<Route> myRoutes = new ArrayList<Route>();
		for (Route route : routes) {
			if (route.includesIsland(island))
				myRoutes.add(route);
		}
		
		for (Route route : myRoutes) {
			for (Island destinationIsland : route.getPossibleDestinations(island)) {
				travelOptions.put(curOptionNumber++, new Object[] {route, destinationIsland});
			}
		}
		
		for (int travelOption : travelOptions.keySet()) {
			Object[] routeIslandPair = travelOptions.get(travelOption);
			Island destinationIsland = (Island) routeIslandPair[1];
			Route viaRoute = (Route) routeIslandPair[0];
			
			logToConsole(travelOption + ". Travel to " + destinationIsland.getIslandName() + " via " + viaRoute.getName() + ".");
		}
		
	}
	
	public static void arriveAtIsland(Island island) {
		
		curIsland = island;
		
		//TODO set Screen?
		
		consoleIslandWelcome(curIsland);
		
		consolePresentIslandOptions(curIsland);
		
	}
	
	
	/**
	 * Initializes the game and walks the player through character creation.
	 * @param args No arguments are currently used for the main method.
	 */
	public static void main(String[] args) {
		
		setupGame();
		
		if (!GUIMode) {
			
			logToConsole(getBanner("Welcome to Sailors of the Saltforge"));
			logToConsole("Created by Ryan Croucher and Steven Johnson\n");
			
			consoleGetInput("<<Press enter to continue>>", true);
		
			consoleCreateCharacter();
			
			consoleChooseGameDuration();
			
			try {
				consoleChooseShip();
			} catch (IllegalArgumentException e) {
				logToConsole("Failed to create Player's ship.");
				System.out.println(e.getMessage());
			}
		}
		
		else {
			//startGUI();
		}
		
		arriveAtIsland(islands[0]); // arrive at the saltforge

	}

}
