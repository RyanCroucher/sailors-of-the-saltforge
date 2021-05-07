package events;

import java.util.ArrayList;

import main.Constants;
import main.Player;

/**
 * Handles of events of the type 'help someone and receive a reward'
 * @author Ryan Croucher rcr69
 *
 */
public class RescueEvent extends RandomEvent {
	
	//gold reward automatically received
	private int prize;
	
	/**
	 * Constructs a new rescue event
	 * @param name the name of the event
	 * @param description a description of the event
	 * @param prize the gold prize the player will receive
	 */
	public RescueEvent(String name, String description, int prize){
		super(name, description);
		
		this.prize = prize;
		
		super.setEffectString("The sailors give you " + prize + " " + Constants.NAME_CURRENCY + ".");
	}
	
	/**
	 * Rescue events have no decisions to make
	 */
	public void chooseOption(int choice) {
		
	}
	
	
	/**
	 * Gives the gold reward to the player
	 */
	public void doEffect() {
		Player.setGold(Player.getGold() + prize);
	}
	
	/**
	 * Rescue events have no decisions to make
	 * @return an empty arraylist (no options available)
	 */
	public ArrayList<String> getOptions() {
		return new ArrayList<String>();
	}

}
