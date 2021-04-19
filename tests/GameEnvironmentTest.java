package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

class GameEnvironmentTest {
	
    @BeforeAll
    static void init() {
    	//sets up islandAndRoutes, items, player gold and store price modifiers
    	GameEnvironment.setupGame();
    }
	
	@Test
	void setupIslandsAndRoutesTest() {
		
		Island[] islands = GameEnvironment.getIslands();
		Route[] routes = GameEnvironment.getRoutes();
		
		//Do we have the correct number of islands?
		assertEquals(islands.length, 5);
		
		//Do we have the correct number of routes?
		assertEquals(routes.length, 6);
		
		//Can every island reach every other island?
		for (Island island : islands) {
			HashSet<Island> reachableIslands = new HashSet<Island>();
			
			for (Route route : routes) {
				for (Island destIsland : route.getPossibleDestinations(island)) {
					reachableIslands.add(destIsland);
				}
			}
			
			assertEquals(reachableIslands.size(), 4);
		}
		
	}
	
	@Test
	void setupItemsTest() {
		
		//do we have 4 items?
		assertEquals(GameEnvironment.getItems().size(), 4);
		
	}
	
	@Test
	void calculateScoreTest() {
		
		//'won' by reaching 10,000 gold
		Player.setGold(10000);
		
		Ship ship = new Ship(ShipModel.BARGE);
		Player.setShip(ship);
		
		//hit gold target but days were close to the limit, score should be lower
		GameEnvironment.setGameDuration(10);
		GameEnvironment.passTime(225);
		assertEquals(GameEnvironment.calculateScore(1), 6);
		GameEnvironment.reverseTime(225);
		
		//hit gold target and heaps of time left
		assertEquals(GameEnvironment.calculateScore(1), 10);
		
		//hit gold target and half time left
		GameEnvironment.passTime(5 * 24);
		assertEquals(GameEnvironment.calculateScore(1), 8);
		GameEnvironment.reverseTime(5 * 24);
		
		
		//'won' by reaching the max duration
		
		//lower gold = lower score
		//gold close to 0
		Player.setGold(0);
		assertEquals(GameEnvironment.calculateScore(0), 5);
		
		Player.setGold(1423);
		assertEquals(GameEnvironment.calculateScore(0), 6);
		
		Player.setGold(8432);
		assertEquals(GameEnvironment.calculateScore(0), 9);
		
		//gold close to threshold
		Player.setGold(9500);
		assertEquals(GameEnvironment.calculateScore(0), 10);
		
		
		//'lost' by running out of money, or killed by an event
		
		//more gold + more days passed = higher score
		//low gold, low days
		Player.setGold(0);
		assertEquals(GameEnvironment.calculateScore(2), 0);
		
		//high gold, low days
		Player.setGold(9000);
		assertEquals(GameEnvironment.calculateScore(2), 3);
		
		//low gold, high days
		Player.setGold(500);
		GameEnvironment.passTime(220);
		assertEquals(GameEnvironment.calculateScore(2), 3);
		
		//high gold, high days
		Player.setGold(9000);
		assertEquals(GameEnvironment.calculateScore(2), 5);
		
		GameEnvironment.reverseTime(220);
		
		//invalid endgame code
		try {
			GameEnvironment.calculateScore(-1);
			fail("Invalid endgameCode should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		} 
		try {
			GameEnvironment.calculateScore(3);
			fail("Invalid endgameCode should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		} 
	}
	
	@Test
	void getTravelOptionsTest() {
		
		Island[] islands = GameEnvironment.getIslands();

		Island saltForge = islands[0];
		Island tunia = islands[1];
		Island sandyFields = islands[2];
		Island skullHaven = islands[3];
		Island seaNomads = islands[4];
		
		Route[] routes = GameEnvironment.getRoutes();
		//routes = new Route[] {tranquilExpanse, basaltSpires, aroundBasaltSpires, shipwreckerShoals, oysterBay, jackalSea};
		Route tranquilExpanse = routes[0];
		Route basaltSpires = routes[1];
		Route aroundBasaltSpires = routes[2];
		Route shipwreckerShoals = routes[3];
		Route oysterBay = routes[4];
		Route jackalSea = routes[5];
		
		//Test all Route, Destination pairs from saltforge
		HashMap<Integer, Object[]> saltForgeTravelOptions = GameEnvironment.getTravelOptions(0, saltForge);
		
		//should be 5 travel options from the saltforge
		assertEquals(saltForgeTravelOptions.values().size(), 5);
		
		ArrayList<Route> containedRoutes = new ArrayList<Route>();
		HashSet<Island> containedIslands = new HashSet<Island>();
		
		for (Object[] routeIslandPair : saltForgeTravelOptions.values()) {
			containedRoutes.add((Route) routeIslandPair[0]);
			containedIslands.add((Island) routeIslandPair[1]);
		}
		
		//saltforge can travel via these routes
		assertTrue(containedRoutes.contains(tranquilExpanse));
		assertTrue(containedRoutes.contains(basaltSpires));
		assertTrue(containedRoutes.contains(aroundBasaltSpires));
		assertTrue(containedRoutes.contains(jackalSea));
		
		//saltforge can't travel via these routes
		assertTrue(!containedRoutes.contains(oysterBay));
		assertTrue(!containedRoutes.contains(shipwreckerShoals));
		
		//saltforge can travel to every island
		assertEquals(containedIslands.size(), 4);
		
		//Test all Route, Destination pairs from tunia
		HashMap<Integer, Object[]> tuniaTravelOptions = GameEnvironment.getTravelOptions(0, tunia);
		
		//should be 5 travel options from the tunia
		assertEquals(tuniaTravelOptions.values().size(), 5);
		
		containedRoutes = new ArrayList<Route>();
		containedIslands = new HashSet<Island>();
		
		for (Object[] routeIslandPair : tuniaTravelOptions.values()) {
			containedRoutes.add((Route) routeIslandPair[0]);
			containedIslands.add((Island) routeIslandPair[1]);
		}
		
		//tunia can travel via these routes
		assertTrue(containedRoutes.contains(basaltSpires));
		assertTrue(containedRoutes.contains(aroundBasaltSpires));
		assertTrue(containedRoutes.contains(jackalSea));
		assertTrue(containedRoutes.contains(oysterBay));
		
		//tunia can't travel via these routes
		assertTrue(!containedRoutes.contains(tranquilExpanse));
		assertTrue(!containedRoutes.contains(shipwreckerShoals));
		
		//tunia can travel to every island
		assertEquals(containedIslands.size(), 4);
		
		//Test all Route, Destination pairs from sandyFields
		HashMap<Integer, Object[]> sandyFieldsTravelOptions = GameEnvironment.getTravelOptions(0, sandyFields);
		
		//should be 5 travel options from the sandyFields
		assertEquals(sandyFieldsTravelOptions.values().size(), 5);
		
		containedRoutes = new ArrayList<Route>();
		containedIslands = new HashSet<Island>();
		
		for (Object[] routeIslandPair : sandyFieldsTravelOptions.values()) {
			containedRoutes.add((Route) routeIslandPair[0]);
			containedIslands.add((Island) routeIslandPair[1]);
		}
		
		//sandyFields can travel via these routes
		assertTrue(containedRoutes.contains(jackalSea));
		assertTrue(containedRoutes.contains(tranquilExpanse));
		assertTrue(containedRoutes.contains(shipwreckerShoals));
		
		//sandyFields can't travel via these routes
		assertTrue(!containedRoutes.contains(oysterBay));
		assertTrue(!containedRoutes.contains(basaltSpires));
		assertTrue(!containedRoutes.contains(aroundBasaltSpires));
		
		//sandyFields can travel to every island
		assertEquals(containedIslands.size(), 4);
		
		//Test all Route, Destination pairs from skullHaven
		HashMap<Integer, Object[]> skullHavenTravelOptions = GameEnvironment.getTravelOptions(0, skullHaven);
		
		//should be 5 travel options from the skullHaven
		assertEquals(skullHavenTravelOptions.values().size(), 5);
		
		containedRoutes = new ArrayList<Route>();
		containedIslands = new HashSet<Island>();
		
		for (Object[] routeIslandPair : skullHavenTravelOptions.values()) {
			containedRoutes.add((Route) routeIslandPair[0]);
			containedIslands.add((Island) routeIslandPair[1]);
		}
		
		//skullHaven can travel via these routes
		assertTrue(containedRoutes.contains(jackalSea));
		assertTrue(containedRoutes.contains(shipwreckerShoals));
		
		//skullHaven can't travel via these routes
		assertTrue(!containedRoutes.contains(tranquilExpanse));
		assertTrue(!containedRoutes.contains(oysterBay));
		assertTrue(!containedRoutes.contains(basaltSpires));
		assertTrue(!containedRoutes.contains(aroundBasaltSpires));
		
		//skullHaven can travel to every island
		assertEquals(containedIslands.size(), 4);
		
		//Test all Route, Destination pairs from seaNomads
		HashMap<Integer, Object[]> seaNomadsTravelOptions = GameEnvironment.getTravelOptions(0, seaNomads);
		
		//should be 5 travel options from the seaNomads
		assertEquals(seaNomadsTravelOptions.values().size(), 4);
		
		containedRoutes = new ArrayList<Route>();
		containedIslands = new HashSet<Island>();
		
		for (Object[] routeIslandPair : seaNomadsTravelOptions.values()) {
			containedRoutes.add((Route) routeIslandPair[0]);
			containedIslands.add((Island) routeIslandPair[1]);
		}
		
		//seaNomads can travel via these routes
		assertTrue(containedRoutes.contains(jackalSea));
		assertTrue(containedRoutes.contains(shipwreckerShoals));
		assertTrue(containedRoutes.contains(oysterBay));
		
		//seaNomads can't travel via these routes
		assertTrue(!containedRoutes.contains(tranquilExpanse));
		assertTrue(!containedRoutes.contains(basaltSpires));
		assertTrue(!containedRoutes.contains(aroundBasaltSpires));
		
		//seaNomads can travel to every island
		assertEquals(containedIslands.size(), 4);
		
	}
	
	@Test
	void buyItemTest() {
		
		//need a ship to store items in
		Player.setShip(new Ship(ShipModel.MERCHANTMAN));
		
		Island island = GameEnvironment.getCurrentIsland();
		Store store = island.getIslandStore();
		
		Item food = GameEnvironment.getItems().get(3);
		Item rawMaterials = GameEnvironment.getItems().get(2);
		
		store.randomizeInventory(10, 20);
		
		//blue sky scenario
		Player.setGold(5000);
		try {
			GameEnvironment.buyItem(food, store, 10);
			
			//check the item arrived in my ship
			assertEquals(Player.getShip().getInventory().getItemQuantity(food), 10);
			
			//check gold was deducted
			assertEquals(Player.getGold(), 5000 - store.getItemPrice(food) * 10);
			
			//check ledger was updated
			Transaction previousTransaction = Ledger.getTransactions(1).get(0);
			
			assertTrue(previousTransaction.getIsPurchase());
			assertEquals(previousTransaction.getItem(), food);
			assertEquals(previousTransaction.getLocation(), island);
			assertEquals(previousTransaction.getPrice(), store.getItemPrice(food));
			assertEquals(previousTransaction.getQuantity(), 10);
			assertEquals(previousTransaction.getTimeOfTransaction(), 0);
			
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientCargoCapacityException | InsufficientItemQuantityException e) {
			fail("Valid test shouldn't throw exception. " + e.getMessage());
		}
		
		store.randomizeInventory(10, 20);
		
		Player.setGold(5000);
		try {
			
			GameEnvironment.buyItem(rawMaterials, store, 8);
			
			//check the item arrived in my ship
			assertEquals(Player.getShip().getInventory().getItemQuantity(rawMaterials), 8);
			
			//check gold was deducted
			assertEquals(Player.getGold(), 5000 - store.getItemPrice(rawMaterials) * 8);
			
			//check ledger was updated
			Transaction previousTransaction = Ledger.getTransactions(1).get(0);
			
			assertTrue(previousTransaction.getIsPurchase());
			assertEquals(previousTransaction.getItem(), rawMaterials);
			assertEquals(previousTransaction.getLocation(), island);
			assertEquals(previousTransaction.getPrice(), store.getItemPrice(rawMaterials));
			assertEquals(previousTransaction.getQuantity(), 8);
			assertEquals(previousTransaction.getTimeOfTransaction(), 0);
			
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientCargoCapacityException | InsufficientItemQuantityException e) {
			fail("Valid test shouldn't throw exception. " + e.getMessage());
		}
		
		
		//invalid purchases
		
		//not enough items available
		Player.setGold(5000);
		store.randomizeInventory(5, 10);
		try {
			
			GameEnvironment.buyItem(food, store, 15);
			fail("Should have thrown InsufficientItemQuantityException");
			
		} catch (InsufficientItemQuantityException e) {
			assert(true);
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientCargoCapacityException e) {
			fail("Wrong exception thrown.");
		}
		
		//boundary value
		Player.setGold(5000);
		store.randomizeInventory(10, 10);
		try {
			
			GameEnvironment.buyItem(food, store, 11);
			fail("Should have thrown InsufficientItemQuantityException");
			
		} catch (InsufficientItemQuantityException e) {
			assert(true);
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientCargoCapacityException e) {
			fail("Wrong exception thrown.");
		}
		
		//not enough gold
		Player.setGold(0);
		
		try {
			GameEnvironment.buyItem(food, store, 1);
			fail("Should have thrown InsufficientGoldException");
		} catch (InsufficientGoldException e) {
			assert(true);
		} catch (IllegalArgumentException | InsufficientCargoCapacityException | InsufficientItemQuantityException e) {
			fail("Wrong exception thrown.");
		}
		
		//not enough cargo space
		Player.setGold(10000);
		store.randomizeInventory(61, 100);
		try {
			GameEnvironment.buyItem(food, store, 60);
			fail("Should have thrown InsufficientCargoCapacityException");
		} catch (InsufficientCargoCapacityException e) {
			assert(true);
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientItemQuantityException e) {
			fail("Wrong exception thrown.");
		}
		
		//invalid item quantity
		try {
			GameEnvironment.buyItem(food, store, 0);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (InsufficientCargoCapacityException | InsufficientGoldException | InsufficientItemQuantityException e) {
			fail("Wrong exception thrown.");
		}
		
	}
	
	@Test
	void sellItemTest() {
		
		//buy some items first
		Player.setShip(new Ship(ShipModel.BARGE));
		
		Island island = GameEnvironment.getCurrentIsland();
		Store store = island.getIslandStore();
		store.randomizeInventory(20, 100);
		
		Item food = GameEnvironment.getItems().get(3);
		Item rawMaterials = GameEnvironment.getItems().get(2);
		
		Player.setGold(2000);
		
		try {
			GameEnvironment.buyItem(food, store, 10);
			GameEnvironment.buyItem(rawMaterials, store, 10);
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientCargoCapacityException | InsufficientItemQuantityException e) {
			fail("shouldn't have thrown exception here");
		}
		
		//blue sky
		try {
			Player.setGold(2000);
			GameEnvironment.sellItem(food, store, 5);
			
			//check items removed from ship
			assertEquals(Player.getShip().getInventory().getItemQuantity(food), 5);
			
			//check gold has been gained
			assertEquals(Player.getGold(), 2000 + store.getItemPrice(food) * 5);
			
		} catch (IllegalArgumentException | InsufficientItemQuantityException e) {
			fail("Shouldn't throw exception on valid test");
		}
		
		//clear out inventory
		try {
			Player.setGold(2000);
			GameEnvironment.sellItem(food, store, 5);
			
			//check items removed from ship
			assertEquals(Player.getShip().getInventory().getItemQuantity(food), 0);
			
			//check gold has been gained
			assertEquals(Player.getGold(), 2000 + store.getItemPrice(food) * 5);
			
		} catch (IllegalArgumentException | InsufficientItemQuantityException e) {
			fail("Shouldn't throw exception on valid test");
		}
		
		
		
		//invalid, sell more than you have	
		try {
			GameEnvironment.sellItem(rawMaterials, store, 11);
			fail("Should throw InsufficientItemQuantityException");
		} catch (InsufficientItemQuantityException e) {
			assert(true);
		} catch (IllegalArgumentException e) {
			fail("Wrong exception thrown");
		}
		
		//invalid item quantity
		try {
			GameEnvironment.sellItem(rawMaterials, store, 0);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (InsufficientItemQuantityException e) {
			fail("Wrong exception thrown");
		}
	
	}

	@Test
	void isValidNameTest() {
		
		ArrayList<String> validNames = new ArrayList<String>();
		
		//Blue sky
		validNames.add("Bruenor");
		validNames.add("Elon Musk");
		validNames.add("abcdef");
		validNames.add("The big dog");
		
		//length (boundary values)
		validNames.add("abc");
		validNames.add("abcdefghijklmno");
		
		//stripped
		validNames.add("    Elon Musk    ");
		

		//valid assertions
		for (String name : validNames) {
			assertTrue(GameEnvironment.isValidName(name));
		}
		
		ArrayList<String> invalidNames = new ArrayList<String>();
		
		//length
		invalidNames.add("");
		invalidNames.add("a");
		invalidNames.add("ab");
		invalidNames.add("abcdefghijklmnopqrstuvwxyz");

		//characters
		invalidNames.add("abc123");
		invalidNames.add("$$!!#");
		
		//spaces
		invalidNames.add("Elon      Musk");

		//invalid assertions
		for (String name : invalidNames) {
			try {
				GameEnvironment.isValidName(name);
				fail("Should have thown an exception");
			} catch (IllegalArgumentException e) {
				assert(true);
			}
		}
		
	}
	
	@Test
	void buyIslandUpgradeTest() {
		
		Player.setShip(new Ship(ShipModel.CUTTER));
		Player.setGold(5000);
		
		//buy every upgrade, make sure it was added to the ship and money deducted
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_CANNONS, 1000);
			assertTrue(Player.getShip().getUpgrades().contains(Constants.UPGRADE_CANNONS));
			assertEquals(Player.getGold(), 4000);
		} catch (IllegalArgumentException | InsufficientGoldException e) {
			fail("Valid test, shouldn't have thrown exception- " + e.getMessage());
		}
		
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_CONTRACT, 1000);
			assertTrue(Player.getShip().getUpgrades().contains(Constants.UPGRADE_CONTRACT));
			assertEquals(Player.getGold(), 3000);
		} catch (IllegalArgumentException | InsufficientGoldException e) {
			fail("Valid test, shouldn't have thrown exception- " + e.getMessage());
		}
		
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_FLAG, 1000);
			assertTrue(Player.getShip().getUpgrades().contains(Constants.UPGRADE_FLAG));
			assertEquals(Player.getGold(), 2000);
		} catch (IllegalArgumentException | InsufficientGoldException e) {
			fail("Valid test, shouldn't have thrown exception- " + e.getMessage());
		}
		
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_HULL, 1000);
			assertTrue(Player.getShip().getUpgrades().contains(Constants.UPGRADE_HULL));
			assertEquals(Player.getGold(), 1000);
		} catch (IllegalArgumentException | InsufficientGoldException e) {
			fail("Valid test, shouldn't have thrown exception- " + e.getMessage());
		}
		
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_SAILS, 1000);
			assertTrue(Player.getShip().getUpgrades().contains(Constants.UPGRADE_SAILS));
			assertEquals(Player.getGold(), 0);
		} catch (IllegalArgumentException | InsufficientGoldException e) {
			fail("Valid test, shouldn't have thrown exception- " + e.getMessage());
		}
		
		
		//Try to buy an upgrade without enough money
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_SAILS, 1000);
			fail("Should throw InsufficientGoldException");
		} catch (InsufficientGoldException e) {
			assert(true);
		} catch (IllegalArgumentException e) {
			fail("Wrong exception thrown");
		}
		
		//Try to buy an upgrade you already have
		Player.setGold(1000);
		try {
			GameEnvironment.buyIslandUpgrade(Constants.UPGRADE_CANNONS, 1000);
			fail("Should throw IllegalArgumentException");
		} catch (InsufficientGoldException e) {
			fail("Wrong exception thrown");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
	}
	
//	@Test
//	void chooseCharacterNameTest() {
//		
//	}
//	
//	@Test
//	void createCharacterTest() {
//		
//	}
	
//	@Test
//	void setGameDurationTest() {
//		
//		String inputData = "";
//		
//		ArrayList<String> validDurations = new ArrayList<String>();
//		//blue sky
//		validDurations.add("22");
//		inputData += "22\n";
//		validDurations.add("30");
//		inputData += "30\n";
//		validDurations.add("45");
//		inputData += "45\n";
//		
//		//boundary values
//		validDurations.add("20");
//		inputData += "20\n";
//		validDurations.add("50");
//		inputData += "50";
//
//		System.setIn(new ByteArrayInputStream(inputData.getBytes()));
//		
//		main.GameEnvironment.setupGame();
//		
//		for (String duration : validDurations) {
//			main.GameEnvironment.consoleChooseGameDuration();
//			
//			assertEquals(main.GameEnvironment.getGameDuration(), Integer.parseInt(duration));
//		}
//		main.GameEnvironment.closeScanner();
//		
//		inputData = "";
//		
//		ArrayList<String> invalidDurations = new ArrayList<String>();
//		//out of range
//		invalidDurations.add("1");
//		inputData += "1\n";
//		inputData += "50\n";
//		invalidDurations.add("-5");
//		inputData += "-5\n";
//		inputData += "50\n";
//		invalidDurations.add("1000");
//		inputData += "1000\n";
//		inputData += "50\n";
//		
//		//boundary values
//		invalidDurations.add("19");
//		inputData += "19\n";
//		inputData += "50\n";
//		invalidDurations.add("51");
//		inputData += "51\n";
//		inputData += "50\n";
//		
//		//wrong format
//		invalidDurations.add("abc");
//		inputData += "abc\n";
//		inputData += "50\n";
//		invalidDurations.add("");
//		inputData += "\n";
//		inputData += "50\n";
//		invalidDurations.add("#");
//		inputData += "#\n";
//		inputData += "50\n";
//		invalidDurations.add("22.2");
//		inputData += "22.2\n";
//		inputData += "50";
//		
//		System.setIn(new ByteArrayInputStream(inputData.getBytes()));
//		main.GameEnvironment.setupGame();
//		
//		for (String duration : invalidDurations) {
//			main.GameEnvironment.consoleChooseGameDuration();
//			
//			//if duration given was valid, then the game duration will NOT be 50
//			//therefore if the duration is 50, the given input must have been invalid
//			assertEquals(main.GameEnvironment.getGameDuration(), 50);
//		}
//		
//		main.GameEnvironment.closeScanner();
//	}

}
