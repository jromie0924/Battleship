package battleship.grid;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class SpaceButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Color pressedColor;
	private Color hoverBorderColor;
	private Color backgroundColor;
	
	public SpaceButton() {
		this(null);
	}
	
	public SpaceButton(String text) {
		super(text);
		super.setContentAreaFilled(false);
		backgroundColor = new Color(20, 90, 204);
		hoverBorderColor = new Color(255, 81, 81);
		pressedColor = backgroundColor;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(getModel().isPressed()) {
			g.setColor(pressedColor);
		
		} else if(getModel().isRollover()) {
			g.setColor(hoverBorderColor);
		
		} else {
			g.setColor(backgroundColor);
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
