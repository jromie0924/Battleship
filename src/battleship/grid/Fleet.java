package battleship.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class Fleet {
	
	private JFrame frame;
	private SpaceButton[][] grid;
	private int numRows;
	private int numCols;
	
	public Fleet(int rows, int cols, Color color, String t) {
		numRows = rows;
		numCols = cols;
		
		frame = new JFrame();
		frame.setTitle(t);
		frame.setLayout(new GridLayout(rows, cols));
		grid = new SpaceButton[rows][cols];
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				grid[r][c] = new SpaceButton();
				grid[r][c].setPreferredSize(new Dimension(60, 60));
				grid[r][c].setBackground(color);
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
}
