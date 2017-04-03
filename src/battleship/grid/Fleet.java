package battleship.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
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
				JButton currentButton = grid[r][c];
				currentButton.setPreferredSize(new Dimension(60, 60));
				currentButton.setBackground(new Color(20, 90, 204));
				currentButton.setRolloverEnabled(true);
				
				frame.add(currentButton);
			}
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
