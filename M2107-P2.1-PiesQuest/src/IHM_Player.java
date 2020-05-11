/**
 * The interface between the player and the game
 */
public class IHM_Player {
	
	/**
	 * The game being played
	 */
	private Game theGame;
	
	/**
	 * The parameters of the current game
	 */
	private Parameter theParameters;
	
	/**
	 * Creates a new player for the game
	 * @param param the parameters of the game
	 * @param game the game played by the player
	 */
	public IHM_Player(Parameter param, Game game) {
		this.theGame = game;
		this.theParameters = param;
	}
	
	/**
	 * Displays a message to the player
	 * @param message the message to transmit
	 */
	public void inform(String message) {
		//TODO implement the method
	}
	
}
