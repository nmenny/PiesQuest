package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * The selection of the options in the menus
	 */
	private int currentSelection;
	
	/**
	 * Stores all the collected strawberries by level id
	 */
	private Map<Integer, Set<Integer>> collectedStrawberries;
	
	/**
	 * creates a new Game ready to be played
	 * @param theIhm the interface between the game and the player
	 */
	public Game(IHM_Player theIhm) {
		this.ihm = theIhm;
		this.character = new Character("Player1", 3);
		this.parameter = new Parameter(this);
		this.collectedStrawberries = new HashMap<Integer, Set<Integer>>();
		
		//At the initialization, the main menu is displayed
		this.currentSelection = 0;
		try {
			this.levels = Level.loadAllLevels();
			//We create a new set containing the collected strawberries position in a map
			for(int level = 0; level < this.levels.length; level++) {
				this.collectedStrawberries.put(level, new HashSet<Integer>());
			}
			//Unlocks the first level
			this.levels[0].unlock();
			//Loads the previous saved game
			this.load();
		} catch (LevelException e) {
			this.ihm.inform("Error ! Missing level's information ! Please add a name or a description !");
		} catch(FileNotFoundException e) {
			this.ihm.inform("Error ! Missing \"Levels/LevelNames.txt\" file ! Please add it !");
		} catch(IOException e) {
			this.ihm.inform("Error ! Error in file \"Levels/LevelNames.txt\" !");
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
		int width = this.parameter.getWidth(), height = this.parameter.getHeight();
	
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//Display the title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString("CHOOSE THE LEVEL", (width / 2) - 220, height / 6);
		
		int y = 0;
		int x = 0;
		int levelInitialIndex = 0;
		int levelSelection = this.currentSelection;
		int nbLevelDisplayedAtOnce = 8;
		
		//Can can only display "nbLevelDisplayedAtOnce" different levels at the same time, so only the wanted levels will be displayed and not the first ones
		while(levelSelection > nbLevelDisplayedAtOnce) {
			levelInitialIndex += 3;
			levelSelection -= 3;
		}
		for(int level = levelInitialIndex; level < this.levels.length; level++) {
			
			if(level == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else if(this.levels[level].isLock()){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			if(level % 3 == 0 && level != levelInitialIndex) {
				y += 1;
				x = 0;
			}
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(this.levels[level].getName(), ((width / 2) - 110) * x + ((int) this.levels[level].getName().length() / 2), height / 3 + 150 * y);
			x++;
		}
	}
	
	/**
	 * selects a level into the levels and loads it in memory
	 * @param levelId the index of the level
	 */
	public void chooseLevel(int levelId) {
		//Saves the game
		this.save();
		if(levelId >= this.levels.length || levelId < 0) {
			this.ihm.inform("The level at index " +levelId +" does not exist !");
			return;
		}
		if(!this.levels[levelId].isLock()) {
			this.currentLevel = levelId;
			//We clear the collected strawberries position in stored in memory
			this.collectedStrawberries.get(this.currentLevel).clear();
			//Initialize the level
			this.levels[this.currentLevel].init();
			//Initialize the movements
			this.ihm.initMovements();
			//Load the level
			try {
				this.levels[this.currentLevel].load();
				//Set the initial position of the character
				this.character.setPosition(this.levels[this.currentLevel].getInitialPlayerPosition(this.parameter.getHeight(), this.parameter.getWidth()));
				System.out.println("Level " +(levelId+1) +" loaded");
			} catch(FileNotFoundException e) {
				this.ihm.inform("Error ! level " +(this.currentLevel + 1) +"'s file not found !");
				this.ihm.displayMenu(0);
			} catch(IOException e) {
				this.ihm.inform("Error while loading the file \"level" +(this.currentLevel + 1) +".txt\" !");
			} catch(NullPointerException e) {
				this.ihm.inform("Error ! Player starting position was not given for level " +(this.currentLevel + 1));
				this.ihm.displayMenu(0);
			}
		}
	}

	/**
	 * display the current level at the screen
	 * @param g the drawing object
	 */
	public void displayLevel(Graphics g) {
		Level currentLevel = this.levels[this.currentLevel];
		currentLevel.registerCollectedStrawberries(this.collectedStrawberries.get(this.currentLevel));
		currentLevel.display(g, this.parameter.getWidth(), this.parameter.getHeight());
		g.setColor(EnumTiles.Player.tileColor);
		g.fillRect((int)this.character.getPosition().getX(), (int)this.character.getPosition().getY(), currentLevel.getTileWidth(), currentLevel.getTileHeight());
		
		//When the character position is higher than the height of the screen, the player is dead
		if(this.character.getPosition().getY() > this.parameter.getHeight()) {
			this.character.die();
			//If the character's health if below 0, this is game over
			if(this.character.getHealth() <= 0) {
				this.ihm.displayMenu(4);
				this.currentSelection = 0;
				this.character.giveHealth(3); //Reinitializes the life of the character
				this.character.init(); //Reinitialize player attributes
			} else {
				//If he is not dead, the level restarts at the beginning
				this.levels[this.currentLevel].init();
				this.ihm.initMovements();
				this.character.setPosition(this.levels[this.currentLevel].getInitialPlayerPosition(this.parameter.getHeight(), this.parameter.getWidth()));
			}
		}
		
		//Displays the current health of the player on the top right of the screen
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString("Lifes : " +this.character.getHealth(), this.parameter.getWidth() - 70, 15);
		
		//Displays the number of strawberries collected
		g.drawString("Strawberries : " +this.character.getNbStrawberriesCollected(), this.parameter.getWidth() - 150, 35);
	}

	/**
	 * displays the selected level at the screen
	 * @param levelId the index of the level
	 * @param g the drawing object
	 */
	public void displayLevel(int levelId, Graphics g) {
		if(levelId >= this.levels.length || levelId < 0) {
			this.currentLevel = 0;
		} else {
			this.currentLevel = levelId;
		}
		
		this.displayLevel(g);
	}
	
	/**
	 * makes the player move in a give direction
	 * @param direction the given direction
	 */
	public void movePlayer(int direction) {
		boolean[] collisions = this.checkCollisions();
		//If the player will be at a position beyond the 2/3 of the screen width, the tiles moves (if the end of the level is not reached)
		if((this.character.getPosition().getX() + Character.MOVING_SPEED > ((2 * this.parameter.getWidth()) / 3)) && (direction > 0) && (!this.levels[this.currentLevel].translationMaxReached(this.parameter.getWidth()) && !collisions[0])) {
			this.levels[this.currentLevel].translationX(1);
		//Else, if the player position is less than the 1/3 of the screen width and if the tiles are not back to normal (the starting offset) the tiles are moving the other way
		} else if((this.character.getPosition().getX() - Character.MOVING_SPEED < (this.parameter.getWidth() / 3)) && (direction < 0) && (this.levels[this.currentLevel].getOffsetX() != 0)  && !collisions[1]){
			this.levels[this.currentLevel].translationX(-1);
		} else  {  //The player moves
			if(direction < 0 && !collisions[1]) { //On the left
				this.character.move(direction);
			} else if (direction > 0 && !collisions[0]) { //On the right
				this.character.move(direction);
			}
			
		}
	}
	
	/**
	 * Allows the player to jump
	 * @return <tt>true</tt> if the ground is hit, <tt>false</tt> else
	 */
	public boolean jumpPlayer() {
		//If the player is high in the sky, the tiles will move up
		if((this.character.getPosition().getY() - Character.JUMPING_SPEED < (this.parameter.getHeight() / 5)) && this.character.getCurrentJumpSpeed() > 0) {
			this.levels[this.currentLevel].translationY(1);
			this.character.setCurrentJumpSpeed(this.character.getCurrentJumpSpeed() - 1);
		//If the player is too low, the tiles will move down
		} else if((this.character.getPosition().getY() + Character.JUMPING_SPEED > (4*this.parameter.getHeight() / 5)) && this.character.getCurrentJumpSpeed() <= 0 && this.levels[this.currentLevel].getOffsetY() != 0) {
			this.levels[this.currentLevel].translationY(-1);
		} else {
			//If the jumping speed is null, the player falls
			if(this.character.getCurrentJumpSpeed() <= 0) { 
				this.character.fall(); 
			} else{
				this.character.jump();
			}
		}
		
		//Checks the collisions with the player
		boolean[] collisions = this.checkCollisions();
		if(collisions[2]) { //Collision on the top
			this.character.setCurrentJumpSpeed(0);
		}
		if(collisions[3]) { //Collision down
			this.character.setCurrentJumpSpeed(Character.JUMPING_SPEED);
			this.character.setCurrentFallingSpeed(1);
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
		
		int playerX = (int)this.character.getPosition().getX();
		int playerY = (int)this.character.getPosition().getY() + 1;
		
		//The list of collected strawberries of the current level
		Set<Integer> currentListOfStrawberries = this.collectedStrawberries.get(this.currentLevel);
		
		//Checking the collision with the edges
		if(playerX <= 0) {
			collisions[1] = true;
		}
		if((playerX + tileWidth) >= this.parameter.getWidth()) {
			collisions[0] = true;
		}
		
		int y = this.parameter.getHeight() - tileHeight;
		int tileIndex = 0;
		for(int line = level.length - 1; line >= 0; line--) {
			for(int x = 0; x < level[line].length(); x++) {
				
				//The dimensions of the tile
				int minTileWidth = x * tileWidth + this.levels[this.currentLevel].getOffsetX() - tileWidth;
				int minTileHeight = y + this.levels[this.currentLevel].getOffsetY();
				int maxTileWidth = minTileWidth + tileWidth;
				int maxTileHeight = minTileHeight + tileHeight;
				
				boolean collisionDoneWithAStrawberry = false;
				
				//Collisions Right
				if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
					if((playerX + tileWidth) >= minTileWidth && (playerX + tileWidth) <= maxTileWidth) {
						//Collision with a wall
						if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation) {
							collisions[0] = true;
						}
						
						//Collision with the end of the level
						if(level[line].charAt(x) == EnumTiles.End.charRepresentation) {
							this.changeLevel();
							break;
						}
						
						//Collision with a strawberry which hasn't been collected
						if(level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation && (!currentListOfStrawberries.contains(tileIndex))) {
							currentListOfStrawberries.add(tileIndex);
							collisionDoneWithAStrawberry = true;
						}
					}
				}
				
				//Collisions Left
				if((playerY >= minTileHeight && playerY <= maxTileHeight)) {
					if(playerX <= (minTileWidth + tileWidth) && playerX >= (minTileWidth + tileWidth)) {
						//Collision with a wall
						if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation) {
							collisions[1] = true;
						}
						
						//Collision with the end of the level
						if(level[line].charAt(x) == EnumTiles.End.charRepresentation) {
							this.changeLevel();
							break;
						}
						
						//Collision with a strawberry which hasn't been collected
						if(level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation && (!currentListOfStrawberries.contains(tileIndex))) {
							currentListOfStrawberries.add(tileIndex);
							collisionDoneWithAStrawberry = true;
						}
					}	
				}
				
				//Collision on the top
				if(playerY >= minTileHeight && playerY <= maxTileHeight) {
					if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) > minTileWidth && (playerX + tileWidth) < maxTileWidth)) {
						//Collision with a wall
						if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation) {
							collisions[2] = true;
						}
						
						//Collision with the end of the level
						if(level[line].charAt(x) == EnumTiles.End.charRepresentation) {
							this.changeLevel();
							break;
						}
						
						//Collision with a strawberry which hasn't been collected
						if(level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation && (!currentListOfStrawberries.contains(tileIndex))) {
							currentListOfStrawberries.add(tileIndex);
							collisionDoneWithAStrawberry = true;
						}
					}
				}
				
				//Collision on the bottom
				if((playerY + tileHeight) >= minTileHeight && playerY < minTileHeight) {
					if((playerX >= minTileWidth && playerX < maxTileWidth) || ((playerX + tileWidth) > minTileWidth && (playerX + tileWidth) < maxTileWidth)) {
						//Collision with a wall
						if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation) {
							collisions[3] = true;
						}
						
						//Collision with the end of the level
						if(level[line].charAt(x) == EnumTiles.End.charRepresentation) {
							this.changeLevel();
							break;
						}
						
						//Collision with a strawberry which hasn't been collected
						if(level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation && (!currentListOfStrawberries.contains(tileIndex))) {
							currentListOfStrawberries.add(tileIndex);
							collisionDoneWithAStrawberry = true;
						}
					}
				}
				
				//If a collision is done with a strawberry and we have collected a dozen of them; we gain a life
				if(collisionDoneWithAStrawberry) {
					this.character.setNbStrawberriesCollected(this.character.getNbStrawberriesCollected() + 1);
					if((this.character.getNbStrawberriesCollected() % 10) == 0) {
						this.character.giveHealth(1);
					}
				}
				
				if(level[line].charAt(x) == EnumTiles.Wall.charRepresentation || level[line].charAt(x) == EnumTiles.End.charRepresentation || level[line].charAt(x) == EnumTiles.Strawberries.charRepresentation) {
					tileIndex++;
				}
				
			}
			y -= tileHeight;
		}
		
		
		return collisions;
	}
	
	/**
	 * Changes the current level
	 */
	private void changeLevel() {
		//If we finished the last level, it's the end
		if(this.currentLevel >= this.levels.length - 1) { 
			//Saves the game
			this.save();
			this.ihm.displayMenu(5);
			this.currentSelection = 0;
		} else { //Else, the next level starts
			this.levels[this.currentLevel + 1].unlock();
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
	 * Gets the level at a given index
	 * @param levelId the index of the level
	 * @return the level at the given index
	 */
	public Level getLevel(int levelId) {
		if(levelId >= this.levels.length || levelId < 0) {
			return this.levels[0];
		}
		return this.levels[levelId];
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
		String[] menus = {"Back to main menu"};
		int width = this.parameter.getWidth(), height = this.parameter.getHeight();
	
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//The title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("GAME OVER", (width / 2) - 270, height / 3);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(menus[menu], (width / 2) - 210, 2 *height / 3);
		}
	}
	
	/**
	 * Load a game save file
	 */
	public void load() {
		File f = new File("Save/saveFile");
		//If the file exits, we can load it
		if(f.exists()) {
			try {
				FileInputStream fis = new FileInputStream(f);
				
				while(fis.read() != -1) {
					int level = fis.read();
					this.levels[level].unlock();
				}
				fis.close();
			} catch (IOException e) {
				System.err.println("Error ! Save file error !");
			}
		}
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
		
		g.setFont(new Font("Arial", Font.PLAIN, 75));
		g.setColor(new Color(0, 0, 0));
		g.drawString("Pie's Quest", (this.parameter.getWidth() / 2) - 5 * 35, 2*this.parameter.getHeight()/9);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(menus[menu], (this.parameter.getWidth() / 2) - menus[menu].length() * 10, 4*this.parameter.getHeight()/9 + menu * this.parameter.getHeight()/9);
		}
	}
	
	/**
	 * displays the victory screen to the player
	 * @param g the drawing object
	 */
	public void displayVictoryScreen(Graphics g) {
		String[] menus = {"Back to main menu"};
		int width = this.parameter.getWidth(), height = this.parameter.getHeight();
	
		
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//Displays the title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("VICTORY", (width / 2) - 210, height / 3);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(menus[menu], (width / 2) - 210, 2 *height / 3);
		}
	}
	
	/**
	 * saves the game into a game save file
	 */
	public void save() {
		File f = new File("Save/saveFile");
		try {
			FileOutputStream fos = new FileOutputStream(f);
		    DataOutputStream dos = new DataOutputStream(fos);
			
			//Get the unlocked levels
			List<Integer> levelUnlockedIndexes = new ArrayList<Integer>();
			for(int level = 0; level < this.levels.length; level++) {
				if(!this.levels[level].isLock()) {
					levelUnlockedIndexes.add(level);
				}
			}
			//Each unlocked level indexes are stored in a file
			for(int unlockLevel : levelUnlockedIndexes) {
				dos.writeInt(unlockLevel);
			}
			
			dos.close();
			fos.close();
			
		} catch (IOException e) {
			System.err.println("Error ! Save file error !");
		}
	}
	
	/**
	 * Moves the selection attribute in a given direction
	 * @param go the direction of the move
	 */
	public void gotoSelect(int go) {
		this.currentSelection += go;
		//If the selection is less than 0, we go to the last option
		if(this.currentSelection < 0) {
			this.currentSelection = this.getNumberOption() - 1;
		}
		//If the selection is more or equals to the number of menus, we go to the first option
		if(this.currentSelection >= this.getNumberOption()) {
			this.currentSelection = 0;
		}
	}
	
	/**
	 * Gets the number of options possible for the current menu
	 * @return the number of option of the current menu
	 */
	private int getNumberOption() {
		//For each menu, there's a defined number of menus
		switch(this.ihm.getDisplayedMenu()) {
		case 0:
			return 4;
		case 2:
			return this.levels.length;
		case 4:
			return 1;
		case 5: 
			return 1;
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
	 * Sets the selection cursor
	 * @param theSelection the menu selection index
	 */
	public void setCurrentSelection(int theSelection) {
		if(theSelection < 0 || theSelection >= this.getNumberOption()) {
			this.currentSelection = 0;
		} else {
			this.currentSelection = theSelection;
		}
		
	}

	/**
	 * Makes the player fall if it's not jumping and there's nothing under it
	 */
	public void makeFall() {
		boolean[] coll = this.checkCollisions();
		//If there's no collision on the bottom, the character falls
		if(!coll[3]) {
			if((this.character.getPosition().getY() + Character.JUMPING_SPEED > (4*this.parameter.getHeight() / 5)) && this.levels[this.currentLevel].getOffsetY() != 0) {
				this.levels[this.currentLevel].translationY(-1);
			} else {
				this.character.fall();
			}
		}
	}
}
