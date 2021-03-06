package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import events.RandomEvent;
import exceptions.InsufficientCargoCapacityException;
import exceptions.InsufficientGoldException;
import exceptions.InsufficientItemQuantityException;
import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Item;
import main.Ledger;
import main.Player;
import main.Route;
import main.Ship;
import main.Store;
import main.Transaction;
import main.Constants.ShipModel;

/**
 * Plays the game in a console interface, if GUIMode is off
 * @author Ryan Croucher rcr69
 *
 */
public class Console {
	
	/**
	 * The scanner is created once when the game begins, and handles all console input.
	 */
	private static Scanner scanner;
	
	/**
	 * Prints given string to the console.
	 * @param s The input string to be printed.
	 */
	public static void logMessage(String s) {
		System.out.println(s);
	}
	
	/**
	 * Prints a prompt to the console to indicate that the user should enter something.
	 */
	public static void logMessagePrompt() {
		System.out.print(">>> ");
	}
	
	/**
	 * Prompts the user to enter something in the console.
	 * @param prompt The prompt to display to the player.
	 * @param anyKey If true, no "&gt;&gt;&gt; " string will be displayed.
	 * @return The string input by the Player.
	 */
	public static String getInput(String prompt, Boolean anyKey) {

		String returnString = "";
		
		logMessage(prompt);
		
		if (!anyKey) {
			logMessagePrompt();
		}
		
		
		try {
			returnString = scanner.nextLine();
		}
		catch (NoSuchElementException | IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		
		logMessage("");
		
		return returnString;
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
	 * Prompts the player for a character name. Loops until a valid name is given.
	 * @return The validated name.
	 */
	private static String chooseCharacterName() {
		
		
		Boolean validName = false;
		String name = "";
		
		do {
			
			name = getInput("What is your name, proud dwarf merchant?", false);
			
			try {
				validName = GameEnvironment.isValidName(name);
			} catch (IllegalArgumentException e) {
				logMessage(e.getMessage());
				validName = false;
			}
			
			
		} while (!validName);

		return name;
		
	}
	
	/**
	 * Walks the player through the character creation process.
	 * Prompts the player to choose a name, and then greets that player by character name.
	 */
	private static void createCharacter() {
		
		logMessage(Constants.PRIMER);
		
		String name = chooseCharacterName();
		Player.setName(name);

		
		logMessage("Well met, " + Player.getName());
		
	}
	
	/**
	 * Prompts the player to choose the duration of the game, during the initial setup of the game.
	 * Loops until a valid game duration between 20 and 50 is given.
	 */
	public static void chooseGameDuration() {
		
		logMessage("How many days do you need to save the Saltforge?");
		
		int duration = 0;
		
		do {
			try {
				duration = Integer.parseInt(getInput("Enter a number between 20 and 50", false));
			}
			catch (NumberFormatException e) {
				logMessage("Number is not valid");
				duration = 0;
			}
			
		} while (!(duration >= Constants.MIN_GAME_DURATION && duration <= Constants.MAX_GAME_DURATION));
		
		GameEnvironment.setGameDuration(duration);
		logMessage("Old Saltbeard has given you " + duration + " days to save the Saltforge from collapse.");
		
	}
	
	/**
	 * Prompts the player to choose their starting ship.
	 * The player must choose one of four options, then the ship object will be instantiated and assigned to the Player object.
	 */
	private static void chooseShip() throws IllegalArgumentException {
		
		logMessage("He will also grant you, " + Player.getName() + ", " + Constants.PLAYER_START_GOLD + " " + Constants.NAME_CURRENCY + " and one ship of your choosing:\n");
		
		
		ArrayList<String> shipOptions = new ArrayList<String>();
		
		String merchantman = Constants.MERCHANTMAN_INFO_STRING;
		String cutter = Constants.CUTTER_INFO_STRING;
		String sloop = Constants.SLOOP_INFO_STRING;
		String barge = Constants.BARGE_INFO_STRING;
		
		shipOptions.add(merchantman);
		shipOptions.add(cutter);
		shipOptions.add(sloop);
		shipOptions.add(barge);
		
		for (int i = 0; i < shipOptions.size(); i++) {
			logMessage((i + 1) + ". " + shipOptions.get(i));
		}
		
		logMessage("");
		
		int choice = 0;
		
		while (choice < 1 || choice > 4) {
			try {
				choice = Integer.parseInt(getInput("Enter a number between 1 and 4.", false));
			} catch (NumberFormatException e) {
				logMessage("Please enter a valid number.");
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
		
		logMessage("You have picked " + selectedShip);
	}
	
	/**
	 * Greet the player with some information about the island they've arrived at
	 * @param island the current location of the player
	 */
	public static void islandWelcome(Island island) {
		logMessage("");
		logMessage(getBanner(island.getIslandName()));
		logMessage("");
		logMessage(island.getIslandDescription());
		logMessage("");
		logMessage("It is day " + GameEnvironment.getHoursSinceStart() / 24 + " of " + GameEnvironment.getGameDuration() + ".");
		logMessage("You have: " + Player.getGold() + " " + Constants.NAME_CURRENCY + ".");
		logMessage("");
	}
	
	/**
	 * Given a list of options, display these choices to the player
	 * @param travelOptions a hashmap mapping choice number to a Route, Island pair
	 */
	private static void presentTravelOptions(HashMap<Integer, Object[]> travelOptions) {
		for (int travelOption : travelOptions.keySet()) {
			Object[] routeIslandPair = travelOptions.get(travelOption);
			Island destinationIsland = (Island) routeIslandPair[1];
			Route viaRoute = (Route) routeIslandPair[0];

			String routeInfo = viaRoute.getRouteInfoString();
						
			logMessage(travelOption + ". Travel to " + destinationIsland.getIslandName() + " via " + viaRoute.getName() + " " + routeInfo);
		}
		
		logMessage("");
	}
	
	/**
	 * Go from island options to market options. Handles buying and selling.
	 * @param island the island where the player currently is
	 */
	public static void goToMarket(Island island) {

		HashMap<Integer, Item> transactionOptions = presentStoreOptions(island);
		
		int choice = 0;
		int quantity = 0;
		
		while (choice < 1 || choice > transactionOptions.size() + 1) {
			String input = getInput("What do you want to do?", false);
			String[] splitInput = input.split(" ");
			
			try {
				
				choice = Integer.parseInt(splitInput[0]);
				quantity = Integer.parseInt(splitInput[1]);
				
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				logMessage("Please enter a valid choice (and quantity)");
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
					Item item = transactionOptions.get(choice);
					Store store = island.getIslandStore();
					GameEnvironment.buyItem(item, store, quantity);
					
					//print a successful message
					logMessage("You purchase " + quantity + " " + item.getName() + " for " + (store.getItemPrice(item) * quantity) + " " + Constants.NAME_CURRENCY);
					logMessage("Beside you, Olard scribbles a note in the ledger, grumbling about the expense.");
				} catch (IllegalArgumentException e) {
					logMessage(e.getMessage());
				} catch (InsufficientGoldException e) {
					logMessage(e.getMessage());
				} catch (InsufficientCargoCapacityException e) {
					logMessage(e.getMessage());
				} catch (InsufficientItemQuantityException e) {
					logMessage(e.getMessage());
				}
				
			}
			
			//sell
			else {
				try {
					Item item = transactionOptions.get(choice);
					Store store = island.getIslandStore();
					GameEnvironment.sellItem(item, store, quantity);
					
					//print a successful message
					logMessage("You sell " + quantity + " " + item.getName() + " for " + (store.getItemPrice(item) * quantity) + " " + Constants.NAME_CURRENCY);
					logMessage("Olard grabs the bag of coin as soon as you make the sale, stashing it somewhere under his armour.");
				} catch (IllegalArgumentException | InsufficientItemQuantityException e) {
					logMessage(e.getMessage());
				}
			}
			logMessage("");
			
			getInput("<<Press enter to continue>>", true);
			
			goToMarket(island);
		}
	}
	
	/**
	 * Presents the list of goods to buy and sell
	 * @param island the island where the player currently is
	 * @return a map of console option to item, so that player can choose among them
	 */
	public static HashMap<Integer, Item> presentStoreOptions(Island island) {
		
		//intro
		logMessage("");
		logMessage("You enter the thriving marketplace, ready to earn your fortune in the name of The Salt Forge.");
		logMessage("");
		logMessage("You quickly discover the main imports and exports of this market:");
		
		Store store = island.getIslandStore();
		
		//imports and exports
		String importString = "Main imports (Will buy and sell for up to double the price): " + GameEnvironment.getImportsString(store);

		
		String exportString = "Main exports (Will buy and sell for down to half the price): " + GameEnvironment.getExportsString(store);
		
		logMessage(importString);
		logMessage(exportString);
		
		logMessage("");
		
		String playerInventoryString = "You have: " + Player.getShip().getInventoryString();
		
		logMessage(playerInventoryString);
		
		//list player wealth
		logMessage("You have: " + Player.getGold() + " " + Constants.NAME_CURRENCY);
		
		//list cargo space available
		logMessage("Your current cargo is: " + Player.getShip().getCargo() + "/" + Player.getShip().getCargoCapacity());
		
		logMessage("");
		
		int consoleOptionNumber = 1;
		
		HashMap<Integer, Item> transactionOptions = new HashMap<Integer, Item>();
		
		for (Item item: store.getInventoryItems()) {
			transactionOptions.put(consoleOptionNumber, item);
			logMessage(consoleOptionNumber++ + ". Buy " + item.getName()  + " (" + item.getDescription() + ") for " + store.getItemPrice(item) + " " + Constants.NAME_CURRENCY + " each (" + store.getItemQuantity(item) + " available).");
			transactionOptions.put(consoleOptionNumber, item);
			logMessage(consoleOptionNumber++ + ". Sell " + item.getName() + " (" + item.getDescription() + ") for " + store.getItemPrice(item) + " " + Constants.NAME_CURRENCY + " each.");
		}
		
		logMessage(consoleOptionNumber + ". Return to your " + Player.getShip().getModelName() + ".");
		
		logMessage("");
		
		//instructions
		logMessage("To buy or sell an item, type the choice number and then the quantity to trade, seperated by a space.");
		logMessage("I.e. to buy the first good, type 1 10, to sell the first good, type 2 3");
		
		return transactionOptions;
		
	}
	
	/**
	 * Displays a list of every action the player can take at the current island
	 * @param island the current location of the player
	 */
	public static void presentIslandOptions(Island currentIsland) {
		
		int curOptionNumber = 1;
		
		logMessage(curOptionNumber + ". Go to the market.");
		curOptionNumber++;
		
		logMessage(curOptionNumber + ". Ask your Bosun Lothar about the state of your " + Player.getShip().getModelName() + ".");
		curOptionNumber++;
		
		logMessage(curOptionNumber + ". Ask your Quartermaster Olard about the transaction ledger.");
		curOptionNumber++;
		
		logMessage(curOptionNumber + ". Inquire about an upgrade for your ship.");
		curOptionNumber++;
		
		HashMap<Integer, Object[]> travelOptions = GameEnvironment.getTravelOptions(curOptionNumber, currentIsland);
		
		//update curOptionNumber to the latest value
		for (int key : travelOptions.keySet()) {
			curOptionNumber = Math.max(curOptionNumber, key) + 1;
		}
		
		presentTravelOptions(travelOptions);
		
		int choice = 0;
		
		while (choice < 1 || choice >= curOptionNumber) {
			try {
				choice = Integer.parseInt(getInput("What do you want to do?", false));
			} catch (NumberFormatException e) {
				logMessage("Please enter a valid number.");
			}
		}
		
		if (choice == 1) {
			goToMarket(currentIsland);
		}

		else if (choice == 2) {
			showShipDetails();
		}
		
		else if (choice == 3) {
			viewLedger();
		}
		
		else if (choice == 4) {
			viewIslandUpgrade();
		}
		
		else if (travelOptions.containsKey(choice)) {
			
			Island chosenIsland = (Island) travelOptions.get(choice)[1];
			Route chosenRoute = (Route) travelOptions.get(choice)[0];
			
			try {
				travelToIsland(chosenIsland, chosenRoute);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				getInput("<<Press enter to continue>>", true);
				presentIslandOptions(currentIsland);
			} catch (InsufficientGoldException e) {
				logMessage(e.getMessage());
				getInput("<<Press enter to continue>>", true);
				presentIslandOptions(currentIsland);
			}
		}
		
	}
	
	/**
	 * Travel to another island. Deducts costs, passes time, handles random event, randomizes stores.
	 * @param destinationIsland the island the player is traveling to
	 * @param chosenRoute the route the player is traveling by
	 */
	public static void travelToIsland(Island destinationIsland, Route chosenRoute) throws IllegalArgumentException, InsufficientGoldException {
		
		int modifiedDuration = GameEnvironment.getModifiedTravelTime(chosenRoute.getDistance());
		int totalCostToTravel = Player.getShip().totalCostToLeaveIsland(modifiedDuration);
		
		//Check validity, pay gold, pass time etc
		GameEnvironment.initiateTravel(destinationIsland, chosenRoute);
		
		boolean eventOccurs = GameEnvironment.doesEventOccur(chosenRoute);
		
		//Show the description of the journey
		logMessage("");
		logMessage(chosenRoute.getDescription());
		logMessage("");
		logMessage(modifiedDuration + " hours have passed.");
		logMessage("You spent " + totalCostToTravel + " " + Constants.NAME_CURRENCY + " on repairs, crew hire and wages.");
		
		if (eventOccurs) {
			logMessage("");
			logMessage("Something happened along the way!");
		}
		
		logMessage("");
		
		getInput("<<Press enter to continue>>", true);
		
		if (eventOccurs) {
			executeEvent();
		}
		
		//Arrive at the island
		arriveAtIsland(destinationIsland);
		
	}
	
	/**
	 * Generates an event, displays it, presents options and outcome.
	 */
	public static void executeEvent() {
		
		RandomEvent event = GameEnvironment.rollRandomEvent();
		
		logMessage(getBanner(event.getName()));
		logMessage("");
		logMessage(event.getDescription());
		logMessage("");
		
		ArrayList<String> options = event.getOptions();
		
		if (options.size() == 0) {
			logMessage(event.getEffect());
			event.doEffect();
		}
		//we need to pick an option
		else {
			
			int optionNumber = 1;
			
			for (String option : options) {
				logMessage(optionNumber++ + ". " + option);
			}
			
			logMessage("");
			
			int choice = 0;
			
			while (choice < 1 || choice > options.size()) {
				try {
					choice = Integer.parseInt(getInput("What do you want to do?", false));
				} catch (NumberFormatException e) {
					logMessage("Please enter a valid number.");
				}
			}
			
			event.chooseOption(choice);
			logMessage(event.getEffect());
			event.doEffect();
		}
		
		getInput("<<Press enter to continue>>", true);
	}
	
	/**
	 * Displays all relevant information about the state of your ship
	 */
	public static void showShipDetails() {
		
		logMessage(GameEnvironment.getShipDetailsString());
		
		logMessage("");
		getInput("<<Press enter to continue>>", true);
		
		arriveAtIsland(GameEnvironment.getCurrentIsland());
	}
	
	/**
	 * Outputs records of previous buy/sell transactions to the console
	 */
	public static void viewLedger() {
		
		logMessage(Constants.OLARD_LEDGER);
		
		logMessage("\nUp to 20 previous transactions:\n");
		
		ArrayList<Transaction> transactions;
		
		try {
			transactions = Ledger.getTransactions(20);
			
			for (Transaction transaction : transactions) {
				logMessage(transaction.toString());
			}
			
		} catch (NullPointerException e) {
			logMessage("There are no transactions yet.");
		}
		
		logMessage("");
		getInput("<<Press enter to continue>>", true);
		
		arriveAtIsland(GameEnvironment.getCurrentIsland());
		
	}
	
	/**
	 * See the unique upgrade for the island, and possibly purchase it
	 */
	public static void viewIslandUpgrade() throws IllegalArgumentException {
		
		String upgrade = "";
		int upgradeCost = Constants.UPGRADE_COST;
		Island curIsland = GameEnvironment.getCurrentIsland();
		
		logMessage(Constants.CRAGNUS_UPGRADE);
		logMessage("");
		
		switch (curIsland.getIslandName()) {
			case (Constants.ISLAND_SALTFORGE):
				upgrade = Constants.UPGRADE_CANNONS;
				logMessage("The dwarven smiths offer to upgrade your cannons for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_TUNIA):
				upgrade = Constants.UPGRADE_HULL;
				logMessage("The Tunian shipyard can reinforce your ship's hull for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SKULLHAVEN):
				upgrade = Constants.UPGRADE_FLAG;
				logMessage("In a dark alley, a shady figure whispers that he can help you to evade attacks from pirates, and offers to sell you a genuine pirate flag for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SANDYFIELDS):
				upgrade = Constants.UPGRADE_CONTRACT;
				logMessage("The Council of Peasants declares that they will sell all goods to you at a reduced price, if you make an upfront investment for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			case (Constants.ISLAND_SEANOMADS):
				upgrade = Constants.UPGRADE_SAILS;
				logMessage("An old woman with leathery hands offers you a painstakingly crafted main sail for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
				break;
			default:
				throw new IllegalArgumentException("Can't buy upgrades at this island");
		}
		
		logMessage("");
		
		if (Player.getShip().getUpgrades().contains(upgrade)) {
			logMessage("You already have this upgrade.");
			logMessage("");
			getInput("<<Press enter to continue>>", true);
		}
		else {
			logMessage("1. Purchase " + upgrade + " for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
			logMessage("2. Return to your ship.");
			logMessage("");
			
			int choice = 0;
			
			while (choice != 1 && choice != 2) {
				try {
					choice = Integer.parseInt(getInput("What do you want to do?", false));
				} catch (NumberFormatException e) {
					logMessage("Please enter a valid number.");
				}
			}
			
			if (choice == 1) {
				
				try {
					
					GameEnvironment.buyIslandUpgrade(upgrade, upgradeCost);
					
					//print successful message
					logMessage("You purchase " + upgrade + " for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".");
					getInput("<<Press enter to continue>>", true);
					
				} catch (InsufficientGoldException e) {
					logMessage(e.getMessage());
					logMessage("");
					getInput("<<Press enter to continue>>", true);
				}
			}
			
		}
		
		arriveAtIsland(curIsland);
		
	}
	
	/**
	 * Calls GameEnvironment arriveAtIsland, and also prints welcome and options to console
	 * @param island the island the player has arrived at
	 */
	private static void arriveAtIsland(Island island) {
		
		GameEnvironment.arriveAtIsland(island);
		
		islandWelcome(island);
		presentIslandOptions(island);
		
	}
	
	/**
	 * Ends the game and prints your stats
	 * @param endGameCode the reason the game ended. 0 for max day reached, 1 for max gold reached, 2 for out of money, 3 for random event
	 */
	public static void endGame(int endGameCode) {
		
		logMessage(getBanner("GAME OVER"));
		logMessage("");
		
		logMessage(GameEnvironment.endgameStats(endGameCode));
		
		logMessage("");
		getInput("<<Press enter to continue>>", true);
		
		GameEnvironment.exitGame();
	}
	
	/**
	 * Closes the main Scanner
	 */
	public static void closeScanner() {
		scanner.close();
	}
	
	/**
	 * If GUIMode is off, this method is called to launch the text based console game
	 */
	public static void startConsoleGame() {
		
		scanner = new Scanner(System.in);
		
		logMessage(getBanner("Welcome to Sailors of the Saltforge"));
		logMessage("Created by Ryan Croucher and Steven Johnson\n");
		
		getInput("<<Press enter to continue>>", true);
		
		createCharacter();
		
		chooseGameDuration();
		
		try {
			chooseShip();
		} catch (IllegalArgumentException e) {
			logMessage("Failed to create Player's ship.");
			logMessage(e.getMessage());
		}
		
		// arrive at the saltforge
		arriveAtIsland(GameEnvironment.getIslands()[0]);
		
	}

}
