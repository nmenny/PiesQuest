package game;

import java.awt.Color;

/**
 * Contains tiles character representation
 */
public enum EnumTiles {
	
	Wall('x', Color.BLACK),
	Player('p', Color.GREEN),
	Strawberries('s', Color.RED),
	End('e', Color.BLUE);
	
	public char charRepresentation;
	
	public Color tileColor;
	
	EnumTiles(char theRepresentation, Color theColor) {
		this.charRepresentation = theRepresentation;
		this.tileColor = theColor;
	}
}
