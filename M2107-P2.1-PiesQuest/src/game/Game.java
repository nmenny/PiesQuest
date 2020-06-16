package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	 * The menu handler allowing us to move in the menus
	 */
	private final MenuHandler menuHandler;
	
	/**
	 * The levels of the game
	 */
	private Level[] levels;
	
	/**
	 * The interface between the game and the player
	 */
	private final PlayerInterface playerInterface;
	
	/**
	 * Stores all the collected strawberries by level id
	 */
	private Map<Integer, Set<Integer>> collectedStrawberries;
	
	/**
	 * creates a new Game ready to be played
	 * @param theInterface the interface between the game and the player
	 */
	public Game(PlayerInterface theInterface) {
		this.playerInterface = theInterface;
		this.menuHandler = new MenuHandler(this);
		this.character = new Character("Player1", 3);
		this.parameter = new Parameter();
		this.collectedStrawberries = new HashMap<Integer, Set<Integer>>();

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
			this.playerInterface.inform("Error ! Missing level's information ! Please add a name or a description !");
		} catch(FileNotFoundException e) {
			this.playerInterface.inform("Error ! Missing \"Levels/LevelNames.txt\" file ! Please add it !");
		} catch(IOException e) {
			this.playerInterface.inform("Error ! Error in file \"Levels/LevelNames.txt\" !");
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
	 * selects a level into the levels and loads it in memory
	 * @param levelId the index of the level
	 */
	public void chooseLevel(int levelId) {
		//Saves the game
		this.save();
		if(levelId >= this.levels.length || levelId < 0) {
			this.playerInterface.inform("The level at index " +levelId +" does not exist !");
			return;
		}
		if(!this.levels[levelId].isLocked()) {
			this.currentLevel = levelId;
			//We clear the collected strawberries position in stored in memory
			this.collectedStrawberries.get(this.currentLevel).clear();
			//Initialize the level
			this.levels[this.currentLevel].init();
			//Initialize the movements
			this.playerInterface.initMovements();
			//Load the level
			try {
				String levelFile = Level.LEVEL_FILE_PATH +"level" +this.levels[this.currentLevel].getName().split("_")[0] +".txt";
				this.levels[this.currentLevel].load(new BufferedReader(new FileReader(levelFile)));
				//Set the initial position of the character
				this.character.setPosition(this.levels[this.currentLevel].getInitialPlayerPosition(this.parameter.getHeight(), this.parameter.getWidth()));
			} catch(FileNotFoundException e) {
				this.playerInterface.inform("Error ! level " +(this.currentLevel + 1) +"'s file not found !");
				this.playerInterface.displayMenu(0);
			} catch(IOException e) {
				this.playerInterface.inform("Error while loading the file \"level" +(this.currentLevel + 1) +".txt\" !");
			} catch(NullPointerException e) {
				this.playerInterface.inform("Error ! Player starting position was not given for level " +(this.currentLevel + 1));
				this.playerInterface.displayMenu(0);
			}
		}
	}
	
	/**
	 * makes the player move in a give direction
	 * @param direction the given direction
	 * 		If less than 0 : the player moves to the left
	 * 		If more than 0 : the player moves to the right
	 * 		Else : Nothing happens
	 */
	public void movePlayer(int direction) {
		boolean[] collisions = this.checkCollisions();
		//If the player will be at a position beyond the 2/3 of the screen width, the tiles moves (if the end of the level is not reached)
		if((this.character.getPosition().getX() + Character.MOVING_SPEED > ((2 * this.parameter.getWidth()) / 3)) && (direction == EnumMovements.RIGHT.value) && (!this.levels[this.currentLevel].maximumScrollReached(this.parameter.getWidth()) && !collisions[0])) {
			this.levels[this.currentLevel].scrollX(EnumMovements.RIGHT.value);
		//Else, if the player position is less than the 1/3 of the screen width and if the tiles are not back to normal (the starting offset) the tiles are moving the other way
		} else if((this.character.getPosition().getX() - Character.MOVING_SPEED < (this.parameter.getWidth() / 3)) && (direction == EnumMovements.LEFT.value) && (this.levels[this.currentLevel].getOffsetX() != 0)  && !collisions[1]){
			this.levels[this.currentLevel].scrollX(EnumMovements.LEFT.value);
		} else  {  //The player moves
			if(direction == EnumMovements.LEFT.value && !collisions[1]) { //On the left
				this.character.move(direction);
			} else if (direction == EnumMovements.RIGHT.value && !collisions[0]) { //On the right
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
		if((this.character.getPosition().getY() < (this.parameter.getHeight() / 5)) && this.character.getCurrentJumpSpeed() > 0) {
			this.levels[this.currentLevel].scrollY(1);
			this.character.setCurrentJumpSpeed(this.character.getCurrentJumpSpeed() - 1);
		//If the player is too low, the tiles will move down
		} else if((this.character.getPosition().getY() > (4*this.parameter.getHeight() / 5)) && this.character.getCurrentJumpSpeed() <= 0 && this.levels[this.currentLevel].getOffsetY() != 0) {
			this.levels[this.currentLevel].scrollY(-1);
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
			this.playerInterface.displayMenu(5);
			this.menuHandler.setCurrentSelection(0);
		} else { //Else, the next level starts
			this.levels[this.currentLevel + 1].unlock();
			this.chooseLevel(this.currentLevel+ 1);
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
				this.playerInterface.displayMenu(4);
				this.menuHandler.setCurrentSelection(0);
				this.character.giveHealth(3); //Reinitializes the life of the character
				this.character.init(); //Reinitialize player attributes
			} else {
				//If he is not dead, the level restarts at the beginning
				this.levels[this.currentLevel].init();
				this.playerInterface.initMovements();
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
	 * gives the index of the current level
	 * @return the current level
	 */
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	/**
	 * Gets the level at a given index
	 * @param levelId the index of the level
	 * @return the level at the given index or the first level if the levelId is not correct
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
	 * Gets the interface in which the game is being played
	 * @return the player interface which contains the game
	 */
	public PlayerInterface getPlayerInterface() {
		return this.playerInterface;
	}
	
	/**
	 * Gets the menu handler of the game
	 * @return the menu handler on the game
	 */
	public MenuHandler getMenuHandler() {
		return this.menuHandler;
	}
	
	
	/**
	 * Load a game save file
	 */
	private void load() {
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
	 * saves the game into a game save file
	 */
	private void save() {
		File f = new File("Save/saveFile");
		try {
			FileOutputStream fos = new FileOutputStream(f);
		    DataOutputStream dos = new DataOutputStream(fos);
			
			//Get the unlocked levels
			List<Integer> levelUnlockedIndexes = new ArrayList<Integer>();
			for(int level = 0; level < this.levels.length; level++) {
				if(!this.levels[level].isLocked()) {
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
	 * Makes the player fall if it's not jumping and there's nothing under it
	 */
	public void makeFall() {
		//If there's no collision on the bottom, the character falls
		if(!this.checkCollisions()[3]) {
			if((this.character.getPosition().getY() + Character.JUMPING_SPEED > (4*this.parameter.getHeight() / 5)) && this.levels[this.currentLevel].getOffsetY() != 0) {
				this.levels[this.currentLevel].scrollY(-1);
			} else {
				this.character.fall();
			}
		}
	}
	
	/**
	 * Gets the number of loaded levels in the game
	 * @return the number of loaded levels in the game
	 */
	public int getNumberLevels() {
		return this.levels.length;
	}
}
