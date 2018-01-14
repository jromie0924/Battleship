package battleship.gameplay;

/**
 * Space class. This class implements the back-end implementation of a particular space in the grid.
 * For instance it stores information about whether it has been occupied and its location in the grid.
 * @author Jackson
 *
 */
public class Space {
	private int row;
	private int column;
	private boolean occupied;
	
	public Space(int row, int column) {
		this.row = row;
		this.column = column;
		occupied = false;
	}
	
	public boolean equals(Space other) {
		return (row == other.getRow() && column == other.getCol());
	}
	
	public void setOccupied() {
		occupied = true;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
}
