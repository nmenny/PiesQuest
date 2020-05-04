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
	
}
