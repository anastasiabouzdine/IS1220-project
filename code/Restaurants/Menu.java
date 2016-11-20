// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package projet_oop;

import java.util.ArrayList;
import java.util.List;

import projet_oop.Dessert;
import projet_oop.MainDish;
import projet_oop.Starter;

/************************************************************/
/** This class presents the Menu of a restaurant and therefore includes all the dishes offered by the restaurant
 * Every restaurant has one and only one menu
 * Dishes will be added to the menu via the class restaurant 
 * 
 * @author Patrick
 */
public class Menu {

	private List<Starter> listOfStarter;
	private List<MainDish> listOfMain;
	private List<Dessert> listOfDessert;
	
	/**
	 * @param listOfStarter = all dishes of type "Starter", listOfMainDish = all dishes of type "MainDish", 
	 * @param listOfDessert = all dishes of type "Dessert"
	 * Constructor where all attributes are created and thus empty
	 *  
	 */
	public Menu() {
		super();
		this.listOfStarter = new ArrayList<Starter>();
		this.listOfMain = new ArrayList<MainDish>();
		this.listOfDessert = new ArrayList<Dessert>();
	}
	
	
	/**
	 * @param listOfStarter = all dishes of type "Starter", listOfMainDish = all dishes of type "MainDish", 
	 * @param listOfDessert = all dishes of type "Dessert"
	 * Constructor where all attributes are transfered via an input
	 *  
	 */
	public Menu(List<Starter> listOfStarter, List<MainDish> listOfMain, List<Dessert> listOfDessert) {
		super();
		this.listOfStarter = listOfStarter;
		this.listOfMain = listOfMain;
		this.listOfDessert = listOfDessert;
	}

	/************************************************************
	 * Getters and Setters 
	 */
	
	public List<Starter> getListOfStarter() {
		return listOfStarter;
	}

	public void setListOfStarter(List<Starter> listOfStarter) {
		this.listOfStarter = listOfStarter;
	}

	public List<MainDish> getListOfMain() {
		return listOfMain;
	}

	public void setListOfMain(List<MainDish> listOfMain) {
		this.listOfMain = listOfMain;
	}

	public List<Dessert> getListOfDessert() {
		return listOfDessert;
	}

	public void setListOfDessert(List<Dessert> listOfDessert) {
		this.listOfDessert = listOfDessert;
	}
	
	/************************************************************/

	/**
	 * 
	 * @param starter a Dish of type "Starter" is added to the menu (listOfStarter) 
	 * @return 
	 */
	public void addStarter(Starter starter) {
		//TODO complete function
	}

	/**
	 * 
	 * @param mainDish a Dish of type "MainDish" is added to the menu (listOfMainDish) 
	 */
	public void addMainDish(MainDish mainDish) {
		//TODO complete function
	}

	/**
	 * 
	 * @param dessert a Dish of type "Dessert" is added to the menu (listOfDessert) 
	 */
	public void addDessert(Dessert dessert) {
		//TODO complete function
	}

	/**
	 * 
	 * @param starter a Dish of type "Starter" is removed from the menu (listOfStarters) 
	 */
	public void removeStarter(Starter starter) {
		//TODO complete function
	}

	/**
	 * 
	 * @param mainDish a Dish of type "mainDish" is removed from the menu (listOfMainDish)
	 */
	public void removeMainDish(MainDish mainDish) {
		//TODO complete function
	}

	/**
	 *
	 * @param dessert a Dish of type "Dessert" is removed from the menu (listOfDessert) 
	 */
	public void removeDessert(Dessert dessert) {
		//TODO complete function
	}

	@Override
	public String toString() {
		return "Menu [listOfStarter=" + listOfStarter + ", listOfMain=" + listOfMain + ", listOfDessert="
				+ listOfDessert + "]";
	}
	
	
};
