package game;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the parameters of the game to configure it
 */
public class Parameter {
	
	/**
	 * The sound volume of the game (in percent)
	 */
	private int volume;
	
	/**
	 * The format in which the game will be displayed (format: width_x_height)
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
		this.displayFormat = "1080x720"; //The default display format
		this.game = theGame;
		this.isDisplayed = false;
	}
	
	/**
	 * Displays the menu at the screen
	 * @param frame the container to put elements in
	 * @param theInterface the interface
	 */
	public void displayMenu(JFrame frame, PlayerInterface theInterface) {
		this.setDisplay(true);
		
		//Creating a panel to contain the format selector
		JPanel panelFormat = new JPanel();
		
		String[] formatValues = {"800x600", "1920x1080"}; //The values held in the Combo box
		JLabel formatLabel = new JLabel("Format : ");
		JComboBox cmb = new JComboBox(formatValues);
		cmb.setSelectedIndex(1);
		cmb.setVisible(true);
		
		panelFormat.add(formatLabel);
		panelFormat.add(cmb);
		
		frame.add(panelFormat);
		
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
