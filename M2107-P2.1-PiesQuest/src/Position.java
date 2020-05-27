/**
 * Represents a position in a 2d environment
 */
public class Position {
	
	/**
	 * The coordinate in the X axis
	 */
	public double x;
	
	/**
	 * The coordinate in the Y axis
	 */
	public double y;
	
	/**
	 * Set the position to the origin (0, 0)
	 */
	public Position() {
		this.x = this.y = 0;
	}
	
	/**
	 * Set the position at given coordinates
	 * @param theX the position in the x axis
	 * @param theY the position in the y axis
	 */
	public Position(double theX, double theY) {
		this.x = theX;
		this.y = theY;
	}
	
}
