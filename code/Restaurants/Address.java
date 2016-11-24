// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package restaurants;

/************************************************************/
/**class that represent a 2-dim address 
 * 
 * @author Patrick 
 */
public class Address {
	
	private int xCoordinate;
	private int yCoordinate;
	
	
	/************************************************************/
	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public Address(int xCoordinate, int yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}


	/************************************************************
	 * Getters and Setters 
	 */
	
	public int getxCoordinate() {
		return xCoordinate;
	}



	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}



	public int getyCoordinate() {
		return yCoordinate;
	}



	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/************************************************************
	 * Overriden methods
	 * toString, hashCode and equals  
	 */
	@Override
	public String toString() {
		return "Adress [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
		return result;
	}


	/**
	 * Return TRUE if two addresses are equals, meaning they have the same X and Y coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (xCoordinate != other.xCoordinate)
			return false;
		if (yCoordinate != other.yCoordinate)
			return false;
		return true;
	}
	
	
};
