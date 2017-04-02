package battleship.backend;

import battleship.Space;

public abstract class ShipInfo {
	protected int size;
	protected int[] damage;	// Each index represents a region of the ship.
							// Hit = 1, Not hit = 0.
	protected Space[] coordinates;
	protected int direction;
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
}
