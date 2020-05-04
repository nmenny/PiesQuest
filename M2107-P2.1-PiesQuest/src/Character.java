/**
 * Represents a character on the game
 */
public class Character {
	
	/**
	 * The health of the Character
	 */
	private int health;
	
	/**
	 * The name of the Character
	 */
	private final String name;
	
	/**
	 * The position of the Character at the screen
	 */
	private Position position;
	
	/**
	 * Creates a new Character with given attributes
	 * @param theName The name of the character
	 * @param theHealth the health of the character
	 */
	public Character(String theName, int theHealth) {
		this.name = theName;
		this.health = theHealth;
		this.position = new Position();
	}
	
	/**
	 * gives the current health of the character
	 * @return the health of the character
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * gives the name of the character
	 * @return the name of the character
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Moves the player in a give direction
	 * @param direction the direction in which the player will move
	 */
	public void move(int direction) {
		
	}
}
