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
	 * The randomised price factor that applies to transactions at this store
	 */
	private double factor;
	
	/**
	 * Creates a new store object
	 * @param inventory an object storing items + quantities, represents goods offered at the store
	 * @param imports a list of items that the island trades at a higher price
	 * @param exports a list of items that the island trades at a lower price
	 */
	public Store(Inventory inventory, ArrayList<Item> imports, ArrayList<Item> exports) throws IllegalArgumentException {
		
		if (inventory.getInventoryItems().size() == 0)
			throw new IllegalArgumentException("Inventory cannot be empty");
		
		this.inventory = inventory;
		this.imports = imports;
		this.exports = exports;
		setFactor(1f);
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
			inventory.addItem(item, quantity);
		}
	}
	
	/**
	 * Calculate the price of an item depending on whether it's a main import/export or not
	 * @param item the item to calculate the modified price of
	 * @return the modified price of the item
	 */
	public int getItemPrice(Item item) {
		if (imports.contains(item)) {
			return (int) (item.getBasePrice() * factor);
		} else if (exports.contains(item)) {
			return (int) (item.getBasePrice() / factor);
		} else {
			return item.getBasePrice();
		}
	}
	
	/**
	 * Sets every item in the inventory to a random value between given min and max
	 * @param minQuantity the minimum new quantity of the item in the inventory
	 * @param maxQuantity the maximum new quantity of the item in the inventory
	 */
	public void randomizeInventory(int minQuantity, int maxQuantity) throws IllegalArgumentException {
		
		if (minQuantity < 0 || minQuantity > maxQuantity)
			throw new IllegalArgumentException("Min quantity should be > 0 and < maxQuantity");
		
		for (Item item : inventory.getInventoryItems()) {
			inventory.setItemQuantity(item, (int) (Math.random() * (maxQuantity - minQuantity) + minQuantity));
		}
	}
	
	/**
	 * 
	 * @param item the item to check quantity of
	 * @return quantity of given item (default 0)
	 */
	public int getItemQuantity(Item item) {
		return inventory.getItemQuantity(item);
	}
	
	/**
	 * 
	 * @return the items in the store's inventory
	 */
	public ArrayList<Item> getInventoryItems() {
		return inventory.getInventoryItems();
	}
	
	/**
	 * 
	 * @return a list of all items imported at this store
	 */
	public ArrayList<Item> getImports() {
		return imports;
	}
	
	/**
	 * 
	 * @param item to add to the store as an export item
	 * @throws IllegalArgumentException
	 */
	public void addExport(Item item) throws IllegalArgumentException {
		
		if (exports.contains(item))
			throw new IllegalArgumentException("Store already has this item as an export.");
		
		exports.add(item);
	}
	
	/**
	 * Remove an import item from the store
	 * @param item the item to remove
	 * @throws IllegalArgumentException
	 */
	public void removeImport(Item item) throws IllegalArgumentException {
		if (!imports.contains(item))
			throw new IllegalArgumentException("Store doesn't contain this import.");
		
		imports.remove(item);
	}
	
	/**
	 * 
	 * @return a list of all items exported at this store
	 */
	public ArrayList<Item> getExports() {
		return exports;
	}

	/**
	 * 
	 * @return the current price factor of the store
	 */
	public double getFactor() {
		return factor;
	}

	/**
	 * 
	 * @param factor the new price factor of the store
	 */
	public void setFactor(double factor) throws IllegalArgumentException {
		
		if (factor <= 0)
			throw new IllegalArgumentException("Factor must be greater than 0");
		
		this.factor = factor;
	}

}
