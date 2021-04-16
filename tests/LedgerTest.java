package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Inventory;
import main.Island;
import main.Item;
import main.Ledger;
import main.Store;
import main.Transaction;

class LedgerTest {
	
	@BeforeEach
	void setup() throws Exception {
		Ledger.addTransaction(1, null, true, 0, null, 0);
		Ledger.getTransactions().clear();
	}

	@Test
	void testAddTransaction() {
		
		// Initialize needed objects
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "Grains, Fruits and Meats");
		inventory.addItem(testItem, 10);
		ArrayList<Item> imports = new ArrayList<Item>();
		ArrayList<Item> exports = new ArrayList<Item>();
		Store testStore = new Store(inventory, imports, exports);
		
		String testIslandName = "The Salt Forge";
		String testIslandDesc = "Salty dwarven island";
		Island testIsland = new Island(testIslandName, testIslandDesc, testStore);

		//Valid tests
		assertEquals(Ledger.getTransactions().size(), 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 0);
		assertEquals(Ledger.getTransactions().size(), 1);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, false, testItem.getBasePrice(), testIsland, 0);
		assertEquals(Ledger.getTransactions().size(), 2);
		
		// Invalid Quantity
		try {
			Ledger.addTransaction(-5, testItem, true, testItem.getBasePrice(), testIsland, 0);
			fail("Quantity must be >= 1, should throw exception.");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		// Invalid Price
		try {
			Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, -5, testIsland, 0);
			fail("Price must be >= 0, should throw exception.");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		// Invalid Time
		try {
			Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 5);
			fail("timeOfTransaction must be > GameEnvironment.getHoursSinceStart(), should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
	}


	@Test
	void testGetTransactions() {

		// Initialize needed objects
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "Grains, Fruits and Meats");
		inventory.addItem(testItem, 10);
		ArrayList<Item> imports = new ArrayList<Item>();
		ArrayList<Item> exports = new ArrayList<Item>();
		Store testStore = new Store(inventory, imports, exports);
		
		String testIslandName = "The Salt Forge";
		String testIslandDesc = "Salty dwarven island";
		Island testIsland = new Island(testIslandName, testIslandDesc, testStore);
		
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, false, testItem.getBasePrice(), testIsland, 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, false, testItem.getBasePrice(), testIsland, 0);
		Ledger.addTransaction(inventory.getItemQuantity(testItem), testItem, true, testItem.getBasePrice(), testIsland, 0);
		
		
		
		assertEquals(Ledger.getTransactions(3).size(), 3);
		assertEquals(Ledger.getTransactions(0).size(), 0);
		assertEquals(Ledger.getTransactions(12).size(), 6);
		
		
	}

}
