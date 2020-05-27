import java.awt.Color;
import java.awt.Font;
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
	 * The selection of the options in the menus
	 */
	private int currentSelection;
	
	/**
	 * Stores the previous direction of the player movement
	 */
	private int previousDirection;
	
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
		this.currentSelection = 0;
		this.previousDirection = 0;
		try {
			this.levels = Level.loadAllLevels();
		} catch (LevelException e) {
			System.err.println("Error while loading the levels, missing level information !");
		}
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
	 * selects a level into the levels and loads it in memory
	 * @param levelId the index of the level
	 */
	public void chooseLevel(int levelId) {
		this.currentLevel = levelId;
		this.levels[this.currentLevel].load();
		this.character.setPosition(this.levels[this.currentLevel].getInitialPlayerPosition(this.parameter.getHeight()));
	}

	/**
	 * display the current level at the screen
	 * @param g the drawing object
	 */
	public void displayLevel(Graphics g) {
		Level currentLevel = this.levels[this.currentLevel];
		currentLevel.display(g, this.parameter.getWidth(), this.parameter.getHeight());
		g.setColor(EnumTiles.Player.tileColor);
		g.fillRect(this.character.getPosition().x, this.character.getPosition().y, currentLevel.getTileWidth(), currentLevel.getTileHeight());
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
		if(direction < 0) {
			this.character.move(direction);
		}
		//If the player will be at a position beyond the 2/3 of the screen width, the tiles moves
		if(this.character.getPosition().x + Character.MOVING_SPEED >= ((2 * this.parameter.getWidth()) / 3)) {
			this.levels[this.currentLevel].translation(direction);
		} else { //The player moves
			this.character.move(direction);
		}
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
		String[] menus = {"Start", "Choose level", "Parameters", "Quit"};
		int width = this.parameter.getWidth(), height = this.parameter.getHeight();
		
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		for(int menu = 0; menu < menus.length; menu++) {
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(menus[menu], (width / 2) - 120, 120 + menu * 120);
		}
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
	
	/**
	 * Moves the selection attribute in a given direction
	 * @param go the direction of the move
	 */
	public void gotoSelect(int go) {
		this.currentSelection += go;
		if(this.currentSelection < 0) {
			this.currentSelection = this.getNumberOption() - 1;
		}
		if(this.currentSelection >= this.getNumberOption()) {
			this.currentSelection = 0;
		}
	}
	
	/**
	 * Gets the number of options possible for the current menu
	 * @return the number of option of the current menu
	 */
	private int getNumberOption() {
		switch(this.currentLevel) {
		case 0:
			return 4;
		case 1:

		case 2:

		case 3:

		case 4:

		case 5: 

		default: 
			return 0;
		}
	}
	
	/**
	 * Gets the current option in the displayed menu
	 * @return the current option selected in the displayed menu
	 */
	public int getCurrentSelection() {
		return this.currentSelection;
	}
}
