package events;

import java.util.ArrayList;

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
		
	}
	
	public ArrayList<String> getOptions() {
		return new ArrayList<String>();
	}
	
	public void chooseOption(int option) {
		
	}

}
