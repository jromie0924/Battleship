package battleship.backend;

import battleship.Space;

public abstract class ShipInfo {
	protected int size;
	protected int[] damage;	// Each index represents a region of the ship.
							// Hit = 1, Not hit = 0.
	protected Space[] coordinates;
	protected int direction; // 0 for horizontal, 1 for vertical.
	protected boolean destroyed;
	
	public abstract void place(int row, int col);
	
	public boolean hit(int idx) {
		if(damage[idx] == 0) {
			damage[idx] = 1;
			
			// Confirmed hit
			return true;
		} else {
			
			// This region of the ship has already been hit. Treat as miss.
			return false;
		}
	}
	
	public void setCoordinates(Space[] coords) {
		for(int idx = 0; idx < coordinates.length; idx++) {
			coordinates[idx] = coords[idx];
		}
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
