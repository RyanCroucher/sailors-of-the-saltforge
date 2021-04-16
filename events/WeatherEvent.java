package events;

import java.util.ArrayList;

import main.Constants;
import main.GameEnvironment;
import main.Player;

public class WeatherEvent extends RandomEvent {
	
	private String name;
	private String description;
	private int hullDamage;
	private int crewLoss;
	private int hoursLost;
	
	public WeatherEvent(String name, String description, int hullDamage, int crewLoss, int hoursLost) {
		super(name, description);
		
		this.hullDamage = hullDamage;
		this.crewLoss = crewLoss;
		this.hoursLost = hoursLost;
	}
	
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
	
	public ArrayList<String> getOptions() {
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("We keep going through the storm, risking damage to hull and crew.");
		options.add("We turn around before the storm gets too bad, and try again tomorrow.");
		
		return options;
	}
	
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
