package game;

import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

/**
 * Represents the parameters of the game to configure it
 */
public class Parameter {
	
	/**
	 * The sound volume of the game (in percent)
	 */
	private int volume;
	
	/**
	 * The format in which the game will be displayed
	 */
	private String displayFormat;
	
	/**
	 * The game in which the parameters will apply
	 */
	private Game game;

	/**
	 * Is the parameter menu displayed
	 */
	private boolean isDisplayed;
	
	/**
	 * Give standard parameters
	 * @param theGame the game which will receive the parameters
	 */
	public Parameter(Game theGame) {
		this.volume = 50;
		this.displayFormat = "800x600";
		this.game = theGame;
		this.isDisplayed = false;
	}
	
	/**
	 * Displays the menu at the screen
	 * @param ihm the container to put elements in
	 */
	public void displayMenu(JFrame frame) {
		this.setDisplay(true);
		frame.setBackground(Color.BLACK);
		JButton b = new JButton("Hi !");
		frame.add(b);
		frame.setVisible(true);
	}
	
	/**
	 * Sets the new volume
	 * @param theVolume the new volume
	 */
	public void setVolume(int theVolume) {
		//TODO implement the method
	}
	
	/**
	 * Sets the new format of the screen
	 * @param theFormat the new format of the screen
	 */
	public void setFormat(String theFormat) {
		//TODO implement the method
	}
	
	/**
	 * Gives the current volume of the game
	 * @return the volume of the game
	 */
	public int getVolume() {
		return this.volume;
	}
	
	/**
	 * Gives the current display format of the game
	 * @return the current format of the screen
	 */
	public String getDisplayFormat() {
		return this.displayFormat;
	}
	
	/**
	 * Gets screen width
	 * @return the width of the screen
	 */
	public int getWidth() {
		return Integer.parseInt(this.displayFormat.split("x")[0]);
	}
	
	/**
	 * Gets the screen height
	 * @return the height of the screen
	 */
	public int getHeight() {
		return Integer.parseInt(this.displayFormat.split("x")[1]);
	}

	/**
	 * Allows us to know if the parameters are displayed
	 * @return <tt>true</tt> if the parameters are displayed, <tt>false</tt> else
	 */
	public boolean isDisplayed() {
		return this.isDisplayed;
	}
	
	/**
	 * Allows us to close and open the menu
	 * @param isDisplayed <tt>true</tt> if you want to display it, <tt>false</tt> if you want to close it
	 */
	public void setDisplay(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
}
