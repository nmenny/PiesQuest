import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	private String[] displayableLevel;
	
	/**
	 * Creates a new level with given proprieties
	 * @param theName the name of the level
	 * @param theDescription the description of the level
	 */
	public Level(String theName, String theDescription) {
		this.name = theName;
		this.description = theDescription;
		this.isLock = true;
	}
	
	/**
	 * Load the level in memory
	 */
	public void load() {
		String levelName = "./levels/level" +this.name.split("_")[0] +".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(levelName));
			System.out.println(br.readLine());
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
	
	
}
