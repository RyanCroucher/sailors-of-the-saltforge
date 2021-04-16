package events;

import java.util.ArrayList;

import main.Constants;
import main.Player;

public class RescueEvent extends RandomEvent {
	
	private int prize;
	
	public RescueEvent(String name, String description, int prize){
		super(name, description);
		
		this.prize = prize;
		
		super.setEffectString("The sailors give you " + prize + " " + Constants.NAME_CURRENCY + ".");
	}
	
	public void chooseOption(int choice) {
		
	}
	
	
	public void doEffect() {
		Player.setGold(Player.getGold() + prize);
	}
	
	public ArrayList<String> getOptions() {
		return new ArrayList<String>();
	}

}