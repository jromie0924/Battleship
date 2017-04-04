package battleship.ships;

import java.util.Random;

import battleship.grid.Space;

/**
 * Submarine class has a size of 3.
 * @author Jackson
 *
 */
public class SubmarineInfo extends ShipInfo {
	
	public SubmarineInfo() {
		size = 3;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
	
	public String getType() {
		return "Submarine";
	}
}
