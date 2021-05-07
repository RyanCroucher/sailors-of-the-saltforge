package events;

import java.util.ArrayList;

import main.Player;

/**
 * Event of the type 'something happens and your ship takes damage'
 * @author Ryan Croucher rcr69
 *
 */
public class ShipDamageEvent extends RandomEvent {
	
	/**
	 * The amount of hull damage the player's ship will take from this event
	 */
	private int hullDamage;
	
	/**
	 * The amount of crew the player's ship will lose from this event
	 */
	private int crewDamage;
	
	/**
	 * Create a new ShipDamageEvent object
	 * @param name the title of the event
	 * @param description the description of what happens in the event
	 * @param hullDamage the damage the ship hull will take
	 * @param crewDamage the number of crew that will be lost
	 */
	public ShipDamageEvent(String name, String description, int hullDamage, int crewDamage) {
		super(name, description);
		
		this.hullDamage = hullDamage;
		this.crewDamage = crewDamage;
		
		super.setEffectString("You ship takes " + hullDamage + " damage and " + crewDamage + " crew fall overboard!");
	}
	
	/**
	 * ShipDamageEvent doesn't have any specific actions the player can take
	 */
	public void chooseOption(int choice) {
		
	}
	
	/**
	 * Apply the hull damage and crew damage to the player's ship
	 */
	public void doEffect() {
		
		Player.getShip().setHull(Player.getShip().getHull() - hullDamage);
		Player.getShip().setCrew(Player.getShip().getCrew() - crewDamage);
	
	}
	
	/**
	 * ShipDamageEvent doesn't have any specific actions the player can take
	 */
	public ArrayList<String> getOptions() {
		
		return new ArrayList<String>();
		
	}

}
