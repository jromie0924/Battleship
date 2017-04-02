package battleship;

public class Space {
	private int[] position;
	private boolean occupied;
	
	public Space(int row, int col) {
		position = new int[2];
		position[0] = row;
		position[1] = col;
		occupied = false;
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
