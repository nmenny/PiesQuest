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
	 * creates a new Game ready to be played
	 * @param theIhm the interface between the game and the player
	 */
	public Game(IHM_Player theIhm) {
		this.ihm = theIhm;
		this.character = new Character("Player1", 100);
		this.parameter = new Parameter(this);
	}
	
	/**
	 * Applies the modifications to the window
	 * @param theNewVolume the new sound volume
	 * @param theNewFormat the new screen format
	 */
	public void applyModification(int theNewVolume, String theNewFormat) {
		
	}
	
	/**
	 * Displays all the levels at the screen
	 */
	public void displayAllLevels() {
		
	}
	
	/**
	 * selects a level into the levels
	 * @param levelId the index of the level
	 */
	public void chooseLevel(int levelId) {
		
	}
	
	/**
	 * displays the selected level at the screen
	 * @param levelId the index of the level
	 */
	public void displayLevel(int levelId) {
		
	}
	
	/**
	 * makes the player move in a give direction
	 * @param direction the given direction
	 */
	public void movePlayer(int direction) {
		
	}
	
	/**
	 * Allows the player to jump
	 */
	public void jumpPlayer() {
		
	}
	
	/**
	 * gives the index of the current level
	 * @return the current level
	 */
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	/**
	 * displays the game over screen to the player
	 */
	public void displayGameOver() {
		
	}
	
	/**
	 * Load a game save file
	 */
	public void loadElements() {
		
	}
	
	/**
	 * displays the main screen to the player
	 */
	public void displayMainScreen() {
		
	}
	
	/**
	 * displays the victory screen to the player
	 */
	public void displayVictoryScreen() {
		
	}
	
	/**
	 * saves the game into a game save file
	 */
	public void save() {
		
	}
}
