package battleship.backend;

import battleship.Space;

public class BattleshipInfo extends ShipInfo {

	@Override
	public void place(int row, int col) {
		// TODO Auto-generated method stub
	}

	public BattleshipInfo() {
		size = 4;
		damage = new int[size];
		coordinates = new Space[size];
		direction = 0;
		destroyed = false;
	}
}
