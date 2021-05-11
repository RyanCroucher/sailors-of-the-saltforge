package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import events.PirateEvent;
import events.RandomEvent;
import main.Constants;
import main.GameEnvironment;
import main.Player;
import main.Ship;
import main.Constants.ShipModel;

/**
 * 
 * @author Ryan Croucher rcr69
 *
 */
class PirateEventTest {

	@Test
	void doEffectTest() {
		GameEnvironment.setupGame();
		
		Player.setGold(1000);
		Player.setShip(new Ship(ShipModel.SLOOP));
		
		//generate a random prize
		int prizeIfWin = (int) (Math.random() * 500f + 200f);
		
		//generate a random ship
		Ship[] shipOptions = new Ship[] {new Ship(ShipModel.BARGE), new Ship(ShipModel.SLOOP), new Ship(ShipModel.MERCHANTMAN), new Ship(ShipModel.CUTTER)};
		Ship pirateShip = shipOptions[(int) (Math.random() * 4)];
		
		//generate a new pirate event
		RandomEvent event = new PirateEvent("Pirate Attack", Constants.EVENT_PIRATE_ATTACK_DESCRIPTION, pirateShip, prizeIfWin);
		
		//tell the event that we won the fight
		event.setStage(1);
		event.doEffect();
		
		//check that the prize was added to the player inventory
		assertEquals(1000 + prizeIfWin, Player.getGold());
		
		//give player some items to survive a pirate boarding
		Player.getShip().getInventory().addItem(GameEnvironment.getItems().get(0), 50);
		
		//tell the event that we surrendered successfully
		event.setStage(2);
		event.doEffect();
		
		//make sure that we lost all of our goods
		assertEquals(Player.getShip().getCargo(), 0);
		
		//tell the event that we surrendered unsuccessfully
		event.setStage(3);
		try {
			event.doEffect();
			fail("Exception should have been thrown");
		} catch (NullPointerException e) {
			//in this test we don't have a gui panel to update with end game stats, so an exception is thrown
			//this means the test passed, because the game ended by being killed by pirates
			assertEquals(Player.getKilledByEvent(), true);
		}
		
		//un-end the game, if it ended
		Player.setKilledByEvent(false);
		
	}
	
	@Test
	void getOptionsTest() {
		
		RandomEvent event = new PirateEvent("Pirate Attack", Constants.EVENT_PIRATE_ATTACK_DESCRIPTION, null, 0);
		
		//any pirate event should have 3 available decision options
		assertEquals(event.getOptions().size(), 3);
		
	}
	
	@Test
	void chooseOptionTest() {
		
		GameEnvironment.setupGame();
		
		Player.setShip(new Ship(ShipModel.SLOOP));
		
		Ship pirateShip = new Ship(ShipModel.MERCHANTMAN);
		
		RandomEvent event = new PirateEvent("Pirate Attack", Constants.EVENT_PIRATE_ATTACK_DESCRIPTION, pirateShip, 0);

		
		//we fight
		event.chooseOption(1);
		
		//the stage of the event should now have changed from 0 to 1, 2 or 3
		//because we either won, lost and survived or lost and died
		assertNotEquals(event.getStage(), 0);
		
		//we run
		event.chooseOption(2);
		
		//event stage should be 4 (escaped) or 3 (surrendered unsuccessfully)
		assert(event.getStage() == 4 || event.getStage() == 3);

		
		//we surrender unsuccessfully
		event.chooseOption(3);
		
		//the stage of the event should be 3 (surrendered and died due to insufficient items)
		assertEquals(event.getStage(), 3);
		
		//surrender successfully
		Player.getShip().getInventory().addItem(GameEnvironment.getItems().get(0), 50);
		event.chooseOption(3);
		
		//the stage of the event should be 2 (surrendered and survived)
		assertEquals(event.getStage(), 2);
		
	}

}
