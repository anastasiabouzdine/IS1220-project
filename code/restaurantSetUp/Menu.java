package restaurantSetUp;

import java.util.ArrayList;
import java.util.List;

import restaurantSetUp.Dessert;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;

/** 
 * This class presents the Menu of a <code>Restaurant</code>
 * and therefore includes all the dishes offered by the restaurant.
 * Those dishes include starters, maindishes and desserts.
 * Every restaurant has one and only one menu and
 * dishes will be added to the menu via the restaurant.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class Menu {

	private List<Starter> listOfStarter;
	private List<MainDish> listOfMainDish;
	private List<Dessert> listOfDessert;
	
	/**
	 * Classe constructor constructor with empty list attributes.
	 * 
	 */
	public Menu() {
		super();
		this.listOfStarter = new ArrayList<Starter>();
		this.listOfMainDish = new ArrayList<MainDish>();
		this.listOfDessert = new ArrayList<Dessert>();
	}
	
	
	/**
	 * Class constructor where all attributes lists are set
	 * by the arguments lists.
	 * 
	 * @param listOfStarter = all dishes of type "Starter"
	 * @param listOfMainDish = all dishes of type "MainDish", 
	 * @param listOfDessert = all dishes of type "Dessert"
	 *  
	 */
	public Menu(List<Starter> listOfStarter, List<MainDish> listOfMainDish, List<Dessert> listOfDessert) {
		super();
		this.listOfStarter = listOfStarter;
		this.listOfMainDish = listOfMainDish;
		this.listOfDessert = listOfDessert;
	}

	
	
	/************************************************************/
	/* Adders and removers */
	
	/**
	 * 
	 * @param starter a Dish of type "Starter" is added to the menu (listOfStarter) 
	 */
	public void addStarter(Starter starter) {
		listOfStarter.add(starter);
	}

	/**
	 * 
	 * @param mainDish a Dish of type "MainDish" is added to the menu (listOfMainDish) 
	 */
	public void addMainDish(MainDish mainDish) {
		listOfMainDish.add(mainDish);
	}

	/**
	 * 
	 * @param dessert a Dish of type "Dessert" is added to the menu (listOfDessert) 
	 */
	public void addDessert(Dessert dessert) {
		listOfDessert.add(dessert);
	}

	/**
	 * 
	 * @param starter a Dish of type "Starter" is removed from the menu (listOfStarters) 
	 */
	public void removeStarter(Starter starter) {
		listOfStarter.remove(starter);
	}

	/**
	 * 
	 * @param mainDish a Dish of type "mainDish" is removed from the menu (listOfMainDish)
	 */
	public void removeMainDish(MainDish mainDish) {
		listOfMainDish.remove(mainDish);
	}

	/**
	 * 
	 * @param dessert a Dish of type "Dessert" is removed from the menu (listOfDessert) 
	 */
	public void removeDessert(Dessert dessert) {
		listOfDessert.remove(dessert);
	}

	@Override
	public String toString() {
		return "Menu [listOfStarter=" + listOfStarter + ", listOfMain=" + listOfMainDish + ", listOfDessert="
				+ listOfDessert + "]";
	}
	
	/************************************************************/
	/* Getters and Setters */
	
	/**
	 * @return the listOfStarter
	 */
	public List<Starter> getListOfStarter() {
		return listOfStarter;
	}

	/**
	 * @param listOfStarter the listOfStarter to set
	 */
	public void setListOfStarter(List<Starter> listOfStarter) {
		this.listOfStarter = listOfStarter;
	}

	/**
	 * @return the listOfMainDish
	 */
	public List<MainDish> getListOfMainDish() {
		return listOfMainDish;
	}

	/**
	 * @param listOfMainDish the listOfMainDish to set
	 */
	public void setListOfMainDish(List<MainDish> listOfMainDish) {
		this.listOfMainDish = listOfMainDish;
	}

	/**
	 * @return the listOfDessert
	 */
	public List<Dessert> getListOfDessert() {
		return listOfDessert;
	}
	
	/**
	 * @param listOfDessert the listOfDessert to set
	 */
	public void setListOfDessert(List<Dessert> listOfDessert) {
		this.listOfDessert = listOfDessert;
	}	
	
};
