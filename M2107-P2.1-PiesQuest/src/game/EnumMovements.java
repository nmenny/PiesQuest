package game;

/**
 * Contains the player's movement values
 */
public enum EnumMovements {
	/**
	 * Movement on the left
	 */
	LEFT(-1),
	/**
	 * Movement on the right
	 */
	RIGHT(1);
	
	/**
	 * The value corresponding of the movement
	 */
	public int value;
	
	/**
	 * Creates a new movement
	 * @param theValue the value corresponding of the movement
	 */
	private EnumMovements(int theValue) {
		this.value = theValue;
	}
	
}
