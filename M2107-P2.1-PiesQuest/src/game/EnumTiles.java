package game;

import java.awt.Color;

/**
 * Contains tiles character representation
 */
public enum EnumTiles {
	
	/**
	 * Representation of a wall
	 */
	Wall('x', Color.BLACK),
	
	/**
	 * Representation of the player's initial position
	 */
	Player('p', Color.GREEN),
	
	/**
	 * Representation of a strawberry
	 */
	Strawberries('s', Color.RED),
	
	/**
	 * Representation of the end of the game
	 */
	End('e', Color.BLUE);
	
	/**
	 * the character representing the specific tile in the level file
	 */
	public char charRepresentation;
	
	/**
	 * The color representing the tile
	 */
	public Color tileColor;
	
	/**
	 * Creates a new tile to be displayed on the screen
	 * @param theRepresentation the character representing the specific tile in the level file
	 * @param theColor the color representing the tile
	 */
	EnumTiles(char theRepresentation, Color theColor) {
		this.charRepresentation = theRepresentation;
		this.tileColor = theColor;
	}
}
