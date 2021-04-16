package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Constants;
import main.GameEnvironment;
import main.Inventory;
import main.Island;
import main.Item;
import main.Store;
import main.Transaction;

class TransactionTest {

	@Test
	void toStringTest() {
		
		// Intialize some objects
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "Grains, Fruits and Meats", 1);
		inventory.addItem(testItem, 10);
		ArrayList<Item> imports = new ArrayList<Item>();
		ArrayList<Item> exports = new ArrayList<Item>();
		Store testStore = new Store(inventory, imports, exports);
		
		String testIslandName = "The Salt Forge";
		String testIslandDesc = "Salty dwarven island";
		Island testIsland = new Island(testIslandName, testIslandDesc, testStore);
		
		int quantity = inventory.getItemQuantity(testItem);
		int price = testItem.getBasePrice();
		Transaction testTransaction;
		// Valid tests
		// purchaseString works(Bought)
		testTransaction = new Transaction(quantity, testItem, true, price, testIsland, 0);
		assertEquals(testTransaction.toString(), ("Bought 10 Food for 20 Gold Crowns each (Total: 200) at The Salt Forge, 0 days ago."));
		
		// purchaseString works(Sell)
		testTransaction = new Transaction(quantity, testItem, false, price, testIsland, 0);
		assertEquals(testTransaction.toString(), ("Sold 10 Food for 20 Gold Crowns each (Total: 200) at The Salt Forge, 0 days ago."));

		// dayString works (1 day)
		testTransaction = new Transaction(quantity, testItem, true, price, testIsland, -24);
		assertEquals(testTransaction.toString(), ("Bought 10 Food for 20 Gold Crowns each (Total: 200) at The Salt Forge, 1 day ago."));

		// dayString works (days)
		testTransaction = new Transaction(quantity, testItem, true, price, testIsland, 0);
		assertEquals(testTransaction.toString(), ("Bought 10 Food for 20 Gold Crowns each (Total: 200) at The Salt Forge, 0 days ago."));

		// Test getters
		assertEquals(testTransaction.getIsPurchase(), true);
		assertEquals(testTransaction.getItem(), testItem);
		assertEquals(testTransaction.getLocation(), testIsland);
		assertEquals(testTransaction.getPrice(), 20);
		assertEquals(testTransaction.getQuantity(), 10);
		assertEquals(testTransaction.getTimeOfTransaction(), 0);
		
		
	}

}
