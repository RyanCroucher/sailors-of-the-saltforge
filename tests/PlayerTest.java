package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.InsufficientGoldException;
import exceptions.InsufficientCargoCapacityException;
import exceptions.InsufficientItemQuantityException;
import main.Constants.ShipModel;
import main.GameEnvironment;
import main.Island;
import main.Item;
import main.Player;
import main.Ship;
import main.Store;

class PlayerTest {

	@Test
	void getNetWorthTest() {
		
		GameEnvironment.setupGame();
		Player.setGold(1000);
		Player.setShip(new Ship(ShipModel.MERCHANTMAN));
		Island saltForge = GameEnvironment.getIslands()[0];
		Item luxuryGoods = GameEnvironment.getItems().get(0);
		Item rawMaterial = GameEnvironment.getItems().get(2);
		Item food = GameEnvironment.getItems().get(3);
		saltForge.getIslandStore().randomizeInventory(10, 15);
		
		assertEquals(Player.getNetWorth(), 1000);
		
		//Buy and sell items. Net worth shouldn't change
		try {
		GameEnvironment.buyItem(luxuryGoods, saltForge.getIslandStore(), 10);
		assertEquals(Player.getNetWorth(), 1000);
		GameEnvironment.sellItem(luxuryGoods, saltForge.getIslandStore(), 10);
		assertEquals(Player.getNetWorth(), 1000);
		} catch (InsufficientItemQuantityException | InsufficientCargoCapacityException | InsufficientGoldException | IllegalArgumentException e) {
			assert(false);
		}
		

		// Testing items with modified price
		try {
		GameEnvironment.buyItem(rawMaterial, saltForge.getIslandStore(), 10);
		} catch (InsufficientItemQuantityException | InsufficientCargoCapacityException | InsufficientGoldException | IllegalArgumentException e) {
			assert(false);
		}

		assertEquals(Player.getNetWorth(), 1000);
		// Reset gold to 1000 while we still have cargo
		Player.setGold(1000);
		assertEquals(Player.getNetWorth(), (1000 + saltForge.getIslandStore().getItemPrice(rawMaterial) * Player.getShip().getInventory().getItemQuantity(rawMaterial)));
		
	}

}
