package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import events.WeatherEvent;
import main.Constants;
import main.GameEnvironment;
import main.Player;
import main.Ship;
import main.Constants.ShipModel;

class WeatherEventTest {

	@Test
	void doEffectTest() {
		
		GameEnvironment.setupGame();
		GameEnvironment.setGameDuration(20);
		Player.setShip(new Ship(ShipModel.MERCHANTMAN));
		int eventDamage = (int) (Math.random() * 5f + 10f);
		int eventCrewLoss = (int) (Math.random() * 1f + 10f);
		int timePassed = 12;
		WeatherEvent weatherTest = new WeatherEvent("Weather Test", "We are testing the weather", eventDamage, eventCrewLoss, timePassed);
		
		weatherTest.setStage(1);
		weatherTest.doEffect();
		assertEquals(Player.getShip().getHull(), Player.getShip().getMaxHull() - eventDamage);
		assertEquals(Player.getShip().getCrew(), Player.getShip().getMaxCrew() - eventCrewLoss);
		
		
		weatherTest.setStage(0);
		weatherTest.doEffect();
		assertEquals(GameEnvironment.getHoursSinceStart(), 12);
		
	}

	@Test
	void chooseOptionTest() {
		GameEnvironment.setupGame();
		GameEnvironment.setGameDuration(20);
		Player.setShip(new Ship(ShipModel.MERCHANTMAN));
		int eventDamage = (int) (Math.random() * 5f + 10f);
		int eventCrewLoss = (int) (Math.random() * 1f + 10f);
		int timePassed = 12;
		WeatherEvent weatherTest = new WeatherEvent("Weather Test", "We are testing the weather", eventDamage, eventCrewLoss, timePassed);
		String testString1 = "";
		String testString2 = "";
		
		testString1 += Constants.EVENT_STORM_OPTION_RISKY + "\n\n";
		testString1 += "You take " + eventDamage + " damage to the hull, and lose " + eventCrewLoss + " crew overboard!";
		
		
		weatherTest.chooseOption(1);
		assertEquals(weatherTest.getEffect(), testString1);
		
		testString2 += Constants.EVENT_STORM_OPTION_SAFE + "\n\n";
		testString2 += "You lose " + timePassed + " hours waiting out the storm.";
		
		weatherTest.chooseOption(0);
		assertEquals(weatherTest.getEffect(), testString2);
		
		
		
	}

}
