package game;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Launch a game of Pie's Quest
 */
public class GameLauncher {
	
	/**
	 * The main of the game which creates a frame to display the game
	 * @param args arguments of the main, not used
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setTitle("Pie's Quest");
		frame.add(new PlayerInterface(frame), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
