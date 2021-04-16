package events;

import java.util.ArrayList;

public interface RandomEvent {
	
	String getName();
	String getDescription();
	
	String getEffect();
	void doEffect();
	
	ArrayList<String> getOptions();
	
	void chooseOption(int choice);
	

}
