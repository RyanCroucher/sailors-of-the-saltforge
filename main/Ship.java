package main;

import java.util.ArrayList;
import main.Constants.ShipModel;

/**
 * 
 * @author Ryan Croucher rcr69
 *
 */
public class Ship {
	
	/**
	 * ArrayList for storing ship upgrades that have been purchased
	 */
	private ArrayList<String> upgrades;
	
	/**
	 * Model of the ship that determines its default stats/properties
	 */
	private ShipModel model;
	
	/**
	 * Name of the ship model
	 */
	private String modelName;
	
	/**
	 * Maximum hull value
	 */
	private int maxHull;
	/**
	 * Current hull value. This value will decrease if damage is taken.
	 */
	private int hull;
	
	/**
	 * Maximum crew value
	 */
	private int maxCrew;
	
	/**
	 * Current crew value. This value will decrease if crew loss occurs
	 */
	private int crew;
	
	/**
	 * Maximum cargo capacity value
	 */
	private int cargoCapacity;
	
	/**
	 * Current cargo value. This value will increase and decrease as items are bought and sold 
	 */
	private int cargo;
	
	/**
	 * Speed rating of the ship. Effects travel time between islands
	 */
	private int speed;
	
	/**
	 * Rating of the ships weapons. Can effect the chances of surviving a pirate attack
	 */
	private int weaponRating;
	
	/**
	 * The inventory that belongs to the ship. Will allow you to store items and their quantities in the ship
	 */
	private Inventory inventory;
	
	/**
	 * Constructs a new ship instance with given parameters.
	 * @param model the type of ship (must be one of four options).
	 * @param maxHull the value of the hull when completely undamaged.
	 * @param maxCrew the maximum number of crew members on the ship.
	 * @param cargoCapacity the maximum amount of items that can be stored on the ship.
	 * @param speed the speed the ship travels, affects travel time.
	 * @param weaponRating the firepower of the ship.
	 * @throws IllegalArgumentException
	 */
	public Ship(ShipModel model, int maxHull, int maxCrew, int cargoCapacity, int speed, int weaponRating) throws IllegalArgumentException {
		
		if (!validShipParams(maxHull, maxCrew, cargoCapacity, speed, weaponRating)) {
			throw new IllegalArgumentException("One or more ship parameters are outside expected bounds.");
		}
		
		initializeShip(model, maxHull, maxCrew, cargoCapacity, speed, weaponRating);
		
		
	}
	
	/**
	 * An alternative Ship constructor that will build a ship with the default values of a given model.
	 * @param model one of the ShipModels defined in Constants
	 * @throws IllegalArgumentException
	 */
	public Ship(ShipModel model) throws IllegalArgumentException {
		switch (model) {
			case MERCHANTMAN:
				initializeShip(model, 
						Constants.MERCHANTMAN_MAX_HULL,
						Constants.MERCHANTMAN_MAX_CREW,
						Constants.MERCHANTMAN_CARGO_CAPACITY,
						Constants.MERCHANTMAN_SPEED,
						Constants.MERCHANTMAN_WEAPONS);
				break;
			case CUTTER:
				initializeShip(model, 
						Constants.CUTTER_MAX_HULL,
						Constants.CUTTER_MAX_CREW,
						Constants.CUTTER_CARGO_CAPACITY,
						Constants.CUTTER_SPEED,
						Constants.CUTTER_WEAPONS);
				break;
			case SLOOP:
				initializeShip(model, 
						Constants.SLOOP_MAX_HULL,
						Constants.SLOOP_MAX_CREW,
						Constants.SLOOP_CARGO_CAPACITY,
						Constants.SLOOP_SPEED,
						Constants.SLOOP_WEAPONS);
				break;
			case BARGE:
				initializeShip(model, 
						Constants.BARGE_MAX_HULL,
						Constants.BARGE_MAX_CREW,
						Constants.BARGE_CARGO_CAPACITY,
						Constants.BARGE_SPEED,
						Constants.BARGE_WEAPONS);
				break;
			default:
				throw new IllegalArgumentException("This Ship Model is not implemented.");
		}
	}
	
	/**
	 * A helper function for the Ship constructors, assigns the given parameters to the properties of the ship object.
	 * @param model the type of ship (must be one of four options).
	 * @param maxHull the value of the hull when completely undamaged.
	 * @param maxCrew the maximum number of crew members on the ship.
	 * @param cargoCapacity the maximum amount of items that can be stored on the ship.
	 * @param speed the speed the ship travels, affects travel time.
	 * @param weaponRating the firepower of the ship.
	 */
	private void initializeShip(ShipModel model, int maxHull, int maxCrew, int cargoCapacity, int speed, int weaponRating) {
		this.model = model;
		
		switch(model) {
			case MERCHANTMAN:
				modelName = "Merchantman";
				break;
			case CUTTER:
				modelName = "Elven Cutter";
				break;
			case SLOOP:
				modelName = "Tunian War-Sloop";
				break;
			case BARGE:
				modelName = "Dwarven Barge";
				break;
		}
		
		this.maxHull = maxHull;
		this.setHull(maxHull);
		
		this.maxCrew = maxCrew;
		this.setCrew(maxCrew);
		
		this.cargoCapacity = cargoCapacity;
		this.setCargo(0);
		
		this.speed = speed;
		
		this.setWeaponRating(weaponRating);
		
		inventory = new Inventory();
		
		upgrades = new ArrayList<String>();
	}
	
	/**
	 * Validates the parameters passed to the Ship constructor
	 * @param hull must be within min and max defined in Constants
	 * @param crew must be within min and max defined in Constants
	 * @param cargo must be within min and max defined in Constants
	 * @param speed must be within min and max defined in Constants
	 * @param weapons must be within min and max defined in Constants
	 * @return true if all params are within expected bounds, else false
	 */
	private boolean validShipParams(int hull, int crew, int cargo, int speed, int weapons) {
		
		if (hull < Constants.SHIP_MIN_MAX_HULL || hull > Constants.SHIP_MAX_MAX_HULL)
			return false;
		
		else if (crew < Constants.SHIP_MIN_MAX_CREW || crew > Constants.SHIP_MAX_MAX_CREW)
			return false;
		
		else if (cargo < Constants.SHIP_MIN_CARGO_CAPACITY || cargo > Constants.SHIP_MAX_CARGO_CAPACITY)
			return false;
		
		else if (speed < Constants.SHIP_MIN_SPEED || speed > Constants.SHIP_MAX_SPEED)
			return false;
		
		else if (weapons < Constants.SHIP_MIN_WEAPONS || weapons > Constants.SHIP_MAX_WEAPONS)
			return false;
		
		else return true;
	}
	
	/**
	 * 
	 * @return the type/model of the ship.
	 */
	public ShipModel getModel() {
		return model;
	}
	
	/**
	 * 
	 * @return the name of the model.
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * 
	 * @return the maximum hull value
	 */
	public int getMaxHull() {
		return maxHull;
	}
	
	/**
	 * 
	 * @return the current hull value
	 */
	public int getHull() {
		return hull;
	}

	/**
	 * 
	 * @param set the hull value
	 */
	public void setHull(int hull) {
		this.hull = hull;
	}

	/**
	 * 
	 * @return the maximum crew value
	 */
	public int getMaxCrew() {
		return maxCrew;
	}

	/**
	 * 
	 * @return the current crew value
	 */
	public int getCrew() {
		return crew;
	}

	/**
	 * 
	 * @param set the crew value
	 */
	public void setCrew(int crew) {
		this.crew = crew;
	}

	/**
	 * 
	 * @return the cargo capacity value
	 */
	public int getCargoCapacity() {
		return cargoCapacity;
	}

	/**
	 * 
	 * @return the current cargo value
	 */
	public int getCargo() {
		return cargo;
	}

	/**
	 * 
	 * @param set the cargo value
	 */
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	/**
	 * 
	 * @return the speed value
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * 
	 * @return the inventory of the ship
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * 
	 * @return the weapon rating value
	 */
	public int getWeaponRating() {
		return weaponRating;
	}

	/**
	 * 
	 * @param Set the weapon rating
	 */
	public void setWeaponRating(int weaponRating) {
		this.weaponRating = weaponRating;
	}

	/**
	 * 
	 * @return an ArrayList of upgrades for the ship
	 */
	public ArrayList<String> getUpgrades() {
		return upgrades;
	}
	
	/**
	 * Equips a ship with an upgrade
	 * @param upgrade the upgrade to add to the ship
	 * @throws IllegalArgumentException
	 */
	public void addUpgrade(String upgrade) throws IllegalArgumentException {
		
		if (upgrades.contains(upgrade))
			throw new IllegalArgumentException("Ship already has this upgrade");
		
		switch (upgrade) {
			case Constants.UPGRADE_CANNONS:
				weaponRating += 2;
				break;
			case Constants.UPGRADE_HULL:
				maxHull += 20;
				hull += 20;
				break;
			case Constants.UPGRADE_SAILS:
				speed += 10;
				break;
		}
		
		upgrades.add(upgrade);
	}
	
	/**
	 * Get the cost to fully stock up on crew
	 * @param cheaperAtCurrentPort whether or not it is cheaper to hire crew
	 * @return total cost to refill the crew
	 */
	public int getRefillCrewCost(boolean cheaperAtCurrentPort) {
		
		int refillCrewCost = (maxCrew - crew) * 20;
		
		if (cheaperAtCurrentPort)
			refillCrewCost /= 2;
		
		return refillCrewCost;
	}
	
	/**
	 * Get the total wage cost to travel for a certain duration
	 * @param hours the time period to pay the crew over
	 * @return the total cost in wages
	 */
	public int getWageCost(int hours) throws IllegalArgumentException {
		
		if (hours < 0)
			throw new IllegalArgumentException("Hours must be at least 0 to calculate wage cost");
		
		//cost per crew member per day
		int wageCost = (int) (maxCrew * 10 * (hours / 24f));
		return wageCost;
		
	}
	
	/**
	 * Get the total cost to fully repair the ship's hull
	 * @param cheaperAtCurrentPort whether or not it is cheaper to do repairs
	 * @return the total cost in repairs
	 */
	public int getRepairCost(boolean cheaperAtCurrentPort) {
		
		//cost to fully repair the hull
		int repairCost = (maxHull - hull) * 20;
		
		if (cheaperAtCurrentPort)
			repairCost /= 2;
		
		return repairCost;
	}
	
	/**
	 * Total cost to leave an island
	 * @param hours The modified duration of the route
	 * @return An integer for the total cost to leave the island. This is determined by whether you have crew to replace and damage to repair
	 * it also checks how much in wages it'll cost to pay your crew for the journey(total number of crew * hours in travel)
	 */
	public int totalCostToLeaveIsland(int hours) {
		
		boolean cheaperCrewHire = GameEnvironment.getCurrentIsland().getIslandName() == Constants.ISLAND_SKULLHAVEN;
		boolean cheaperRepairs = GameEnvironment.getCurrentIsland().getIslandName()  == Constants.ISLAND_SKULLHAVEN;
		
		int hireAndWagesCost = Player.getShip().getRefillCrewCost(cheaperCrewHire) + Player.getShip().getWageCost(hours);
		int repairCost = Player.getShip().getRepairCost(cheaperRepairs);

		int totalCostToLeaveIsland = hireAndWagesCost + repairCost;
		
		return totalCostToLeaveIsland;
	}
	
	
}
