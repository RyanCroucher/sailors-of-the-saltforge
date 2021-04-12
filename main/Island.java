package main;
/**
 * Each island object represents a location in the game
 * @author Steven Johnson sjo139
 *
 */
public class Island {

	private String islandName;
	private String islandDescription;
	//private Store islandStore;
	
	/**
	 * Constructs an instance of the island class with the given parameters
	 * @param name The name of the island
	 * @param description The description of the island
	 */
	public Island(String name, String description) {
		islandName = name;
		islandDescription = description;
//		islandStore = store;
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
	 * TODO create Store class so we can add a store to the island
	 */
//	public Store getIslandStore() {
//		return islandStore
//	}
	
	
	
}
