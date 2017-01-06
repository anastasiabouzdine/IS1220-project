package policies;

import java.io.Serializable;

import users.Restaurant;

/**
 * The abstract class <code>SortPolicy</code> allow restaurants and managers
 * to sort the shipped orders according to different criteria.
 * 
 * The classes implementing this interface are
 * <ul>
 * 	<li><code>MealSort</code></li>
 *  <li><code>DishSort</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public abstract class SortPolicy implements Comparable<SortPolicy>, Serializable {

	private static final long serialVersionUID = 1682061892888342802L;
	private int count;
	private Restaurant rest;
	
	/**
	 * Constructor of SortPolicy which is protected. 
	 * 
	 *@param	count	is the number of times the respective choice was taken
	 *@param	rest	is the restaurant where the choice was ordered from
	 *
	 */
	protected SortPolicy(int count, Restaurant rest) {
		super();
		this.count = count;
		this.rest = rest;
	}

	/**
	 * Constructor for attributeless Instance of SortPolicy that exists because core 
	 * needs an attributeless SortPolicy that can be changed.
	 * 
	 */
	public SortPolicy() {
		super();
	}

	
	
	/*********************************************************************/

	/**
	 *the abstract function to implement
	 *@param	order	is a boolean that states whether the list will be displayed 
	 *in ascending or descending order 
	 *@return	return	order	boolean
	 */
	public abstract boolean howToSortOrder(boolean order);
	
	/**
	 * override compareTo method to sort list according to Count
	 */
	@Override
	public int compareTo(SortPolicy o) {
		return (this.count <= o.getCount()) ? 1 : -1;
	}
	
	/*********************************************************************/
	/* Getters and Setter */ 
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the rest
	 */
	public Restaurant getRest() {
		return rest;
	}

	/**
	 * @param rest the rest to set
	 */
	public void setRest(Restaurant rest) {
		this.rest = rest;
	}

	
}
