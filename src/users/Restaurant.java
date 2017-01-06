package users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import core.Order;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
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

public class Restaurant extends User implements Serializable {

	private static final long serialVersionUID = -8940299787192000061L;
	private Address address;
	private String surname;
	private String emailAddress;
	private String phoneNumb;
	private double discountFactor; // discount factor is set by default to 5%
	private double specDiscFact ;  // discount factor is set by default to 10%
	private Menu menu;
	private Meal specialMeal;
	private ArrayList<Meal> listOfMeal; 
	private int nbOfDeliveredOrders;

	/**
	 * Class constructor with password. 
	 * 	
	 * @param name			 	name of restaurant
	 * @param address 	 		an Address object containing the address of the restaurant 
	 * @param username 	 		log-in name of restaurant
	 * @param password 			password of restaurant
	 */
	public Restaurant(String name, Address address, String username, String password) {
		super(name, username, password);
		this.address = address;
		this.discountFactor = 0.05;
		this.specDiscFact = 0.1;
		this.menu = new Menu();
		this.listOfMeal = new ArrayList<Meal>();
	}

	/************************************************************/
	/* Auxiliaries function */

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
	 * @param	dessert		will be added to the restaurant's menu
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
	 * @param dessert	will be removed from the restaurant's menu
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
		else if(isMealSpecial(meal)) {
			return Order.round2(meal.getPrice()*(1-getSpecDiscFact()));
		}
		return Order.round2(meal.getPrice()*(1-getDiscountFactor()));
	}

	/**
	 * @param	meal	is a Meal 
	 * @return 	true or false depending on whether the meal is the special meal of the week
	 */
	public boolean isMealSpecial(Meal meal) {
		if(!(listOfMeal.contains(meal))) {
			System.out.println("Restaurant does not offer this meal");
		}
		if(meal.equals(specialMeal)){
			return true;
		} 
		return false;		
	}


	/**
	 * Return the Meal with given mealName, null if this restaurant has not that meal.
	 * @param mealName a String containing the name of the meal
	 * @return the Meal associated with the given meal name
	 */
	public Meal getMealByName(String mealName) {
		for(Meal m : listOfMeal){
			if (m.getName().equalsIgnoreCase(mealName)){
				return m;
			}
		}
		return null;
	}

	/**
	 * Return the Dish with given dishName, null if this restaurant has not that dish.
	 * @param dishName a String containing the name of the dish
	 * @return the Dish associated with the given dish name
	 */
	public Dish getDishByName(String dishName) {
		for (Dish m : menu.getListOfStarter()){
			if (m.getName().equalsIgnoreCase(dishName)){
				return m;
			}
		}
		for (Dish m : menu.getListOfMainDish()){
			if (m.getName().equalsIgnoreCase(dishName)){
				return m;
			}
		}
		for (Dish m : menu.getListOfDessert()){
			if (m.getName().equalsIgnoreCase(dishName)){
				return m;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Restaurant [username=" + getUsername() + ", name=" + getName() + "]";
	}


	/************************************************************/
	/**
	 * @return the discountFactor
	 */
	public double getDiscountFactor() {
		return discountFactor;
	}

	/**
	 * @param discountFactor the discountFactor to set
	 */
	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}

	/**
	 * @return the specDiscFact
	 */
	public double getSpecDiscFact() {
		return specDiscFact;
	}

	/**
	 * @param specDiscFact the specDiscFact to set
	 */
	public void setSpecDiscFact(double specDiscFact) {
		this.specDiscFact = specDiscFact;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the listOfMeal
	 */
	public List<Meal> getListOfMeal() {
		return listOfMeal;
	}

	/**
	 * @param listOfMeal the listOfMeal to set
	 */
	public void setListOfMeal(ArrayList<Meal> listOfMeal) {
		this.listOfMeal = listOfMeal;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the phoneNumb
	 */
	public String getPhoneNumb() {
		return phoneNumb;
	}

	/**
	 * @param phoneNumb the phoneNumb to set
	 */
	public void setPhoneNumb(String phoneNumb) {
		this.phoneNumb = phoneNumb;
	}

	/**
	 * @return the specialMeal
	 */
	public Meal getSpecialMeal() {
		return specialMeal;
	}

	/**
	 * @param specialMeal the specialMeal to set
	 */
	public void setSpecialMeal(Meal specialMeal) {
		this.specialMeal = specialMeal;
	}

	/**
	 * @return the nbOfDeliveredOrders
	 */
	public int getNbOfDeliveredOrders() {
		return nbOfDeliveredOrders;
	}

	/**
	 * @param numberOfDeliveredOrders the nbOfDeliveredOrders to set
	 */
	public void setNbOfDeliveredOrders(int numberOfDeliveredOrders) {
		this.nbOfDeliveredOrders = numberOfDeliveredOrders;
	}



};
