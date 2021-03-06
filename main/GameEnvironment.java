package main;

import java.util.ArrayList;
import java.util.HashMap;

import events.PirateEvent;
import events.RandomEvent;
import events.RescueEvent;
import events.ShipDamageEvent;
import events.WeatherEvent;
import exceptions.InsufficientCargoCapacityException;
import exceptions.InsufficientGoldException;
import exceptions.InsufficientItemQuantityException;
import main.Constants.ShipModel;
import ui.Console;
import ui.GameOverPanel;
import ui.PanelManager;

/**
 * The GameEnvironment class implements most of the logic flow of the game
 * and maintains the current state of the game.
 * 
 * @author Ryan Croucher rcr69
 * @author Steven Johnson sjo139
 *
 */
public class GameEnvironment {
	
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
	public static Boolean GUIMode = true;
	
	
	
	/**
	 * Validates the given name.
	 * Validates the length, composition and use of spaces in the given name.
	 * @param name The potential name to validate.
	 * @return Whether the given name is valid or not.
	 */
	public static Boolean isValidName(String name) throws IllegalArgumentException {
		Boolean valid = true;
		
		name = name.strip();
		
		if (name.length() < 3 || name.length() > 15) {
			valid = false;
			throw new IllegalArgumentException("Name invalid: choose a name between 3 and 15 characters.");
		}
		
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
				valid = false;
				throw new IllegalArgumentException("Name invalid: please use only english characters.");
			}
		}
		
		for (int i = 1; i < name.length() - 1; i++) {
			if (name.charAt(i) == ' ' && (name.charAt(i-1) == ' ' || name.charAt(i + 1) == ' ')) {
				valid = false;
				throw new IllegalArgumentException("Name invalid: please use only single spaces between words.");
			}
		}
		
		return valid;
	}
	
	/**
	 * Construct all of the item instances to be used in the game.
	 */
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
	 * Creates island instances and uses them to populate routes with island-pairs.
	 */
	private static void setupIslands() {
		
		Item luxuryGoods = items.get(0);
		Item alcohol = items.get(1);
		Item rawMaterials = items.get(2);
		Item food = items.get(3);
		
		Island saltForge = new Island(Constants.ISLAND_SALTFORGE, Constants.ISLAND_SALTFORGE_DESCRIPTION, 
				constructStore(new Item[] {luxuryGoods, alcohol, rawMaterials, food}, 
						new Item[] {alcohol}, 
						new Item[] {rawMaterials}));
		
		Island tunia = new Island(Constants.ISLAND_TUNIA, Constants.ISLAND_TUNIA_DESCRIPTION, 
				constructStore(new Item[] {luxuryGoods, alcohol, rawMaterials, food}, 
						new Item[] {luxuryGoods}, 
						new Item[] {alcohol}));
		
		Island sandyFields = new Island(Constants.ISLAND_SANDYFIELDS, Constants.ISLAND_SANDYFIELDS_DESCRIPTION, 
				constructStore(new Item[] {luxuryGoods, alcohol, rawMaterials, food}, 
						new Item[] {rawMaterials}, 
						new Item[] {food}));
		
		Island skullHaven = new Island(Constants.ISLAND_SKULLHAVEN, Constants.ISLAND_SKULLHAVEN_DESCRIPTION, 
				constructStore(new Item[] {luxuryGoods, alcohol, rawMaterials, food}, 
						new Item[] {alcohol, food}, 
						new Item[] {}));
		
		Island seaNomads = new Island(Constants.ISLAND_SEANOMADS, Constants.ISLAND_SEANOMADS_DESCRIPTION, 
				constructStore(new Item[] {luxuryGoods, alcohol, rawMaterials, food}, 
						new Item[] {rawMaterials, food}, 
						new Item[] {luxuryGoods}));
		
		islands = new Island[] {saltForge, tunia, sandyFields, skullHaven, seaNomads};

	}
	
	/**
	 * Constructs all Route instances to allow travel between islands
	 */
	private static void setupRoutes() {

		Island saltForge = islands[0];
		Island tunia = islands[1];
		Island sandyFields = islands[2];
		Island skullHaven = islands[3];
		Island seaNomads = islands[4];
		
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
		
		Route tranquilExpanse = new Route("The Tranquil Expanse", Constants.ROUTE_TRANQUIL_EXPANSE_DESCRIPTION, 20, 48, tranquilExpansePairList);
		Route basaltSpires = new Route("Through the Basalt Spires", Constants.ROUTE_BASALT_SPIRES_DESCRIPTION, 40, 20, basaltSpiresPairList);
		Route aroundBasaltSpires = new Route("Around the Basalt Spires", Constants.ROUTE_AROUND_BASALT_DESCRIPTION, 20, 32, aroundBasaltPairList);
		Route shipwreckerShoals = new Route("The Shipwrecker Shoals", Constants.ROUTE_SHIPWRECKER_SHOALS_DESCRIPTION, 20, 36, shipwreckerPairList);
		Route oysterBay = new Route("Oyster Bay", Constants.ROUTE_OYSTER_BAY_DESCRIPTION, 20, 24, oysterBayPairList);
		Route jackalSea = new Route("The Sea of Jackals", Constants.ROUTE_JACKAL_SEA_DESCRIPTION, 60, 28, jackalSeaPairList);
		
		routes = new Route[] {tranquilExpanse, basaltSpires, aroundBasaltSpires, shipwreckerShoals, oysterBay, jackalSea};
	}
	
	/**
	 * Constructs a store, to be assigned to an island when the island is initialized
	 * @param items the items that the store will buy and sell
	 * @param importItems the items that the store will pay extra for
	 * @param exportItems the items that the store will sell at a lower price
	 * @return the new store object
	 */
	private static Store constructStore(Item[] items, Item[] importItems, Item[] exportItems) {
		
		Inventory inventory = new Inventory();
		
		for (int i = 0; i < items.length; i++) {
			inventory.addItem(items[i], 1);
		}
		
		ArrayList<Item> imports = new ArrayList<Item>();
		
		for (int i = 0; i < importItems.length; i++) {
			imports.add(importItems[i]);
		}
		
		ArrayList<Item> exports = new ArrayList<Item>();
		
		for (int i = 0; i < exportItems.length; i++) {
			exports.add(exportItems[i]);
		}
		
		Store store = new Store(inventory, imports, exports);
		store.randomizeInventory(0, 100);
		
		return store;
	}
	
	/**
	 * Initializes all of the necessary objects and states to be used in the game.
	 */
	public static void setupGame() {
		
		setupItems();
		setupIslands();
		setupRoutes();
		
		//player starts at the Saltforge
		curIsland = islands[0];
		
		//start with some gold
		Player.setGold(Constants.PLAYER_START_GOLD);
		
	}
	
	/**
	 * Calculates the endgame score based on factors such as gold and days passed
	 * @param endGameCode the reason the game ended. 0 duration reached. 1 gold reached. 2 out of gold. 3 killed by event.
	 * @return
	 */
	public static int calculateScore(int endGameCode) {
		
		if (endGameCode < 0 || endGameCode > 3)
			throw new IllegalArgumentException("Invalid endgame code");
		
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
			score -= (int) (5f * (1f - (((hoursSinceStart / 24f) / gameDuration) + Player.getGold() / 10000f)/2f));
		}
		//reached game duration, more gold is better
		else {
			score -= Math.round((5f * (1f - Player.getGold() / 10000f)));
		}
		
		return score;
		
	}
	
	
	/**
	 * Completes a graceful exit of the program.
	 */
	public static void exitGame() {
		Console.closeScanner();
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
	 * Buys items from a store, into the player ship's cargo
	 * @param item the item to buy
	 * @param store the store the player is buying from
	 * @param quantity the quantity of the item to buy
	 * @throws IllegalArgumentException
	 * @throws InsufficientGoldException
	 * @throws InsufficientCargoCapacityException
	 * @throws InsufficientItemQuantityException
	 */
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
		if (quantity * item.getSize() > availableCapacity)
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
		
	}
	
	/**
	 * Sells items from Player ship cargo to the store
	 * @param item the item type to sell
	 * @param store the store where the player is selling the item
	 * @param quantity the quantity of the item to sell
	 * @throws IllegalArgumentException
	 * @throws InsufficientItemQuantityException
	 */
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
		
	}
	
	/**
	 * Buys an upgrade for the ship, applies effect to one island if contract upgrade chosen.
	 * @param upgrade the upgrade to be purchased
	 * @param upgradeCost the cost of the upgrade
	 * @throws InsufficientGoldException
	 * @throws IllegalArgumentException
	 */
	public static void buyIslandUpgrade(String upgrade, int upgradeCost) throws InsufficientGoldException, IllegalArgumentException {
		
		if (Player.getGold() < upgradeCost)
			throw new InsufficientGoldException("You don't have enough " + Constants.NAME_CURRENCY + " to buy this upgrade.");
		
		Player.getShip().addUpgrade(upgrade);
		
		// if we buy the exclusive contract, give sandy fields all items as imports
		if (upgrade.equals(Constants.UPGRADE_CONTRACT)) {
			Island sandyFields = islands[2];
			sandyFields.getIslandStore().addExport(items.get(0));
			sandyFields.getIslandStore().addExport(items.get(1));
			sandyFields.getIslandStore().addExport(items.get(2));
			sandyFields.getIslandStore().removeImport(items.get(2));
		}
		
		Player.setGold(Player.getGold() - upgradeCost);
		
		//we might have just lost!
		checkEndgameConditions();
		
	}
	
	/**
	 * Calculates whether a random event will occur, based on route properties
	 * @param chosenRoute the route the player is traveling by
	 * @return true if random roll is less than the route risk level, else false
	 */
	public static boolean doesEventOccur(Route chosenRoute) {
		return Math.random() * 100 < chosenRoute.getRiskLevel();
	}
	
	/**
	 * Performs actions necessary when moving between islands.
	 * Deducts gold from player, repair ship and hire crew, pass time and randomize stores.
	 * @param destinationIsland the island the player is traveling to
	 * @param chosenRoute the chosen route the player is traveling by
	 * @throws IllegalArgumentException
	 * @throws InsufficientGoldException
	 */
	public static void initiateTravel(Island destinationIsland, Route chosenRoute) throws IllegalArgumentException, InsufficientGoldException {
		
		if (!chosenRoute.includesIsland(destinationIsland))
			throw new IllegalArgumentException("Can't reach that island by that route!");
		
		int modifiedDuration = getModifiedTravelTime(chosenRoute.getDistance());
		
		int totalCostToTravel = Player.getShip().totalCostToLeaveIsland(modifiedDuration);
				
		if (totalCostToTravel > Player.getGold())
			throw new InsufficientGoldException("Not enough money to leave port!");
		
		Player.setGold(Player.getGold() - totalCostToTravel);
		
		Player.getShip().setHull(Player.getShip().getMaxHull());
		Player.getShip().setCrew(Player.getShip().getMaxCrew());
		
		passTime(modifiedDuration);
		
		//Set the random item quantities and random price modifier for imports and exports
		destinationIsland.getIslandStore().setFactor(1 + Math.random());
		destinationIsland.getIslandStore().randomizeInventory(0, 100);
		
	}
	
	/**
	 * Generates a random event, with random event parameters.
	 * Pirate events have a 40% chance to occur, or less if player has the correct upgrade.
	 * @return the generated Event object
	 */
	public static RandomEvent rollRandomEvent() {
		
		int numEvents = 4;
		
		//random number between 0 and numEvents-1
		int eventType = (int) (Math.random() * numEvents);
		RandomEvent event = null;
		
		float chanceOfPirates = 0.4f;
		if (Player.getShip().getUpgrades().contains(Constants.UPGRADE_FLAG))
			chanceOfPirates = 0.2f;
		
		if (Math.random() <= chanceOfPirates) {
			
			int prizeIfWin = (int) (Math.random() * 500f + 200f);
			
			Ship[] shipOptions = new Ship[] {new Ship(ShipModel.BARGE), new Ship(ShipModel.SLOOP), new Ship(ShipModel.MERCHANTMAN), new Ship(ShipModel.CUTTER)};
			Ship pirateShip = shipOptions[(int) (Math.random() * 4)];
			
			String shipDescription = "\nThe pirate ship appears to be a " + pirateShip.getModelName() + ".";
			
			event = new PirateEvent("Pirate Attack", Constants.EVENT_PIRATE_ATTACK_DESCRIPTION + shipDescription, pirateShip, prizeIfWin);
		}
		
		//if not a pirate event, even chance of any other event
		else if (eventType == 0) {
			int prize = (int) (Math.random() * 75 + 25);
			event = new RescueEvent("Drowning Sailors", Constants.EVENT_RESCUE_DESCRIPTION, prize);
		}
		else if (eventType == 1) {
			int prize = (int) (Math.random() * 100 + 100);
			event = new RescueEvent("Dead in the Water", Constants.EVENT_RESCUE_TWO_DESCRIPTION, prize);
		}
		else if (eventType == 2) {
			
			int hullDamage = (int) (Math.random() * 20 + 5);
			int crewLoss = (int) (Math.random() * 10 + 1);
			int hoursLoss = 12;
			
			event = new WeatherEvent("Sudden Storm", Constants.EVENT_STORM_DESCRIPTION, hullDamage, crewLoss, hoursLoss);
		}
		else if (eventType == 3) {
			
			int hullDamage = (int) (Math.random() * 10 + 1);
			int crewLoss = (int) (Math.random() * 2 + 1);
			
			event = new ShipDamageEvent("Something From Beneath", Constants.EVENT_FROM_BENEATH_DESCRIPTION, hullDamage, crewLoss);
		}
		
		return event;
	}
	
	/**
	 * Increments elapsed game time by given duration. Checks if time elapsed exceeds game duration.
	 * @param duration time passed in hours
	 * @throws IllegalArgumentException
	 */
	public static void passTime(int duration) throws IllegalArgumentException {

		if (duration < 0)
			throw new IllegalArgumentException("Time passed must be at least 0");
		
		hoursSinceStart += duration;
		checkEndgameConditions();
		
	}
	
	/**
	 * Reverse time, but can't reduce hoursSinceStart below 0
	 * @param duration the hours to go backwards in time by
	 */
	public static void reverseTime(int duration) {
		hoursSinceStart = Math.max(0, hoursSinceStart - duration);
	}
	
	/**
	 * Gets the travel duration in hours modified by the speed of your ship.
	 * the duration of time is equal to the distance, reduced by the speed of the ship as a percentage.
	 * @param distance the distance in miles to travel
	 * @return the modified travel time in hours
	 * @throws IllegalArgumentException
	 */
	public static int getModifiedTravelTime(int distance) throws IllegalArgumentException {
		
		if (distance < 0)
			throw new IllegalArgumentException("Distance must be at least 0");
		
		return (int) (distance * (100 - Player.getShip().getSpeed()) / 100);
	}
	
	/**
	 * Checks some endgame conditions and orders the Console or GUI to end the game if one is met.
	 */
	public static void checkEndgameConditions() {
		
		//default, don't end game
		int endGameCode = 10;
		
		if (Player.getKilledByEvent())
			endGameCode = 3;
		
		else if (Player.getGold() > 10000)
			endGameCode = 1;
		
		else if (hoursSinceStart + minHoursToLeaveIsland() >= gameDuration * 24) {
			endGameCode = 0;
		}
		
		else if (Player.getNetWorth() < minCostToLeaveIsland())
			endGameCode = 2;
		
		if (endGameCode != 10) {
			if (GUIMode) {
				GameOverPanel.updateDetails(endGameCode);
				PanelManager.setPanel("GameOverPanel");
			} else {
				Console.endGame(endGameCode);
			}
		}
	}
	
	/**
	 * Generates a string for the end-game screen representing some statistics about the achievements of the player.
	 * @param endGameCode the reason the game ended
	 * @return the generated statistics string
	 */
	public static String endgameStats(int endGameCode) {
		
		String statisticsString = "";
		
		//code 0, reached maximum number of days
		if (endGameCode == 0) {
			statisticsString += "Congratulations, you reached day " + getGameDuration() + " of " + getGameDuration() + ".";
			statisticsString += "\nYou may have survived the rigors of the ocean, but you failed to acquire the gold to save the Saltforge.";
		}
		
		//code 1, acquired 10,000 gold to save the Salt Forge
		else if (endGameCode == 1) {
			statisticsString += "You have acquired the necessary " + Constants.NAME_CURRENCY + " to save the Saltforge from ruin!";
			statisticsString += "\nOld Saltbeard shakes your hand with salty tears in his eyes, and declares you a hero of the dwarven people.";
		}
		
		//code 2, couldn't afford to travel anywhere
		else if (endGameCode == 2) {
			statisticsString += "You have failed to manage your finances, and cannot afford to leave port.";
			statisticsString += "\nYour crew quickly abandon you, and you're forced to beg passage back to the Saltforge and live in shame.";
		}
		
		//code 3, killed by a random event
		else if (endGameCode == 3) {
			statisticsString += "\nThe trials of the open sea have proven too much for you, something happened out there, and you were never seen again.";
		}
		
		statisticsString += "\n\n";
		
		statisticsString += "Name: " + Player.getName();
		statisticsString += "\nGame Duration: " + getGameDuration();
		statisticsString += "\nActual Duration: " + Math.min((getHoursSinceStart() / 24), getGameDuration());
		
		statisticsString += "\n\n";
		
		int totalTransactions = 0;
		if (Ledger.getTransactions() != null)
			totalTransactions = Ledger.getTransactions().size();
		
		statisticsString += "\nTotal number of buy/sell transactions: " + totalTransactions;
		statisticsString += "\nNumber of upgrades purchased: " + Player.getShip().getUpgrades().size();
		statisticsString += "\nProfit: " + (Player.getGold() - Constants.PLAYER_START_GOLD) + " (Current gold - starting gold)";
		
		statisticsString += "\n\n";
		
		int score = calculateScore(endGameCode);
		
		statisticsString += "\nYour final merchant score is: " + score + "/" + 10;
		if (score == 0)
			statisticsString += "\nWow, that's terrible!";
		else if (score < 5)
			statisticsString += "\nAt least you tried!";
		else if (score < 7)
			statisticsString += "\nGetting somewhere!";
		else statisticsString += "\nNailed it!";
		
		return statisticsString;
	}
	
	/**
	 * Set current location of the player to another island and check endgame conditions
	 * @param island the island that the player arrives at
	 */
	public static void arriveAtIsland(Island island) {

		curIsland = island;
		
		//if we hit an endgame condition, end the game
		GameEnvironment.checkEndgameConditions();

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
	
	/**
	 * 
	 * @param island the island to set the current location of the player to
	 */
	public static void setCurrentIsland(Island island) {
		curIsland = island;
	}
	
	/**
	 * @return the list of all islands in the game
	 */
	public static Island[] getIslands() {
		return islands;
	}
	
	/**
	 * 
	 * @return the list of all routes in the game
	 */
	public static Route[] getRoutes() {
		return routes;
	}
	
	/**
	 * 
	 * @return the list of all items in the game
	 */
	public static ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Calculates the cheapest cost to leave the current island (via any route)
	 * @return the min cost to leave the current island
	 */
	public static int minCostToLeaveIsland() {
		
		int cost = 10000;
		
		for (Route route : routes) {
			if (route.includesIsland(curIsland)) {
				cost = Math.min(cost, Player.getShip().totalCostToLeaveIsland(getModifiedTravelTime(route.getDistance())));
			}
		}
		
		return cost;
		
	}
	
	/**
	 * Calculates the minimum time to any destination from current island
	 * @return the time in hours
	 */
	public static int minHoursToLeaveIsland() {
		
		int hours = 10000;
		
		for (Route route : routes) {
			if (route.includesIsland(curIsland)) {
				hours = Math.min(hours, getModifiedTravelTime(route.getDistance()));
			}
		}
		
		return hours;
		
	}
	
	/**
	 * Builds a string representing all imports at a location
	 * @param store an island store
	 * @return a string of comma-separated imports
	 */
	public static String getImportsString(Store store) {
		
		String importString = "";
		
		ArrayList<Item> imports = store.getImports();
		ArrayList<String> importStrings = new ArrayList<String>();
		
		for (Item importItem : imports) {
			importStrings.add(importItem.getName());
		}
		
		importString += String.join(", ", importStrings);
		
		return importString;
		
	}
	
	/**
	 * Builds a string representing all exports from a location
	 * @param store an island store
	 * @return a string of comma-separated exports
	 */
	public static String getExportsString(Store store) {
		
		String exportString = "";
		
		ArrayList<Item> exports = store.getExports();
		ArrayList<String> exportStrings = new ArrayList<String>();
		
		for (Item exportItem : exports) {
			exportStrings.add(exportItem.getName());
		}
		
		exportString += String.join(", ", exportStrings);
		
		return exportString;
		
	}
	
	/**
	 * Builds a string to represent the current state of the player's ship
	 * @return a multiline string representing the state of the player's ship
	 */
	public static String getShipDetailsString() {
		
		String shipDetailsString = Constants.LOTHAR_MUMBLES;
		
		Ship ship = Player.getShip();
		
		shipDetailsString += "\n\nYou are Captain " + Player.getName() + ", a dwarven merchant from the Salt Forge.";
		shipDetailsString += "\n\nYour ship is a " + ship.getModelName() + ".";
		
		String refillCrewString = "You do not need to hire more crew.";
		if (ship.getCrew() < ship.getMaxCrew()) {
			int costToRefill = ship.getRefillCrewCost(GameEnvironment.getCurrentIsland().getIslandName() == Constants.ISLAND_SKULLHAVEN);
			refillCrewString = "It will cost you " + costToRefill + " " + Constants.NAME_CURRENCY + " to hire more crew.";
		}
		
		shipDetailsString += "\n\nYour ship has " + ship.getCrew() + " crew out of a maximum of " + ship.getMaxCrew() + ". " + refillCrewString;
		
		shipDetailsString += "\n\nYou must pay your crew a total wage of " + ship.getWageCost(24) + " " + Constants.NAME_CURRENCY + " per day.";
		shipDetailsString += "\n\nThe " + ship.getModelName() + " is carrying " + ship.getCargo() + " items out of a maximum of " + ship.getCargoCapacity() + ".";
		
		String upgradeString = "You have not yet purchased any upgrades for your ship.";
		if (ship.getUpgrades().size() > 0)
			upgradeString = "You have wisely purchased these upgrades: " + String.join(", ", ship.getUpgrades());
		
		shipDetailsString += "\n\n" + upgradeString;
		
		String hullRepairString = "Your hull has not sustained any damage.";
		if (ship.getHull() < ship.getMaxHull()) {
			int costToRepair = ship.getRepairCost(GameEnvironment.getCurrentIsland().getIslandName() == Constants.ISLAND_SKULLHAVEN);
			hullRepairString = "Your ship has taken damage and is at " + ship.getHull() + " out of a maximum of " + ship.getMaxHull() + " hull points. It will cost " + costToRepair + " " + Constants.NAME_CURRENCY + " to repair.";
		}
		
		shipDetailsString += "\n\n" + hullRepairString;
		
		return shipDetailsString;
		
	}
	
	
	/**
	 * Initializes the game.
	 * @param args Give 'Console' argument to run as console game rather than GUI
	 */
	public static void main(String[] args) {
		
		setupGame();
		
		if (args.length > 0 && args[0].equals("Console")) {
			GUIMode = false;
			Console.startConsoleGame();
		} else {
			GUIMode = true;
			PanelManager.startGUIGame();
			Sound.playSound("Pirates.wav", true);
		}
		
		
	}

}
