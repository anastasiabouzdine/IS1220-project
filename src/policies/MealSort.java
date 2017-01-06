package policies;

import java.io.Serializable;

import restaurantSetUp.Meal;
import users.Restaurant;


/**
 * The class <code>MealSort</code> inherits the class <code>Sort</code>
 * and is used to give back a sorted list of all meals being sold.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */


public class MealSort extends SortPolicy implements Serializable {
	
	private static final long serialVersionUID = 4056913146495292206L;
	private Meal meal;
	
	/**
	 * Constructor of MealSort.
	 * 
	 *@param	count	is the number of times the respective choice was taken
	 *@param	rest	is the restaurant where the choice was ordered from
	 *@param	meal	is the meal that was ordered
	 */
	public MealSort(Meal meal, int count, Restaurant rest) {
		super(count, rest);
		this.meal = meal;	
	}

	/**
	 * Constructer which exists because core needs an attributeless MealSort to save as policy.
	 */
	public MealSort() {
		super();
	}

	/*********************************************************************/
	
	
	/**
	 *This method return whether the list should be sorted in ascending or descending order.
	 *
	 *@param	order	is a boolean that states whether the list will be displayed 
	 *in ascending or descending order 
	 *@return	return	order	boolean (true or false)
	 */
	@Override
	public boolean howToSortOrder(boolean order) {
		return order;
	}


	@Override
	public String toString() {
		return "[meal=" + meal.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}	
	
	/*********************************************************************/
	/* Getters and Setters */ 
	
	/**
	 * @return the meal
	 */
	public Meal getMeal() {
		return meal;
	}

	/**
	 * @param meal the meal to set
	 */
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
}
