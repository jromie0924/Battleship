package battleship.grid;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class SpaceButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Color pressedColor;
	private Color hoverBorderColor;
	private int row;
	private int col;
	
	public SpaceButton() {
		this(null, -1, -1);
	}
	
	public SpaceButton(String text, int r, int c) {
		super(text);
		super.setContentAreaFilled(false);
		hoverBorderColor = new Color(255, 81, 81);
		pressedColor = this.getForeground();
		row = r;
		col = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(getModel().isPressed()) {
			g.setColor(pressedColor);
		
		} else if(getModel().isRollover()) {
			g.setColor(hoverBorderColor);
		
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
