package policies;

import java.io.Serializable;

import restaurantSetUp.Dish;
import users.Restaurant;


/**
 * The class <code>DishSort</code> inherits the class <code>Sort</code>
 * and is used to give back a sorted list of all Dishes being sold.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class DishSort extends SortPolicy implements Serializable {

	private static final long serialVersionUID = -5880129005073197401L;
	private Dish dish;
	
	/**
	 * Constructor of DishSort
	 * 
	 *@param	count	is the number of times the respective choice was taken
	 *@param	rest	is the restaurant where the choice was ordered from
	 *@param	dish	is the dish that was ordered
	 */
	public DishSort(Dish dish, int count, Restaurant rest) {
		super(count, rest);
		this.dish = dish;
	}
	
	/**
	 * Is there because core needs an attributeless SortPolicy to save as policy.
	 */
	public DishSort(){
		super();
	}
	
	/*********************************************************************/
	
	/**
	 *This method return whether the list should be sorted in ascending or descending order.
	 *
	 *@param	order	is a boolean that states whether the list will be displayed 
	 *in ascending or descending order 
	 *@return	return	order	boolean
	 */
	@Override
	public boolean howToSortOrder(boolean order) {
		return order;
	}


	@Override
	public String toString() {
		return "[dish=" + dish.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}
	
	/*********************************************************************/
	/* Getters and Setter */ 

	/**
	 * @return the dish
	 */
	public Dish getDish() {
		return dish;
	}

	/**
	 * @param dish the dish to set
	 */
	public void setDish(Dish dish) {
		this.dish = dish;
	}


	
}
