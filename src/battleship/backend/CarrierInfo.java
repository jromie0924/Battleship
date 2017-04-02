package battleship.backend;

import battleship.Space;

public class CarrierInfo extends ShipInfo {

	@Override
	public void place(int row, int col) {
		// TODO Auto-generated method stub
	}
	
	public CarrierInfo() {
		size = 5;
		damage = new int[size];
		coordinates = new Space[5];
		direction = 0;
		destroyed = false;
	}
}
