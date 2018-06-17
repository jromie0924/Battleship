package battleship.ships;

import java.util.Random;

import battleship.enumerations.ShipTypes;
import battleship.gameplay.Space;

/**
 * Class Ship defines the structure and functionality of a Ship
 * @author Jackson
 *
 */
public class Ship {
	protected ShipTypes shipType;
	protected int size;
	protected int[] damage;
	protected Space[] coordinates;
	protected int direction; // 0 for horizontal, 1 for vertical.
	protected boolean destroyed;
	
	public Ship(ShipTypes type) {
		switch(type) {
			case CARRIER:
				size = 5;
				break;
			case BATTLESHIP:
				size = 4;
				break;
			case CRUISER:
				size = 3;
				break;
			case SUBMARINE:
				size = 3;
				break;
			case DESTROYER:
				size = 2;
				break;
		}
		
		shipType = type;
		damage = new int[size];
		coordinates = new Space[size];
		destroyed = false;
		
		Random rand = new Random();
		direction = rand.nextInt(2);
	}
	
	public ShipTypes getType() {
		return this.shipType;
	}
	
	public boolean hit(int row, int col) {
		int idx = 0;
		if(!destroyed) {
			for(int a = 0; a < coordinates.length; a++) {
				if(coordinates[a].getRow() == row && coordinates[a].getCol() == col) {
					idx = a;
					break;
				}
			}
			
			if(damage[idx] == 0) {
				damage[idx] = 1;
				int numHits = 0;
				for(int d : damage) {
					numHits += d;
				}
				
				if(numHits == damage.length) {
					destroyed = true;
				}
				
				// Confirmed hit
				return true;
			} else {
				
				// This region of the ship has already been hit. Treat as miss.
				return false;
			}
		
		} else {
			return false;
		}
	}
	
	public void setOccupiedSpaces(Space[] coords) {
		for(int idx = 0; idx < coordinates.length; idx++) {
			coordinates[idx] = coords[idx];
		}
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Space[] getOccupiedSpaces() {
		return coordinates;
	}
	
	public void setDirection(int dir) {
		direction = dir;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getSize() {
		return size;
	}
}
