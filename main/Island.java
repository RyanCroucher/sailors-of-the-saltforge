package main;
/**
 * Each island object represents a location in the game
 * @author Steven Johnson sjo139
 *
 */
public class Island {

	/**
	 * String that holds the name of the island
	 */
	private String islandName;
	
	/**
	 * String that contains the description of the island
	 */
	private String islandDescription;
	
	/**
	 * Store object to be able to access a Store on an island
	 */
	private Store islandStore;
	
	/**
	 * Constructs an instance of the island class with the given parameters
	 * @param name The name of the island
	 * @param description The description of the island
	 */
	public Island(String name, String description, Store store) {
		islandName = name;
		islandDescription = description;
		islandStore = store;
	}
	
	
	/**
	 * 
	 * @return Returns the name of the island instance
	 */
	public String getIslandName() {
		return islandName;
	}

	/**
	 * 
	 * @return Returns the description of the island instance
	 */
	public String getIslandDescription() {
		return islandDescription;
	}

	/**
	 * Get the store attached to the island
	 * @return the store instance of this island
	 */
	public Store getIslandStore() {
		return islandStore;
	}
	
	
	
}
