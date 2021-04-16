package events;

import java.util.ArrayList;

//package events;
//
//import java.util.ArrayList;
//
//public interface RandomEvent {
//	
//	String getName();
//	String getDescription();
//	
//	String getEffect();
//	void doEffect();
//	
//	ArrayList<String> getOptions();
//	
//	void chooseOption(int choice);
//	
//
//}

public abstract class RandomEvent {
	
	private String name;
	private String description;
	private String effectString;
	
	private int stage = 0;
	
	public RandomEvent(String name, String description) {
		
		this.name = name;
		this.description = description;
		
		this.effectString = "";
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEffect() {
		return effectString;
	}
	
	public void setEffectString(String effectString) {
		this.effectString = effectString;
	}
	
	public int getStage() {
		return stage;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public abstract void doEffect();
	
	public abstract ArrayList<String> getOptions();
	
	public abstract void chooseOption(int choice);
	
	
}