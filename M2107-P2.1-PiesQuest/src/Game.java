import java.awt.Graphics;

/**
 * Represents a game of Pies's Quest
 */
public class Game {
	
	/**
	 * The level which is being played
	 */
	private int currentLevel;
	
	/**
	 * the player's character
	 */
	private final Character character;
	
	/**
	 * the parameter which will give some information about the window
	 */
	private final Parameter parameter;
	
	/**
	 * The levels of the game
	 */
	private Level[] levels;
	
	/**
	 * The interface between the game and the player
	 */
	private final IHM_Player ihm;
	
	/**
	 * Allows us to know which screen has to be displayed by looking at the value
	 * 0 - main menu displayed
	 * 1 - parameters displayed
	 * 2 - level selection displayed
	 * 3 - level displayed
	 * 4 - game over screen displayed
	 * 5 - victory screen displayed
	 */
	public int menuDisplayed;
	
	/**
	 * creates a new Game ready to be played
	 * @param theIhm the interface between the game and the player
	 */
	public Game(IHM_Player theIhm) {
		this.ihm = theIhm;
		this.character = new Character("Player1", 100);
		this.parameter = new Parameter(this);
		
		//At the initialization, the main menu is displayed
		this.menuDisplayed = 0;
	}
	
	/**
	 * Applies the modifications to the window
	 * @param theNewVolume the new sound volume
	 * @param theNewFormat the new screen format
	 */
	public void applyModification(int theNewVolume, String theNewFormat) {
		//TODO implement the method
	}
	
	/**
	 * Displays all the levels at the screen
	 * @param g the drawing object
	 */
	public void displayAllLevels(Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * selects a level into the levels
	 * @param levelId the index of the level
	 */
	public void chooseLevel(int levelId) {
		//TODO implement the method
	}
	
	/**
	 * display the current level at the screen
	 * @param g the drawing object
	 */
	public void displayLevel(Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * displays the selected level at the screen
	 * @param levelId the index of the level
	 * @param g the drawing object
	 */
	public void displayLevel(int levelId, Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * makes the player move in a give direction
	 * @param direction the given direction
	 */
	public void movePlayer(int direction) {
		//TODO implement the method
	}
	
	/**
	 * Allows the player to jump
	 */
	public void jumpPlayer() {
		//TODO implement the method
	}
	
	/**
	 * gives the index of the current level
	 * @return the current level
	 */
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	/**
	 * Gets the current parameters of the game
	 * @return the parameter of the game
	 */
	public Parameter getParameter() {
		return this.parameter;
	}
	
	/**
	 * displays the game over screen to the player
	 * @param g the drawing object
	 */
	public void displayGameOver(Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * Load a game save file
	 */
	public void loadElements() {
		//TODO implement the method
	}
	
	/**
	 * displays the main screen to the player
	 * @param g the drawing object
	 */
	public void displayMainScreen(Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * displays the victory screen to the player
	 * @param g the drawing object
	 */
	public void displayVictoryScreen(Graphics g) {
		//TODO implement the method
	}
	
	/**
	 * saves the game into a game save file
	 */
	public void save() {
		//TODO implement the method
	}
}
