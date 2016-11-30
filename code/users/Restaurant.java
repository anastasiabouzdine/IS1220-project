package users;

import java.util.ArrayList;
import java.util.List;

import restaurantSetUp.Address;
import restaurantSetUp.Dessert;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Menu;
import restaurantSetUp.Starter;


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
	private List<Meal> listOfMeal; 

	
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
	
	public Restaurant(){
		super();
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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
		if(!(listOfMeal.contains(specMeal))) {
			System.out.println("Restaurant does not offer this meal");
			throw new NullPointerException();
		}
		this.specialMeal = specMeal;
	}
	
	/************************************************************/


	/**
	 * @param	starter	will be added to the restaurant's menu 
	 */
	public void addStarter(Starter starter){
		menu.addStarter(starter);
	}
	
	
		/**
		 * @param	mainDish	will be added to the restaurant's menu  
		 */
	public void addMainDish(MainDish mainDish){
		menu.addMainDish(mainDish);
	}
	
	
		/**
		 * @param	Dessert	will be added to the restaurant's menu
		 */
	public void addDessert(Dessert dessert){
		menu.addDessert(dessert);
	}
	

	

		/**
		 * @param	starter	will be removed from the restaurant's menu 
		 */
	public void removeStarter(Starter starter){
		menu.removeStarter(starter);
	}
	

		/**
		 * @param	mainDish	will be removed from the restaurant's menu  
		 */
	public void removeMainDish(MainDish mainDish){
		menu.removeMainDish(mainDish);
	}
	

		/**
		 * @param Dessert	will be removed from the restaurant's menu
		 */
	public void removeDessert(Dessert dessert){
		menu.removeDessert(dessert);
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


	/**
	 * @param	meal	is a Meal 
	 * @return the price of a meal is returned as a double
	 */
	public double getPrice(Meal meal) {
		
		if(!(listOfMeal.contains(meal))) {
			System.out.println("Restaurant does not offer this meal");
			throw new NullPointerException();
		}
		
			return meal.getPrice()*(1-getDiscountFactor());
	}
	
	/**
	 * @param	meal	is a Meal 
	 * @return 	true or false depending on whether the meal is the special meal of the week
	 */
	public boolean isMealSpecial(Meal meal) {
	
	if(!(listOfMeal.contains(meal))) {
		System.out.println("Restaurant does not offer this meal");
		throw new NullPointerException();
	}
	
	if(meal.equals(specialMeal)){
		return true;
	} 
		return false;		
	}
	
	/* Notifying Restaurant of new order */
	// TODO
	

	@Override
	public String toString() {
		return "Restaurants [name=" + name + ", Adress=" + address + ", username=" + username + ", discountFactor="
				+ discountFactor + ", specDiscFact=" + specDiscFact + ", menu=" + menu + ", id=" + id + ", listOfMeal="
				+ listOfMeal + "]";
	}

	
	
	
};
