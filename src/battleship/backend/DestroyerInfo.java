package battleship.backend;

import java.util.Random;

import battleship.Space;

public class DestroyerInfo extends ShipInfo {
	
	public DestroyerInfo() {
		size = 2;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
}
