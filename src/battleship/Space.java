package battleship;

public class Space {
	private int[] position;
	private boolean occupied;
	
	public Space(int row, int col) {
		position[0] = row;
		position[1] = col;
		occupied = false;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
}
