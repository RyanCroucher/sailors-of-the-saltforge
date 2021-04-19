package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Player;
import main.Route;
import main.Ship;
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
