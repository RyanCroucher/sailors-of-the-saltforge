package main;
/**
 * Class to create different items, they have a name and basePrice
 * @author Steven Johnson sjo139
 *
 */
public class Item {

	private String name;
	private int basePrice;
	
	/**
	 * Construct an item object with name and basePrice parameters
	 * @param name The name of the item
	 * @param basePrice The base price of the item before modifiers
	 */
	public Item(String name, int basePrice) throws IllegalArgumentException {
		if (basePrice < 1) {
			throw new IllegalArgumentException("basePrice cannot be less than 1");
		}
		this.name = name;
		this.basePrice = basePrice;
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

	
}
