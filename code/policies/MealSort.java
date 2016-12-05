package policies;

import restaurantSetUp.Meal;
import users.Restaurant;


//TODO give description of this class


public class MealSort extends SortPolicy {
	
	private Meal meal;
	
	/**
	 *@param	count	is the number of times the respective choice was taken
	 *@param	rest	is the restaurant where the choice was ordered from
	 *@param	meal	is the meal that was ordered
	 */
	public MealSort(Meal meal, int count, Restaurant rest) {
		super(count, rest);
		this.meal = meal;	
	}

	/**
	 * is there because core needs an attributeless MealSort to save as policy
	 */
	public MealSort() {
		super();
	}

	/*********************************************************************/
	/* Getters and Setter */ 
	
	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	/*********************************************************************/
	
	
	/**
	 *the abstract function to implement
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
		return "[meal=" + meal.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}	
}
