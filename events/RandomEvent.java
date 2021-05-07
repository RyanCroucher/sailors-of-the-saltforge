package events;

import java.util.ArrayList;

/**
 * 
 * @author Ryan Croucher rcr69
 *
 */
public abstract class RandomEvent {
	
	//the name of the event
	private String name;
	
	//description of the event
	private String description;
	
	//a string representing the effect of the event
	private String effectString;
	
	//events are staged as we can make decisions
	private int stage = 0;
	
	/**
	 * Constructs a new random event
	 * @param name the name of the event
	 * @param description a description of the event
	 */
	public RandomEvent(String name, String description) {
		
		this.name = name;
		this.description = description;
		
		this.effectString = "";
		
	}
	
	/**
	 * 
	 * @return the name of the event
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return the description of the event
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @return the string representing the effect of the event
	 */
	public String getEffect() {
		return effectString;
	}
	
	/**
	 * sets the current effect string
	 * @param effectString the string to set
	 */
	public void setEffectString(String effectString) {
		this.effectString = effectString;
	}
	
	/**
	 * 
	 * @return the current stage of the event
	 */
	public int getStage() {
		return stage;
	}
	
	/**
	 * sets the current stage of the event
	 * @param stage stage to set the event to
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	/**
	 * implemented by children, applies effect of the event
	 */
	public abstract void doEffect();
	
	/**
	 * implemented by children, gets all possible decision options
	 * @return an arraylist of strings with the wording of each decision
	 */
	public abstract ArrayList<String> getOptions();
	
	/**
	 * implemented by children, progresses the event based on the chosen option
	 * @param choice the integer index of the decision
	 */
	public abstract void chooseOption(int choice);
	
	
}