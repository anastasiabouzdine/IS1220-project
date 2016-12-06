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

public class Restaurant extends User {
	
	//TODO discuss with John whether a static attribute of the core is a good idea or not
	
	private Address address;
	private double discountFactor; // discount factor is set by default to 5%
	private double specDiscFact ;  // discount factor is set by default to 10%
	private Menu menu;
	private Meal specialMeal;
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
		super(name, username);
		this.address = address;
		this.discountFactor = 0.05;
		this.specDiscFact = 0.1;
		this.menu = new Menu();
		this.listOfMeal = new ArrayList<Meal>();
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
	
//	/**
//	 * @param	message	of which this user is going to be notified
//	 */
//	public void update(String message) {
//		System.out.println("[Restaurant UPDATE] " + message);
//	}
	
	
	

	@Override
	public String toString() {
		return "Restaurant [getUsername()=" + getUsername() + ", getName()=" + getName() + "]";
		// return "Restaurant [name=" + getName() + ", Adress=" + address + ", username=" + getUsername() + ", discountFactor="
		//		+ discountFactor + ", specDiscFact=" + specDiscFact + ", menu=" + menu + ", ID=" + ID + ", listOfMeal="
		//		+ listOfMeal + "]";
	}

	
	/************************************************************
	 * Getters and Setters 
	 */

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
	
	
};
