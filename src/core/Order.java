package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import policies.FidCardPlanBasic;
import restaurantSetUp.Dish;
import restaurantSetUp.Meal;
import users.Courier;
import users.Customer;
import users.Restaurant;

/**
 * The class <code>Order</code> allows to create an Order which contains
 * informations about the <code>Customer</code> who ordered it it and the
 * <code>Restaurant</code> to which it has been addressed. It also contains the
 * quantity of the different <code>Dish</code> or <code>Meal</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Order implements Serializable {

	private static final long serialVersionUID = 4130029589405922273L;
	private int ID;
	private static int counter=0;
	private Customer customer;
	private Restaurant restaurant;
	private ArrayList<Meal> meals;
	private ArrayList<Dish> dishes;
	// quantity.get(i) gives the quantity of meals.get(i) or dishes.get(i)
	// customer can either choose multiple dishes or multiple meals
	private ArrayList<Integer> quantity;
	private Courier courier;
	private double profitFinal;
	private double priceInter;
	private double priceFinal;
	private Calendar date;

	/**
	 * Class constructor.
	 * 
	 * @param customer 		customer that has initialised the order
	 * @param restaurant 	restaurant the user has ordered at
	 */
	public Order(Customer customer, Restaurant restaurant) {
		this.ID = (++counter);
		this.customer = customer;
		this.restaurant = restaurant;
		meals = new ArrayList<Meal>();
		dishes = new ArrayList<Dish>();
		quantity = new ArrayList<Integer>();
		this.date = Calendar.getInstance();
	}
	
	/**
	 * Class constructor with given date.
	 * 
	 * @param customer 		customer that has initialised the order
	 * @param restaurant	restaurant the user has ordered at
	 * @param date			date at which the order has been put
	 */
	public Order(Customer customer, Restaurant restaurant, Calendar date) {
		this.ID = (++counter);
		this.customer = customer;
		this.restaurant = restaurant;
		meals = new ArrayList<Meal>();
		dishes = new ArrayList<Dish>();
		quantity = new ArrayList<Integer>();
		this.date = date;
	}


	/**
	 * Add a given number of a given meal to the order.
	 * @param m		a Meal object corresponding to the meal to be added to the order
	 * @param q		an int corresponding to the quantity of meals to add to the order
	 */
	public void addMeal(Meal m, int q) {
		boolean alreadyInList = false;
		for(int i = 0; i < meals.size(); i++) {
			if (m.equals(meals.get(i))) {
				quantity.set(i, quantity.get(i) + q);
				alreadyInList = true;
			}
		}
		if (!alreadyInList) {
			meals.add(m);
			quantity.add(q);
		}
	}

	/**
	 * Add a given number of a given dish to the order.
	 * @param m		a Dish object corresponding to the dish to be added to the order
	 * @param q		an int corresponding to the quantity of dishes to add to the order
	 */
	public void addDish(Dish m, int q) {
		boolean alreadyInList = false;
		for(int i = 0; i < dishes.size(); i++) {
			if (m.equals(dishes.get(i))) {
				quantity.set(i, quantity.get(i) + q);
				alreadyInList = true;
			}
		}
		if (!alreadyInList) {
			dishes.add(m);
			quantity.add(q);
		}
	}

	/*********************************************************************/
	/**
	 * The method states whether the Fidelity is basic or not.
	 * 
	 * @return a boolean to true if the customer fidCardPlan is basic, false if
	 *         not
	 */
	public boolean isFidCardPlanBasic() {
		return (customer.getFidCardPlan() instanceof FidCardPlanBasic);
	}

	/**
	 * The method <code>Order.getPrice</code> calculates the price of the
	 * <code>Order</code> depending on
	 * 
	 * <ul>
	 * <li>whether the customer ordered multiple <code>Meal</code> or multiple
	 * <code>Deal</code></li>
	 * <li>whether the customer has a <code>FidCardPlanBasic</code> ,a
	 * <code>FidCardPlanPoints</code> or a <code>FidCardPlanLottery</code></li>
	 * </ul>
	 * 
	 * @return price of the order is returned as a double
	 */
	public double getPrice() {
		double price = 0.0;

		if (isFidCardPlanBasic()) {
			for (int i = 0; i < meals.size(); i++) {
				if (restaurant.isMealSpecial(meals.get(i))) {
					price += quantity.get(i) * meals.get(i).getPrice() * (1 - restaurant.getSpecDiscFact());
				} else {
					price += quantity.get(i) * restaurant.getPrice(meals.get(i));
				}
			}
		} else {
			for (int i = 0; i < meals.size(); i++) {
				price += quantity.get(i) * restaurant.getPrice(meals.get(i));
			}
		}
		for (int i = 0; i < dishes.size(); i++) {
			price += quantity.get(i) * dishes.get(i).getPrice();
		}
		price *= customer.getFidCardPlan().applyReduction();
		price = round2(price);
		this.priceInter = price;
		return price;
	}

	@Override
	public String toString() {
		return "Order [ID=" + ID + ", customer=" + customer + ", restaurant=" + restaurant + ", courier=" + courier
				+ ", date="+ date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)
				+ "/"+date.get(Calendar.YEAR)+ ", dishes=" + dishes + ", meals=" + meals + "]";
	}

	/*********************************************************************/
	/* Round function */

	/**
	 * Returns the input rounded to 2 decimal places.
	 * 
	 * @param n
	 *            the number to round
	 * @return the rounded number
	 */
	public static double round2(double n) {
		return Math.round(n * 100.0D) / 100.0D;
	}

	/**
	 * Returns the input rounded to 4 decimal places.
	 * 
	 * @param n
	 *            the number to round
	 * @return the rounded number
	 */
	public static double round4(double n) {
		return Math.round(n * 10000.0D) / 10000.0D;
	}

	/*********************************************************************/
	/* Getters and Setter */
	// no set ID!

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the meals
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}

	/**
	 * @param meals
	 *            the meals to set
	 */
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}

	/**
	 * @return the dishes
	 */
	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	/**
	 * @param dishes
	 *            the dishes to set
	 */
	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

	/**
	 * @return the quantity
	 */
	public ArrayList<Integer> getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the courier
	 */
	public Courier getCourier() {
		return courier;
	}

	/**
	 * @param courier
	 *            the courier to set
	 */
	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	/**
	 * @return the profitFinal
	 */
	public double getProfitFinal() {
		return round2(profitFinal);
	}

	/**
	 * @param profitFinal
	 *            the profitFinal to set
	 */
	public void setProfitFinal(double profitFinal) {
		this.profitFinal = round2(profitFinal);
	}

	/**
	 * @return the priceInter
	 */
	public double getPriceInter() {
		return round2(priceInter);
	}

	/**
	 * @param priceInter
	 *            the priceInter to set
	 */
	public void setPriceInter(double priceInter) {
		this.priceInter = round2(priceInter);
	}

	/**
	 * @return the priceFinal
	 */
	public double getPriceFinal() {
		return round2(priceFinal);
	}

	/**
	 * @param priceFinal
	 *            the priceFinal to set
	 */
	public void setPriceFinal(double priceFinal) {
		this.priceFinal = round2(priceFinal);
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the counter
	 */
	public static int getCounter() {
		return counter;
	}
	
	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	

}
