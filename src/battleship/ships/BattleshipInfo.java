package battleship.ships;

import java.util.Random;

import battleship.grid.Space;

public class BattleshipInfo extends ShipInfo {

	public BattleshipInfo() {
		size = 4;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
}
