package battleship.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import battleship.ships.ShipInfo;

public class Fleet {
	
	private JFrame frame;
	private JButton[][] grid;
	private int numRows;
	private int numCols;
	private final int DIM = 60;
	
	public Fleet(int rows, int cols, String t) {
		numRows = rows;
		numCols = cols;
		
		frame = new JFrame();
		frame.setTitle(t);
		frame.setLayout(new GridLayout(rows, cols));
		grid = new JButton[rows][cols];
		makeGrid();
	}
	
	public void makeGrid() {
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				grid[r][c] = new SpaceButton();
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
	
	public void setRollover(boolean state) {
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				grid[r][c].setRolloverEnabled(state);
			}
		}
	}
	
	public void placeShipSpace(int row, int col) {
		grid[row][col].setForeground(Color.GRAY);
	}
}
