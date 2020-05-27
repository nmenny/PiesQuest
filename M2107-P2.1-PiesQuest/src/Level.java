import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	 * Creates a new level with given proprieties
	 * @param theName the name of the level
	 * @param theDescription the description of the level
	 */
	public Level(String theName, String theDescription) {
		this.name = theName;
		this.description = theDescription;
		this.isLock = true;
		this.loadedLevel = new ArrayList<String>();
	}
	
	/**
	 * Load the level in memory
	 */
	public void load() {
		String levelName = "Levels/level" +this.name.split("_")[0] +".txt";
		System.out.println(levelName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(levelName));
			String tileSize = br.readLine();
			
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
	 * Displays the level at the screen
	 * @param g the graphical component
	 */
	public void display(Graphics g) {
		Iterator<String> it = this.loadedLevel.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
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
			BufferedReader br = new BufferedReader(new FileReader("Save/LevelNames.txt"));
			int nbLevels = Integer.parseInt(br.readLine());
			levels = new Level[nbLevels];
			String line;
			for(int i = 0; i < nbLevels; i++) {
				line = br.readLine();
				String[] levelInformations = line.split(",");
				if(levelInformations.length == 2) {
					levels[i] = new Level(levelInformations[0], levelInformations[1]);
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
	
	
}
