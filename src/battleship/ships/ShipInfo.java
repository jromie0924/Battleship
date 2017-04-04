package battleship.ships;

import battleship.grid.Space;

/**
 * Abstract class ShipInfo is the parent class for each of the 5 ships in this game of battleship.
 * @author Jackson
 *
 */
public abstract class ShipInfo {
	protected int size;
	protected int[] damage;	// Each index represents a region of the ship.
							// Hit = 1, Not hit = 0.
	protected Space[] coordinates;
	protected int direction; // 0 for horizontal, 1 for vertical.
	protected boolean destroyed;
	
	public abstract String getType();
	
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
