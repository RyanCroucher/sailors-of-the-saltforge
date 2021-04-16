package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Inventory;
import main.Item;

class InventoryTest {

	@Test
	void getItemQuantityTest() {
		// Valid test
		Inventory inventory1 = new Inventory();
		Item testItem = new Item("Food", 20, "", 1);
		inventory1.addItem(testItem, 5);
		
		assertEquals(inventory1.getItemQuantity(testItem), 5);
		
		// Valid test
		Inventory inventory2 = new Inventory();
		Item testItem2 = new Item("Food", 20, "", 1);
		inventory2.addItem(testItem2, 10000);
		
		assertEquals(inventory2.getItemQuantity(testItem2), 10000);
		
		// Empty inventory test
		Inventory emptyInventory = new Inventory();
		Item testItem3 = new Item("Food", 20, "", 1);
		
		assertEquals(emptyInventory.getItemQuantity(testItem3), 0);
		
		// Find quantity of an item not in inventory
		Inventory missingInventory = new Inventory();
		Item testItem4 = new Item("Raw Materials", 40, "", 1);
		inventory2.addItem(testItem4, 2);
		
		assertEquals(missingInventory.getItemQuantity(testItem3), 0);
	}
		

	@Test
	void setItemQuantityTest() {
		
		// Valid test, Item exists and quantity to set >= 0  
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "", 1);
		inventory.addItem(testItem, 5);
		inventory.setItemQuantity(testItem, 10);
		
		assertEquals(inventory.getItemQuantity(testItem), 10);
		
		// Invalid quantity
		try {
			Inventory inventory2 = new Inventory();
			Item testItem2 = new Item("Food", 20, "", 1);
			inventory2.addItem(testItem2, 10);
			inventory2.setItemQuantity(testItem2, -5);
			fail("Can't set quantity < 0, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		// Item not in inventory
		try {
			Inventory inventory3 = new Inventory();
			Item testItem3 = new Item("Food", 20, "", 1);
			inventory3.setItemQuantity(testItem3, 10);
			fail("Item not in inventory, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
	}
		

	@Test
	void addItemTest() {
		
		// Valid use test
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "", 1);
		inventory.addItem(testItem, 5);
		inventory.addItem(testItem, 10);
		inventory.addItem(testItem, 5);
		inventory.addItem(testItem, 15);
				
		assertEquals(inventory.getItemQuantity(testItem), 35);
		
		// Valid add multiple items to inventory
		Inventory inventory2 = new Inventory();
		Item testItem2 = new Item("Food", 20, "", 1);
		inventory2.addItem(testItem2, 10);
		
		assertEquals(inventory2.getItemQuantity(testItem2), 10);
		
		Item testItem3 = new Item("Alcohol", 40, "", 1);
		inventory2.addItem(testItem3, 15);
		
		assertEquals(inventory2.getItemQuantity(testItem2), 10);
		assertEquals(inventory2.getItemQuantity(testItem3), 15);
				
		
		// Invalid quantity
		try {
			Inventory inventory3 = new Inventory();
			Item testItem4 = new Item("Food", 20, "", 1);
			inventory3.addItem(testItem4, 0);
			fail("Can't set quantity < 1, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		
	}

	@Test
	void removeItemTest() {
		
		// Valid use test
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "", 1);
		inventory.addItem(testItem, 5);
		inventory.addItem(testItem, 10);
		inventory.addItem(testItem, 5);
		inventory.removeItem(testItem, 7);
				
		assertEquals(inventory.getItemQuantity(testItem), 13);
	
		// Test to remove something you don't have
		try {
			Inventory inventory2 = new Inventory();
			Item testItem2 = new Item("Food", 20, "", 1);
			inventory2.removeItem(testItem2, 10);
			fail("Can't remove item not in inventory, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		// Test to remove more of an item than you have
		try {
			Inventory inventory3 = new Inventory();
			Item testItem3 = new Item("Food", 20, "", 1);
			inventory3.addItem(testItem3, 10);
			inventory3.removeItem(testItem3, 20);
			fail("Can't remove more than you have, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		// Test to remove an item with quantity zero
		try {
			Inventory inventory4 = new Inventory();
			Item testItem4 = new Item("Food", 20, "", 1);
			inventory4.addItem(testItem4, 10);
			inventory4.removeItem(testItem4, 0);
			fail("Can't remove < 1 item, should have thrown exception");
		} catch(IllegalArgumentException e) {
			assert(true);
		}
		
		
		
	}

	@Test
	void getInventoryItemsTest() {
		
		// Valid use test
		Inventory inventory = new Inventory();
		Item testItem = new Item("Food", 20, "", 1);
		Item testItem2 = new Item("Raw Materials", 20, "", 1);
		Item testItem3 = new Item("Alcohol", 20, "", 1);
		Item testItem4 = new Item("Luxury Goods", 20, "", 1);
		inventory.addItem(testItem, 10);
		inventory.addItem(testItem2, 10);
		inventory.addItem(testItem3, 10);
		inventory.addItem(testItem4, 10);
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(testItem);
		items.add(testItem2);
		items.add(testItem3);
		items.add(testItem4);
		items.sort((item1, item2) -> item1.getName().compareTo(item2.getName()));
		ArrayList<Item> inventoryItems = inventory.getInventoryItems();
		inventoryItems.sort((item1, item2) -> item1.getName().compareTo(item2.getName()));
				
		assertEquals(inventoryItems, items);
		
		//Empty inventory
		Inventory inventory2 = new Inventory();
		ArrayList<Item> items2 = new ArrayList<Item>();
		assertEquals(inventory2.getInventoryItems(), items2);		
		
		
	}

}
