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
	 * The moving speed of the player
	 */
	public static final int MOVING_SPEED = 10;
	
	/**
	 * The jumping speed
	 */
	public static final double JUMPING_SPEED = 20;
	
	/**
	 * The falling speed of the player
	 */
	public static final double FALLING_SPEED = 15;
	
	/**
	 * The current jump of falling speed of the player
	 */
	public double currentFallingSpeed, currentJumpSpeed;
	
	
	/**
	 * Creates a new Character with given attributes
	 * @param theName The name of the character
	 * @param theHealth the health of the character
	 */
	public Character(String theName, int theHealth) {
		this.name = theName;
		this.health = theHealth;
		this.position = new Position();
		this.currentFallingSpeed = 1;
		this.currentJumpSpeed = Character.JUMPING_SPEED;
	}
	
	/**
	 * gives the current health of the character
	 * @return the health of the character
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Gives a certain amount of health to the player
	 * @param healthPoints the amount of health given to the player
	 */
	public void giveHealth(int healthPoints) {
		this.health += healthPoints;
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
	 * Place the character somewhere on the screen
	 * @param thePosition the position of the character
	 */
	public void setPosition(Position thePosition) {
		this.currentFallingSpeed = 1;
		this.currentJumpSpeed = Character.JUMPING_SPEED;
		this.position = new Position(thePosition.x, thePosition.y);
	}
	
	public double getCurrentJumpSpeed() {
		return this.currentJumpSpeed;
	}
	
	public void initJumpSpeed() {
		this.currentJumpSpeed = Character.JUMPING_SPEED;
	}
	
	/**
	 * Moves the player in a give direction
	 * @param direction the direction in which the player will move
	 */
	public void move(int direction) {
		switch(direction) {
			case 1:
				this.position = new Position(this.position.x + Character.MOVING_SPEED, this.position.y);
			break;
			case -1:
				this.position = new Position(this.position.x - Character.MOVING_SPEED, this.position.y);
			break;
		}
	}
	
	/**
	 * makes the player jump
	 */
	public void jump() {
		this.position.y -= (int) this.currentJumpSpeed;
		
		this.currentJumpSpeed -= 1;
	}
	
	/**
	 * the player dies
	 */
	public void die() {
		this.health--;
	}
	
	/**
	 * Makes the player fall
	 */
	public void fall() {
		this.position.y += (int) this.currentFallingSpeed;
		
		if(this.currentFallingSpeed < Character.FALLING_SPEED) {
			this.currentFallingSpeed += 1;
		}
	}
}
