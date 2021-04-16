package events;

import java.util.ArrayList;

public class FortuneEvent {
	
	protected String name;
	protected String description;
	protected String effectString;
	
	private int stage = 0;
	
	public FortuneEvent(String name, String description) {
		
		this.name = name;
		this.description = description;
		
		this.effectString = "";
		
	}
	

}
