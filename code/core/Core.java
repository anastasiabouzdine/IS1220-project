package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
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

	/* Users */
	private ArrayList<Courier> courierList;
	private ArrayList<Customer> customerList;
	private ArrayList<Manager> managerList;
	private ArrayList<Restaurant> restaurantList;
	
	
	private Sort sortPolicy;
	
	private TreeSet<Sort> mealHeap;
	private TreeSet<Sort> mealRestHeap;
	private MealSort mealSort;
	
	private TreeSet<Sort> dishHeap;
	private TreeSet<Sort> dishRestHeap;
	private DishSort dishSort;
	
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
		
		this.courierList = new ArrayList<Courier>();
		this.customerList = new ArrayList<Customer>();
		this.managerList = new ArrayList<Manager>();
		this.restaurantList = new ArrayList<Restaurant>();
		
		this.sortPolicy = new MealSort();
		
		this.mealHeap = new TreeSet<Sort>();
		this.mealRestHeap = new TreeSet<Sort>();
		
		this.dishHeap = new TreeSet<Sort>();
		this.dishRestHeap = new TreeSet<Sort>();

	}

	
	public Sort getSoPolicy() {
		return sortPolicy;
	}

	public void setSort(Sort sortPolicy) {
		this.sortPolicy = sortPolicy;
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
	
	
	/* Notifying users of special offers */
	// TODO
	
	/* Computing the total income and total profit of the system */ 
	// TODO
	
	/* Choose target policy */
	// TODO
	
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

	
}
