package policies;

import restaurantSetUp.Dish;
import users.Restaurant;


//TODO give description of this class

public class DishSort extends SortPolicy{
	
	private Dish dish;
	
	/**
	 *@param	count	is the number of times the respective choice was taken
	 *@param	rest	is the restaurant where the choice was ordered from
	 *@param	dish	is the dish that was ordered
	 */
	public DishSort(Dish dish, int count, Restaurant rest) {
		super(count, rest);
		this.dish = dish;
	}
	
	/**
	 * is there because core needs an attributeless SortPolicy to save as policy
	 */
	public DishSort(){
		super();
	}
	
	/*********************************************************************/
	/* Getters and Setter */ 
	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
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
		return "[dish=" + dish.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}


	
}
