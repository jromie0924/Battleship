package battleship;

import battleship.backend.ShipInfo;

public class Grid {
	private final int DIM_R = 10;
	private final int DIM_C = 10;
	
	private Space[][] spaces;
	
	private ShipInfo[] ships;
	
	
	public Grid() {
		spaces = new Space[DIM_R][DIM_C];
		for(int row = 0; row < DIM_R; row++) {
			for(int col = 0; col < DIM_C; col++) {
				spaces[row][col] = new Space(row, col);
			}
		}
	}
}
