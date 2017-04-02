package battleship.backend;

public abstract class Ship {
	private int size;
	private int[] damage;	// Each index represents a region of the ship.
							// Hit = 1, Not hit = 0.
	private int[] coordinates;
	private int direction;
	
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
