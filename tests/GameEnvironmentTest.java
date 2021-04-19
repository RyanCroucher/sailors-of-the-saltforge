package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameEnvironmentTest {
	
	private static Scanner scanner;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//scanner = new Scanner(System.in);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		//scanner.close();
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
			assertTrue(main.GameEnvironment.isValidName(name));
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
				main.GameEnvironment.isValidName(name);
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
