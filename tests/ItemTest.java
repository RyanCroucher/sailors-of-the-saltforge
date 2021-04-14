package tests;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Item;

class ItemTest {

	@Test
	void itemConstructionTest() {
		
		String name = "coal";
		int basePrice = 30;
		
		// Initialize a valid item
		try {
		Item testItem = new Item(name, basePrice);
		
		assertEquals(testItem.getName(), name);
		assertEquals(testItem.getBasePrice(), basePrice);	
		} catch(IllegalArgumentException e) {
			fail("Should not be an Exception");
		}
		//Boundary test
		
		try {
			
			Item boundaryItem = new Item(name, 1);
			
		} catch(IllegalArgumentException e) {
			fail("Shouldn't be an exception");
		}
		//Initialize an Invalid item
		Item invalidItem;
		
		try {
			
			invalidItem = new Item(name, 0);
			fail("Should have thrown exception");
			
		} catch(IllegalArgumentException e) {
			
			assert(true);
		}
		try {
			
			invalidItem = new Item(name, -1);
			fail("Should have thrown exception");
			
		} catch(IllegalArgumentException e) {
			
			assert(true);
		}
		
	}

}
