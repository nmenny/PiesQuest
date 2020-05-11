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
	 */
	public IHM_Player() {
		this.theGame = new Game(this);
		this.theParameters = this.theGame.getParameter();
	}
	
	/**
	 * Displays a message to the player
	 * @param message the message to transmit
	 */
	public void inform(String message) {
		//TODO implement the method
	}
	
}
