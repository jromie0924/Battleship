package battleship.main;

import javax.swing.JOptionPane;

import battleship.enumerations.PlayerTypes;
import battleship.gameplay.Player;

public class Main {
	public static void main(String[] args) {
		Player user = new Player(PlayerTypes.USER);
		Player computer = new Player(PlayerTypes.COMPUTER);
		boolean gamePlaying = true;
		// Place user & computer ships
		computer.fill();
		user.fill();
		
		// User goes first
		user.myTurn = true;
		
		while(gamePlaying) {
			if(user.myTurn) {
				user.takeTurn(computer);
			
			} else {
				computer.takeTurn(user);
			}
			
			if(user.lost) {
				gamePlaying = false;
				JOptionPane.showMessageDialog(null, "You lost.");
			
			} else if(computer.lost) {
				gamePlaying = false;
				JOptionPane.showMessageDialog(null, "You Won!");
			}
		}
	}
}
