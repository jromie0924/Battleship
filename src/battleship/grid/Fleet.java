package battleship.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class Fleet {
	private JFrame frame;
	private SpaceButton[][] grid;
	
	public Fleet(int rows, int cols) {
		frame = new JFrame();
		frame.setLayout(new GridLayout(rows, cols));
		grid = new SpaceButton[rows][cols];
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				grid[r][c] = new SpaceButton();
				grid[r][c].setPreferredSize(new Dimension(60, 60));
				grid[r][c].setBackground(new Color(20, 90, 204));
				grid[r][c].setRolloverEnabled(true);
				
				frame.add(grid[r][c]);
			}
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
