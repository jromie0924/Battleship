package battleship.grid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import battleship.ships.BattleshipInfo;
import battleship.ships.CarrierInfo;
import battleship.ships.CruiserInfo;
import battleship.ships.DestroyerInfo;
import battleship.ships.ShipInfo;
import battleship.ships.SubmarineInfo;

public class Grid {
	
	public static final int DIM_R = 10;
	public static final int DIM_C = 10;
	public final int NUM_SHIPS = 5;
	
	private Space[][] spaces;
	
	private ShipInfo[] ships;
	private ArrayList<Space> alreadyGuessed;
	
	
	public Grid() {
		spaces = new Space[DIM_R][DIM_C];
		for(int row = 0; row < DIM_R; row++) {
			for(int col = 0; col < DIM_C; col++) {
				spaces[row][col] = new Space(row, col);
			}
		}
		
		ships = new ShipInfo[NUM_SHIPS];
		alreadyGuessed = new ArrayList<Space>();
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
					ship.setOccupiedSpaces(shipSpot);
				
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
					ship.setOccupiedSpaces(shipSpot);
					
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
	 * Computer player will use this method to randomly place the ships
	 */
	public void fill() {
		// Fill the ships array
		ships[0] = fitShip(new CarrierInfo());
		ships[1] = fitShip(new BattleshipInfo());
		ships[2] = fitShip(new CruiserInfo());
		ships[3] = fitShip(new SubmarineInfo());
		ships[4] = fitShip(new DestroyerInfo());
	}
	
	/**
	 * Used for when the user places ships on his/her grid.
	 * @param ship
	 * @param shipSpot
	 */
	public boolean place(ShipInfo ship, Space[] shipSpot) {
		for(Space s : shipSpot) {
			int row = s.getRow();
			int col = s.getCol();
			if(spaces[row][col].isOccupied()) {
				return false;
			}
		}
		
		for(Space s : shipSpot) {
			int row = s.getRow();
			int col = s.getCol();
			spaces[row][col].setOccupied();
		}
		
		ship.setOccupiedSpaces(shipSpot);
		int idx = 0;
		while(!(ships[idx] == null)) {
			idx++;
		}
		ships[idx] = ship;
		return true;
	}
	
	public boolean fire(Space target, Grid g) {
		int row = target.getRow();
		int col = target.getCol();
		boolean confirmation = false;
		if(g.checkOccupation(spaces[row][col])) {
			
			// We hit one of the opponent's ships
			for(ShipInfo ship : ships) {
				Space[] occupation = ship.getOccupiedSpaces();
				for(Space s : occupation) {
					if(s.equals(target)) {
						confirmation = ship.hit(row, col);
					}
				}
			}
		}
		
		alreadyGuessed.add(target);
		return confirmation;
	}
	
	public boolean checkOccupation(Space space) {
		int row = space.getRow();
		int col = space.getCol();
		boolean occupied = false;
		if(spaces[row][col].isOccupied()) {
			occupied = true;
		}
		
		return occupied;
	}
	
	public static void main(String[] args) {
		Grid user = new Grid();
		Fleet userGui = new Fleet(Grid.DIM_R, Grid.DIM_C, new Color(20, 90, 204), "Player");
		
	}
}
