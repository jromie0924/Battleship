package battleship.backend;

import java.util.Random;

import battleship.Space;

public class SubmarineInfo extends ShipInfo {

	@Override
	public void place(int row, int col) {
		// TODO Auto-generated method stub

	}
	
	public SubmarineInfo() {
		size = 3;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
}
