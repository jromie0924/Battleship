package battleship.backend;

import java.util.Random;

import battleship.Space;

public class CarrierInfo extends ShipInfo {
	
	public CarrierInfo() {
		size = 5;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
}
