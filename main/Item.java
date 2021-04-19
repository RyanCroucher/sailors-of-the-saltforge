package main;
/**
 * Class to create different items, they have a name and basePrice
 * @author Steven Johnson sjo139
 *
 */
public class Item {

	/**
	 * String containing the name of the Item
	 */
	private String name;
	
	/**
	 * Integer that holds the base price of the Item
	 */
	private int basePrice;
	
	/**
	 * String containing the description of the Item
	 */
	private String description;
	
	/**
	 * Integer that holds the size of an item. Most objects are only size one but Raw Materials are size 2.
	 */
	private int size;
	
	/**
	 * Construct an item object with name and basePrice parameters
	 * @param name The name of the item
	 * @param basePrice The base price of the item before modifiers
	 */
	public Item(String name, int basePrice, String description, int size) throws IllegalArgumentException {
		
		if (basePrice < 1) {
			throw new IllegalArgumentException("basePrice cannot be less than 1");
		}
		
		if (size < 1 || size > 2) {
			throw new IllegalArgumentException("size must be 1 or 2");
		}
		
		this.name = name;
		this.basePrice = basePrice;
		this.description = description;
		this.size = size;
	}
	
	/**
	 * Get the name
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the base price
	 * @return the base price of the item
	 */
	public int getBasePrice() {
		return basePrice;
	}
	
	/**
	 * 
	 * @return the item's description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @return the cargo capacity taken up by each of this item
	 */
	public int getSize() {
		return size;
	}

	
}
