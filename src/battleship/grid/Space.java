package battleship.grid;

/**
 * Space class. This class implements the back-end implementation of a particular space in the grid.
 * For instance it stores information about whether it has been occupied and its location in the grid.
 * @author Jackson
 *
 */
public class Space {
	private int[] position;
	private boolean occupied;
	
	public Space(int row, int col) {
		position = new int[2];
		position[0] = row;
		position[1] = col;
		occupied = false;
	}
	
	public boolean equals(Space other) {
		return (position[0] == other.getRow() && position[1] == other.getCol());
	}
	
	public void setOccupied() {
		occupied = true;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public int getRow() {
		return position[0];
	}
	
	public int getCol() {
		return position[1];
	}
}
