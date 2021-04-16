package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Ship;
import main.Constants;
import main.Constants.ShipModel;
import main.Player;


class ShipTest {
	
	@Test
	void shipConstructorOneTest() {
		
		//valid ships
		Ship validShip;
		
		//blue sky
		try {
			
			int hull = 50;
			int crew = 25;
			int cargo = 40;
			int speed = 45;
			int weapons = 5;
			
			validShip = new Ship(ShipModel.MERCHANTMAN, hull, crew, cargo, speed, weapons);
			
			assertEquals(validShip.getModel(), ShipModel.MERCHANTMAN);
			
			assertEquals(validShip.getMaxHull(), hull);
			assertEquals(validShip.getHull(), hull);
			
			assertEquals(validShip.getMaxCrew(), crew);
			assertEquals(validShip.getCrew(), crew);
			
			assertEquals(validShip.getCargoCapacity(), cargo);
			assertEquals(validShip.getCargo(), 0);
			
			assertEquals(validShip.getSpeed(), speed);
			
			assertEquals(validShip.getWeaponRating(), weapons);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments");
		}
		
		try {
			
			int hull = 32;
			int crew = 15;
			int cargo = 36;
			int speed = 55;
			int weapons = 3;
			
			validShip = new Ship(ShipModel.CUTTER, hull, crew, cargo, speed, weapons);
			
			assertEquals(validShip.getModel(), ShipModel.CUTTER);
			
			assertEquals(validShip.getMaxHull(), hull);
			assertEquals(validShip.getHull(), hull);
			
			assertEquals(validShip.getMaxCrew(), crew);
			assertEquals(validShip.getCrew(), crew);
			
			assertEquals(validShip.getCargoCapacity(), cargo);
			assertEquals(validShip.getCargo(), 0);
			
			assertEquals(validShip.getSpeed(), speed);
			
			assertEquals(validShip.getWeaponRating(), weapons);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments");
		}
		
		//boundary values
		
		try {
			
			int hull = Constants.SHIP_MIN_MAX_HULL;
			int crew = Constants.SHIP_MIN_MAX_CREW;
			int cargo = Constants.SHIP_MIN_CARGO_CAPACITY;
			int speed = Constants.SHIP_MIN_SPEED;
			int weapons = Constants.SHIP_MIN_WEAPONS;
			
			validShip = new Ship(ShipModel.SLOOP, hull, crew, cargo, speed, weapons);
			
			assertEquals(validShip.getModel(), ShipModel.SLOOP);
			
			assertEquals(validShip.getMaxHull(), hull);
			assertEquals(validShip.getHull(), hull);
			
			assertEquals(validShip.getMaxCrew(), crew);
			assertEquals(validShip.getCrew(), crew);
			
			assertEquals(validShip.getCargoCapacity(), cargo);
			assertEquals(validShip.getCargo(), 0);
			
			assertEquals(validShip.getSpeed(), speed);
			
			assertEquals(validShip.getWeaponRating(), weapons);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments");
		}
		
		try {
			
			int hull = Constants.SHIP_MAX_MAX_HULL;
			int crew = Constants.SHIP_MAX_MAX_CREW;
			int cargo = Constants.SHIP_MAX_CARGO_CAPACITY;
			int speed = Constants.SHIP_MAX_SPEED;
			int weapons = Constants.SHIP_MAX_WEAPONS;
			
			validShip = new Ship(ShipModel.BARGE, hull, crew, cargo, speed, weapons);
			
			assertEquals(validShip.getModel(), ShipModel.BARGE);
			
			assertEquals(validShip.getMaxHull(), hull);
			assertEquals(validShip.getHull(), hull);
			
			assertEquals(validShip.getMaxCrew(), crew);
			assertEquals(validShip.getCrew(), crew);
			
			assertEquals(validShip.getCargoCapacity(), cargo);
			assertEquals(validShip.getCargo(), 0);
			
			assertEquals(validShip.getSpeed(), speed);
			
			assertEquals(validShip.getWeaponRating(), weapons);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments");
		}
		
		
		
		
		//invalid ships
		Ship invalidShip;
		
		int hull = 50;
		int crew = 25;
		int cargo = 40;
		int speed = 45;
		int weapons = 5;
		
		//hull too low
		try {
			invalidShip = new Ship(ShipModel.MERCHANTMAN, 0, crew, cargo, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//hull too high
		try {
			invalidShip = new Ship(ShipModel.CUTTER, 1000, crew, cargo, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//crew too low
		try {
			invalidShip = new Ship(ShipModel.SLOOP, hull, -1, cargo, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//crew too high
		try {
			invalidShip = new Ship(ShipModel.BARGE, hull, 99, cargo, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//cargo too low
		try {
			invalidShip = new Ship(ShipModel.MERCHANTMAN, hull, crew, 1, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//cargo too high
		try {
			invalidShip = new Ship(ShipModel.MERCHANTMAN, hull, crew, 150, speed, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//speed too low
		try {
			invalidShip = new Ship(ShipModel.CUTTER, hull, crew, cargo, -10, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//speed too high
		try {
			invalidShip = new Ship(ShipModel.SLOOP, hull, crew, cargo, 9000, weapons);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//weapons too low
		try {
			invalidShip = new Ship(ShipModel.BARGE, hull, crew, cargo, speed, -5000);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//weapons too high
		try {
			invalidShip = new Ship(ShipModel.MERCHANTMAN, hull, crew, cargo, speed, 5000);
			fail("Invalid parameter, should throw exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
	}

	@Test
	void shipConstructorTwoTest() {
		
		//valid ships
		
		Ship validShip;

		try {
			
			validShip = new Ship(ShipModel.MERCHANTMAN);
			
			assertEquals(validShip.getModel(), ShipModel.MERCHANTMAN);
			assertEquals(validShip.getMaxHull(), Constants.MERCHANTMAN_MAX_HULL);
			assertEquals(validShip.getHull(), Constants.MERCHANTMAN_MAX_HULL);
			assertEquals(validShip.getMaxCrew(), Constants.MERCHANTMAN_MAX_CREW);
			assertEquals(validShip.getCrew(), Constants.MERCHANTMAN_MAX_CREW);
			assertEquals(validShip.getCargoCapacity(), Constants.MERCHANTMAN_CARGO_CAPACITY);
			assertEquals(validShip.getCargo(), 0);
			assertEquals(validShip.getSpeed(), Constants.MERCHANTMAN_SPEED);
			assertEquals(validShip.getWeaponRating(), Constants.MERCHANTMAN_WEAPONS);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments.");
		}
		
		try {
			
			validShip = new Ship(ShipModel.CUTTER);
			
			assertEquals(validShip.getModel(), ShipModel.CUTTER);
			assertEquals(validShip.getMaxHull(), Constants.CUTTER_MAX_HULL);
			assertEquals(validShip.getHull(), Constants.CUTTER_MAX_HULL);
			assertEquals(validShip.getMaxCrew(), Constants.CUTTER_MAX_CREW);
			assertEquals(validShip.getCrew(), Constants.CUTTER_MAX_CREW);
			assertEquals(validShip.getCargoCapacity(), Constants.CUTTER_CARGO_CAPACITY);
			assertEquals(validShip.getCargo(), 0);
			assertEquals(validShip.getSpeed(), Constants.CUTTER_SPEED);
			assertEquals(validShip.getWeaponRating(), Constants.CUTTER_WEAPONS);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments.");
		}
		
		try {
			
			validShip = new Ship(ShipModel.SLOOP);
			
			assertEquals(validShip.getModel(), ShipModel.SLOOP);
			assertEquals(validShip.getMaxHull(), Constants.SLOOP_MAX_HULL);
			assertEquals(validShip.getHull(), Constants.SLOOP_MAX_HULL);
			assertEquals(validShip.getMaxCrew(), Constants.SLOOP_MAX_CREW);
			assertEquals(validShip.getCrew(), Constants.SLOOP_MAX_CREW);
			assertEquals(validShip.getCargoCapacity(), Constants.SLOOP_CARGO_CAPACITY);
			assertEquals(validShip.getCargo(), 0);
			assertEquals(validShip.getSpeed(), Constants.SLOOP_SPEED);
			assertEquals(validShip.getWeaponRating(), Constants.SLOOP_WEAPONS);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments.");
		}
		
		try {
			
			validShip = new Ship(ShipModel.BARGE);
			
			assertEquals(validShip.getModel(), ShipModel.BARGE);
			assertEquals(validShip.getMaxHull(), Constants.BARGE_MAX_HULL);
			assertEquals(validShip.getHull(), Constants.BARGE_MAX_HULL);
			assertEquals(validShip.getMaxCrew(), Constants.BARGE_MAX_CREW);
			assertEquals(validShip.getCrew(), Constants.BARGE_MAX_CREW);
			assertEquals(validShip.getCargoCapacity(), Constants.BARGE_CARGO_CAPACITY);
			assertEquals(validShip.getCargo(), 0);
			assertEquals(validShip.getSpeed(), Constants.BARGE_SPEED);
			assertEquals(validShip.getWeaponRating(), Constants.BARGE_WEAPONS);
			
		} catch (IllegalArgumentException e) {
			fail("Invalid ship arguments.");
		}
	}
	
	@Test
	void getRefillCrewCostTest() {
		
		Ship ship = new Ship(ShipModel.BARGE);
		ship.setCrew(ship.getMaxCrew() - 5);
		
		//Blue Sky
		
		//confirm regular refill crew cost
		assertEquals(ship.getRefillCrewCost(false), (ship.getMaxCrew() - ship.getCrew()) * 20);
		
		//confirm cheaper refill crew cost
		assertEquals(ship.getRefillCrewCost(true), (ship.getMaxCrew() - ship.getCrew()) * 10);
		
		//One crew member
		
		ship = new Ship(ShipModel.CUTTER);
		ship.setCrew(1);
		
		//confirm regular refill crew cost
		assertEquals(ship.getRefillCrewCost(false), (ship.getMaxCrew() - ship.getCrew()) * 20);
				
		//confirm cheaper refill crew cost
		assertEquals(ship.getRefillCrewCost(true), (ship.getMaxCrew() - ship.getCrew()) * 10);
		
		//Full crew
		
		ship = new Ship(ShipModel.MERCHANTMAN);
		
		//confirm regular refill crew cost
		assertEquals(ship.getRefillCrewCost(false), 0);
						
		//confirm cheaper refill crew cost
		assertEquals(ship.getRefillCrewCost(true), 0);
	}
	
	@Test
	void getWageCostTest() {
		
		Ship ship = new Ship(ShipModel.SLOOP);
		
		//blue sky
		int duration = 10;
		int expectedWageCost = (int) (ship.getMaxCrew() * 10 * (duration / 24f));
		assertEquals(ship.getWageCost(duration), expectedWageCost);
		
		duration = 32;
		expectedWageCost = (int) (ship.getMaxCrew() * 10 * (duration / 24f));
		assertEquals(ship.getWageCost(duration), expectedWageCost);
		
		//edge case
		//0 time passed should cost 0 wages
		assertEquals(ship.getWageCost(0), 0);
		
		//invalid
		try {
			ship.getWageCost(-1);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
	}
	
	@Test
	void getRepairCostTest() {
		
		Ship ship = new Ship(ShipModel.CUTTER);
		
		//blue sky
		ship.setHull(ship.getMaxHull() - 12);
		int expectedRepairCost = (ship.getMaxHull() - ship.getHull()) * 20;
		assertEquals(ship.getRepairCost(false), expectedRepairCost);
		
		ship.setHull(ship.getMaxHull() - 1);
		expectedRepairCost = (ship.getMaxHull() - ship.getHull()) * 20;
		assertEquals(ship.getRepairCost(false), expectedRepairCost);
		
		//cheaper repair at this island
		ship.setHull(ship.getMaxHull() - 10);
		expectedRepairCost = (ship.getMaxHull() - ship.getHull()) * 10;
		assertEquals(ship.getRepairCost(true), expectedRepairCost);
		
		//ship is not damaged
		ship.setHull(ship.getMaxHull());
		expectedRepairCost = 0;
		assertEquals(ship.getRepairCost(false), expectedRepairCost);
	}
	
	@Test
	void totalCostToLeaveIslandTest() {
		
		Ship ship = new Ship(ShipModel.BARGE);
		Player.setShip(ship);
		
		//blue sky
		ship.setHull(ship.getMaxHull() - 12);
		ship.setCrew(ship.getMaxCrew() - 5);
		int duration = 12;
		int expectedCrewRefillCost = (ship.getMaxCrew() - ship.getCrew()) * 20;
		int expectedRepairCost = (ship.getMaxHull() - ship.getHull()) * 20;
		int expectedWageCost = (int) (ship.getMaxCrew() * 10 * (duration / 24f));
		
		assertEquals(ship.totalCostToLeaveIsland(false, false, duration), expectedCrewRefillCost + expectedRepairCost + expectedWageCost);
		
		//no repairs or crew needed
		ship = new Ship(ShipModel.MERCHANTMAN);
		Player.setShip(ship);
		
		duration = 30;
		expectedCrewRefillCost = 0;
		expectedRepairCost = 0;
		expectedWageCost = (int) (ship.getMaxCrew() * 10 * (duration / 24f));
		
		assertEquals(ship.totalCostToLeaveIsland(false, false, duration), expectedCrewRefillCost + expectedRepairCost + expectedWageCost);
		
		//no travel duration
		ship.setHull(ship.getMaxHull() - 7);
		ship.setCrew(ship.getMaxCrew() - 2);
		duration = 0;
		expectedCrewRefillCost = (ship.getMaxCrew() - ship.getCrew()) * 20;
		expectedRepairCost = (ship.getMaxHull() - ship.getHull()) * 20;
		expectedWageCost = 0;
		
		assertEquals(ship.totalCostToLeaveIsland(false, false, duration), expectedCrewRefillCost + expectedRepairCost + expectedWageCost);
		
	}
	
	@Test
	void addUpgradeTest() {
		
		//Initialize the ship
		Ship ship = new Ship(ShipModel.BARGE);
		
		// add some upgrades
		ship.addUpgrade(Constants.UPGRADE_FLAG);
		ship.addUpgrade(Constants.UPGRADE_CONTRACT);
		ship.addUpgrade(Constants.UPGRADE_HULL);
		ship.addUpgrade(Constants.UPGRADE_CANNONS);
		ship.addUpgrade(Constants.UPGRADE_SAILS);
		
		//Check ship now has 5 upgrades
		assertEquals(ship.getUpgrades().size(), 5);
		
		// Check if upgrades work
		assertEquals(ship.getHull(), Constants.BARGE_MAX_HULL + 20);
		assertEquals(ship.getSpeed(), Constants.BARGE_SPEED + 10);
		assertEquals(ship.getWeaponRating(), Constants.BARGE_WEAPONS + 2);
		
		try {
			ship.addUpgrade(Constants.UPGRADE_HULL);
			fail("Can't add same upgrade twice, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		
		
	}
	
	

}
