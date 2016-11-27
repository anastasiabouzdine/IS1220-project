package core;

import java.util.ArrayList;

import restaurants.Dish;
import restaurants.Meal;
import restaurants.Restaurant;
import users.Customer;

/**
 * The class <code>Order</code> allows to create an Order which
 * contains informations about the <code>Customer</code> who ordered it it
 * and the <code>Restaurant</code> to which it has been addressed.
 * It also contains the quantity of the different <code>Dish</code> or <code>Meal</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Order {
	private Customer customer;
	private Restaurant restaurant;
	private ArrayList<Meal> meals;
	private ArrayList<Dish> dishes;
	// quantity.get(i) gives the quantity of meals.get(i) or dishes.get(i)
	private ArrayList<Integer> quantity; 
	
	public Order(Customer customer, Restaurant restaurant){
		this.customer = customer;
		this.restaurant = restaurant;
		meals = new ArrayList<Meal>();
		dishes = new ArrayList<Dish>();
		quantity = new ArrayList<Integer>();
	}
	
	public void addMeal(Meal m, int q){
		meals.add(m);
		quantity.add(q);
	}
	
	public void addDish(Dish m, int q){
		dishes.add(m);
		quantity.add(q);
	}
	
	/*********************************************************************/
	/* Getters and Setter */

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public ArrayList<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}

	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}

	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

}
