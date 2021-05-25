package events;

import java.util.ArrayList;

import main.Constants;
import main.GameEnvironment;
import main.Inventory;
import main.Item;
import main.Player;
import main.Ship;

/**
 * The random event to handle pirate attacks, can end the game in some cases.
 * @author Ryan Croucher rcr69
 *
 */
public class PirateEvent extends RandomEvent {
		
	/**
	 * The ship the pirates are sailing
	 */
	private Ship pirateShip;
	
	/**
	 * The amount of hull damage the event will deal to the ship (if any)
	 */
	private int hullDamage = 0;
	
	/**
	 * The number of crew losses the event will deal to the ship (if any)
	 */
	private int crewDamage = 0;
	
	/**
	 * The gold that will be awarded to the player, if they fight and win
	 */
	private int prize = 0;
	
	/**
	 * Stores the current chunk of text we are up to if we had a round by round dice game
	 */
	private int roundChunk = 1;
	
	/**
	 * Constructs a new Pirate Event.
	 * @param name the title of the event
	 * @param description a description of what happens in the event
	 * @param pirateShip the generated pirate ship object
	 * @param prizeIfWon gold reward for the player if they win a fight
	 */
	public PirateEvent(String name, String description, Ship pirateShip, int prizeIfWon) {
		super(name, description);
		
		this.pirateShip = pirateShip;
		this.prize = prizeIfWon;
	}
	
	/**
	 * Executes whatever effect the event has (i.e. hull damage, lose game, gain gold etc)
	 */
	public void doEffect() {
		
		Player.getShip().setHull(Player.getShip().getHull() - hullDamage);
		Player.getShip().setCrew(Player.getShip().getCrew() - crewDamage);
		
		//Won fight
		if (super.getStage() == 1) {
			
			//we won some gold!
			Player.setGold(Player.getGold() + prize);
			
		}
		
		//Lost fight or failed to escape -> surrender accepted
		else if (super.getStage() == 2) {
			
			Inventory playerInventory = Player.getShip().getInventory();
			
			for (Item item: playerInventory.getInventoryItems()) {
				int quantity = playerInventory.getItemQuantity(item);
				if (quantity > 0)
					playerInventory.removeItem(item, playerInventory.getItemQuantity(item));
			}
			
			Player.getShip().setCargo(0);
			
		}
		
		//Lost fight or failed to escape -> surrender not accepted
		else if (super.getStage() == 3) {
			
			Inventory playerInventory = Player.getShip().getInventory();
			
			for (Item item: playerInventory.getInventoryItems()) {
				int quantity = playerInventory.getItemQuantity(item);
				if (quantity > 0)
					playerInventory.removeItem(item, playerInventory.getItemQuantity(item));
			}
			
			Player.getShip().setCargo(0);
			
			Player.setKilledByEvent(true);
			GameEnvironment.checkEndgameConditions();
			
		}
		
		//Escaped
		else if (super.getStage() == 4){
			//there is no effect
		}
	}
	
	/**
	 * Returns a list of available options to the player to handle the event
	 */
	public ArrayList<String> getOptions() {
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("We stand and fight. To battle!");
		options.add("We must try to run. Hoist the sails!");
		options.add("It's too risky, let's surrender and hope they leave us our lives...");
		
		return options;
	}
	
	/**
	 * After input from the player, executes the chosen option to deal with the event
	 */
	public void chooseOption(int option) {
		
		
		//Fight
		if (option == 1) {

			fight();

		}
		
		//Run
		else if (option == 2) {
			
			flee();
			
		}
		
		//Surrender
		else {
			surrender();	
			
		}
		
		
	}
	
	/**
	 * Updates the effect string and stage of the event commensurate with a simulated ship chase
	 */
	private void flee() {
		
		String fleeString = "We flee!\n";
		
		Ship playerShip = Player.getShip();
		int round = 1;
		
		//starting distance between two ships in metres
		int shipDistance = 150;
		
		while (shipDistance > 0 && round <= 6) {
			
			int playerSpeed = playerShip.getSpeed();
			int pirateSpeed = pirateShip.getSpeed();
			
			int pirateGain = (int) (Math.random() * 50 + pirateSpeed);
			int playerGain = (int) (Math.random() * 50 + playerSpeed);
			
			shipDistance += playerGain - pirateGain;
			
			fleeString += "\nRound " + round + ": " + Player.getName() + "'s " + playerShip.getModelName() + " increases the gap by " + playerGain + " metres.";
			fleeString += "\nRound " + round + ": Pirate's " + pirateShip.getModelName() + " closes the gap by " + pirateGain + " metres.";
			fleeString += "\nThe two ships are now " + shipDistance + " metres apart.\n";
			
			round++;
		}
		
		fleeString += "\n";
		
		//time exceeded, pirate gives up
		if (round > 6) {
			
			fleeString += Constants.EVENT_PIRATE_ATTACK_RUN_SUCCESS;
			super.setEffectString(fleeString);
			super.setStage(4);
			
		}
		
		//they must have caught up to you
		else {
			
			fleeString += Constants.EVENT_PIRATE_ATTACK_RUN_FAIL ;
			
			super.setEffectString(fleeString);
			
			surrender();
			
		}
		
	}
	
	/**
	 * Updates the effect string and stage of the event commensurate with a simulated ship battle. 
	 */
	private void fight() {
		
		String combatString = "We fight!!!\n\n";
		
		Ship playerShip = Player.getShip();
		int round = 1;
		
		int playerHullLost = 0;
		int pirateHullLost = 0;
		
		//shoot each other until one ship is below half of it's max hull
		while(playerHullLost < playerShip.getMaxHull() / 2f && pirateHullLost < pirateShip.getMaxHull() / 2f) {
			
			int playerDamage = (int) (Math.random() * 10 + playerShip.getWeaponRating());
			int pirateDamage = (int) (Math.random() * 10 + pirateShip.getWeaponRating());
			
			playerHullLost += pirateDamage;
			pirateHullLost += playerDamage;
			
			combatString += "Round " + round + ": " + Player.getName() + "'s " + playerShip.getModelName() + " fires its cannons, dealing " + playerDamage + " to the pirates.\n";
			combatString += "Round " + round + ": Pirate's " + pirateShip.getModelName() + " fires its cannons, dealing " + pirateDamage + " to your ship.\n\n";
			round++;

		}
		
		
		hullDamage = playerHullLost;
		crewDamage = playerHullLost / 10;
		
		//player won
		if (pirateHullLost >= pirateShip.getMaxHull() / 2f) {
			
			combatString += Constants.EVENT_PIRATE_ATTACK_FIGHT_SUCCESS + "\n";
			combatString += "\nYou Lost:\n";
			combatString += playerHullLost + " hull integrity.";
			combatString += "\n" + crewDamage + " crew.";
			combatString += "\n\nThe pirates dumped some goods as they fled. You Gain " + prize + " " + Constants.NAME_CURRENCY + ".";
			
			super.setEffectString(combatString);
			super.setStage(1);
		}
		
		//pirates won
		else {
			
			combatString += Constants.EVENT_PIRATE_ATTACK_FIGHT_FAIL + "\n";
			combatString += "\nYou Lost:\n";
			combatString += playerHullLost + " hull integrity.";
			combatString += "\n" + crewDamage + " crew.\n\n";
			
			super.setEffectString(combatString);
			surrender();
		}
		
		
	}
	
	/**
	 * Updates the effect string and stage of the event commensurate with the surrender result
	 */
	private void surrender() {
		
		String effectString = super.getEffect() + Constants.EVENT_PIRATE_ATTACK_SURRENDER + "\n\n";
		
		if (piratesAreSatisfied()) {
			super.setStage(2);
			
			effectString += Constants.EVENT_PIRATE_ATTACK_SURRENDER_SUCCESS
						 + "\n" + getItemsLostString()
						 + "\nYou have survived the encounter.";
			
		}
		
		else {
			super.setStage(3);
			
			effectString += Constants.EVENT_PIRATE_ATTACK_SURRENDER_FAIL;
		}
		
		super.setEffectString(effectString);
		
	}
	
	/**
	 * 
	 * @return a string representing the items stolen by the pirates
	 */
	private String getItemsLostString() {
		
		String itemsLost = "\nYou lose:";
		
		Inventory playerInventory = Player.getShip().getInventory();
		
		for (Item item: playerInventory.getInventoryItems()) {
			itemsLost += "\n" + playerInventory.getItemQuantity(item) + " " + item.getName() + ".";
		}
		
		return itemsLost + "\n";
		
	}
	
	/**
	 * Calculates the worth of your items, determining whether the pirates let you go or kill you.
	 * @return whether the pirates are satisfied or not.
	 */
	private boolean piratesAreSatisfied() {
		
		Inventory playerInventory = Player.getShip().getInventory();
		
		int totalWorth = 0;
		
		for (Item item: playerInventory.getInventoryItems()) {
			totalWorth += item.getBasePrice() * playerInventory.getItemQuantity(item);
		}
		
		return totalWorth >= 1000;
		
	}
	
	/**
	 * Return the event result string.
	 * Breaks string into chunks if we're in GUI mode
	 */
	@Override
	public String getEffect() {
		
		if (GameEnvironment.GUIMode == false)
			return super.getEffect();

		//there was a fight or a flee, break string up into chunks
		if (super.getEffect().contains("Round")) {
			
			int endIndexOfRoundSubstring = super.getEffect().indexOf("Round " + roundChunk);
			
			if (endIndexOfRoundSubstring == -1)
				return super.getEffect();
			
			int endIndexOfChunk = super.getEffect().indexOf("\n\n", endIndexOfRoundSubstring);
			
			if (endIndexOfChunk == -1)
				return super.getEffect();
		
			roundChunk++;
			return super.getEffect().substring(0, endIndexOfChunk + 1);
			
		} else {
			return super.getEffect();
		}
	}

}
