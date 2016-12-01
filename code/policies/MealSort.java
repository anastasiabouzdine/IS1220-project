package policies;

import restaurantSetUp.Meal;
import users.Restaurant;


//TODO give description of this class


public class MealSort extends Sort {
	
	private Meal meal;
	
	public MealSort(Meal meal, int count, Restaurant rest) {
		super(count, rest);
		this.meal = meal;	
	}


	public MealSort() {
		super();
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
	@Override
	public boolean howToSortOrder(boolean order) {
		return order;
	}
	
	@Override
	public String toString() {
		return "[meal=" + meal.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}	
}
