/**
 * Represents a position in a 2d environment
 */
public class Position {
	
	/**
	 * The coordinate in the X axis
	 */
	private double x;
	
	/**
	 * The coordinate in the Y axis
	 */
	private double y;
	
	/**
	 * Set the position to the origin (0, 0)
	 */
	public Position() {
		this.x = this.y = 0;
	}
	
	/**
	 * Gets the coordinate on the x axis
	 * @return the x value
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Gets the coordinate on the y axis
	 * @return the y value
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Adds a given value to the x value
	 * @param val the value to add
	 */
	public void addToX(double val) {
		this.x = this.x + val;
	}
	
	/**
	 * Adds a given value to the y value
	 * @param val the value to add
	 */
	public void addToY(double val) {
		this.y = this.y + val;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
	
}
