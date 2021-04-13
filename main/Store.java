package main;

import java.util.ArrayList;

/**
 * Class to manage buy/sell transactions at an island
 * @author Ryan Croucher rcr69
 *
 */
public class Store {
	
	/**
	 * Each store has an inventory object which stores items
	 */
	private Inventory inventory;
	
	/**
	 * A list of the items that will be bought/sold at higher price than the base price
	 */
	private ArrayList<Item> imports;
	
	/**
	 * A list of the items that will be bought/sold at lower price than the base price
	 */
	private ArrayList<Item> exports;
	
	/**
	 * Creates a new store object
	 * @param inventory an object storing items + quantities, represents goods offered at the store
	 * @param imports a list of items that the island trades at a higher price
	 * @param exports a list of items that the island trades at a lower price
	 */
	public Store(Inventory inventory, ArrayList<Item> imports, ArrayList<Item> exports) {
		this.inventory = inventory;
		this.imports = imports;
		this.exports = exports;
	}
	
	/**
	 * Removes given number of given item from the store
	 * @param item the item to reduce quantity of
	 * @param quantity the quantity to reduce the item by
	 * @throws IllegalArgumentException
	 */
	public void buyItem(Item item, int quantity) throws IllegalArgumentException {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		} else if (inventory.getItemQuantity(item) < quantity) {
			throw new IllegalArgumentException("Store does not have enough quantity of this item");
		} else {
			inventory.removeItem(item, quantity);
		}
	}
	
	/**
	 * Adds given number of item to the store
	 * @param item item to increase quantity of
	 * @param quantity to increase the item quantity by in the inventory
	 * @throws IllegalArgumentException
	 */
	public void sellItem(Item item, int quantity) throws IllegalArgumentException {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		else {
			inventory.addItem(Item item, int quantity);
		}
	}
	
	/**
	 * Calculate the price of an item depending on whether it's a main import/export or not
	 * @param item the item to calculate the modified price of
	 * @return the modified price of the itme
	 */
	public int getItemPrice(Item item) {
		if (imports.contains(item)) {
			return item.getBasePrice() * 2;
		} else if (exports.contains(item)) {
			return item.getBasePrice() / 2;
		} else {
			return item.getBasePrice();
		}
	}
	
	/**
	 * Sets every item in the inventory to a random value between given min and max
	 * @param minQuantity the minimum new quantity of the item in the inventory
	 * @param maxQuantity the maximum new quantity of the item in the inventory
	 */
	public void randomizeInventory(int minQuantity, int maxQuantity) {
		for (Item item : inventory.getInventoryItems()) {
			inventory.setItemQuantity(item, (int) (Math.random() * (maxQuantity - minQuantity) + minQuantity));
		}
	}

}
