import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a level of our game
 */
public class Level {
	
	/**
	 * The name of the level
	 */
	private final String name;
	
	
	/**
	 * The description of the level
	 */
	private final String description;
	
	/**
	 * Is the level unlocked or not ?
	 * <tt>true</tt> if we haven't finished the level yet
	 * <tt>false</tt> else
	 */
	private boolean isLock;
	
	/**
	 * contains the entire level loaded from a file
	 */
	private List<String> loadedLevel;
	
	/**
	 * The width of the tiles
	 */
	private int width;
	
	/**
	 * The height of the tiles
	 */
	private int height;
	
	/**
	 * The offset on the x axis of the tiles
	 */
	private int offsetX;
	
	/**
	 * The offset on the y axis of the tiles
	 */
	private int offsetY;
	
	/**
	 * Stores the position of all the collected strawberries
	 */
	private Set<Position> strawberriesCollectedPositions;
	
	/**
	 * Creates a new level with given proprieties
	 * @param theName the name of the level
	 * @param theDescription the description of the level
	 */
	public Level(String theName, String theDescription) {
		this.name = theName;
		this.description = theDescription;
		this.isLock = true;
		this.loadedLevel = new ArrayList<String>();
		this.strawberriesCollectedPositions = new HashSet<Position>();
		this.init();
	}
	

	/**
	 * Initializes the level attributes
	 */
	public void init() {
		this.offsetX = 0;
		this.offsetY = 0;
	}
	
	/**
	 * Load the level in memory
	 */
	public void load() {
		this.loadedLevel = new ArrayList<String>();
		this.strawberriesCollectedPositions.clear();
		String levelName = "Levels/level" +this.name.split("_")[0] +".txt";
		System.out.println(levelName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(levelName));
			String[] tileSize = br.readLine().split(" ");
			this.width = Integer.parseInt(tileSize[0]); 
			this.height = Integer.parseInt(tileSize[1]);
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
				this.loadedLevel.add(line);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Error, level not found !");
		} catch (IOException e) {
			System.err.println("File error !");
		}
		
	}
	
	/**
	 * is the level locked ?
	 * @return <tt>true</tt> the level has been finished or <tt>false</tt> the level has not been finished yet
	 */
	public boolean isLock() {
		return this.isLock;
	}
	
	/**
	 * Unlocks the current level
	 */
	public void unlock() {
		this.isLock = false;
	}
	
	/**
	 * Gives the name of the level
	 * @return the name of the level
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gives the description of the level
	 * @return the description of the level
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Gets the width of the tiles
	 * @return the width of the tiles
	 */
	public int getTileWidth() {
		return this.width;
	}
	
	/**
	 * Gets the height of the tiles
	 * @return the height of the tiles
	 */
	public int getTileHeight() {
		return this.height;
	}
	
	/**
	 * Displays the level at the screen
	 * @param g the graphical component
	 * @param gameWidth the width of the game
	 * @param gameHeight the height of the game
	 */
	public void display(Graphics g, int gameWidth, int gameHeight) {
		int y = gameHeight - this.height;
		for(int level = this.loadedLevel.size() - 1; level >= 0; level--) {
			String line = this.loadedLevel.get(level);
			for(int x = 0; x < line.length(); x++) {
				if(line.charAt(x) == EnumTiles.Wall.charRepresentation) {
					g.setColor(EnumTiles.Wall.tileColor);
					g.fillRect((x * this.width) + this.offsetX, y + this.offsetY, this.width, this.height);
					
				}
				if(line.charAt(x) == EnumTiles.End.charRepresentation) {
					g.setColor(EnumTiles.End.tileColor);
					g.fillRect((x * this.width) + this.offsetX,  y + this.offsetY, this.width, this.height);
					
				}
				if(line.charAt(x) == EnumTiles.Strawberries.charRepresentation) {
					g.setColor(EnumTiles.Strawberries.tileColor);
					g.fillRect((x * this.width) + this.offsetX,  y + this.offsetY, this.width, this.height);
				}
			}
			y -= this.height;
		}
	}
	
	/**
	 * Loads all the levels of the game
	 * @return an array containing all the levels
	 * @throws LevelException If the level file does not contain enough information about the level
	 */
	public static Level[] loadAllLevels() throws LevelException {
		Level[] levels = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Levels/LevelNames.txt"));
			int nbLevels = Integer.parseInt(br.readLine());
			levels = new Level[nbLevels];
			String line;
			for(int i = 0; i < nbLevels; i++) {
				line = br.readLine();
				String[] levelInformations = line.split(",");
				if(levelInformations.length == 2) {
					levels[i] = new Level((i+1) +"_" +levelInformations[0], levelInformations[1]);
				} else {
					throw new LevelException();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found !");
		} catch (NumberFormatException e) {
			System.err.println("The first line of the level file is not a number !");
		} catch (IOException e) {
			System.err.println("File error !");
		}
		return levels;
	}

	/**
	 * Returns the initial position of the character
	 * @param gameHeight the height of the game
	 * @return The initial position of the player
	 */
	public Position getInitialPlayerPosition(int gameHeight) {
		Position pos = null;
		int y = gameHeight - this.height;
		for(int level = this.loadedLevel.size() - 1; level >= 0; level--) {
			String line = this.loadedLevel.get(level);
			for(int x = 0; x < line.length(); x++) {
				if(line.charAt(x) == EnumTiles.Player.charRepresentation) {
					pos = new Position(x * this.width, y);
				}
			}
			y -= this.height;
		}
		return pos;
	}
	
	/**
	 * Translates the level on the x axis tiles
	 * @param direction the direction of the translation
	 */
	public void translationX(int direction) {
		if(direction > 0) { //Moves to the right
			this.offsetX -= Character.MOVING_SPEED;
		} else if(direction < 0) { //Moves to the left
			this.offsetX += Character.MOVING_SPEED;
		}
	}
	
	/**
	 * Translates the level on the y axis
	 * @param direction the direction of the translation
	 */
	public void translationY(int direction) {
		if(direction > 0) { //Moves Up
			this.offsetY += Character.JUMPING_SPEED;
		} else if(direction < 0) { //Moves Down
			this.offsetY -= Character.JUMPING_SPEED;
		}
	}
	
	/**
	 * Returns the value of the offset on the x axis
	 * @return the value of the offset on the x axis
	 */
	public int getOffsetX() {
		return this.offsetX;
	}
	
	/**
	 * Returns the value of the offset on the y axis
	 * @return the value of the offset on the y axis
	 */
	public int getOffsetY() {
		return this.offsetY;
	}
	
	/**
	 * Allows us to know if we've reached the edge of the level
	 * @param gameWidth the width of the screen
	 * @return <tt>true</tt> if the end is reached, <tt>false</tt> else
	 */
	public boolean translationMaxReached(int gameWidth) {
		int xMax = 0;
		for(int line = 0; line < this.loadedLevel.size(); line++) {
			for(int x = 0; x < this.loadedLevel.get(line).length(); x++) {
				if(this.loadedLevel.get(line).charAt(x) == 'x') {
					if(((x * this.width) + this.offsetX) > xMax)
						xMax = (x * this.width) + this.offsetX;
				}
			}
		}
		
		return (xMax <= gameWidth);
	}
	
	@Override
	/**
	 * Gets the String representation of a level
	 * @return the level with String format
	 */
	public String toString() {
		return this.loadedLevel.toString();
	}
	
	/**
	 * Gets the 2d representation of a level
	 * @return the 2d representation of a level
	 */
	public String[] getMap() {
		String level = this.toString();
		
		//Taking the brackets off the string
		level = level.substring(1);
		level = level.substring(0, level.length() - 1);
		
		String[] layers = level.split(",");
		
		return layers;
	}

	/**
	 * Saves the position of all the collected strawberries
	 * @param set the list containing the position of the strawberries
	 */
	public void registerCollectedStrawberries(Set<Position> set) {
		
	}

}
