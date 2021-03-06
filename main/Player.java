package main;

/**
 * Contains data relevant to the character such as their ship, ledger and name.
 * @author Ryan Croucher rcr69
 *
 */
public class Player {
	
	/**
	 * The name of the player character
	 */
	private static String name;
	
	/**
	 * The player character's ship
	 */
	private static Ship ship;
	
	/**
	 * The wealth of the player. Acquire 10,000 to win.
	 */
	private static int gold;
	
	/**
	 * A flag that will be set to true if some type of event kills the player
	 */
	private static boolean killedByEvent = false;
	
	/**
	 * 
	 * @return the name of the player character.
	 */
	public static String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param playerName the name of the character, should already have been validated.
	 */
	public static void setName(String playerName) {
		name = playerName;
	}

	/**
	 * 
	 * @return the ship object owned by the player
	 */
	public static Ship getShip() {
		return ship;
	}

	/**
	 * 
	 * @param ship the player's new ship
	 */
	public static void setShip(Ship ship) {
		Player.ship = ship;
	}

	/**
	 * 
	 * @return the current gold of the player
	 */
	public static int getGold() {
		return gold;
	}

	/**
	 * 
	 * @param gold the amount of gold to set the player's wealth to
	 */
	public static void setGold(int gold) {
		Player.gold = gold;
		if (Player.gold > 10000) {
			GameEnvironment.checkEndgameConditions();
		}
	}
	
	/**
	 * Calculates the net worth of the player, based on gold and the owned items.
	 * @return Net worth of the player
	 */
	public static int getNetWorth() {
		int netWorth = gold;
		
		
		for (Item item : ship.getInventory().getInventoryItems()) {
			int quantity = ship.getInventory().getItemQuantity(item);
			Store currentStore = GameEnvironment.getCurrentIsland().getIslandStore();
			
			int value = currentStore.getItemPrice(item);
			
			netWorth += quantity * value;
		}
		
		return netWorth;
	}
	
	/**
	 * 
	 * @return whether or not the player has been killed by an event
	 */
	public static boolean getKilledByEvent() {
		return killedByEvent;
	}
	
	/**
	 * Sets the killedByEvent flag to true, for instance when killed by pirates
	 */
	public static void setKilledByEvent(boolean state) {
		killedByEvent = state;
	}
	
}
