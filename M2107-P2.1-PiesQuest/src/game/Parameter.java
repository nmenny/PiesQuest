package game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

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
	 * Give standard parameters
	 * @param theGame the game which will receive the parameters
	 */
	public Parameter(Game theGame) {
		this.volume = 50;
		this.displayFormat = "800x600";
		this.game = theGame;
	}
	
	/**
	 * Displays the menu at the screen
	 * @param ihm the container to put elements in
	 */
	public void displayMenu(IHM_Player ihm) {
		
		JLabel labelVolume = new JLabel("Volume:");
		JTextPane volume = new JTextPane();
		JLabel labelFormat = new JLabel("Screen format:");
		JTextPane screenFormat = new JTextPane();
		JButton btn = new JButton("Valider");
		
		ihm.add(labelVolume);
		ihm.add(volume);
		ihm.add(labelFormat);
		ihm.add(screenFormat);
		ihm.add(btn);
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
	
}
