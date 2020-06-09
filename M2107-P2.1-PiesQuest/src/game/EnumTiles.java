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
	
	public char charRepresentation;
	
	public Color tileColor;
	
	EnumTiles(char theRepresentation, Color theColor) {
		this.charRepresentation = theRepresentation;
		this.tileColor = theColor;
	}
}
