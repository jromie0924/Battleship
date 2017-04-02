package battleship.backend;

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
		direction = 0;
		destroyed = false;
	}
}
