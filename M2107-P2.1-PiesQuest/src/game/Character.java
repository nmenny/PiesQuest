package game;

/**
 * Represents a character in the game
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
	 * The position of the Character on the screen
	 */
	private Position position;
	
	/**
	 * The moving speed of the player
	 */
	public static final int MOVING_SPEED = 10;
	
	/**
	 * The health max of the player
	 */
	public static final int MAX_HEALTH = 100;
	
	/**
	 * The jumping speed
	 */
	public static final double JUMPING_SPEED = 20;
	
	/**
	 * The falling speed of the player
	 */
	public static final double FALLING_SPEED = 15;
	
	/**
	 * The current jump and falling speed of the player
	 */
	private double currentFallingSpeed, currentJumpSpeed;
	
	/**
	 * The number of strawberries collected
	 */
	private int nbStrawberriesCollected;
	
	
	/**
	 * Creates a new Character with given attributes
	 * @param theName The name of the character
	 * @param theHealth The health of the character
	 */
	public Character(String theName, int theHealth) {
		this.name = theName;
		if(theHealth > Character.MAX_HEALTH || theHealth <= 0) {
			this.health = 3;
		} else {
			this.health = theHealth;
		}
		this.init();
	}
	
	/**
	 * Initialize some of the character's attributes
	 */
	public void init() {
		this.position = new Position();
		this.currentFallingSpeed = 1;
		this.currentJumpSpeed = Character.JUMPING_SPEED;
		this.nbStrawberriesCollected = 0;
	}
	
	/**
	 * Gives the current health of the character
	 * @return The health of the character
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Gives a certain amount of health to the player
	 * @param healthPoints The amount of health given to the player
	 */
	public void giveHealth(int healthPoints) {
		if(((this.health + healthPoints) <= Character.MAX_HEALTH) && ((this.health + healthPoints) > 0)) {
			this.health += healthPoints;
		}
	}
	
	/**
	 * Gives the name of the character
	 * @return The name of the character
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gives the current position of the Character
	 * @return The position of the player
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Place the character somewhere on the screen
	 * @param x The coordinate on the x axis
	 * @param y The coordinate on the y axis
	 */
	public void setPosition(int x, int y) {
		this.currentFallingSpeed = 1;
		this.currentJumpSpeed = Character.JUMPING_SPEED;
		this.position = new Position(x, y);
	}
	
	/**
	 * Place the character somewhere on the screen
	 * @param thePosition The position of the character
	 */
	public void setPosition(Position thePosition) {
		this.setPosition(thePosition.getX(), thePosition.getY());
	}
	
	/**
	 * Gets the current jumping speed of the character
	 * @return The current jumping speed
	 */
	public double getCurrentJumpSpeed() {
		return this.currentJumpSpeed;
	}
	
	/**
	 * Sets the value of the current jump speed
	 * @param value The value of the jumping speed
	 */
	public void setCurrentJumpSpeed(double value) {
		this.currentJumpSpeed = value;
	}
	
	/**
	 * Sets the value of the current falling speed
	 * @param value The value of the falling speed
	 */
	public void setCurrentFallingSpeed(double value) {
		this.currentFallingSpeed = value;
	}
	
	/**
	 * Initializes the jump speed to the default value
	 */
	public void initJumpSpeed() {
		this.currentJumpSpeed = Character.JUMPING_SPEED;
	}
	
	/**
	 * Moves the player in a give direction
	 * @param direction The direction in which the player will move
	 * 		If < 0 : The player moves on the left
	 * 		If > 0 : The player moves on the right
	 * 		Else : Nothing happens
	 */
	public void move(int direction) {
		if(direction > 0) { //Moves to the right
			this.position = new Position(this.position.getX() + Character.MOVING_SPEED, this.position.getY());
		}
		else if(direction < 0) { //Moves to the left 
			this.position = new Position(this.position.getX() - Character.MOVING_SPEED, this.position.getY());
		}
	}
	
	/**
	 * Makes the player jump
	 */
	public void jump() {
		this.position.addToY(- (int) this.currentJumpSpeed);
		
		//The speed reduces the higher he gets
		this.currentJumpSpeed -= 1;
	}
	
	/**
	 * The player looses one life
	 */
	public void die() {
		if(this.health > 0) {
			this.health--;
		}
	}
	
	/**
	 * Makes the player fall
	 */
	public void fall() {
		this.position.addToY((int) this.currentFallingSpeed);
		
		if(this.currentFallingSpeed < Character.FALLING_SPEED) {
			this.currentFallingSpeed += 1;
		}
	}
	
	/**
	 * Gets the number of strawberries collected by this character
	 * @return The number of strawberries collected
	 */
	public int getNbStrawberriesCollected() {
		return this.nbStrawberriesCollected;
	}

	/**
	 * Sets the number of strawberries collected by this character
	 * @param i The number of strawberries collected
	 */
	public void setNbStrawberriesCollected(int i) {
		if(i < 0) {
			this.nbStrawberriesCollected = 0;
		}
		this.nbStrawberriesCollected = i;
	}
}
