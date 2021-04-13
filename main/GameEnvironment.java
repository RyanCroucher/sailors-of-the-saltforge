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
	/**
	 * an arraylist of all item instances in the game (one for each item type)
	 */
	private static ArrayList<Item> items;
	
	/**
	 * An array of all of the routes between island pairs in the game.
	 */
	private static Route[] routes;
	
	/**
	 * An array of all island locations in the game
	 */
	private static Island[] islands;
	
	/**
	 * The island where the player is currently located
	 */
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
		
		//TODO clean this up
		
		
		//initialize saltForge inventory and store
		Inventory saltForgeInventory = new Inventory();
		saltForgeInventory.addItem(items.get(0), 1);
		saltForgeInventory.addItem(items.get(1), 1);
		saltForgeInventory.addItem(items.get(2), 1);
		saltForgeInventory.addItem(items.get(3), 1);
		
		ArrayList<Item> imports = new ArrayList<Item>();
		ArrayList<Item> exports = new ArrayList<Item>();
		imports.add(items.get(1));
		exports.add(items.get(2));
		
		Store saltForgeStore = new Store(saltForgeInventory, imports, exports);
		saltForgeStore.randomizeInventory(0, 60);
		
		//initialize tunia inventory and store
		Inventory tuniaInventory = new Inventory();
		tuniaInventory.addItem(items.get(0), 1);
		tuniaInventory.addItem(items.get(1), 1);
		tuniaInventory.addItem(items.get(2), 1);
		tuniaInventory.addItem(items.get(3), 1);
		
		imports = new ArrayList<Item>();
		exports = new ArrayList<Item>();
		imports.add(items.get(0));
		exports.add(items.get(1));
		
		Store tuniaStore = new Store(tuniaInventory, imports, exports);
		tuniaStore.randomizeInventory(0, 60);
		
		//initialize sandyFields inventory and store
		Inventory sandyFieldsInventory = new Inventory();
		sandyFieldsInventory.addItem(items.get(0), 1);
		sandyFieldsInventory.addItem(items.get(1), 1);
		sandyFieldsInventory.addItem(items.get(2), 1);
		sandyFieldsInventory.addItem(items.get(3), 1);
		
		imports = new ArrayList<Item>();
		exports = new ArrayList<Item>();
		imports.add(items.get(2));
		exports.add(items.get(3));
		
		Store sandyFieldsStore = new Store(sandyFieldsInventory, imports, exports);
		sandyFieldsStore.randomizeInventory(0, 60);
		
		//initialize skullHaven inventory and store
		Inventory skullHavenInventory = new Inventory();
		skullHavenInventory.addItem(items.get(0), 1);
		skullHavenInventory.addItem(items.get(1), 1);
		skullHavenInventory.addItem(items.get(2), 1);
		skullHavenInventory.addItem(items.get(3), 1);
		
		imports = new ArrayList<Item>();
		exports = new ArrayList<Item>();
		imports.add(items.get(3));
		imports.add(items.get(1));
		
		Store skullHavenStore = new Store(skullHavenInventory, imports, exports);
		skullHavenStore.randomizeInventory(0, 60);
		
		//initialize skullHaven inventory and store
		Inventory seaNomadsInventory = new Inventory();
		seaNomadsInventory.addItem(items.get(0), 1);
		seaNomadsInventory.addItem(items.get(1), 1);
		seaNomadsInventory.addItem(items.get(2), 1);
		seaNomadsInventory.addItem(items.get(3), 1);
		
		imports = new ArrayList<Item>();
		exports = new ArrayList<Item>();
		imports.add(items.get(2));
		imports.add(items.get(3));
		exports.add(items.get(0));
		
		Store seaNomadsStore = new Store(seaNomadsInventory, imports, exports);
		seaNomadsStore.randomizeInventory(0, 60);
		
		Island saltForge = new Island(Constants.ISLAND_SALTFORGE, Constants.ISLAND_SALTFORGE_DESCRIPTION, saltForgeStore);
		Island tunia = new Island(Constants.ISLAND_TUNIA, Constants.ISLAND_TUNIA_DESCRIPTION, tuniaStore);
		Island sandyFields = new Island(Constants.ISLAND_SANDYFIELDS, Constants.ISLAND_SANDYFIELDS_DESCRIPTION, sandyFieldsStore);
		Island skullHaven = new Island(Constants.ISLAND_SKULLHAVEN, Constants.ISLAND_SKULLHAVEN_DESCRIPTION, skullHavenStore);
		Island seaNomads = new Island(Constants.ISLAND_SEANOMADS, Constants.ISLAND_SEANOMADS_DESCRIPTION, seaNomadsStore);
		
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
	
	public static void setupItems() {
		Item luxuryGoods = new Item("Luxury Goods", Constants.ITEM_BASE_PRICE_LUXURY);
		Item alcohol = new Item("Alcohol", Constants.ITEM_BASE_PRICE_ALCOHOL);
		Item rawMaterials = new Item("Raw Materials", Constants.ITEM_BASE_PRICE_RAW_MATERIALS);
		Item food = new Item("Food", Constants.ITEM_BASE_PRICE_FOOD);
		
		items = new ArrayList<Item>();
		items.add(luxuryGoods);
		items.add(alcohol);
		items.add(rawMaterials);
		items.add(food);
	}
	
	/**
	 * Initializes all of the necessary objects and states to be used in the game.
	 */
	public static void setupGame() {
		
		scanner = new Scanner(System.in);
		
		setupItems();
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
	
	/**
	 * Greet the player with some information about the island they've arrived at
	 * @param island the current location of the player
	 */
	public static void consoleIslandWelcome(Island island) {
		logToConsole("");
		logToConsole(getBanner(island.getIslandName()));
		logToConsole("");
		logToConsole(island.getIslandDescription());
		logToConsole("");
	}
	
	/**
	 * Gets all travel destinations (via route) from this location
	 * @param curOptionNumber the current option number (for display/choice purposes)
	 * @param island current player location
	 * @return a hashmap mapping a choice number to a Route, Island pair
	 */
	public static HashMap<Integer, Object[]> getTravelOptions(int curOptionNumber, Island island) {
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
		
		return travelOptions;
	}
	/**
	 * Given a list of options, display these choices to the player
	 * @param travelOptions a hashmap mapping choice number to a Route, Island pair
	 */
	private static void consolePresentTravelOptions(HashMap<Integer, Object[]> travelOptions) {
		for (int travelOption : travelOptions.keySet()) {
			Object[] routeIslandPair = travelOptions.get(travelOption);
			Island destinationIsland = (Island) routeIslandPair[1];
			Route viaRoute = (Route) routeIslandPair[0];
			
			int riskLevel = viaRoute.getRiskLevel();
			int duration = viaRoute.getDuration();
			
			String routeInfo = "(Encounter Chance: " + riskLevel + "%, Travel Time: " + duration + " hours)";
			
			logToConsole(travelOption + ". Travel to " + destinationIsland.getIslandName() + " via " + viaRoute.getName() + " " + routeInfo);
		}
		
		logToConsole("");
	}
	
	public static HashMap<Integer, Item> consolePresentStoreOptions(Island island) {
		
		logToConsole("");
		logToConsole("You enter the thriving marketplace, ready to earn your fortune in the name of The Salt Forge.");
		logToConsole("");
		logToConsole("You quickly discover the main imports and exports of this market:");
		
		Store store = island.getIslandStore();
		
		String importString = "Main imports (Will buy and sell for double the price): ";
		ArrayList<Item> imports = store.getImports();
		ArrayList<String> importStrings = new ArrayList<String>();
		
		for (Item importItem : imports) {
			importStrings.add(importItem.getName());
		}
		
		importString += String.join(", ", importStrings);
		
		String exportString = "Main exports (Will buy and sell for half the price): ";
		ArrayList<Item> exports = store.getExports();
		ArrayList<String> exportStrings = new ArrayList<String>();
		
		for (Item exportItem : exports) {
			exportStrings.add(exportItem.getName());
		}
		
		exportString += String.join(", ", exportStrings);
		
		logToConsole(importString);
		logToConsole(exportString);
		
		logToConsole("");
		logToConsole("To buy or sell an item, type the choice number and then the quantity to trade, seperated by a space.");
		logToConsole("I.e. to buy the first good, type 1 10, to sell the first good, type 2 3");
		logToConsole("");
		
		int consoleOptionNumber = 1;
		
		HashMap<Integer, Item> transactionOptions = new HashMap<Integer, Item>();
		
		for (Item item: store.getInventoryItems()) {
			transactionOptions.put(consoleOptionNumber, item);
			logToConsole(consoleOptionNumber++ + ". Buy " + item.getName() + " for " + store.getItemPrice(item) + " gold crowns each.");
			transactionOptions.put(consoleOptionNumber, item);
			logToConsole(consoleOptionNumber++ + ". Sell " + item.getName() + " for " + store.getItemPrice(item) + " gold crowns each.");
		}
		
		logToConsole(consoleOptionNumber + ". Return to your " + Player.getShip().getModelName() + ".");
		
		logToConsole("");
		
		return transactionOptions;
		
	}
	
	public static void goToMarket(Island island) {
		HashMap<Integer, Item> transactionOptions = consolePresentStoreOptions(island);
		
		int choice = 0;
		int quantity = 0;
		
		while (choice < 1 || choice > transactionOptions.size() + 1) {
			String input = consoleGetInput("What do you want to do?", false);
			String[] splitInput = input.split(" ");
			
			try {
				
				choice = Integer.parseInt(splitInput[0]);
				quantity = Integer.parseInt(splitInput[1]);
				
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				logToConsole("Please enter a valid choice (and quantity)");
			}
		}
		
		//Not one of the buy/sell options, must be exit
		if (choice == transactionOptions.size() + 1) {
			arriveAtIsland(island);
		} else {
		
			String buyOrSell = choice % 2 == 0 ? "sell" : "buy";
			
			logToConsole("Player wants to " + buyOrSell + " " + quantity + " " + transactionOptions.get(choice).getName());
			
			logToConsole("");
			
			consoleGetInput("<<Press enter to continue>>", true);
			
			goToMarket(island);
		}
	}
	
	/**
	 * Displays a list of every action the player can take at the current island
	 * @param island the current location of the player
	 */
	public static void consolePresentIslandOptions(Island island) {
		
		int curOptionNumber = 1;
		
		logToConsole(curOptionNumber + ". Go to the market.");
		curOptionNumber++;
		
		HashMap<Integer, Object[]> travelOptions = getTravelOptions(curOptionNumber, island);
		
		//update curOptionNumber to the latest value
		for (int key : travelOptions.keySet()) {
			curOptionNumber = Math.max(curOptionNumber, key);
		}
		
		curOptionNumber++;
		
		consolePresentTravelOptions(travelOptions);
		
		int choice = 0;
		
		while (choice < 1 || choice >= curOptionNumber) {
			try {
				choice = Integer.parseInt(consoleGetInput("What do you want to do?", false));
			} catch (NumberFormatException e) {
				logToConsole("Please enter a valid number.");
			}
		}
		
		//go to market
		if (choice == 1) {
			goToMarket(island);
		}
		
		else if (travelOptions.containsKey(choice)) {
			
			Island chosenIsland = (Island) travelOptions.get(choice)[1];
			Route chosenRoute = (Route) travelOptions.get(choice)[0];
			
			logToConsole("");
			logToConsole(chosenRoute.getDescription());
			logToConsole("");
			consoleGetInput("<<Press enter to continue>>", true);
			
			arriveAtIsland(chosenIsland);
		}
		
	}
	
	/**
	 * Set current location of the player to another island
	 * @param island the island that the player arrives at
	 */
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
