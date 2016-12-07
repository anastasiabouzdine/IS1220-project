package core;


import java.util.ArrayList;
import java.util.Calendar;

import restaurantSetUp.Dish;
import restaurantSetUp.FidCardPlanBasic;
import restaurantSetUp.Meal;
import users.Courier;
import users.Customer;
import users.Restaurant;

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
	private int ID;
	private static int counter;
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
	private final Calendar date;
	
	public Order(Customer customer, Restaurant restaurant){
		this.ID = (++counter);
		this.customer = customer;
		this.restaurant = restaurant;
		meals = new ArrayList<Meal>();
		dishes = new ArrayList<Dish>();
		quantity = new ArrayList<Integer>();
		this.date = Calendar.getInstance();
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
	
	public boolean isFidCardPlanBasic() {
		return (customer.getFidCardPlan() instanceof FidCardPlanBasic);
	}
	
	/**
	 * The method <code>Order.getPrice</code> calculates the price of the <code>Order</code> depending on 
	 * 
	 * <ul>
	 * 	<li>whether the customer ordered multiple <code>Meal</code> or multiple <code>Deal</code></li>
	 *  <li>whether the customer has a <code>FidCardPlanBasic</code>
	 *  ,a <code>FidCardPlanPoints</code> or a <code>FidCardPlanLottery</code></li>
	 * </ul>
	 * 
	 * @return	price	of the order is returned as a double
	 */
	public double getPrice(){
		double price = 0.0;
		
		if(isFidCardPlanBasic()) {
			for(int i = 0; i < meals.size(); i++) {
				if(restaurant.isMealSpecial(meals.get(i))) {
					price += quantity.get(i)*meals.get(i).getPrice()*(1-restaurant.getSpecDiscFact());
					// price += quantity.get(i)*restaurant.getPrice(meals.get(i))*(1.0-restaurant.getSpecDiscFact())/(1.0-restaurant.getDiscountFactor());
				} else {
					price += quantity.get(i)*restaurant.getPrice(meals.get(i));
				}
			}
		} else {
			for(int i = 0; i < meals.size(); i++) {
				price += quantity.get(i)*restaurant.getPrice(meals.get(i));
			}
		}
		for(int i = 0; i < dishes.size(); i++) {
			price += quantity.get(i)*dishes.get(i).getPrice();
		}
		price *= customer.getFidCardPlan().applyReduction();
		price = round2(price);
		this.priceInter = price;
		return price;
	}

	@Override
	public String toString() {
		return "Order [ID=" + ID + ", customer=" + customer + ", restaurant=" + restaurant 
				+ ", courier=" + courier + "]";
	}

	/*********************************************************************/
	/* Round function */
	
	public static double round2(double n){
		return Math.round(n * 100.0D) / 100.0D;
	}
	
	public static double round4(double n){
		return Math.round(n * 10000.0D) / 10000.0D;
	}
	
	/* Getters and Setter */ //no set ID! no set Date!
	
	public Calendar getDate() {
		return date;
	}
	
	public double getPriceFinal() {
		return round2(priceFinal);
	}

	public void setPriceFinal(double priceFinal) {
		this.priceFinal = round2(priceFinal);
	}

	public double getProfitFinal() {
		return profitFinal;
	}

	public void setProfitFinal(double priceFinal) {
		this.profitFinal = round2(priceFinal);
	}
	
	public int getID() {
		return ID;
	}

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

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	
	public double getPriceInter() {
		return priceInter;
	}

}
