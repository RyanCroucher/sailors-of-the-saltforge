package main;

/**
 * Contains data relevant to the character such as their ship, ledger and name.
 * @author Ryan Croucher rcr69
 *
 */
public class Player {
	
	private static String name;
	//private static Ledger ledger;
	private static Ship ship;
	
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
	 * @param sets the ship object of the player
	 */
	public static void setShip(Ship ship) {
		Player.ship = ship;
	}
	
}
