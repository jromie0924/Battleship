package battleship.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import battleship.ships.Ship;

public class UserGrid {
	
	private JFrame frame;
	private JButton[][] grid;
	private int numRows;
	private int numCols;
	private final int DIM = 60;
	private boolean indicateClicked;
	private int[] targetCoords;
	
	public UserGrid(int rows, int cols, String t) {
		numRows = rows;
		numCols = cols;
		targetCoords = new int[2];
		targetCoords[0] = -1;
		targetCoords[1] = -1;
		indicateClicked = false;
		
		frame = new JFrame();
		frame.setTitle(t);
		frame.setLayout(new GridLayout(rows, cols));
		grid = new JButton[rows][cols];
		makeGrid();
	}
	
	/** 
	 * Creates all initial buttons for the grid
	 */
	public void makeGrid() {
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				grid[r][c] = new SpaceButton(null, r, c);
				grid[r][c].setForeground(new Color(20, 90, 204));
				grid[r][c].setPreferredSize(new Dimension(DIM, DIM));
				grid[r][c].setRolloverEnabled(false);
				
				frame.add(grid[r][c]);
			}
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Creates the targeting grid for the user.
	 * This grid is green (the user's ships grid is blue).
	 */
	public void setTargetGrid() {
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				grid[r][c].setForeground(Color.GREEN);
				@SuppressWarnings("unused")
				MouseAdapter ma;
				grid[r][c].addMouseListener(ma = new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						SpaceButton source = (SpaceButton) arg0.getSource();
						targetCoords[0] = source.getRow();
						targetCoords[1] = source.getCol();
						indicateClicked = true;
						MouseListener[] listeners = source.getMouseListeners();
						for(MouseListener l : listeners) {
							source.removeMouseListener(l);
						}
					}
				});
			}
		}
	}
	
	/**
	 * @return targetCoords[] array
	 * This list is of length 2.
	 * Index 0 is the row of the targeted cell.
	 * Index 1 is the column of the targeted cell.
	 */
	public int[] getCoords() {
		return targetCoords;
	}
	
	public boolean checkForFire() {
		try{
			Thread.sleep(100);
		} catch(InterruptedException e) {}
		
		if(indicateClicked) {
			indicateClicked = false;
			return true;
		
		} else {
			return false;
		}
	}
	
	/**
	 * Changes the color of a square specified by the parameters to red.
	 * Indicates that the computer has hit one of the user's ships.
	 * @param row
	 * @param col
	 */
	public void imHit(int row, int col) {
		grid[row][col].setForeground(Color.RED);
	}
	
	/**
	 * Changes the color of a square specified by the parameters to white.
	 * Indicates that the computer has missed the target.
	 * @param row
	 * @param col
	 */
	public void theyMissed(int row, int col) {
		grid[row][col].setForeground(Color.WHITE);
	}
	
	/**
	 * This method is meant for the user to see that they have hit one of the computer's ships.
	 * The indicated square will be colored red if targetHit is true, and white if targetHit is false.
	 * @param row
	 * @param col
	 * @param targetHit
	 */
	public void displayHit(int row, int col, boolean targetHit) {
		if(targetHit) {
			grid[row][col].setForeground(Color.RED);
		
		} else {
			grid[row][col].setForeground(Color.WHITE);
		}
	}
	
	/**
	 * Places all ships in the user's ships grid.
	 * @param ships
	 */
	public void placeShips(Ship[] ships) {
		for(Ship ship : ships) {
			for(Space s : ship.getOccupiedSpaces()) {
				int row = s.getRow();
				int col = s.getCol();
				grid[row][col].setForeground(Color.GRAY);
			}
		}
	}
}
