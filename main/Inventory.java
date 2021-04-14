package main;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class to create inventories, they are handled by a HashMap
 * @author Steven Johnson sjo139
 * 
 */
public class Inventory {

	private HashMap<Item, Integer> inventory;
	
	public Inventory() {
		inventory = new HashMap<Item, Integer>();
	}
	
	/**
	 * Get the quantity of an item given that it is in the HashMap
	 * @param item The item we want to retrieve the quantity of
	 * @return The quantity of the given item
	 */
	public int getItemQuantity(Item item) {
		try {
			int quantity = inventory.get(item);
			return quantity;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	/**
	 * Set the quantity of an item
	 * @param item The item to set, given it exists
	 * @param quantity The quantity to set, must be > 0
	 */
	public void setItemQuantity(Item item, int quantity) throws IllegalArgumentException {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity should be a minimum of 0");
		}
		
		else if (!inventory.containsKey(item)) {
			throw new IllegalArgumentException("Item not in inventory");
		}
		else {
			inventory.put(item, quantity);
		}
	}
	
	/**
	 * Add quantity of an item into inventory 
	 * @param item Item to be added
	 * @param quantity Quantity to be added, must be >=1
	 */
	public void addItem(Item item, int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException("Quantity should be a minimum of 1");
		}
		else if (!inventory.containsKey(item)) {
			inventory.put(item, quantity);
		}
		else {
			inventory.put(item, inventory.get(item) + quantity);
		}
	}
	
	/**
	 * Remove quantity of an item from inventory
	 * @param item Item to remove quantity from. Must exist.
	 * @param quantity Quantity to remove. Must be >=1 and cannot be greater than current inventory amount.
	 */
	public void removeItem(Item item, int quantity) {
		if (!inventory.containsKey(item)) {
			throw new IllegalArgumentException("Trying to remove item not in inventory");
		}
		else if (quantity < 1) {
			throw new IllegalArgumentException("Removal quantity should be a minimum of 1");
		}
		else if (quantity > inventory.get(item)) {
			throw new IllegalArgumentException("Cannot remove more items than in inventory");
		}
		else {
			inventory.put(item, inventory.get(item) - quantity);
		}
		
	}
	
	/**
	 * Returns an ArrayList of all items contained in inventory.
	 * @return ArrayList of items in inventory
	 */
	public ArrayList<Item> getInventoryItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.addAll(inventory.keySet());
		return items;
	} 
	
	
}
