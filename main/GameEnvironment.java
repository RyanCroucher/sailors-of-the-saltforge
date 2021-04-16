package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import exceptions.InsufficientCargoCapacityException;
import exceptions.InsufficientGoldException;
import exceptions.InsufficientItemQuantityException;
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
		
		logToConsole("He will also grant you, " + Player.getName() + ", " + Constants.PLAYER_START_GOLD + " " + Constants.NAME_CURRENCY + " and one ship of your choosing:\n");
		
		
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
		saltForgeStore.randomizeInventory(0, 100);
		
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
		tuniaStore.randomizeInventory(0, 100);
		
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
		sandyFieldsStore.randomizeInventory(0, 100);
		
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
		skullHavenStore.randomizeInventory(0, 100);
		
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
		seaNomadsStore.randomizeInventory(0, 100);
		
		Island saltForge = new Island(Constants.ISLAND_SALTFORGE, Constants.ISLAND_SALTFORGE_DESCRIPTION, saltForgeStore);
		Island tunia = new Island(Constants.ISLAND_TUNIA, Constants.ISLAND_TUNIA_DESCRIPTION, tuniaStore);
		Island sandyFields = new Island(Constants.ISLAND_SANDYFIELDS, Constants.ISLAND_SANDYFIELDS_DESCRIPTION, sandyFieldsStore);
		Island skullHaven = new Island(Constants.ISLAND_SKULLHAVEN, Constants.ISLAND_SKULLHAVEN_DESCRIPTION, skullHavenStore);
		Island seaNomads = new Island(Constants.ISLAND_SEANOMADS, Constants.ISLAND_SEANOMADS_DESCRIPTION, seaNomadsStore);
		
		islands = new Island[] {saltForge, tunia, sandyFields, skullHaven, seaNomads};
		
		Island[] tranquilExpansePair = {saltForge, sandyFields};
		Island[] basaltSpiresPair = {saltForge, tunia};
		Island[] aroundBasaltPair = {saltForge, tunia};
		Island[] shipwreckerPairOne = {sandyFields, skullHaven};
		Island[] shipwreckerPairTwo = {seaNomads, skullHaven};
		Island[] oysterBayPair = {tunia, seaNomads};
		Island[] jackalSeaPairOne = {sandyFields, skullHaven};
		Island[] jackalSeaPairTwo = {saltForge, skullHaven};
		Island[] jackalSeaPairThree = {saltForge, seaNomads};
		Island[] jackalSeaPairFour = {tunia, sandyFields};
		Island[] jackalSeaPairFive = {tunia, skullHaven};
		Island[] jackalSeaPairSix = {seaNomads, sandyFields};
		
		ArrayList<Island[]> tranquilExpansePairList = new ArrayList<Island[]>();
		tranquilExpansePairList.add(tranquilExpansePair);
		
		ArrayList<Island[]> basaltSpiresPairList = new ArrayList<Island[]>();
		basaltSpiresPairList.add(basaltSpiresPair);
		
		ArrayList<Island[]> aroundBasaltPairList = new ArrayList<Island[]>();
		aroundBasaltPairList.add(aroundBasaltPair);
		
		ArrayList<Island[]> shipwreckerPairList = new ArrayList<Island[]>();
		shipwreckerPairList.add(shipwreckerPairOne);
		shipwreckerPairList.add(shipwreckerPairTwo);
		
		ArrayList<Island[]> oysterBayPairList = new ArrayList<Island[]>();
		oysterBayPairList.add(oysterBayPair);
		
		ArrayList<Island[]> jackalSeaPairList = new ArrayList<Island[]>();
		jackalSeaPairList.add(jackalSeaPairOne);
		jackalSeaPairList.add(jackalSeaPairTwo);
		jackalSeaPairList.add(jackalSeaPairThree);
		jackalSeaPairList.add(jackalSeaPairFour);
		jackalSeaPairList.add(jackalSeaPairFive);
		jackalSeaPairList.add(jackalSeaPairSix);
		
		Route tranquilExpanse = new Route("The Tranquil Expanse", Constants.ROUTE_TRANQUIL_EXPANSE_DESCRIPTION, 20, 28, tranquilExpansePairList);
		Route basaltSpires = new Route("Through the Basalt Spires", Constants.ROUTE_BASALT_SPIRES_DESCRIPTION, 40, 10, basaltSpiresPairList);
		Route aroundBasaltSpires = new Route("Around the Basalt Spires", Constants.ROUTE_AROUND_BASALT_DESCRIPTION, 20, 16, aroundBasaltPairList);
		Route shipwreckerShoals = new Route("The Shipwrecker Shoals", Constants.ROUTE_SHIPWRECKER_SHOALS_DESCRIPTION, 20, 24, shipwreckerPairList);
		Route oysterBay = new Route("Oyster Bay", Constants.ROUTE_OYSTER_BAY_DESCRIPTION, 20, 10, oysterBayPairList);
		Route jackalSea = new Route("The Sea of Jackals", Constants.ROUTE_JACKAL_SEA_DESCRIPTION, 60, 12, jackalSeaPairList);
		
		routes = new Route[] {tranquilExpanse, basaltSpires, aroundBasaltSpires, shipwreckerShoals, oysterBay, jackalSea};
	
		
		curIsland = saltForge;
	}
	
	public static void setupItems() {
		Item luxuryGoods = new Item("Luxury Goods", Constants.ITEM_BASE_PRICE_LUXURY, "Silks, jewellery and spices", 1);
		Item alcohol = new Item("Alcohol", Constants.ITEM_BASE_PRICE_ALCOHOL, "Barrels and bottles of strong liquor", 1);
		Item rawMaterials = new Item("Raw Materials", Constants.ITEM_BASE_PRICE_RAW_MATERIALS, "Lumber, ores and coal [takes 2 cargo space]", 2);
		Item food = new Item("Food", Constants.ITEM_BASE_PRICE_FOOD, "Crops, fruits and meats", 1);
		
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
	 * Ends the game and prints your stats
	 * @param endGameCode the reason the game ended. 0 for max day reached, 1 for max gold reached, 2 for out of money, 3 for random event
	 */
	public static void endGame(int endGameCode) {
		
		logToConsole(getBanner("GAME OVER"));
		logToConsole("");
		
		//code 0, reached maximum number of days
		if (endGameCode == 0) {
			logToConsole("Congratulations, you reached day " + gameDuration + " of " + gameDuration + ".");
			logToConsole("You may have survived the rigors of the ocean, but you failed to acquire the gold to save the Saltforge.");
		}
		
		//code 1, acquired 10,000 gold to save the Salt Forge
		else if (endGameCode == 1) {
			logToConsole("You have acquired the necessary " + Constants.NAME_CURRENCY + " to save the Saltforge from ruin!");
			logToConsole("Old Saltbeard shakes your hand with salty tears in his eyes, and declares you a hero of the dwarven people.");
		}
		
		//code 2, couldn't afford to travel anywhere
		else if (endGameCode == 2) {
			logToConsole("You have failed to manage your finances, and cannot afford to leave port.");
			logToConsole("Your crew quickly abandon you, and you're forced to beg passage back to the Saltforge and live in shame.");
		}
		
		//code 3, killed by a random event
		else if (endGameCode == 3) {
			logToConsole("The trials of the open sea have proven too much for you, something happened out there, and you were never seen again.");
		}
		
		logToConsole("");
		
		logToConsole("Name: " + Player.getName());
		logToConsole("Game Duration: " + gameDuration);
		logToConsole("Actual Duration: " + Math.min((hoursSinceStart / 24), gameDuration));
		
		logToConsole("");
		
		int totalTransactions = 0;
		if (Ledger.getTransactions() != null)
			totalTransactions = Ledger.getTransactions().size();
		
		logToConsole("Total number of buy/sell transactions: " + totalTransactions);
		logToConsole("Profit: " + (Player.getGold() - Constants.PLAYER_START_GOLD) + " (Current gold - starting gold)");
		
		logToConsole("");
		
		int score = 10;
		
		//game was a 'loss'
		if (endGameCode > 1)
			score -= 5;
		
		if (Player.getGold() >= 10000)
			//if player reached the gold target, less days is better
			//lose up to half your score based on percentage of total game duration passed.
			score -= (int) (5f * ((hoursSinceStart / 24f) / gameDuration));
		else if (endGameCode != 0){
			//if you didn't reach the gold target, and didn't reach the game duration, more days and more gold is better
			score -= (int) (5f * (1f - ((hoursSinceStart / 24f) / gameDuration) * Player.getGold() / 10000));
		}
		//reached game duration, more gold is better
		else {
			score -= (int) (5f * (1f - Player.getGold() / 10000));
		}
		
		logToConsole("Your final merchant score is: " + score + "/" + 10);
		if (score == 0)
			logToConsole("Wow, that's terrible!");
		else if (score < 5)
			logToConsole("At least you tried!");
		else if (score < 7)
			logToConsole("Getting somewhere!");
		else logToConsole("Nailed it!");
		
		logToConsole("");
		consoleGetInput("<<Press enter to continue>>", true);
		
		exitGame();
	}
	
	/**
	 * Completes a graceful exit of the program.
	 */
	public static void exitGame() {
		closeScanner();
		System.exit(0);
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
		logToConsole("It is day " + hoursSinceStart / 24 + " of " + gameDuration + ".");
		logToConsole("You have: " + Player.getGold() + " " + Constants.NAME_CURRENCY + ".");
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
			int distance = viaRoute.getDistance();
			int modifiedTravelTime = getModifiedTravelTime(distance);
			
			int travelDays = modifiedTravelTime / 24;
			int travelHours = modifiedTravelTime % 24;
			
			boolean cheaperCrewHire = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;
			boolean cheaperRepairs = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;

			int totalCostToLeaveIsland = Player.getShip().totalCostToLeaveIsland(cheaperRepairs, cheaperCrewHire, modifiedTravelTime);
			
			String routeInfo = "(" + distance + " miles, Encounter Chance: " + riskLevel + "%, Travel Time: " + travelDays + " days " + travelHours + " hours, Crew and Repairs Cost: " + totalCostToLeaveIsland + ")";
			
			logToConsole(travelOption + ". Travel to " + destinationIsland.getIslandName() + " via " + viaRoute.getName() + " " + routeInfo);
		}
		
		logToConsole("");
	}
	
	/**
	 * Presents the list of goods to buy and sell
	 * @param island the island where the player currently is
	 * @return a map of console option to item, so that player can choose among them
	 */
	public static HashMap<Integer, Item> consolePresentStoreOptions(Island island) {
		
		//intro
		logToConsole("");
		logToConsole("You enter the thriving marketplace, ready to earn your fortune in the name of The Salt Forge.");
		logToConsole("");
		logToConsole("You quickly discover the main imports and exports of this market:");
		
		Store store = island.getIslandStore();
		
		//imports and exports
		String importString = "Main imports (Will buy and sell for up to double the price): ";
		ArrayList<Item> imports = store.getImports();
		ArrayList<String> importStrings = new ArrayList<String>();
		
		for (Item importItem : imports) {
			importStrings.add(importItem.getName());
		}
		
		importString += String.join(", ", importStrings);
		
		String exportString = "Main exports (Will buy and sell for down to half the price): ";
		ArrayList<Item> exports = store.getExports();
		ArrayList<String> exportStrings = new ArrayList<String>();
		
		for (Item exportItem : exports) {
			exportStrings.add(exportItem.getName());
		}
		
		exportString += String.join(", ", exportStrings);
		
		logToConsole(importString);
		logToConsole(exportString);
		
		//instructions
		logToConsole("");
		logToConsole("To buy or sell an item, type the choice number and then the quantity to trade, seperated by a space.");
		logToConsole("I.e. to buy the first good, type 1 10, to sell the first good, type 2 3");
		logToConsole("");
		
		String playerInventoryString = "You have: ";
		
		//list what the player has in inventory
		for (Item ownedItem : items) {
			playerInventoryString += ownedItem.getName() + "- " + Player.getShip().getInventory().getItemQuantity(ownedItem) + " ";
		}
		
		logToConsole(playerInventoryString);
		
		//list player wealth
		logToConsole("You have: " + Player.getGold() + " " + Constants.NAME_CURRENCY);
		
		//list cargo space available
		logToConsole("Your current cargo is: " + Player.getShip().getCargo() + "/" + Player.getShip().getCargoCapacity());
		
		logToConsole("");
		
		int consoleOptionNumber = 1;
		
		HashMap<Integer, Item> transactionOptions = new HashMap<Integer, Item>();
		
		for (Item item: store.getInventoryItems()) {
			transactionOptions.put(consoleOptionNumber, item);
			logToConsole(consoleOptionNumber++ + ". Buy " + item.getName()  + " (" + item.getDescription() + ") for " + store.getItemPrice(item) + " " + Constants.NAME_CURRENCY + " each (" + store.getItemQuantity(item) + " available).");
			transactionOptions.put(consoleOptionNumber, item);
			logToConsole(consoleOptionNumber++ + ". Sell " + item.getName() + " (" + item.getDescription() + ") for " + store.getItemPrice(item) + " " + Constants.NAME_CURRENCY + " each.");
		}
		
		logToConsole(consoleOptionNumber + ". Return to your " + Player.getShip().getModelName() + ".");
		
		logToConsole("");
		
		return transactionOptions;
		
	}
	
	/**
	 * Go from island options to market options. Handles buying and selling.
	 * @param island the island where the player currently is
	 */
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
		
			//buy orders are odd numbered
			
			//buy
			if (choice % 2 == 1) {
			
				try {
					buyItem(transactionOptions.get(choice), island.getIslandStore(), quantity);
				} catch (IllegalArgumentException e) {
					logToConsole(e.getMessage());
				} catch (InsufficientGoldException e) {
					logToConsole(e.getMessage());
				} catch (InsufficientCargoCapacityException e) {
					logToConsole(e.getMessage());
				} catch (InsufficientItemQuantityException e) {
					logToConsole(e.getMessage());
				}
				
			}
			
			//sell
			else {
				try {
					sellItem(transactionOptions.get(choice), island.getIslandStore(), quantity);
				} catch (IllegalArgumentException | InsufficientItemQuantityException e) {
					logToConsole(e.getMessage());
				}
			}
			logToConsole("");
			
			consoleGetInput("<<Press enter to continue>>", true);
			
			goToMarket(island);
		}
	}
	
	public static void buyItem(Item item, Store store, int quantity) throws IllegalArgumentException, InsufficientGoldException, InsufficientCargoCapacityException, InsufficientItemQuantityException {
		
		//validate input
		if (quantity < 1)
			throw new IllegalArgumentException("Quantity should be at least one");
		
		//can we afford it?
		int cost = store.getItemPrice(item) * quantity;
		if (cost > Player.getGold())
			throw new InsufficientGoldException("You are too poor (" + cost + " > " + Player.getGold() + ")");
		
		//can it fit in cargo?
		int availableCapacity = Player.getShip().getCargoCapacity() - Player.getShip().getCargo();
		if (quantity > availableCapacity)
			throw new InsufficientCargoCapacityException("Not enough space in the ship (" + (Player.getShip().getCargoCapacity() - Player.getShip().getCargo()) + ") capacity available.");
		
		//does the store have enough?
		int storeQuantity = store.getItemQuantity(item);
		if (storeQuantity < quantity)
			throw new InsufficientItemQuantityException("You can't buy more than the market has (" + storeQuantity + " < " + quantity + ")");
		
		//looks good, adjust player gold, ships cargo and add to ship's inventory. remove from store inventory
		Player.setGold(Player.getGold() - cost);
		Player.getShip().setCargo(Player.getShip().getCargo() + (quantity * item.getSize()));
		Player.getShip().getInventory().addItem(item, quantity);
		store.buyItem(item, quantity);
		
		//record the transaction in the ledger
		Ledger.addTransaction(quantity, item, true, store.getItemPrice(item), curIsland, hoursSinceStart);
		
		//print a successful message
		logToConsole("You purchase " + quantity + " " + item.getName() + " for " + cost + " " + Constants.NAME_CURRENCY);
		logToConsole("Beside you, Olard scribbles a note in the ledger, grumbling about the expense.");
	}
	
	public static void sellItem(Item item, Store store, int quantity) throws IllegalArgumentException, InsufficientItemQuantityException {
		
		//validate input
		if (quantity < 1)
			throw new IllegalArgumentException("Quantity should be at least one");
		
		//do we have enough of this item to sell it?
		int quantityOwned = Player.getShip().getInventory().getItemQuantity(item);
		if (quantityOwned < quantity)
			throw new InsufficientItemQuantityException("Not enough of this item to sell: (" + quantityOwned + " < " + quantity + ")");
		
		//looks good, adjust player gold, ships cargo and remove from ship's inventory, add to store inventory
		int netProfit = store.getItemPrice(item) * quantity;
		Player.setGold(Player.getGold() + netProfit);
		Player.getShip().setCargo(Player.getShip().getCargo() - (quantity * item.getSize()));
		Player.getShip().getInventory().removeItem(item, quantity);
		store.sellItem(item, quantity);
		
		//record the transaction in the ledger
		Ledger.addTransaction(quantity, item, false, store.getItemPrice(item), curIsland, hoursSinceStart);
		
		//print a successful message
		logToConsole("You sell " + quantity + " " + item.getName() + " for " + netProfit + " " + Constants.NAME_CURRENCY);
		logToConsole("Olard grabs the bag of coin as soon as you make the sale, stashing it somewhere under his armour.");
	}
	
	/**
	 * Displays all relevant information about the state of your ship
	 */
	public static void consoleShowShipDetails() {
		
		Ship ship = Player.getShip();
		
		logToConsole("You find Lothar where he is mumbling to himself in the corner, and demand to know the state of your ship.");
		logToConsole("He tells you:");
		logToConsole("");
		logToConsole("You are Captain " + Player.getName() + ", a dwarven merchant from the Salt Forge.");
		logToConsole("Your ship is a " + ship.getModelName() + ".");
		
		String refillCrewString = "You do not need to hire more crew.";
		if (ship.getCrew() < ship.getMaxCrew()) {
			int costToRefill = ship.getRefillCrewCost(curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN);
			refillCrewString = "It will cost you " + costToRefill + " " + Constants.NAME_CURRENCY + " to hire more crew.";
		}
		logToConsole("Your ship has " + ship.getCrew() + " crew out of a maximum of " + ship.getMaxCrew() + ". " + refillCrewString);
		
		logToConsole("You must pay your crew a total wage of " + ship.getWageCost(24) + " " + Constants.NAME_CURRENCY + " per day.");
		logToConsole("The " + ship.getModelName() + " is carrying " + ship.getCargo() + " items out of a maximum of " + ship.getCargoCapacity() + ".");
		
		String upgradeString = "You have not yet purchased any upgrades for your ship.";
		if (ship.getUpgrades().size() > 0)
			upgradeString = "You have wisely purchased these upgrades: " + String.join(", ", ship.getUpgrades());
		logToConsole(upgradeString);
		
		String hullRepairString = "Your hull has not sustained any damage.";
		if (ship.getHull() < ship.getMaxHull()) {
			int costToRepair = ship.getRepairCost(curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN);
			hullRepairString = "Your ship has taken damage and is at " + ship.getHull() + " out of a maximum of " + ship.getMaxHull() + " hull points. It will cost " + costToRepair + " " + Constants.NAME_CURRENCY + " to repair.";
		}
		logToConsole(hullRepairString);
		
		logToConsole("");
		consoleGetInput("<<Press enter to continue>>", true);
		
		arriveAtIsland(curIsland);
	}
	
	public static void consoleViewLedger() {
		
		logToConsole("As you enter Olard's cabin, you inquire about the ship's ledger, but he motions his hand to shush you. He appears to kneeling before a great book, deep in prayer.");
		logToConsole("After an awkward few minutes, he stands up and gestures to a small book on a nearby table. 'Oh, the ledger is over there.'");
		logToConsole("");
		logToConsole("Up to 20 previous transactions:");
		
		ArrayList<Transaction> transactions;
		
		try {
			transactions = Ledger.getTransactions(20);
			
			for (Transaction transaction : transactions) {
				logToConsole(transaction.toString());
			}
			
		} catch (NullPointerException e) {
			logToConsole("There are no transactions yet.");
		}
		
		logToConsole("");
		consoleGetInput("<<Press enter to continue>>", true);
		
		arriveAtIsland(curIsland);
		
	}
	
	/**
	 * Displays a list of every action the player can take at the current island
	 * @param island the current location of the player
	 */
	public static void consolePresentIslandOptions(Island currentIsland) {
		
		int curOptionNumber = 1;
		
		logToConsole(curOptionNumber + ". Go to the market.");
		curOptionNumber++;
		
		logToConsole(curOptionNumber + ". Ask your Bosun Lothar about the state of your " + Player.getShip().getModelName() + ".");
		curOptionNumber++;
		
		logToConsole(curOptionNumber + ". Ask your Quartermaster Olard about the transaction ledger.");
		curOptionNumber++;
		
		logToConsole(curOptionNumber + ". Inquire about an upgrade for your ship.");
		curOptionNumber++;
		
		HashMap<Integer, Object[]> travelOptions = getTravelOptions(curOptionNumber, currentIsland);
		
		//update curOptionNumber to the latest value
		for (int key : travelOptions.keySet()) {
			curOptionNumber = Math.max(curOptionNumber, key) + 1;
		}
		
		consolePresentTravelOptions(travelOptions);
		
		int choice = 0;
		
		while (choice < 1 || choice >= curOptionNumber) {
			try {
				choice = Integer.parseInt(consoleGetInput("What do you want to do?", false));
			} catch (NumberFormatException e) {
				logToConsole("Please enter a valid number.");
			}
		}
		
		if (choice == 1) {
			goToMarket(currentIsland);
		}

		else if (choice == 2) {
			consoleShowShipDetails();
		}
		
		else if (choice == 3) {
			consoleViewLedger();
		}
		
		else if (choice == 4) {
			consoleViewIslandUpgrade();
		}
		
		else if (travelOptions.containsKey(choice)) {
			
			Island chosenIsland = (Island) travelOptions.get(choice)[1];
			Route chosenRoute = (Route) travelOptions.get(choice)[0];
			
			try {
				travelToIsland(chosenIsland, chosenRoute);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				consoleGetInput("<<Press enter to continue>>", true);
				consolePresentIslandOptions(currentIsland);
			} catch (InsufficientGoldException e) {
				logToConsole(e.getMessage());
				consoleGetInput("<<Press enter to continue>>", true);
				consolePresentIslandOptions(currentIsland);
			}
		}
		
	}
	
	/**
	 * See the unique upgrade for the island, and possibly purchase it
	 */
	public static void consoleViewIslandUpgrade() throws IllegalArgumentException {
		
		String upgrade = "";
		int upgradeCost = 1000;
		
		switch (curIsland.getIslandName()) {
			case (Constants.ISLAND_SALTFORGE):
				upgrade = Constants.UPGRADE_CANNONS;
				logToConsole("The dwarven smiths offer to upgrade your cannons for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_TUNIA):
				upgrade = Constants.UPGRADE_HULL;
				logToConsole("The Tunian shipyard can reinforce your ship's hull for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SKULLHAVEN):
				upgrade = Constants.UPGRADE_FLAG;
				logToConsole("In a dark alley, a shady figure whispers that he can help you to evade attacks from pirates, and offers to sell you a genuine pirate flag for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SANDYFIELDS):
				upgrade = Constants.UPGRADE_CONTRACT;
				logToConsole("The Council of Peasants declares that they will sell all goods to you at a reduced price, if you make an upfront investment for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SEANOMADS):
				upgrade = Constants.UPGRADE_SAILS;
				logToConsole("An old woman with leathery hands offers you a painstakingly crafted main sail for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			default:
				throw new IllegalArgumentException("Can't buy upgrades at this island");
		}
		
		logToConsole("");
		
		if (Player.getShip().getUpgrades().contains(upgrade)) {
			logToConsole("You already have this upgrade.");
			logToConsole("");
			consoleGetInput("<<Press enter to continue>>", true);
			arriveAtIsland(curIsland);
		}
		else {
			logToConsole("1. Purchase " + upgrade + " for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
			logToConsole("2. Return to your ship.");
			logToConsole("");
			
			int choice = 0;
			
			while (choice != 1 && choice != 2) {
				try {
					choice = Integer.parseInt(consoleGetInput("What do you want to do?", false));
				} catch (NumberFormatException e) {
					logToConsole("Please enter a valid number.");
				}
			}
			
			if (choice == 1) {
				
				try {
				
					if (Player.getGold() < upgradeCost)
						throw new InsufficientGoldException("You don't have enough " + Constants.NAME_CURRENCY + " to buy this upgrade.");
					
					Player.getShip().addUpgrade(upgrade);
					
					// if we buy the exclusive contract, give sandy fields all items as imports
					if (curIsland.getIslandName() == Constants.ISLAND_SANDYFIELDS) {
						curIsland.getIslandStore().addExport(items.get(0));
						curIsland.getIslandStore().addExport(items.get(1));
						curIsland.getIslandStore().addExport(items.get(2));
						curIsland.getIslandStore().removeImport(items.get(2));
					}
					
					Player.setGold(Player.getGold() - upgradeCost);
					
					logToConsole("You purchase " + upgrade + " for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
					consoleGetInput("<<Press enter to continue>>", true);
					arriveAtIsland(curIsland);
					
				} catch (InsufficientGoldException e) {
					logToConsole(e.getMessage());
					logToConsole("");
					consoleGetInput("<<Press enter to continue>>", true);
					arriveAtIsland(curIsland);
				}
			}
			
			else {
				arriveAtIsland(curIsland);
			}
		}
		
		
		
	}
	
	/**
	 * Travel to another island. Handles route description, event generation, passing of time etc
	 * @param island the island to travel to
	 * @param route the route to travel by
	 */
	public static void travelToIsland(Island destinationIsland, Route chosenRoute) throws IllegalArgumentException, InsufficientGoldException {
		
		if (!chosenRoute.includesIsland(destinationIsland))
			throw new IllegalArgumentException("Can't reach that island by that route!");
		
		int modifiedDuration = getModifiedTravelTime(chosenRoute.getDistance());
		
		boolean cheaperRepairs = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;
		boolean cheaperCrewHire = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;
		
		int totalCostToTravel = Player.getShip().totalCostToLeaveIsland(cheaperRepairs, cheaperCrewHire, modifiedDuration);
				
		if (totalCostToTravel > Player.getGold())
			throw new InsufficientGoldException("Not enough money to leave port!");
		
		//deduct gold from the player
		Player.setGold(Player.getGold() - totalCostToTravel);
		
		//pass time given route duration and speed of your ship
		passTime(modifiedDuration);
		
		//Show the description of the journey
		logToConsole("");
		logToConsole(chosenRoute.getDescription());
		logToConsole("");
		logToConsole(modifiedDuration + " hours have passed.");
		logToConsole("You spent " + totalCostToTravel + " " + Constants.NAME_CURRENCY + " on crew hire, wages and repairs.");
		consoleGetInput("<<Press enter to continue>>", true);
		
		//TODO: Handle events
		
		
		//Set the random price modifier for imports and exports
		destinationIsland.getIslandStore().setFactor(1 + Math.random());
		//set random item quantities
		destinationIsland.getIslandStore().randomizeInventory(0, 100);
		
		//Arrive at the island
		arriveAtIsland(destinationIsland);
		
		
	}
	
	/**
	 * Increments elapsed game time by given duration. Checks if time elapsed exceeds game duration.
	 * @param duration time passed in hours
	 * @throws IllegalArgumentException
	 */
	public static void passTime(int duration) throws IllegalArgumentException {
		//validate duration
		if (duration < 0)
			throw new IllegalArgumentException("Time passed must be at least 0");
		
		hoursSinceStart += duration;
		
		if (hoursSinceStart >= gameDuration * 24) {
			endGame(0);
		}
	}
	
	/**
	 * Gets the travel duration in hours modified by the speed of your ship
	 * @param distance the distance in miles to travel
	 * @return the modified travel time in hours
	 * @throws IllegalArgumentException
	 */
	public static int getModifiedTravelTime(int distance) throws IllegalArgumentException {
		
		if (distance < 0)
			throw new IllegalArgumentException("Distance must be at least 0");
		
		//the duration of time is equal to the distance, reduced by the speed of the ship as a percentage
		return (int) (distance * (100 - Player.getShip().getSpeed()) / 100);
	}
	
	/**
	 * Set current location of the player to another island
	 * @param island the island that the player arrives at
	 */
	public static void arriveAtIsland(Island island) {
		
		curIsland = island;
		
		if (hoursSinceStart + minHoursToLeaveIsland(island) >= gameDuration * 24) {
			endGame(0);
		}
		
		if (Player.getNetWorth() < minCostToLeaveIsland())
			endGame(2);
		
		consoleIslandWelcome(curIsland);
		
		consolePresentIslandOptions(curIsland);
		
		
	}
	
	/**
	 * Closes the main Scanner
	 */
	public static void closeScanner() {
		scanner.close();
	}
	
	/**
	 * 
	 * @return the number of hours since the game started
	 */
	public static int getHoursSinceStart() {
		return hoursSinceStart;
	}
	
	/**
	 * 
	 * @return the current island where the player is
	 */
	public static Island getCurrentIsland() {
		return curIsland;
	}
	
	public static int minCostToLeaveIsland() {
		
		int cost = 10000;
		
		boolean cheaperCrewHire = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;
		boolean cheaperRepairs = curIsland.getIslandName() == Constants.ISLAND_SKULLHAVEN;
		
		for (Route route : routes) {
			if (route.includesIsland(curIsland)) {
				cost = Math.min(cost, Player.getShip().totalCostToLeaveIsland(cheaperRepairs, cheaperCrewHire, getModifiedTravelTime(route.getDistance())));
			}
		}
		
		return cost;
		
	}
	
	public static int minHoursToLeaveIsland(Island island) {
		
		int hours = 10000;
		
		for (Route route : routes) {
			if (route.includesIsland(island)) {
				hours = Math.min(hours, getModifiedTravelTime(route.getDistance()));
			}
		}
		
		return hours;
		
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
		
		//start with some gold
		Player.setGold(Constants.PLAYER_START_GOLD);
		
		//Set the random price modifier for imports and exports at the saltforge
		islands[0].getIslandStore().setFactor(1 + Math.random());
		
		// arrive at the saltforge
		arriveAtIsland(islands[0]);

	}

}
