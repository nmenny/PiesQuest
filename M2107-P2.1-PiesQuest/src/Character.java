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
	 * Gives the current position of the Character
	 * @return the position of the player
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Place the character somewhere on the screen
	 * @param x the coordinate on the x axis
	 * @param y the coordinate on the y axis
	 */
	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}
	
	/**
	 * Moves the player in a give direction
	 * @param direction the direction in which the player will move
	 */
	public void move(int direction) {
		//TODO implement the method
	}
	
	/**
	 * makes the player jump
	 */
	public void jump() {
		//TODO implement the method
	}
	
	/**
	 * the player dies
	 */
	public void die() {
		//TODO implement the method
	}
}
