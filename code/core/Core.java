package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import policies.*;
import restaurantSetUp.*;
import users.*;

/**
 * The class <code>Core</code> is the core class of the system.
 * It can be seen as the center amongst all other classes and
 * it keeps track of all useful informations for all users.
 * 
 * 
 * TODO add useful description of this class
 * 
 * In order to efficiently display a sorted list of all the <code>Menu</code> and
 * the <code>Dish</code> the <code>TreeMap</code> called <code>MealHeap</code> and 
 * <code>DishHeap</code> are implemented. Adding a new order as well as removing one 
 * is executed in O(log(n)) with n standing for the number of elements
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Core{
	
	private String name;
	
	/* Policies */
	private DeliveryPolicy dPolicy;
	private TargetProfitPolicy tpPolicy;
	private Sort sortPolicy;
	
	/* Meals and Dishes */
	private TreeSet<Sort> mealHeap;
	private TreeSet<Sort> mealRestHeap;
	private MealSort mealSort;
	private TreeSet<Sort> dishHeap;
	private TreeSet<Sort> dishRestHeap;
	private DishSort dishSort;

	/* Users */
	private ArrayList<Courier> courierListActive;
	private ArrayList<Courier> courierListInactive;
	private ArrayList<Customer> customerListActive;
	private ArrayList<Customer> customerListInactive;
	private ArrayList<Manager> managerListActive;
	private ArrayList<Manager> managerListInactive;
	private ArrayList<Restaurant> restaurantListActive;
	private ArrayList<Restaurant> restaurantListInactive;
	
	/* Orders */
	private LinkedList<Order> receivedOrders;
	private Stack<Order> savedOrders;
	
	
	
	/* Profit-related information */
	private double serviceFee;
	private double markupPercentage;
	private double deliveryCost;
	
	
	/**
	 * Core constructor.
	 */
	public Core(String name){
		super();
		this.name = name;
		 
		this.dPolicy = new FastestDelivery();
		this.tpPolicy = new MarkupProfit();
		this.sortPolicy = new MealSort();
		
		this.courierListActive = new ArrayList<Courier>();
		this.customerListActive = new ArrayList<Customer>();
		this.managerListActive = new ArrayList<Manager>();
		this.restaurantListActive = new ArrayList<Restaurant>();
		
		this.courierListInactive = new ArrayList<Courier>();
		this.customerListInactive = new ArrayList<Customer>();
		this.managerListInactive = new ArrayList<Manager>();
		this.restaurantListInactive = new ArrayList<Restaurant>();
		
		this.mealHeap = new TreeSet<Sort>();
		this.mealRestHeap = new TreeSet<Sort>();
		this.dishHeap = new TreeSet<Sort>();
		this.dishRestHeap = new TreeSet<Sort>();
		
		this.receivedOrders = new LinkedList<Order>();
		this.savedOrders = new Stack<Order>();

	}

	/*********************************************************************/
	/* Getters and Setter policies */ 
	
	public Sort getSoPolicy() {
		return sortPolicy;
	}

	public void setSort(Sort sortPolicy) {
		this.sortPolicy = sortPolicy;
	}

	public DeliveryPolicy getdPolicy() {
		return dPolicy;
	}

	public void setdPolicy(DeliveryPolicy dPolicy) {
		this.dPolicy = dPolicy;
	}

	/* Setting the profit-related attributes */
	public double getServiceFee() {
		return serviceFee;
	}
	
	public void setServiceFee(double service_fee) {
		this.serviceFee = service_fee;
	}
	
	public double getMarkupPercentage() {
		return markupPercentage;
	}
	
	public void setMarkup_percentage(double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}
	
	public double getDeliveryCost() {
		return deliveryCost;
	}
	
	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public TreeSet<Sort> getMealHeap() {
		return mealHeap;
	}
	
	public void setMealHeap(TreeSet<Sort> mealHeap) {
		this.mealHeap = mealHeap;
	}
	
	public MealSort getMealSort() {
		return mealSort;
	}

	public void setMealSort(MealSort mealSort) {
		this.mealSort = mealSort;
	}
	
	public TreeSet<Sort> getDishHeap() {
		return dishHeap;
	}
	
	public void setDishHeap(TreeSet<Sort> dishHeap) {
		this.dishHeap = dishHeap;
	}

	public void setDishRestHeap(TreeSet<Sort> dishRestHeap) {
		this.dishRestHeap = dishRestHeap;
	}


	public DishSort getDishSort() {
		return dishSort;
	}

	public void setDishSort(DishSort dishSort) {
		this.dishSort = dishSort;
	}
	
	/*********************************************************************/
	/* methods for the sorting policy */
	 
	
	/**
	 * // TODO
	 */
	public TreeSet<Sort> getMealRestHeap(Restaurant rest) {
	
		if(!mealRestHeap.isEmpty())
		mealRestHeap.clear();
	
		for(Sort mCount : mealHeap)
			if(mCount.getRest().equals(rest)) 
				mealRestHeap.add(mCount);
		
		return mealRestHeap;
	}
	
	/**
	 * // TODO
	 */
	public TreeSet<Sort> getDishRestHeap(Restaurant rest) {
		
		if(!dishRestHeap.isEmpty())
			dishRestHeap.clear();
		
			for(Sort dCount : dishHeap)
				if(dCount.getRest().equals(rest)) 
					dishRestHeap.add(dCount);
			
			return dishRestHeap;
	}
	
	/**
	 * // TODO
	 */
	public int getMealSort(Meal meal){
		
		for(Sort mCount : mealHeap)
			if(((MealSort) mCount).getMeal().equals(meal)) {
				setMealSort((MealSort) mCount);
				return mCount.getCount();
			}
			
		return 0;
	}
	
	/**
	 * // TODO
	 */
	public void addMealCount(Meal meal, int count, Restaurant rest){
		
		int holder = getMealSort(meal);
		
		if(holder != 0)
			mealHeap.remove(mealSort);
			
		mealHeap.add(new MealSort(meal, count+holder, rest));
	}
	
	/**
	 * // TODO
	 */
	public int getDishSort(Dish dish){
		
		for(Sort dCount : dishHeap)
			if(((DishSort) dCount).getDish().equals(dish)) {
				setDishSort((DishSort) dCount);
				return dCount.getCount();
			}
			
		return 0;
	}
	
	/**
	 * // TODO
	 */
	public void addDishCount(Dish dish, int count, Restaurant rest){
		
		int holder = getDishSort(dish);
		
		if(holder != 0)
			dishHeap.remove(dishSort);
			
		dishHeap.add(new DishSort(dish, count+holder, rest));
	}
	
	/**
	 * // TODO
	 */
	public TreeSet<Sort> getSortedList(boolean order){
		if(this.sortPolicy instanceof MealSort){
			if(this.sortPolicy.howToSortOrder(order))
				return getMealHeap();
		
			else 
				return (TreeSet<Sort>) getMealHeap().descendingSet();
		
		} else {
			if(this.sortPolicy.howToSortOrder(order))
				return getDishHeap();
			else
				return (TreeSet<Sort>) getDishHeap().descendingSet();
		}
	}
	
	/**
	 * // TODO
	 */
	public TreeSet<Sort> getSortedList(Restaurant rest, boolean order){
		if(this.sortPolicy instanceof MealSort) {
			if(this.sortPolicy.howToSortOrder(order))
				return getMealRestHeap(rest);
			else
				return (TreeSet<Sort>) getMealRestHeap(rest).descendingSet();
		
		} else {
			if(this.sortPolicy.howToSortOrder(order))
				return getDishRestHeap(rest);
			else
				return (TreeSet<Sort>) getDishRestHeap(rest).descendingSet();
		}
	}
	
	/*********************************************************************/
	/*methods for the delivery policy */
	
	/* Notifying core of new order */
	// TODO
	
	/**
	 * @param	TODO
	 */
	public void treatNewOrders(){
		Order order = this.receivedOrders.removeFirst();
		ArrayList<Courier> currentList = this.dPolicy.howToDeliver(courierListActive,order.getRestaurant().getAddress());
		
		while(order.getCourier() != null || currentList.isEmpty()) {
			Courier courier = currentList.get(0);
			courier.getListOfReceivedOrders().add(order);
			courier.update("You have received a new order. Please respond whether you can carry out the order or not,");
			order = courier.replyRand();
			currentList.remove(0);
		}
			if(currentList.isEmpty()) {
				order.getCustomer().update("All courriers are occupied. Your order could not be treated. Please try again later.");
				
			} else{
				
				savedOrders.push(order);
				order.getCourier().setNbOfDeliveredOrders(order.getCourier().getNbOfDeliveredOrders() + 1);
				if(order.getMeals().isEmpty()){
					
					for(int i=0; i < order.getMeals().size(); i++)
						addMealCount(order.getMeals().get(i), order.getQuantity().get(i), order.getRestaurant());
					order.getRestaurant().update("Please prepare the meal(s): " + order.getMeals() + "to be picked up shortly by: " + order.getCourier().getName() + ".");
				
				} else{	
					
					for(int i=0; i < order.getDishes().size(); i++)
						addDishCount(order.getDishes().get(i), order.getQuantity().get(i), order.getRestaurant());
					order.getRestaurant().update("Please prepare the dish(es): " + order.getDishes() + "to be picked up shortly by: " + order.getCourier().getName() + ".");
				}
				order.getCustomer().update("Your order has been accepted and will be carried out as soon as possible.");
			}
	}
	/* Implementing an update function */
	// TODO
	
	
	/* Notifying users of special offers */
	// TODO
	
	/* Computing the total income and total profit of the system */ 
	// TODO
	
	/* Choose target policy */
	// TODO

	
}
