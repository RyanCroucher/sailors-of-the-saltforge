package main;

/**
 * Contains static values used in multiple places that will remain fixed for the entire game
 * @author Ryan Croucher rcr69
 *
 */
public class Constants {
	//
	// Constant Strings
	//
	
	public final static String NAME_CURRENCY = "Gold Crowns";

	public final static String PRIMER = "You are a proud dwarf from The Saltforge, but the mountain-island is in decline." 
						+ "\n\nThe costs to repair the decaying mountainside are rising ever higher."
						+ "\n\nOld Saltbeard himself has tasked you to set forth and acquire 10,000 " + NAME_CURRENCY + " to return to your home."
						+ "\n\nIt would be unwise to disobey him.\n";
	
	public final static String ISLAND_SALTFORGE = "The Salt Forge";

	public final static String ISLAND_SALTFORGE_DESCRIPTION = "Welcome to The Salt Forge, the home to the salt dwarves ruled by the great Old Saltbeard, the saltiest of them all."
						+ "\n\nAs you pull into port on the mountainous island, you can see that the fortress-city is in a state of disrepair, buildings and roads need upkeep, and parts of the cliff are eroding, occassionally falling into areas of the city."
						+ "\n\nWhen you arrive at the market however you are overwhelmed by the sound of smithies working in their forges and the sight of stacks of iron and other materials."
						+ "\nI’m sure you won’t find it hard to to buy raw materials to take with you here, it’s also said that the dwarves will pay big coin for beers and ales, I hope you have some on board.";
	
	public final static String ISLAND_TUNIA = "The Grand Duchy of Tunia";

	public final static String ISLAND_TUNIA_DESCRIPTION = "Welcome to the Grand Duchy of Tunia. It is ruled by the Grand Duke Frederick Von Kraus, and is home to the richest and greediest people along the coasts."
						+ "\n\nAfter you manage to deal with the bureaucracy of coming into port, you see a grand city ahead of you filled with all the expected pomp and circumstance you have heard about."
						+ "\nIt boasts arguably the largest and busiest of marketplaces you’ve ever witnessed, with people trying to flog their wares to you from every angle."
						+ "\n\nA large number of these are brewers and vinters telling you they have the best beers and wines you’ll ever have tasted."
						+ "\nWith the number of people around here hawking alcohols of various varieties you should be able to haggle some good prices for them, that being said if you have anything aboard that might help someone flaunt their wealth you might be able to make a fair bit of coin here.";
	
	public final static String ISLAND_SANDYFIELDS= "Sandy Fields";

	public final static String ISLAND_SANDYFIELDS_DESCRIPTION = "Welcome to Sandy Fields. A town led by a peasant syndicate, it is the home to some of the hardest working and humblest farmers this side of the continent."
						+ "\nYou dock at their pier and take in the sights of this humble town. There are no major defensive walls or castles in sight. For as far as your eyes can see there are fields of every variety you can imagine, with farmhouses dotted amongst them."
						+ "\n\nJust down from the pier you find the marketplace a fairly busy place yet no one seems to be forcing their wares upon you. Most of the people selling goods have wagons filled with many different foodstuffs, wheat, pumpkins, fruits and meats. Each merchant is happy to sell you whatever you need."
						+ "\n\nThese people have ample food to go around and have no need of high prices; all foodstuffs seem to be incredibly cheap here. However, you have heard a few people mention they need more materials such as wood for more barns and metals for their tools. Perhaps you could oblige these needs?";
						
	public final static String ISLAND_SKULLHAVEN = "Skull Haven";

	public final static String ISLAND_SKULLHAVEN_DESCRIPTION = "YARRR!!! Welcome to Skull Haven. The hidden island home of the worst and most dangerous people around."
						+ "\n\nThis place doesn’t seem to have a stable leader but someone new appears to take the title by force once a week or so. The port is unkempt and riddled with ships flying a variety of colours, you can see in the city that fights break out at the slightest of arguments and drunkards can be found littering the streets."
						+ "\n\nWhat they pass as a marketplace has seen better days and doesn’t have a lot to offer you, however they are very eager to purchase food and alcohol from you if you have it and are more than happy to use their ill gotten gains to get it. If you’re brave enough and think you can handle them, you may also be able to aquire some cheap labour to help top up your crew here.";
						
	public final static String ISLAND_SEANOMADS = "The Nomad Flotilla";

	public final static String ISLAND_SEANOMADS_DESCRIPTION = "Welcome to the flotilla of the Sea Nomads. This is an artificial island made of connected ships and is ruled by “The Mother”, a particularly old and wise woman among the nomads."
						+"\n\nUsually quite hard to find as they go where the currents take them, they have of late found themselves staying a bit more sedentary so they can trade for the things they need more easily."
						+"\nWhen you pull alongside the island and attach your ship you can see that it appears to function as any town would; on the deck you see tents that the people work and socialise in during the day while they go below deck at night to their homes."
						+"\n\nTheir marketplace is filled with fancy items, hand-made by the Nomad artisans. A lot of which are made using pearls found in the oysters that are a staple of the Nomad’s diets."
						+"\nWith the lack of natural resources here we should be able to sell them wood and metals to make repairs to the ships and foods to supplement their seafood heavy diet. In return, perhaps we can negotiate better deals for these hand-made goods to sell on in other ports.";
	
	// Route blurbs
	
	public final static String ROUTE_TRANQUIL_EXPANSE_DESCRIPTION = "You sail through the Tranquil Expanse - a wide, gentle sea, reknowned for it’s easy sailing. "
																	+ "\nThe vast ocean spreads before you in a seemingly endless expanse of azure blue and gentle breezes. "
																	+ "\n\nYour sailors rejoice as they haul in bulging nets teeming with fish and you feel a sense of calm wash over you, this is where you belong, on the open sea. ";
	
	public final static String ROUTE_BASALT_SPIRES_DESCRIPTION = "You sail through the Basalt Spires – a treacherous region between Tunia and the Saltforge where great basalt spires thrust up from the sea bed and breach the surface. "
																+ "\n\nWaves thunder and collapse into white spray upon the mysterious terrain, soaking your crew as they frantically use long poles to push the ship away from disaster. "
																+ "\n\nLegend says that the spires were once the enormous tentacles of a sea monster who preyed upon entire fleets, dragging them into the depths. "
																+ "\nOne day the sea monster preyed upon the wrong ship, and the mighty sorceror on board cast a spell and turned it to stone. "
																+ "\n\nThese days, pirates and similar miscreants love to hide their ships behind the basalt spires and ambush unsuspecting merchants. ";
	
	public final static String ROUTE_AROUND_BASALT_DESCRIPTION = "You direct your helmsman to carefully skirt Around the Basalt Spires – a treacherous region between Tunia and the Salt Forge. "
																+ "\n\nThis adds time to the journey, but lowers the risk of being ambushed by any ill-doers lurking amongst and behind the huge rock pillars.";
	
	public final static String ROUTE_SHIPWRECKER_SHOALS_DESCRIPTION = "You skilfully guide your ship through the Shipwrecker Shoals, a deadly shallow reef that has claimed the lives of countless sailors who dared to navigate through. "
																	+ "\n\nIt was not by mistake that Old Saltbeard chose you for this job and lent you his old map, you prove your worth as you tersely bark orders at your crew, dodging shipwrecks and hidden shallows alike as you follow the dubious directions. "
																	+ "\n\nEventually, you heave a great sigh of relief as you reach the other side. ";
	
	public final static String ROUTE_JACKAL_SEA_DESCRIPTION = "You grit your teeth as you reluctantly order your helmsman to steer you into the Jackal Sea – a dark and stormy sea surrounding the mysterious Skull Island. "
															+ "Only the brave, foolish or criminal would risk this route, as it is mostly populated by pirates. "
															+ "\n\nThe corsairs of this region circle their areas like jackals, waiting for a vessel (merchant, farmers or even other pirates) to venture too close, "
															+ "whenceforth they raise their black flag and aim their cannons, seizing bounty or blood at their whim. ";
	
	public final static String ROUTE_OYSTER_BAY_DESCRIPTION = "Having completed your business, you set sail through Oyster Bay – a heavily patrolled region between the Grand Duchy of Tunia, and the floatilla of the Sea Nomads. "
															+ "\n\nThe sea passage is thick with trade, and dotted with hundreds of diving bells – inverted and weighted barrels designed to carry their occupant down within an air pocket "
															+ "to hunt for valuable pearls in the oyster fields upon the seabed. "
															+ "\n\nMany of the saltier Tunisian sailors on nearby ships call out insults to the nomadic pearl divers, but they pay little mind as they acquire their fortunes. ";
	
	
	
	// Ship upgrades

	public final static String UPGRADE_CANNONS = "Dwarven Dragons";
	public final static String UPGRADE_HULL = "Iron Cladding";
	public final static String UPGRADE_FLAG = "Jolly Roger";
	public final static String UPGRADE_CONTRACT = "Exclusive Trading Contract";
	public final static String UPGRADE_SAILS = "Artisinal Sails";
	
	public final static int UPGRADE_COST = 1000;
	
	// Event Descriptions
	
	//Storm
	public final static String EVENT_STORM_DESCRIPTION = "Whilst discussing the day's events on the deck with your First Mate Semli, the ship is suddenly engulfed in shadow."
												 + "\nYou look up to see a black cloud forming, and without warning the heavens open up and winds start gusting."
												 + "\nSemli turns to you “This don’t look good Captain, what shall we do?”";
	
	public final static String EVENT_STORM_OPTION_RISKY = "'Batten the hatches and raise the sails, we see the storm through.'"
														+ "\nThe crew hurry to make preparations to weather the storm."
														+ "\nOnce the storm passes you exit your cabin and tell Semli to do a head count of the crew whilst you check for damage to the ship."
														+ "\n"
														+ "\nYou’ve weathered the storm.";
	
	public final static String EVENT_STORM_OPTION_SAFE = "'Turn the ship around and make for port, we’ll wait the storm out there.'"
													   + "\nThe crew hurry to prepare the ship to go back to port."
													   + "\nYou arrive back in port and wait until you see a break in the weather before attempting your route again."
													   + "\n"
													   + "\nFinally, there’s a break in the storm and you reach your destination. ";
	
	//Rescued Sailors
	public final static String EVENT_RESCUE_DESCRIPTION = "Whilst having a drink in your cabin you hear a commotion outside. You exit to hear Sugnar yelling “There’s men in the water, ease the sails we have to collect them.”"
														+ "\n You bring the ship alongside the stranded men and throw down lines for them to climb aboard."
														+ "\nThey explain they were caught in a storm, and they were surely goners if you hadn’t rescued them."
														+ "\nThey offer you a reward of coin for their lives."
														+ "\n"
														+ "\nYou rescued some sailors!";
	
	//Dead in the water
	public final static String EVENT_RESCUE_TWO_DESCRIPTION = "Sugnar calls down from his nest that he sees a motionless body in the water ahead. As you sail past, your men haul the body aboard, and see that it is clutching at some sort of object."
			+ "\nYou pry the object from its grasp. It is made of solid gold and riddled with jewels. It should fetch a pretty penny in port."
			+ "\n"
			+ "\nYou sell the object for " + NAME_CURRENCY + "!";
	
	//Something from beneath
	public final static String EVENT_FROM_BENEATH_DESCRIPTION = "In the dead of night, you lie sleeping in your cabin. Suddenly you are tossed from your bed by an impact of some sort."
															  + "\nAs you stand up, something seems wrong. You hear commotion on the deck of the ship."
															  + "\nYou emerge to find many of your crew looking over the edge of the ship waving lanterns at the water."
															  + "\nYou hear them talking about the crew on patrol seeing something large emerge from the water before the impact."
															  + "\nSome of them seem terrified, adamant that it wasn’t a whale but maybe something ancient from the depths below."
															  + "\nYou don’t have time for the conspiracies, all you can do is assess the damage and do a head count of the crew and make sure you can get to port."
															  + "\n"
															  + "\nSomething from the depths attacked you.";

	//Pirate attack
	public final static String EVENT_PIRATE_ATTACK_DESCRIPTION = "It has been a peaceful trip thus far; you have been checking your ledger with Olard trying to figure out what best to sell at the next port."
															   + "\n\nYou are startled at the sound of the horn in the crow's nest."
															   + "\nYou exit onto the deck to hear Sugnar yelling down from the crow's nest, “Ship approaching fast, no colours and black sails. I think it’s pirates, Captain.”"
															   + "\n\nThe ship is abuzz as the crew scurry to positions awaiting your orders. "
															   + "\n"
															   + "\nAvast ye!!! We have been set upon by Pirates. We must make a decison Captain. ";
	
	public final static String EVENT_PIRATE_ATTACK_FIGHT_SUCCESS = "Your men man the guns and you unleash devastating volleys against your attackers whilst you skilfully manoeuvre the ship to avoid crippling damage to the ship."
		    + "\nThe enemy limp away with their tails beneath their legs.";
	
	public final static String EVENT_PIRATE_ATTACK_FIGHT_FAIL = "Your men man the guns and prepare for battle. They fight well but they are out matched, and you have had to surrender to save the remaining mens lives."
			+ "\nThe pirates hook their ship to yours and come aboard, ready to judge if your goods are enough to satisfy their greed.";
	
	public final static String EVENT_PIRATE_ATTACK_RUN_SUCCESS = "Your crew jump to action to loose the sails."
			+ "\nAs if the gods themselves were watching you, a lucky gust of wind fills the sails, allowing you to surge ahead in the water and leaving the pirates in your wake."
			+ "\n"
			+ "\nYou Escaped!";
	
	public final static String EVENT_PIRATE_ATTACK_RUN_FAIL = "Your crew jump to action to loose the sails, the sails catch a big gust of wind and just as you feel"
			+ "\nthe ship roll forward with speed the worst happens - your main sail has torn!"
			+ "\nYou are now dead in the water."
			+ "\nThe pirates approach and hook their ship to yours and board ready to judge if your goods are enough to satisfy their greed."
			+ "\n"
			+ "\nThey caught you.";
	
	public final static String EVENT_PIRATE_ATTACK_SURRENDER = "We cannot beat them boys, we must surrender!!"
			+ "\n"
			+ "\nReluctantly the crew agree that surrender is the only way to survive the encounter, you just hope they take it easy on you because you gave them what they wanted. You have Surrendered.";
	
	public final static String EVENT_PIRATE_ATTACK_SURRENDER_FAIL = "The pirates board the ship, sneering at your defeated crew. After looting all of your merchandise, the pirates seem angry at the pitiful haul. "
		 	+ "Their anger builds into a rage, and before you know it, you and your crew are surrounded by a hooting cacophony of pirates with drawn cutlasses. With shrieks of glee, they force your men one by one to walk the plank."
		 	+ "\n\nYour last thought before you sink into the icy depths is about your home, the Saltforge, and how it will now be doomed to ruin.";
	
	public final static String EVENT_PIRATE_ATTACK_SURRENDER_SUCCESS = "The pirates board the ship, sneering at your defeated crew."
			 + "\n\nAfter looting all of your merchandise, they seem content to leave you and your men alive, perhaps to prey upon another day.";
	
	
	//other flavour text
	public final static String LOTHAR_MUMBLES = "You find your Bosun Lothar where he is mumbling to himself in the corner, and demand to know the state of your ship.\nHe tells you:";
	
	public final static String OLARD_LEDGER = "As you enter the cabin of the Quartermaster, Olard, you inquire about the ship's ledger, but he motions his hand to shush you. He appears to kneeling before a great book, deep in prayer."
			+ "\n\nAfter an awkward few minutes, he stands up and gestures to a small book on a nearby table. 'Oh, the ledger is over there.'";
	
	public final static String CRAGNUS_UPGRADE = "As though anticipating your need, your Navigator Cragnus appears behind you right as you went to find him. Smelling faintly of brimstone, he grins at you from behind a particularly wide and flat nose."
											+ "\n\n'Aye, cap'n?' he smirks at you as though leery of your authority."
											+ "\n'You're the Navigator, Cragnus, so I need you to find something for me. Go see what this island might offer for our ship.'"
											+ "\n\nSome time later, he finds you on the ship, though you didn't see him coming.";
	
	
	//
	// Game Settings
	//
	public final static int MIN_GAME_DURATION = 20;
	public final static int MAX_GAME_DURATION = 50;
	public final static int PLAYER_START_GOLD = 1000;
	
	
	//
	// Ship Models
	//
	
	public static enum ShipModel {
		MERCHANTMAN,
		CUTTER,
		SLOOP,
		BARGE
	};
	
	//
	// Ship Base Values
	//
	
	// Merchantman
	public final static int MERCHANTMAN_CARGO_CAPACITY = 50;
	public final static int MERCHANTMAN_MAX_HULL = 40;
	public final static int MERCHANTMAN_SPEED = 20;
	public final static int MERCHANTMAN_WEAPONS = 4;
	public final static int MERCHANTMAN_MAX_CREW = 20;
	
	public final static String MERCHANTMAN_INFO_STRING = "Merchantman      (Cargo capacity: Medium, hull: Medium, speed: Medium, weapons: Medium, wages: Medium)";
	
	// Elven Cutter
	public final static int CUTTER_CARGO_CAPACITY = 50;
	public final static int CUTTER_MAX_HULL = 40;
	public final static int CUTTER_SPEED = 40;
	public final static int CUTTER_WEAPONS = 2;
	public final static int CUTTER_MAX_CREW = 20;
	
	public final static String CUTTER_INFO_STRING = "Elven Cutter     (Cargo capacity: Medium, hull: Medium, speed: Fast,   weapons: Low,    wages: Medium)";
	
	// Tunian War-Sloop
	public final static int SLOOP_CARGO_CAPACITY = 50;
	public final static int SLOOP_MAX_HULL = 60;
	public final static int SLOOP_SPEED = 20;
	public final static int SLOOP_WEAPONS = 6;
	public final static int SLOOP_MAX_CREW = 30;
	
	public final static String SLOOP_INFO_STRING = "Tunian War-Sloop (Cargo capacity: Medium, hull: High,   speed: Medium, weapons: High,   wages: High)";
	
	// Dwarven Barge
	public final static int BARGE_CARGO_CAPACITY = 80;
	public final static int BARGE_MAX_HULL = 60;
	public final static int BARGE_SPEED = 0;
	public final static int BARGE_WEAPONS = 2;
	public final static int BARGE_MAX_CREW = 10;
	
	public final static String BARGE_INFO_STRING = "Dwarven Barge    (Cargo capacity: High,   hull: High,   speed: Slow,   weapons: Low,    wages: Low)";
	
	// Absolute Bounds (Ship values must be between these)
	public final static int SHIP_MIN_MAX_HULL = 20;
	public final static int SHIP_MAX_MAX_HULL = 100;
	
	public final static int SHIP_MIN_CARGO_CAPACITY = 20;
	public final static int SHIP_MAX_CARGO_CAPACITY = 100;
	
	public final static int SHIP_MIN_SPEED = 0;
	public final static int SHIP_MAX_SPEED = 100;
	
	public final static int SHIP_MIN_WEAPONS = 0;
	public final static int SHIP_MAX_WEAPONS = 10;
	
	public final static int SHIP_MIN_MAX_CREW = 1;
	public final static int SHIP_MAX_MAX_CREW = 50;
	
	
	//ITEM stats
	public final static int ITEM_BASE_PRICE_LUXURY = 100;
	public final static int ITEM_BASE_PRICE_ALCOHOL = 50;
	public final static int ITEM_BASE_PRICE_RAW_MATERIALS = 50;
	public final static int ITEM_BASE_PRICE_FOOD = 20;
	
	//GUI settings
	public final static int WINDOW_WIDTH = 1920;
	public final static int WINDOW_HEIGHT = 1080;
}
