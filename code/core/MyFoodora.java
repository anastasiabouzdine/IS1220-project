package core;

import java.util.ArrayList;

import policies.*;
import restaurantSetUp.*;
import users.*;

/**
 * The class <code>MyFoodora</code> is the core class of the system.
 * It can be seen as the node amongst all other classes and
 * it keeps track of all useful informations for all users.
 * 
 * 
 * TODO add useful description of this class
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class MyFoodora {
	
	/* Policies */
	private DeliveryPolicy dPolicy;
	private TargetProfitPolicy tpPolicy;
	private SortOrderPolicy soPolicy;
	/* Users */
	private ArrayList<Courier> courier_list;
	private ArrayList<Customer> customer_list;
	private ArrayList<Manager> manager_list;
	private ArrayList<Restaurant> restaurant_list;
	/* Profit-related information */
	private double service_fee;
	private double markup_percentage;
	private double delivery_cost;
	
	/**
	 * MyFoodora empty constructor.
	 */
	public MyFoodora(){
		super();
	}

	
	/* Setting the profit-related attributes */
	public double getService_fee() {
		return service_fee;
	}
	public void setService_fee(double service_fee) {
		this.service_fee = service_fee;
	}
	public double getMarkup_percentage() {
		return markup_percentage;
	}
	public void setMarkup_percentage(double markup_percentage) {
		this.markup_percentage = markup_percentage;
	}
	public double getDelivery_cost() {
		return delivery_cost;
	}
	public void setDelivery_cost(double delivery_cost) {
		this.delivery_cost = delivery_cost;
	}
	
	/* Notifying users of special offers */
	// TODO
	
	/* Computing the total income and total profit of the system */ 
	// TODO
	
	/* Choose target policy */
	// TODO
	
	
}
