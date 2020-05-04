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
	 * Give standard parameters
	 */
	public Parameter() {
		this.volume = 50;
		this.displayFormat = "1920x1080";
	}
	
	/**
	 * Displays the menu at the screen
	 */
	public void displayMenu() {
		
	}
	
	/**
	 * Sets the new volume
	 * @param theVolume the new volume
	 */
	public void setVolume(int theVolume) {
		
	}
	
	/**
	 * Sets the new format of the screen
	 * @param theFormat the new format of the screen
	 */
	public void setFormat(String theFormat) {
		
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
	
	
	
}
