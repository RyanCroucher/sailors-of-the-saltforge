package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import events.RandomEvent;
import events.RescueEvent;
import main.GameEnvironment;
import main.Player;

class RescueEventTest {

	@Test
	void doEffectTest() {
		GameEnvironment.setupGame();
		
		Player.setGold(1000);
		int randomPrize = (int) (Math.random() * 75f + 25f);
		RescueEvent rescueTest = new RescueEvent("Test Event", "Event is for testing", randomPrize);
		rescueTest.doEffect();
		assertEquals(Player.getGold(), 1000 + randomPrize);
		
	}

}
