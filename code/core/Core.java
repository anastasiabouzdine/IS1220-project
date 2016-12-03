package core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
	
	/* Basic attributes */
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
	private ArrayList<Courier> courierList;
	private ArrayList<Courier> courierListInactive;
	private ArrayList<Customer> customerList;
	private ArrayList<Customer> customerListInactive;
	private ArrayList<Manager> managerList;
	private ArrayList<Manager> managerListInactive;
	private ArrayList<Restaurant> restaurantList;
	private ArrayList<Restaurant> restaurantListInactive;
	
	/* Two users can't have the same USERNAME,
	 * so we need an HashMap mapping each USERNAME
	 * to its corresponding user.
	*/ 
	
	/* Orders */
	private LinkedList<Order> receivedOrders;
	private ArrayList<Order> savedOrders;
	
	
	
	/* Profit-related information */
	private double serviceFee;
	private double markupPercentage;
	private double deliveryCost;
	
	/* PlaceHolder */
	private Calendar dateAfter;
	private Calendar dateBefore;
	
	
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
		
		this.courierListInactive = new ArrayList<Courier>();
		this.customerListInactive = new ArrayList<Customer>();
		this.managerListInactive = new ArrayList<Manager>();
		this.restaurantListInactive = new ArrayList<Restaurant>();
		
		this.mealHeap = new TreeSet<Sort>();
		this.mealRestHeap = new TreeSet<Sort>();
		this.dishHeap = new TreeSet<Sort>();
		this.dishRestHeap = new TreeSet<Sort>();
		
		this.receivedOrders = new LinkedList<Order>();
		this.savedOrders = new ArrayList<Order>();

		this.dateAfter = Calendar.getInstance();
		this.dateBefore = Calendar.getInstance();
		
		this.serviceFee = 2.50;
		this.markupPercentage = 0.05;
		this.deliveryCost = 4;
	}

	/*********************************************************************/
	/* Add and remove users */
	
	public void addCourier(Courier e){
		this.courierList.add(e);
	}
	public void addCustomer(Customer e){
		this.customerList.add(e);
	}
	public void addManager(Manager e){
		this.managerList.add(e);
	}
	public void addRestaurant(Restaurant e){
		this.restaurantList.add(e);
	}
	public void removeCourier(Courier e){
		this.courierList.remove(e);
	}
	public void removeCustomer(Customer e){
		this.customerList.remove(e);
	}
	public void removeManager(Manager e){
		this.managerList.remove(e);
	}
	public void removeRestaurant(Restaurant e){
		this.restaurantList.remove(e);
	}
	
	/*********************************************************************/
	/* Restaurant functions */
	
	
	
	/*********************************************************************/
	/* Methods for the sorting policy */
	 
	/**
	 * @param rest is of type <code>Restaurant</code>
	 * @return mealRestHeap	is a TreeSet of all the orders of meals of a specific restaurant
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
	 * @param rest is of type <code>Restaurant</code>
	 * @return mealRestHeap	is a TreeSet of all the orders of dishes of a specific restaurant
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
	 * @param meal	is of type <code>Meal</code>
	 * @return	mCount.getCount() or 0: either the Meal was already ordered then its count is returned
	 * or the Meal has not been ordered yet then its count is 0
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
	 * This function adds a new meal count each time a meal was ordered 
	 * and delivered. It is only used by the function <code>treatNewOrders</code>
	 * once meals have been well ordered. 
	 * 
	 * @param	meal	is of type Meal
	 * @param	count	is the amount meal has already been ordered
	 * @param	rest	is of type restaurant
	 */
	public void addMealCount(Meal meal, int count, Restaurant rest){
		
		int holder = getMealSort(meal);
		
		if(holder != 0)
			mealHeap.remove(mealSort);
			
		mealHeap.add(new MealSort(meal, count+holder, rest));
	}
	
	/**
	 * @param dish	is of type <code>Dish</code>
	 * @return	dCount.getCount() or 0: either the Dish was already ordered then its count is returned
	 * or the Dish has not been ordered yet then its count is 0
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
	 * This function adds a new dish count each time a dish was ordered 
	 * and delivered. It is only used by the function <code>treatNewOrders</code>
	 * once dishes have been well ordered.
	 * 
	 * @param	dish	is of type Dish
	 * @param	count	is the amount dish has already been ordered
	 * @param	rest	is of type restaurant
	 */
	public void addDishCount(Dish dish, int count, Restaurant rest){
		
		int holder = getDishSort(dish);
		
		if(holder != 0)
			dishHeap.remove(dishSort);
			
		dishHeap.add(new DishSort(dish, count+holder, rest));
	}
	
	/**
	 * @param order	is either true or false (true = descending order)
	 * @return	MealHeap or DishHeap according to the current policy established by the
	 * managers. returned Heap can be in ascending or descending order according to the input
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
	 * @param rest	is of type restaurant
	 * @param order	is either true or false (true = descending order)
	 * @return	MealHeap or DishHeap according to the current policy established by the
	 * managers for the chosen restaurant. returned Heap can be in ascending or descending order according to the input
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
	/* Methods for the delivery policy */
	
	/* Notifying core of new order */
	// TODO
	
	/**
	 *	this is one of the most important functions in the core:
	 *	it treats new orders that were received by the system:
	 *	1) it takes the latest order from the queue (LIFO principle)
	 *	2) according to its policy a list of the given couriers is given
	 *	3) a loop is implemented that waits until a courier accepts or declines the order (the courier 
	 *	does that randomly in our case) 
	 *	4) either no courier has been found (then the order is disregarded) 
	 *	5) or the courier accepts and all the data is saved and updated respectively
	 */
	public void treatNewOrders(){
		Order order = this.receivedOrders.removeFirst();
		ArrayList<Courier> currentList = this.dPolicy.howToDeliver(courierList,order.getRestaurant().getAddress()); //get ordered list of couriers according to the chosen policy
		Courier courier = null;
		while(order.getCourier() == null && !currentList.isEmpty()) { // while no courier has been found yet or until there are couriers
			courier = currentList.get(0);
			courier.addNewOrder(order); // courier receives order
			courier.update("You have received a new order. Please respond whether you can carry out the order or not.");
//			courier.declineOrder();
			courier.replyRandom(); // courier decides randomly if he accepts or declines
			currentList.remove(0);
		}
		
		if(currentList.isEmpty()) {
				order.getCustomer().update("All courriers are occupied. Your order could not be treated. Please try again later.");
		} else {
				order.setProfitFinal(order.getPrice()*this.markupPercentage + this.serviceFee - this.deliveryCost); // profit was saved
				order.setPriceFinal(order.getPriceInter() + this.serviceFee + this.deliveryCost); // total cost was saved
				savedOrders.add(order); // order is saved

				Restaurant order_restaurant = order.getRestaurant();
				List<Meal> order_meals = order.getMeals();
				List<Dish> order_dishes = order.getDishes();
				if(!order_meals.isEmpty()){
					for(int i=0; i < order_meals.size(); i++) // count of meals is updated
						addMealCount(order_meals.get(i), order.getQuantity().get(i), order_restaurant);
					order_restaurant.update("Please prepare the meal(s): " + order_meals + "to be picked up shortly by: " + courier.getName() + ".");
				
				} else{	
					for(int i=0; i < order_dishes.size(); i++) // count of dishes is updated
						addDishCount(order_dishes.get(i), order.getQuantity().get(i), order_restaurant);
					order_restaurant.update("Please prepare the dish(es): " + order_dishes + "to be picked up shortly by: " + courier.getName() + ".");
				}
				order.getCustomer().update("Your order has been accepted and will be carried out as soon as possible.");
			}
	}
	
	/*********************************************************************/
	/* Methods for a new order */
	
	/**
	 * creates a new order
	 * @param	cust	as type of Customer	
	 * @param	rest	as type of Restaurant
	 * @return	new Order
	 */
	public Order createNewOrder(Customer cust, Restaurant rest){
		return new Order(cust, rest);
	}
	
	/**
	 * places a new order in the queue of new orders
	 * @param	order	of type order
	 */
	public void placeNewOrder(Order order){
		this.receivedOrders.add(order);
		order.getCustomer().update("Your order has succesfully been placed.");
	}
	
	
	/* Implementing an update function */
	// TODO
	
	
	/* Notifying users of special offers */
	// TODO
	 
	
	/*********************************************************************/
	/* Profit related methods */
	
	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * @return	sum	which is the total income over a given Period of time 
	 */
	public double calcTotalIncome(){
		double sum = 0;
		for(Order order : this.savedOrders)
			if(order.getDate().after(dateBefore)&&order.getDate().before(dateAfter))
				sum += order.getPriceFinal();
		return sum;
	}
	
	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * @return	sum	which is the total profit over a given Period of time 
	 */
	public double calcTotalProfit(){
		double sum = 0;
		for(Order order : this.savedOrders)
			if(order.getDate().after(dateBefore)&&order.getDate().before(dateAfter))
				sum += order.getProfitFinal();
		return sum;
	}


	
	
	/* Choose target policy */
	// TODO

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

	/* Getters and setters for the profit-related attributes */
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
	
	public ArrayList<Courier> getCourierList() {
		return courierList;
	}

	public void setCourierList(ArrayList<Courier> courierList) {
		this.courierList = courierList;
	}
	
	public LinkedList<Order> getReceivedOrders() {
		return receivedOrders;
	}

	public void setReceivedOrders(LinkedList<Order> receivedOrders) {
		this.receivedOrders = receivedOrders;
	}
	
	/* Getters and setters date */ 
	public Calendar getDateAfter() {
		return dateAfter;
	}

	public void setDateAfter(int year, int month, int date) {
		dateAfter.clear();
		dateAfter.set(year, month, date);
	}

	public Calendar getDateBefore() {
		return dateBefore;
	}

	public void setDateBefore(int year, int month, int date) {
		dateBefore.clear();
		dateBefore.set(year, month, date);
	}
	
}
