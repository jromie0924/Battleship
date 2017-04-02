package battleship;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import battleship.backend.BattleshipInfo;
import battleship.backend.CarrierInfo;
import battleship.backend.CruiserInfo;
import battleship.backend.DestroyerInfo;
import battleship.backend.ShipInfo;
import battleship.backend.SubmarineInfo;

public class Grid {
	private final int DIM_R = 10;
	private final int DIM_C = 10;
	private final int NUM_SHIPS = 5;
	
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
	
	public ShipInfo fitShip(ShipInfo ship) {
		int dir = ship.getDirection();
		int shipSize = ship.getSize();
		Random rand = new Random();
		Space[] shipSpot = new Space[shipSize];
		boolean changeDirection = true;
		
		while(changeDirection) {
			if(dir == 1) {	// Vertical
				int randCol = rand.nextInt(DIM_C);
				boolean validCol = false;
				int startRow = -1;
				while(!validCol) {
					int vacantStreak = 0;
					for(int row = 0; row < DIM_R; row++) {
						if(!spaces[row][randCol].isOccupied()) {
							if(vacantStreak == 0) {
								startRow = row;
							}
							
							vacantStreak++;
							if(vacantStreak == shipSize) {
								validCol = true;
								break;
							}
							
							spaces[row][randCol].setOccupied();
							
						} else {
							vacantStreak = -1;
							startRow = -1;
						}
					}
					
					if(!validCol) {
						randCol = rand.nextInt(DIM_C);
					}
				}
				
				if(startRow != -1) {
					// At this point we have found a starting Space for the ship.
					changeDirection = false;
					int idx = 0;
					for(int row = startRow; row < (startRow + shipSize); row++) {
						shipSpot[idx++] = new Space(row, randCol);
					}
					ship.setCoordinates(shipSpot);
				
				} else {
					if(dir == 0) {
						dir = 1;
						ship.setDirection(dir);
					
					} else {
						dir = 0;
						ship.setDirection(dir);
					}
				}
				
			} else { // Horizontal
				int randRow = rand.nextInt(DIM_R);
				boolean validRow = false;
				int startCol = -1;
				while(!validRow) {
					int vacantStreak = 0;
					for(int col = 0; col < DIM_C; col++) {
						if(!spaces[randRow][col].isOccupied()) {
							if(vacantStreak == 0) {
								startCol = col;
							}
							
							vacantStreak++;
							if(vacantStreak == shipSize) {
								validRow = true;
								break;
								
							}
							
							spaces[randRow][col].setOccupied();
						
						} else {
							vacantStreak = -1;
							startCol = -1;
						}
					}
					
					if(!validRow) {
						randRow = rand.nextInt(DIM_R);
					}
				}
				
				if(startCol != -1) {
					// At this point we have found a starting Space for the ship.
					changeDirection = false;
					int idx = 0;
					for(int col = startCol; col < startCol + shipSize; col++) {
						shipSpot[idx++] = new Space(randRow, col);
					}
					ship.setCoordinates(shipSpot);
					
				} else {
					if(dir == 0) {
						dir = 1;
						ship.setDirection(dir);
					
					} else {
						dir = 0;
						ship.setDirection(dir);
					}
				}
			}
		}
		
		return ship;
	}
	
	/**
	 * Computer player will use this version of fill() to randomly place the ships
	 */
	public void fill() {
		// Fill the ships array
		ships = new ShipInfo[NUM_SHIPS];
		ships[0] = fitShip(new CarrierInfo());
		ships[1] = fitShip(new BattleshipInfo());
		ships[2] = fitShip(new CruiserInfo());
		ships[3] = fitShip(new SubmarineInfo());
		ships[4] = fitShip(new DestroyerInfo());
	}
	
	public static void main(String[] args) {
		
	}
}
