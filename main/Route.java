package main;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Each route object has a list of island pairs, where each island in the pair can travel to the other through this route.
 * @author Ryan Croucher rcr69
 *
 */
public class Route {
	
	/**
	 * Represents the likelihood of a random event occurring while traveling this route.
	 */
	private int riskLevel;
	
	/**
	 * The distance between islands via this route.
	 */
	private int distance;
	
	/**
	 * The name of this route.
	 */
	private String name;
	
	/**
	 * A description of traveling the route.
	 */
	private String description;
	
	/**
	 * Stores pairs of Islands that can travel to each other via this route.
	 */
	private ArrayList<Island[]> locationPairs;
	
	/**
	 * 
	 * @param name the name of the route.
	 * @param description a description of traveling the route.
	 * @param riskLevel the relative likelihood of events occurring on the route.
	 * @param distance the distance between islands through this route in miles.
	 * @param locationPairs every pair of islands that can reach each other through this route.
	 */
	public Route(String name, String description, int riskLevel, int distance, ArrayList<Island[]> locationPairs) throws IllegalArgumentException {
		
		if (!validRouteParams(riskLevel, distance, locationPairs)) {
			throw new IllegalArgumentException("Invalid Route params");
		}
		
		this.name = name;
		this.description = description;
		this.riskLevel = riskLevel;
		this.distance = distance;
		this.locationPairs = locationPairs;
	}
	
	private boolean validRouteParams(int riskLevel, int distance, ArrayList<Island[]> locationPairs) {

		if (riskLevel < 0 || riskLevel > 100) {
			return false;
		}
		else if (distance < 0) {
			return false;
		}
		
		else if (locationPairs.size() == 0) {
			return false;
		}
		
		HashSet<ArrayList<Island>> pairsSet = new HashSet<ArrayList<Island>>();
		
		for (Island[] pair : locationPairs) {
			
			if (pair[0] == pair[1]) {
				return false;
			}
			
			ArrayList<Island> pairList = new ArrayList<Island>();
			pairList.add(pair[0]);
			pairList.add(pair[1]);
			
			pairList.sort((o1, o2) -> o1.getIslandName().compareTo(o2.getIslandName()));
			
			if (pairsSet.contains(pairList)) {
				return false;
			}
			else {
				pairsSet.add(pairList);
			}
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * @return the relative risk of traveling this route
	 */
	public int getRiskLevel() {
		return riskLevel;
	}
	
	/**
	 * 
	 * @return the distance of the route in miles
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * 
	 * @return the name of the route
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return the description of traveling the route
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Return whether you can travel to or from an island using this route
	 * @param island the island to check
	 * @return true if route includes this island, else false
	 */
	public boolean includesIsland(Island island) {
		for (Island[] pair : locationPairs) {
			if (pair[0] == island || pair[1] == island)
				return true;
		}
		return false;
	}
	
	/**
	 * Returns a list of all islands reachable by the given island via this route.
	 * @param currentLocation the location from where you want to find all possible destinations via this route
	 * @return a list of all islands reachable by the given island via this route.
	 */
	public ArrayList<Island> getPossibleDestinations(Island currentLocation) {
		
		ArrayList<Island> possibleDestinations = new ArrayList<Island>();
		
		for (Island[] pair : locationPairs) {
			if (pair[0] == currentLocation)
				possibleDestinations.add(pair[1]);
			else if (pair[1] == currentLocation)
				possibleDestinations.add(pair[0]);
		}
		
		return possibleDestinations;
		
	}

}
