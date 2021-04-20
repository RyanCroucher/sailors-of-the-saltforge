package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Constants.ShipModel;
import main.GameEnvironment;
import main.Island;
import main.Player;
import main.Route;
import main.Ship;

class RouteTest {

	@Test
	void getDestinationsTest() {
		
		//initialize some islands to create routes between
		Island island_a = new Island("A", "this is a test island", null);
		Island island_b = new Island("B", "this is a test island", null);
		Island island_c = new Island("C", "this is a test island", null);
		
		//good pairs
		Island[] pair_one = {island_a, island_b};
		Island[] pair_two = {island_a, island_c};
		
		//bad pairs
		//duplicate
		Island[] pair_three = {island_a, island_b};
		//reversed duplicate
		Island[] pair_four = {island_b, island_a};
		
		
		//good pairs lists
		
		ArrayList<Island[]> pairList_one = new ArrayList<Island[]>();
		pairList_one.add(pair_one);
		pairList_one.add(pair_two);
		
		ArrayList<Island[]> pairList_two = new ArrayList<Island[]>();;
		pairList_two.add(pair_four);
		pairList_two.add(pair_three);
		
		//blue sky
		try {
			Route route_a = new Route("Route A", "this is a test route", 20, 8, pairList_one);
			
			//get all destinations reachable from island_a
			ArrayList<Island> expectedDestinations = new ArrayList<Island>();
			expectedDestinations.add(island_b);
			expectedDestinations.add(island_c);
			
			assertEquals(expectedDestinations, route_a.getPossibleDestinations(island_a));
			
			expectedDestinations = new ArrayList<Island>();
			expectedDestinations.add(island_a);
			
			assertEquals(expectedDestinations, route_a.getPossibleDestinations(island_b));
			
		} catch (IllegalArgumentException e) {
			fail("Valid parameters have thrown an exception");
		}
		
		//invalid risklevel or duration
		//risk level too low
		try {
			Route route_a = new Route("Route A", "this is a test route", -1, 8, pairList_one);
			fail("Invalid risk level given, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//risk level too high
		try {
			Route route_a = new Route("Route A", "this is a test route", 101, 8, pairList_one);
			fail("Invalid risk level given, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//duration too low
		try {
			Route route_a = new Route("Route A", "this is a test route", 10, -1, pairList_one);
			fail("Invalid risk level given, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//duration too high
		try {
			Route route_a = new Route("Route A", "this is a test route", 101, 25, pairList_one);
			fail("Invalid risk level given, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
		//duplicate island pairs in route
		try {
			Route route_b = new Route("Route B", "this is a test route", 20, 8, pairList_two);
			fail("Duplicate island pair given, should throw exception");
			
		} catch (IllegalArgumentException e) {
			assert(true);
		}
		
	}
	
	@Test
	void includesIslandTest() {
		//initialize some islands to create routes between
		Island island_a = new Island("A", "this is a test island", null);
		Island island_b = new Island("B", "this is a test island", null);
		Island island_c = new Island("C", "this is a test island", null);
		
		Island[] pair_one = {island_a, island_b};
		
		ArrayList<Island[]> pairList_one = new ArrayList<Island[]>();
		pairList_one.add(pair_one);
		
		Route route_a = new Route("Route A", "this is a test route", 20, 8, pairList_one);
		
		//route_a should contain islands a and b, but not c
		assertTrue(route_a.includesIsland(island_a));
		assertTrue(route_a.includesIsland(island_b));
		assertFalse(route_a.includesIsland(island_c));
		
	}
	
	@Test
	void getRouteInfoString() {
		
		GameEnvironment.setupGame();
		
		Player.setShip(new Ship(ShipModel.BARGE));
		
		//player is at saltforge
		//get information about each route from here
		Route[] routes = GameEnvironment.getRoutes();
		
		Route tranquilExpanse = GameEnvironment.getRoutes()[0];
		Route basaltSpires = GameEnvironment.getRoutes()[1];
		Route aroundBasaltSpires = GameEnvironment.getRoutes()[2];
		Route jackalSea = GameEnvironment.getRoutes()[5];
		
		assertEquals(tranquilExpanse.getRouteInfoString(), "(48 miles, Encounter Chance: 20%, Travel Time: 2 days 0 hours, Crew and Repairs Cost: 100)");
		assertEquals(basaltSpires.getRouteInfoString(), "(20 miles, Encounter Chance: 40%, Travel Time: 0 days 20 hours, Crew and Repairs Cost: 41)");
		assertEquals(aroundBasaltSpires.getRouteInfoString(), "(32 miles, Encounter Chance: 20%, Travel Time: 1 days 8 hours, Crew and Repairs Cost: 66)");
		assertEquals(jackalSea.getRouteInfoString(), "(28 miles, Encounter Chance: 60%, Travel Time: 1 days 4 hours, Crew and Repairs Cost: 58)");
		
		//Faster ship has less travel time
		Player.setShip(new Ship(ShipModel.CUTTER));
		assertEquals(tranquilExpanse.getRouteInfoString(), "(48 miles, Encounter Chance: 20%, Travel Time: 1 days 4 hours, Crew and Repairs Cost: 116)");

		//Damaged ship costs more
		Player.getShip().setHull(5);
		assertEquals(tranquilExpanse.getRouteInfoString(), "(48 miles, Encounter Chance: 20%, Travel Time: 1 days 4 hours, Crew and Repairs Cost: 816)");
		Player.getShip().setHull(Player.getShip().getMaxHull());
		
		//Hiring crew costs more
		Player.getShip().setCrew(1);
		assertEquals(tranquilExpanse.getRouteInfoString(), "(48 miles, Encounter Chance: 20%, Travel Time: 1 days 4 hours, Crew and Repairs Cost: 496)");

	}

}
