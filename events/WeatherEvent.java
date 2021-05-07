package events;

import java.util.ArrayList;

import main.Constants;
import main.GameEnvironment;
import main.Player;

/**
 * Events of the type 'Bad weather occurs and you take damage, crew loss, and lose time etc'
 * @author Ryan Croucher rcr69
 *
 */
public class WeatherEvent extends RandomEvent {
	
	//damage the player's ship may take
	private int hullDamage;
	
	//the number of crew the player may lose
	private int crewLoss;
	
	//the number of hours the game may progress
	private int hoursLost;
	
	/**
	 * Constructs a new weather event
	 * @param name the name of the event
	 * @param description a description of the event
	 * @param hullDamage the amount of damage the player's ship's hull may take
	 * @param crewLoss number of crew the player may lose
	 * @param hoursLost number of hours the game may progress
	 */
	public WeatherEvent(String name, String description, int hullDamage, int crewLoss, int hoursLost) {
		super(name, description);
		
		this.hullDamage = hullDamage;
		this.crewLoss = crewLoss;
		this.hoursLost = hoursLost;
	}
	
	/**
	 * Applies the effect of the player's decision (proceed or turn back)
	 */
	public void doEffect() {
		
		//sailed through
		if (super.getStage() == 1) {
			Player.getShip().setHull(Player.getShip().getHull() - hullDamage);
			Player.getShip().setCrew(Player.getShip().getCrew() - crewLoss);
		}
		
		//turned back
		else {
			GameEnvironment.passTime(hoursLost);
		}
	}
	
	/**
	 * Returns a list of options available for the player to choose among
	 */
	public ArrayList<String> getOptions() {
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("We keep going through the storm, risking damage to hull and crew.");
		options.add("We turn around before the storm gets too bad, and try again tomorrow.");
		
		return options;
	}
	
	/**
	 * Progresses the event based on the player's selected decision
	 */
	public void chooseOption(int option) {
		
		super.setStage(option);
		
		String effectString = "";
		
		//continue through the storm
		if (option == 1) {
			
			effectString += Constants.EVENT_STORM_OPTION_RISKY + "\n\n";
			effectString += "You take " + hullDamage + " damage to the hull, and lose " + crewLoss + " crew overboard!";
			
			super.setEffectString(effectString);
			
		}
		
		//turn back
		else {
			
			effectString += Constants.EVENT_STORM_OPTION_SAFE + "\n\n";
			effectString += "You lose " + hoursLost + " hours waiting out the storm.";
			
			super.setEffectString(effectString);
			
		}
		
	}

}
