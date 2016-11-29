package core;



import restaurantSetUp.Dish;
import users.Restaurant;

public class DishSort extends Sort{
	
	private Dish dish;
	
	public DishSort(){
		super();
	}
	
	public DishSort(Dish dish, int count, Restaurant rest) {
		super(count, rest);
		this.dish = dish;
	}
	

	public Dish getDish() {
		return dish;
	}


	public void setDish(Dish dish) {
		this.dish = dish;
	}
	
	@Override
	public boolean howToSortOrder(boolean order) {
		return order;
	}


	@Override
	public String toString() {
		return "[dish=" + dish.getName() + ", sold=" + getCount() + ", Restaurant=" + getRest().getName() + "]";
	}


	
}
