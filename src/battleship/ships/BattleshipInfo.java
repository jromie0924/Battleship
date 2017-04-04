package battleship.ships;

import java.util.Random;

import battleship.grid.Space;

/**
 * Battleship class has a size of 4.
 * @author Jackson
 *
 */
public class BattleshipInfo extends ShipInfo {

	public BattleshipInfo() {
		size = 4;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
	
	public String getType() {
		return "Battleship";
	}
}
