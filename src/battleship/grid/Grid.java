package battleship.grid;

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
	private UserGrid myShips;
	private UserGrid myTargetGrid;
	private boolean isUser;
	public boolean myTurn;
	
	
	public Grid(boolean user) {
		isUser = user;
		spaces = new Space[DIM_R][DIM_C];
		for(int row = 0; row < DIM_R; row++) {
			for(int col = 0; col < DIM_C; col++) {
				spaces[row][col] = new Space(row, col);
			}
		}
		
		myTurn = false;
		ships = new ShipInfo[NUM_SHIPS];
		alreadyGuessed = new ArrayList<Space>();
	}
	
	public ShipInfo[] getShips() {
		return ships;
	}
	
	public ShipInfo fitShip(ShipInfo ship) {
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
	 * Computer player will use this method to randomly place the ships
	 */
	public void fill() {
		// Fill the ships array
		ships[0] = fitShip(new CarrierInfo());
		ships[1] = fitShip(new BattleshipInfo());
		ships[2] = fitShip(new CruiserInfo());
		ships[3] = fitShip(new SubmarineInfo());
		ships[4] = fitShip(new DestroyerInfo());
		
		if(isUser) {
			myShips = new UserGrid(DIM_R, DIM_C, "Player's Ships");
			myShips.placeShips(ships);
			
			myTargetGrid = new UserGrid(DIM_R, DIM_C, "Player's Target Screen");
			myTargetGrid.setTargetGrid();
		}
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
	
	public boolean damageShip(int row, int col) {
		for(ShipInfo ship : ships) {
			for(Space s : ship.getOccupiedSpaces()) {
				if(s.getRow() == row && s.getCol() == col) {
					ship.hit(row, col);
					
					if(isUser) {
						try {
							Thread.sleep(200);

						} catch(InterruptedException e) {}
						
						myShips.imHit(row, col);
						if(ship.isDestroyed()) {
							JOptionPane.showMessageDialog(null, "Your " + ship.getType() + " has been destroyed.");
						}
					
					} else {
						if(ship.isDestroyed()) {
							JOptionPane.showMessageDialog(null, "You destroyed your opponent's " + ship.getType());
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
			
			myShips.theyMissed(row, col);
		}
		
		return false;
	}
	
	public void play(Grid opponent) {
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
	
	public static void main(String[] args) {
		Grid user = new Grid(true);
		Grid computer = new Grid(false);
		boolean gamePlaying = true;
		// Place user & computer ships
		computer.fill();
		user.fill();
		
		while(gamePlaying) {
			if(user.myTurn) {
				user.play(computer);
			
			} else {
				computer.play(user);
			}
		}
	}
}
