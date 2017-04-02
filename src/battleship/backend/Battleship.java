package battleship.backend;

import battleship.Space;

public class Battleship extends ShipInfo {

	@Override
	public void place(int row, int col) {
		// TODO Auto-generated method stub
	}

	public Battleship() {
		size = 4;
		damage = new int[size];
		coordinates = new Space[size];
		direction = 0;
		destroyed = false;
	}
}
