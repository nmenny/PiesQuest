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
		this.levels[this.currentLevel].init();
		this.ihm.initMovements();
		this.levels[this.currentLevel].load();
		this.character.setPosition(this.levels[this.currentLevel].getInitialPlayerPosition(this.parameter.getHeight()));
		System.out.println("Level " +(levelId+1) +" loaded");
	}

	/**
	 * display the current level at the screen
	 * @param g the drawing object
	 */
	public void displayLevel(Graphics g) {
		Level currentLevel = this.levels[this.currentLevel];
		currentLevel.display(g, this.parameter.getWidth(), this.parameter.getHeight());
		g.setColor(EnumTiles.Player.tileColor);
		g.fillRect((int)this.character.getPosition().x, (int)this.character.getPosition().y, currentLevel.getTileWidth(), currentLevel.getTileHeight());
		if(this.character.getPosition().y > this.parameter.getHeight()) {
			System.out.println("draw death");
			this.character.die();
		}
	}
	
	/**
	 * displays the selected level at the screen
	 * @param levelId the index of the level
	 * @param g the drawing object
	 */
	public void displayLevel(int levelId, Graphics g) {
		this.currentLevel = levelId;
		this.displayLevel(g);
	}
	
	/**
	 * makes the player move in a give direction
	 * @param direction the given direction
	 */
	public void movePlayer(int direction) {
		boolean[] collisions = this.checkCollisions();
		//If the player will be at a position beyond the 2/3 of the screen width, the tiles moves (if the end of the level is not reached)
		if((this.character.getPosition().x + Character.MOVING_SPEED > ((2 * this.parameter.getWidth()) / 3)) && (direction > 0) && (!this.levels[this.currentLevel].translationMaxReached(this.parameter.getWidth()))) {
			this.levels[this.currentLevel].translationX(1);
		//Else, if the player position is less than the 1/3 of the screen width and if the tiles are not back to normal (the starting offset) the tiles are moving the other way
		} else if((this.character.getPosition().x - Character.MOVING_SPEED < (this.parameter.getWidth() / 3)) && (direction < 0) && (this.levels[this.currentLevel].getOffsetX() != 0)){
			this.levels[this.currentLevel].translationX(-1);
		} else  {  //The player moves
			if(direction < 0 && !collisions[1]) {
				this.character.move(direction);
			} else if (direction > 0 && !collisions[0]) {
				this.character.move(direction);
			}
			
		}
	}
	
	//TODO Handle the translation of the level on the y axis
	/**
	 * Allows the player to jump
	 * @return <tt>true</tt> if the ground is hit, <tt>false</tt> else
	 */
	public boolean jumpPlayer() {
		if(this.character.getCurrentJumpSpeed() <= 0) {
			this.character.fall(); 
		} else{
			this.character.jump();
		}
		boolean[] collisions = this.checkCollisions();
		if(collisions[2]) {
			this.character.currentJumpSpeed = 0;
		}
		if(collisions[3]) {
			this.character.currentJumpSpeed = Character.JUMPING_SPEED;
			this.character.currentFallingSpeed = 1;
			return true;
		}
			
		return false;
	}
	
	/**
	 * Checks the collisions with the player
	 * @return 4 booleans, each indexes has its signification
	 * 			0 => Collision on the right
	 * 			1 => Collision on the left
	 * 			2 => Collision on top
	 * 			3 => Collision on bottom
	 */
	private boolean[] checkCollisions() {
		boolean[] collisions = {false, false, false, false};
		
		String[] level = this.levels[this.currentLevel].getMap();
		int tileHeight = this.levels[this.currentLevel].getTileHeight();
		int tileWidth = this.levels[this.currentLevel].getTileWidth();
		
		int playerX = (int)this.character.getPosition().x;
		int playerY = (int)this.character.getPosition().y + 1;
		
		//Checking the collision with the edges
		if(playerX <= 0) {
			collisions[1] = true;
		}
		if((playerX + tileWidth) >= this.parameter.getWidth()) {
			collisions[0] = true;
		}
		
		int y = this.parameter.getHeight() - tileHeight;
		for(int line = level.length - 1; line >= 0; line--) {
			for(int x = 0; x < level[line].length(); x++) {
				int minTileWidth = x * tileWidth + this.levels[this.currentLevel].getOffsetX() - tileWidth;
				int minTileHeight = y + this.levels[this.currentLevel].getOffsetY();
				int maxTileWidth = minTileWidth + tileWidth;
				int maxTileHeight = y + tileHeight;
				
				//If the tile is a wall
				if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation) {
					//Collision on the top
					if(playerY >= minTileHeight && playerY <= maxTileHeight) {
						if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) >= minTileWidth && (playerX + tileWidth) <= maxTileWidth)) {
							collisions[2] = true;
						}
					}
					
					//Collision on the bottom
					if((playerY + tileHeight) >= minTileHeight && playerY < minTileHeight) {
						if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) >= minTileWidth && (playerX + tileWidth) <= maxTileWidth)) {
							collisions[3] = true;
						}
					}
					
					//Collisions Right
					if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
						if((playerX + tileWidth) == minTileWidth) {
							collisions[0] = true;
						}
					}
					
					//Collisions Left
					if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
						if(playerX == (minTileWidth + tileWidth)) {
							collisions[1] = true;
						}
					}
				}
				
				//If the end of the level is reached, on go to the next level or we end the game
				if(level[line].charAt(x) == EnumTiles.End.charRepresentation) {
					//Collision on the top
					if(playerY >= minTileHeight && playerY <= maxTileHeight) {
						if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) >= minTileWidth && (playerX + tileWidth) <= maxTileWidth)) {
							this.changeLevel();
							break;
						}
					}
					
					//Collision on the bottom
					if((playerY + tileHeight) >= minTileHeight && playerY < minTileHeight) {
						if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) >= minTileWidth && (playerX + tileWidth) <= maxTileWidth)) {
							this.changeLevel();
							break;
						}
					}
					
					//Collisions Right
					if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
						if((playerX + tileWidth) == minTileWidth) {
							this.changeLevel();
							break;
						}
					}
					
					//Collisions Left
					if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
						if(playerX == (minTileWidth + tileWidth)) {
							this.changeLevel();
							break;
						}
					}
				}
				
				/*
				if(level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation) {
				}
				*/
			}
			y -= tileHeight;
		}
		
		
		return collisions;
	}
	
	/**
	 * Changes the current level
	 */
	private void changeLevel() {
		if(this.currentLevel == this.levels.length - 1) { //If we finished the last level
			this.menuDisplayed = 5;
		} else {
			this.chooseLevel(this.currentLevel+ 1);
		}
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
		System.out.println("Game Over ! You've reached the end !");
		System.exit(0);
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

	/**
	 * Makes the player fall if it's not jumping and there's nothing under it
	 */
	public void makeFall() {
		boolean[] coll = this.checkCollisions();
		if(!coll[3]) {
			this.character.fall();
		}
	}
}
