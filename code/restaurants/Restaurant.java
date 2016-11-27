package restaurants;

import java.util.ArrayList;
import java.util.List;


/**
 * The class <code>Restaurant</code> allows to create a Restaurant which will be able to
 * <ul>
 * 	<li>edit the restaurant <code>Menu</code></li>
 *  <li>create/remove a <code>Meal</code> 
 *  (which can either be a <code>HalfMeal</code> or a <code>FullMeal</code>)</li>
 *  <li>establish the generic discount factor (default 5%) to apply when computing
 *  a <code>Meal</code> price</li>
 *  <li>establish the special discount factor (default 10%) to apply when computing
 *  a meal-of-the-week special offer</li>
 *  <li>sort the shipped orders with respect to different criteria</li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Restaurant {
	
	private String name;
	private static int counter = 0;
	private Address address;
	private String username;
	private double discountFactor; // discount factor is set by default to 5%
	private double specDiscFact ;  // discount factor is set by default to 10%
	private Menu menu;
	private Meal specialMeal;
	
	private int id;
	private List<Meal> listOfMeal; //TODO consider doing a HashMap instead of ArrayList so that Meal can be found by name
	
	
	
	public Restaurant(){
		super();
	}
	
	/**
	 * Constructor 
	 * 
	 * @param name = name of restaurant
	 * @param xCoord = x coordinate of restaurant
	 * @param yCoord = y coordinate of restaurant
	 * @param username = log-in name of restaurant
	 * @param discountFactor = in %/ Factor
	 * 		   by which the price of a meal is cheaper than the sum of its single dishes
	 * @param specDiscFact = > discountFactor/ applied only
	 * 		  for one meal, menu = menu of restaurant that includes all dishes
	 */
	public Restaurant(String name, Address address, String username) {
		super();
		this.id = (++counter);
		this.name = name;
		this.address = address;
		this.username = username;
		this.discountFactor = 0.05;
		this.specDiscFact = 0.1;
		this.menu = new Menu();
		this.listOfMeal = new ArrayList<Meal>();
	}
		
	/************************************************************
	 * Getters and Setters 
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAdress() {
		return address;
	}

	public void setAdress(Address address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getDiscountFactor() {
		return discountFactor;
	}

	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}

	public double getSpecDiscFact() {
		return specDiscFact;
	}

	public void setSpecDiscFact(double specDiscFact) {
		this.specDiscFact = specDiscFact;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Meal> getListOfMeal() {
		return listOfMeal;
	}

	public void setListOfMeal(List<Meal> listOfMeal) {
		this.listOfMeal = listOfMeal;
	}

	public int getId() {
		return id;
	}
	
	public Meal getSpecMeal() {
		return specialMeal;
	}

	public void setSpecMeal(Meal specMeal) {
		this.specialMeal = specMeal;
	}
	
	/************************************************************/

	/**
	 * @param dish is either starter, mainDish or dessert and will be added to the Menu 
	 */
	public void addDish(Dish dish) {
		if (dish instanceof Starter){
			Starter sdish = (Starter) dish;
			menu.addStarter(sdish);
		} else if (dish instanceof MainDish){
			MainDish mdish = (MainDish) dish;
			menu.addMainDish(mdish);
		} else if (dish instanceof Dessert){
			Dessert ddish = (Dessert) dish;
			menu.addDessert(ddish);
		}
	}
	
	public void removeStarter(Starter starter){
		menu.removeStarter(starter);
	}

	/**
	 * @param dish is either starter, mainDish or dessert and will be removed from the Menu
	 */
	public void removeDish(Dish dish) {
		if (dish instanceof Starter){
			Starter sdish = (Starter) dish;
			menu.removeStarter(sdish);
		} else if (dish instanceof MainDish){
			MainDish mdish = (MainDish) dish;
			menu.removeMainDish(mdish);
		} else if (dish instanceof Dessert){
			Dessert ddish = (Dessert) dish;
			menu.removeDessert(ddish);
		}
	}

	/**
	 * @param meal is either HalfMeal or FullMeal will be added to the listOfMeal 
	 * 
	 */
	public void addMeal(Meal meal) {
		listOfMeal.add(meal);
	}

	/**
	 * @param meal is either HalfMeal or FullMeal will be removed from the listOfMeal 
	 * 
	 */
	public void removeMeal(Meal meal) {
		listOfMeal.remove(meal);
	}

	//TODO decide whether the following two functions are necessary or whether the setters and getters are enough
	
//	/**
//	 * @param discFac 
//	 */
//	public void setDiscFac(int discFac) {
//	}
//
//	/**
//	 * @param specDiscFac 
//	 */
//	public void setSpecDiscFac(int specDiscFac) {
//	}

	/**
	 * @param meal is either a normal Meal or a special Meal
	 * @return the price of a meal is returned as a double
	 */
	public double getPrice(Meal meal) {
		
		if(!(listOfMeal.contains(meal))) {
			System.out.println("Restaurant does not offer this meal");
			throw new NullPointerException();
		}
		
		if(meal.equals(specialMeal)){
			return meal.getPrice()*(1-getSpecDiscFact());
		} else {
			return meal.getPrice()*(1-getDiscountFactor());
		}
	}
	

	@Override
	public String toString() {
		return "Restaurants [name=" + name + ", Adress=" + address + ", username=" + username + ", discountFactor="
				+ discountFactor + ", specDiscFact=" + specDiscFact + ", menu=" + menu + ", id=" + id + ", listOfMeal="
				+ listOfMeal + "]";
	}

	
	
	
};
