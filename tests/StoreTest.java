package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Inventory;
import main.Item;
import main.Store;

class StoreTest {

	@Test
	void testStoreConstruction() {
	
		// Valid construction of a store
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		Item testItem2 = new Item("Food", 20, "");
		Item testItem3 = new Item("Alcohol", 50, "");
		inventory.addItem(testItem1, 10);
		inventory.addItem(testItem2, 5);
		inventory.addItem(testItem3, 12);
				
		ArrayList<Item> imports = new ArrayList<Item>();
		imports.add(testItem3);
		ArrayList<Item> exports = new ArrayList<Item>();
		exports.add(testItem2);
		
		Store testStore = new Store(inventory, imports, exports);
		
		ArrayList<Item> storeItems = testStore.getInventoryItems();
		storeItems.sort((item1, item2) -> item1.getName().compareTo(item2.getName()));
		
		ArrayList<Item> testItemArray = new ArrayList<Item>();
		testItemArray.add(testItem1);
		testItemArray.add(testItem2);
		testItemArray.add(testItem3);
		testItemArray.sort((item1, item2) -> item1.getName().compareTo(item2.getName()));
		
		assertEquals(storeItems, testItemArray);
		assertEquals(testStore.getImports(), imports);
		assertEquals(testStore.getExports(), exports);
		
		//Empty Inventory test
		try {
			
			Inventory emptyInventory = new Inventory();
			Item emptyTestItem1 = new Item("Raw Materials", 40, "");
			Item emptyTestItem2 = new Item("Food", 20, "");
			ArrayList<Item> emptyTestImports = new ArrayList<Item>();
			emptyTestImports.add(emptyTestItem1);
			ArrayList<Item> emptyTestExports = new ArrayList<Item>();
			emptyTestExports.add(emptyTestItem2);
			Store emptyStore = new Store(emptyInventory, emptyTestImports, emptyTestExports);
			
			fail("Inventory can't be empty, exception should have been thrown");
			
		} catch (IllegalArgumentException e) {
			
			assert(true);
			
		}
	}

	@Test
	void testBuyItem() {
		
		// Valid buying test
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		Item testItem2 = new Item("Food", 20, "");
		Item testItem3 = new Item("Alcohol", 50, "");
		inventory.addItem(testItem1, 10);
		inventory.addItem(testItem2, 5);
		inventory.addItem(testItem3, 12);
		
		ArrayList<Item> imports = new ArrayList<Item>();
		imports.add(testItem3);
		ArrayList<Item> exports = new ArrayList<Item>();
		exports.add(testItem2);
		
		Store testStore;
		testStore = new Store(inventory, imports, exports);
		testStore.buyItem(testItem1, 2);
		assertEquals(testStore.getItemQuantity(testItem1), 8);
		testStore.buyItem(testItem1, 2);
		testStore.buyItem(testItem2, 5);
		testStore.buyItem(testItem3, 3);
		assertEquals(testStore.getItemQuantity(testItem1), 6);
		assertEquals(testStore.getItemQuantity(testItem2), 0);
		assertEquals(testStore.getItemQuantity(testItem3), 9);
		
		// Quantity too low test
		try {
			testStore = new Store(inventory, imports, exports);
			testStore.buyItem(testItem1, 0);
			fail("Cannot buy 0 or less items, exception should have been thrown");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		try {
			testStore = new Store(inventory, imports, exports);
			testStore.buyItem(testItem1, -10);
			fail("Cannot buy 0 or less items, exception should have been thrown");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		// Store quantity not high enough
		try {
			testStore = new Store(inventory, imports, exports);
			testStore.buyItem(testItem1, 20);
			fail("Store quantity not high enough, exception should have been thrown");
		} catch(IllegalArgumentException e) {
			assert(true);
		}

	}

	@Test
	void testSellItem() {
		
		// Valid selling test
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		Item testItem2 = new Item("Food", 20, "");
		Item testItem3 = new Item("Alcohol", 50, "");
		inventory.addItem(testItem1, 10);
		inventory.addItem(testItem2, 5);
		inventory.addItem(testItem3, 12);
		
		ArrayList<Item> imports = new ArrayList<Item>();
		imports.add(testItem3);
		ArrayList<Item> exports = new ArrayList<Item>();
		exports.add(testItem2);
		
		Store testStore;
		testStore = new Store(inventory, imports, exports);
		testStore.sellItem(testItem1, 2);
		assertEquals(testStore.getItemQuantity(testItem1), 12);
		testStore.sellItem(testItem1, 2);
		testStore.sellItem(testItem2, 5);
		testStore.sellItem(testItem3, 3);
		assertEquals(testStore.getItemQuantity(testItem1), 14);
		assertEquals(testStore.getItemQuantity(testItem2), 10);
		assertEquals(testStore.getItemQuantity(testItem3), 15);
		
		// Quantity too low test
		try {
			testStore = new Store(inventory, imports, exports);
			testStore.sellItem(testItem1, 0);
			fail("Cannot buy 0 or less items, exception should have been thrown");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		try {
			testStore = new Store(inventory, imports, exports);
			testStore.sellItem(testItem1, -10);
			fail("Cannot buy 0 or less items, exception should have been thrown");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
	}

	@Test
	void testGetItemPrice() {
		
		// Valid Get Item Price
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		Item testItem2 = new Item("Food", 20, "");
		Item testItem3 = new Item("Alcohol", 50, "");
		inventory.addItem(testItem1, 10);
		inventory.addItem(testItem2, 5);
		inventory.addItem(testItem3, 12);
		
		ArrayList<Item> imports = new ArrayList<Item>();
		imports.add(testItem3);
		ArrayList<Item> exports = new ArrayList<Item>();
		exports.add(testItem2);
		
		Store testStore;
		
		testStore = new Store(inventory, imports, exports);
		assertEquals(testStore.getItemPrice(testItem1), testItem1.getBasePrice());
		assertEquals(testStore.getItemPrice(testItem2), testItem2.getBasePrice());
		
		// Test for if item is an Import
		testStore = new Store(inventory, imports, exports);
		testStore.setFactor(.5);
		assertEquals(testStore.getItemPrice(testItem3), (int) (testItem3.getBasePrice() * testStore.getFactor()));
		testStore.setFactor(1.123);
		assertEquals(testStore.getItemPrice(testItem3), (int) (testItem3.getBasePrice() * testStore.getFactor()));
		testStore.setFactor(1.5);
		assertEquals(testStore.getItemPrice(testItem3), (int) (testItem3.getBasePrice() * testStore.getFactor()));
		testStore.setFactor(100);
		assertEquals(testStore.getItemPrice(testItem3), (int) (testItem3.getBasePrice() * testStore.getFactor()));
		
		// Test for if item is an Export
		testStore = new Store(inventory, imports, exports);
		testStore.setFactor(.5);
		assertEquals(testStore.getItemPrice(testItem2), (int) (testItem2.getBasePrice() / testStore.getFactor()));
		testStore.setFactor(1.123);
		assertEquals(testStore.getItemPrice(testItem2), (int) (testItem2.getBasePrice() / testStore.getFactor()));
		testStore.setFactor(1.5);
		assertEquals(testStore.getItemPrice(testItem2), (int) (testItem2.getBasePrice() / testStore.getFactor()));
		testStore.setFactor(100);
		assertEquals(testStore.getItemPrice(testItem2), (int) (testItem2.getBasePrice() / testStore.getFactor()));
		
	}

	@Test
	void testRandomizeInventory() {
		
		// Valid Randomization test
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		Item testItem2 = new Item("Food", 20, "");
		Item testItem3 = new Item("Alcohol", 50, "");
		inventory.addItem(testItem1, 10);
		inventory.addItem(testItem2, 5);
		inventory.addItem(testItem3, 12);
		
		ArrayList<Item> imports = new ArrayList<Item>();
		imports.add(testItem3);
		ArrayList<Item> exports = new ArrayList<Item>();
		exports.add(testItem2);

		Store testStore;
		int max = 20;
		int min = 10;
		testStore = new Store(inventory, imports, exports);
		
		// Loop many times to try catch randomization errors
		int loops = 10000;
		
		for (int i = 0; i < loops; i++) {
			testStore.randomizeInventory(min, max);
			assertTrue(testStore.getItemQuantity(testItem1) <= max, "Inventory is too high");
			assertTrue(testStore.getItemQuantity(testItem1) >= min, "Inventory is too low");
		}

		max = 100;
		min = 0;
		testStore = new Store(inventory, imports, exports);
		for (int i = 0; i < loops; i++) {
			testStore.randomizeInventory(min, max);
			assertTrue(testStore.getItemQuantity(testItem1) <= max, "Inventory is too high");
			assertTrue(testStore.getItemQuantity(testItem1) >= min, "Inventory is too low");
		}
		
		// Invalid min
		max = 50;
		min = -50;
		testStore = new Store(inventory, imports, exports);
		try {
			testStore.randomizeInventory(min, max);
			fail("Min cannot be below zero, should throw exception");
		} catch (IllegalArgumentException e){
			assert(true);
		}
		
		// Max less than min
		max = 25;
		min = 50;
		testStore = new Store(inventory, imports, exports);
		try {
			testStore.randomizeInventory(min, max);
			fail("Min must be less than Max, should throw exception");
		} catch (IllegalArgumentException e){
			assert(true);
		}

	}
	
	@Test
	void testSetFactor() {
		
		// Valid Set Factor
		Inventory inventory = new Inventory();
		Item testItem1 = new Item("Raw Materials", 40, "");
		inventory.addItem(testItem1, 10);

		ArrayList<Item> imports = new ArrayList<Item>();
		ArrayList<Item> exports = new ArrayList<Item>();
		
		Store testStore;
		
		testStore = new Store(inventory, imports, exports);
		testStore.setFactor(.5);
		assertEquals(testStore.getFactor(), .5);
		
		//invalid factor
		try {
			testStore.setFactor(-2);
			fail("Factor cannot be less than 0, Should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
	}

}
