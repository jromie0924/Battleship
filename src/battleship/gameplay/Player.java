package battleship.gameplay;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import battleship.enumerations.ShipTypes;
import battleship.enumerations.PlayerTypes;
import battleship.enumerations.RewardLevels;
import battleship.ships.Ship;
import battleship.userInterface.UserGrid;

public class Player {
	
	public static final int DIM_R = 10;
	public static final int DIM_C = 10;
	public final int NUM_SHIPS = 5;
	
	private Space[][] spaces;
	private Ship[] ships;
	private ArrayList<Space> alreadyGuessed;
	private UserGrid myShips;
	private UserGrid myTargetGrid;
	private boolean isUser;
	public boolean myTurn;
	public boolean lost;
	public RewardLevels reward;
	
	public Player(PlayerTypes user) {
		isUser = user == PlayerTypes.USER ? true : false;
		spaces = new Space[DIM_R][DIM_C];
		for(int row = 0; row < DIM_R; row++) {
			for(int col = 0; col < DIM_C; col++) {
				spaces[row][col] = new Space(row, col);
			}
		}
		
		myTurn = false;
		lost = false;
		reward = RewardLevels.LEVEL_ONE;
		ships = new Ship[NUM_SHIPS];
		alreadyGuessed = new ArrayList<Space>();
	}
	
	public Ship[] getShips() {
		return ships;
	}
	
	/**
	 * This method fits a ship in the grid.
	 * It needs to be sure that the ship will not "collide" with any other already-placed ships, and ships must be contained within the grid.
	 * @param ship
	 * @return
	 */
	public Ship fitShip(Ship ship) {
		int dir = ship.getDirection();
		int shipSize = ship.getSize();
		Random rand = new Random();
		boolean changeDirection = true;
		
		while(changeDirection) {
			if(dir == 0) {	// Horizontal
				int randRow = rand.nextInt(DIM_R);
				boolean validRow = false;
				int startCol = -1;
				int counter = 0;
				while(!validRow) {
					int col = rand.nextInt(DIM_C);
					if(DIM_C - col >= shipSize) {
						boolean validStartPoint = true;
						for(int c = col; c < col + shipSize; c++) {
							if(spaces[randRow][c].isOccupied()) {
								validStartPoint = false;
							}
						}
						
						if(validStartPoint) {
							startCol = col;
							validRow = true;
							for(int c = col; c < col + shipSize; c++) {
								spaces[randRow][c].setOccupied();
							}
						
						} else {
							if(++counter > (DIM_R + DIM_C) / 2) {
								break;
							}
						}
					}
				}
				
				if(validRow) {
					Space[] occupation = new Space[shipSize];
					int idx = 0;
					for(int c = startCol; c < startCol + shipSize; c++) {
						occupation[idx++] = spaces[randRow][c];
					}
					
					ship.setOccupiedSpaces(occupation);
					changeDirection = false;
				
				} else {
					dir = 1;
					ship.setDirection(dir);
				}
			
			} else if(dir == 1) { // Vertical
				int randCol = rand.nextInt(DIM_C);
				boolean validCol = false;
				int startRow = -1;
				int counter = 0;
				while(!validCol) {
					int row = rand.nextInt(DIM_R);
					if(DIM_R - row >= shipSize) {
						boolean validStartPoint = true;
						for(int r = row; r < row + shipSize; r++) {
							if(spaces[r][randCol].isOccupied()) {
								validStartPoint = false;
							}
						}
						
						if(validStartPoint) {
							startRow = row;
							validCol = true;
							for(int r = row; r < row + shipSize; r++) {
								spaces[r][randCol].setOccupied();
							}
						
						} else {
							if(++counter > (DIM_R + DIM_C) / 2) {
								break;
							}
						}
					}
				}
				
				if(validCol) {
					Space[] occupation = new Space[shipSize];
					int idx = 0;
					for(int r = startRow; r < startRow + shipSize; r++) {
						occupation[idx++] = spaces[r][randCol];
					}
					
					ship.setOccupiedSpaces(occupation);
					changeDirection = false;
				
				} else {
					dir = 0;
					ship.setDirection(dir);
				}
			}
		}
		
		return ship;
	}
	
	/**
	 * Each player will use this method to randomly place the ships
	 */
	public void fill() {
		// Fill the ships array
		ships[0] = fitShip(new Ship(ShipTypes.CARRIER));
		ships[1] = fitShip(new Ship(ShipTypes.BATTLESHIP));
		ships[2] = fitShip(new Ship(ShipTypes.CRUISER));
		ships[3] = fitShip(new Ship(ShipTypes.SUBMARINE));
		ships[4] = fitShip(new Ship(ShipTypes.DESTROYER));
		
		if(isUser) {
			myShips = new UserGrid(DIM_R, DIM_C, "Your Ships");
			myShips.placeShips(ships);
			
			myTargetGrid = new UserGrid(DIM_R, DIM_C, "Your Target Screen");
			myTargetGrid.setTargetGrid();
		}
	}
	
	/**
	 * Was the shot a hit?
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public boolean damageShip(int row, int col) {
		for(Ship ship : ships) {
			for(Space s : ship.getOccupiedSpaces()) {
				if(s.getRow() == row && s.getCol() == col) {
					ship.hit(row, col);
					
					if(isUser) {
						try {
							Thread.sleep(200);

						} catch(InterruptedException e) {}
						
						myShips.imHit(row, col);
						if(ship.isDestroyed()) {
							JOptionPane.showMessageDialog(null, "Your " + ship.getType().name().toLowerCase() + " has been destroyed.");
							boolean allDestroyed = true;
							for(Ship aShip : ships) {
								if(!aShip.isDestroyed()) {
									allDestroyed = false;
									break;
								}
							}
							
							if(allDestroyed) {
								lost = true;
							}
						}
					
					} else {
						if(ship.isDestroyed()) {
							JOptionPane.showMessageDialog(null, "You destroyed your opponent's " + ship.getType().name().toLowerCase());
							boolean allDestroyed = true;
							for(Ship aShip : ships) {
								if(!aShip.isDestroyed()) {
									allDestroyed = false;
									break;
								}
							}
							
							if(allDestroyed) {
								lost = true;
							}
						}
					}
					
					return true;
				}
			}
		}
		
		if(isUser) {
			try {
				Thread.sleep(200);

			} catch(InterruptedException e) {}
			
			myShips.miss(row, col);
		}
		
		return false;
	}

	public void takeTurn(Player opponent) {
		if(isUser) {
			boolean fired = myTargetGrid.checkForFire();
			if(fired) {
				int[] coords = myTargetGrid.getCoords();
				int row = coords[0];
				int col = coords[1];
				
				
				if(opponent.damageShip(row, col)) {	// Target hit
					myTargetGrid.displayHit(row, col, true);
					
				} else {
					myTargetGrid.displayHit(row, col, false);
				}
				
				myTurn = false;
			}
		
		} else {
			Random rand = new Random();
			int row = -1;
			int col = -1;
			boolean duplicate = true;
			while(duplicate) {
				row = rand.nextInt(DIM_R);
				col = rand.nextInt(DIM_C);
				duplicate = false;
				for(int idx = 0; idx < alreadyGuessed.size(); idx++) {
					Space current = alreadyGuessed.get(idx);
					if(current.getRow() == row && current.getCol() == col) {
						duplicate = true;
						break;
					}
				}
			}
			
			if(opponent.damageShip(row, col)) {}
			
			alreadyGuessed.add(spaces[row][col]);
			opponent.myTurn = true;
		}
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
}
