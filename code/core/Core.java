package core;

import java.util.*;

import exceptions.AlreadyUsedUsernameException;
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
	private SortPolicy sortPolicy;
	
	/* Meals and Dishes */
	private TreeSet<SortPolicy> mealHeap;
	private TreeSet<SortPolicy> mealRestHeap;
	private MealSort mealSort;
	private TreeSet<SortPolicy> dishHeap;
	private TreeSet<SortPolicy> dishRestHeap;
	private DishSort dishSort;

	/* Users */
	private ArrayList<Courier> courierList;
	private ArrayList<Customer> customerList;
	private ArrayList<Manager> managerList;
	private ArrayList<Restaurant> restaurantList;
	
	private HashMap<String,User> users = new HashMap<String, User>();
	private User current_user;
	private Courier current_courier;
	private Customer current_customer;
	private Manager current_manager;
	private Restaurant current_restaurant;
	
	/* Two users can't have the same USERNAME,
	 * so we need an HashMap mapping each USERNAME
	 * to its corresponding user.
	*/ 
	
	/* Orders */
	private LinkedList<Order> receivedOrders;
	private ArrayList<Order> savedOrders;
	
	/* Profit-related information */
	private double serviceFee = 2.50;
	private double markupPercentage = 0.05;
	private double deliveryCost = 4;
	
	/* PlaceHolder */
	private Calendar dateAfter;
	private Calendar dateBefore;
	
	
	/**
	 * Class constructor.
	 */
	public Core(){
		this.name = "MyFoodora";
		
		this.dPolicy = new FastestDelivery();
		this.tpPolicy = new MarkupProfit();
		this.sortPolicy = new MealSort();
		
		this.courierList = new ArrayList<Courier>();
		this.customerList = new ArrayList<Customer>();
		this.managerList = new ArrayList<Manager>();
		this.restaurantList = new ArrayList<Restaurant>();
		
		this.mealHeap = new TreeSet<SortPolicy>();
		this.mealRestHeap = new TreeSet<SortPolicy>();
		this.dishHeap = new TreeSet<SortPolicy>();
		this.dishRestHeap = new TreeSet<SortPolicy>();
		
		this.receivedOrders = new LinkedList<Order>();
		this.savedOrders = new ArrayList<Order>();

		this.dateAfter = Calendar.getInstance();
		this.dateBefore = Calendar.getInstance();
	}
	
	/**
	 * Class constructor specifying the name of the core system.
	 * @param name	a <code>String</code> containing the name of the core system
	 */
	public Core(String name){
		this();
		this.name = name;
	}
	

	/*********************************************************************/
	/* Log in */
	public String logIn(String username){
		if (users.containsKey(username)){
			current_user = users.get(username);
			if (current_user instanceof Courier){
				current_courier = (Courier) current_user;
				return "Successfully logged in as a Courier !";
			} else if (current_user instanceof Customer){
				current_customer = (Customer) current_user;
				return "Successfully logged in as a Customer !";
			} else if (current_user instanceof Manager){
				current_manager = (Manager) current_user;
				return "Successfully logged in as a Manager !";
			} else if (current_user instanceof Restaurant){
				current_restaurant = (Restaurant) current_user;
				return "Successfully logged in as a Restaurant !";
			}
		}
		return null;
	}
	public void logOut(){
		current_user = null;
		current_courier = null;
		current_customer = null;
		current_manager = null;
		current_restaurant = null;
	}
	
	/* Add and remove users */
	public void activateUser(User e){
		users.put(e.getUsername(), e);
	}
	public void disactivateUser(User e){
		users.remove(e.getUsername(), e);
	}
	public void addUser(User e) throws AlreadyUsedUsernameException {
		if (users.containsKey(e.getUsername())){
			throw new AlreadyUsedUsernameException();
		}
		activateUser(e);
		if (e instanceof Courier){
			Courier courier_e = (Courier) e;
			this.courierList.add(courier_e);
		} else if (e instanceof Customer){
			Customer customer_e = (Customer) e;
			this.customerList.add(customer_e);
		} else if (current_user instanceof Manager){
			Manager manager_e = (Manager) e;
			this.managerList.add(manager_e);
		} else if (current_user instanceof Restaurant){
			Restaurant restaurant_e = (Restaurant) e;
			this.restaurantList.add(restaurant_e);
		}	
	}
	public void removeUser(User e){
		disactivateUser(e);
		if (e instanceof Courier){
			Courier courier_e = (Courier) e;
			this.courierList.remove(courier_e);
		} else if (e instanceof Customer){
			Customer customer_e = (Customer) e;
			this.customerList.remove(customer_e);
		} else if (current_user instanceof Manager){
			Manager manager_e = (Manager) e;
			this.managerList.remove(manager_e);
		} else if (current_user instanceof Restaurant){
			Restaurant restaurant_e = (Restaurant) e;
			this.restaurantList.remove(restaurant_e);
		}	
	}
	
	/*********************************************************************/
	/* Restaurant functions */
	
	
	
	/*********************************************************************/
	/* Methods for the sorting policy */
	 
	/**
	 * @param rest is of type <code>Restaurant</code>
	 * @return mealRestHeap	is a TreeSet of all the orders of meals of a specific restaurant
	 */
	public TreeSet<SortPolicy> getMealRestHeap(Restaurant rest) {
		if(!mealRestHeap.isEmpty())
		mealRestHeap.clear();
	
		for(SortPolicy mCount : mealHeap)
			if(mCount.getRest().equals(rest)) 
				mealRestHeap.add(mCount);
		
		return mealRestHeap;
	}
	

	/**
	 * @param rest is of type <code>Restaurant</code>
	 * @return mealRestHeap	is a TreeSet of all the orders of dishes of a specific restaurant
	 */
	public TreeSet<SortPolicy> getDishRestHeap(Restaurant rest) {
		
		if(!dishRestHeap.isEmpty())
			dishRestHeap.clear();
		
			for(SortPolicy dCount : dishHeap)
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
		
		for(SortPolicy mCount : mealHeap)
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
		
		for(SortPolicy dCount : dishHeap)
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
	public TreeSet<SortPolicy> getSortedList(boolean order){
		if(this.sortPolicy instanceof MealSort){
			if(this.sortPolicy.howToSortOrder(order))
				return getMealHeap();
			else 
				return (TreeSet<SortPolicy>) getMealHeap().descendingSet();
		
		} else {
			if(this.sortPolicy.howToSortOrder(order))
				return getDishHeap();
			else
				return (TreeSet<SortPolicy>) getDishHeap().descendingSet();
		}
	}
	
	/**
	 * @param rest	is of type restaurant
	 * @param order	is either true or false (true = descending order)
	 * @return	MealHeap or DishHeap according to the current policy established by the
	 * managers for the chosen restaurant. returned Heap can be in ascending or descending order according to the input
	 */
	public TreeSet<SortPolicy> getSortedList(Restaurant rest, boolean order){
		if(this.sortPolicy instanceof MealSort) {
			if(this.sortPolicy.howToSortOrder(order))
				return getMealRestHeap(rest);
			else
				return (TreeSet<SortPolicy>) getMealRestHeap(rest).descendingSet();
		
		} else {
			if(this.sortPolicy.howToSortOrder(order))
				return getDishRestHeap(rest);
			else
				return (TreeSet<SortPolicy>) getDishRestHeap(rest).descendingSet();
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
		while (receivedOrders.size() != 0){
			Order order = this.receivedOrders.removeFirst();
			//get ordered list of couriers according to the chosen policy
			ArrayList<Courier> currentList = this.dPolicy.howToDeliver(courierList,order.getRestaurant().getAddress());
			Courier courier = null;
			// while no courier has been found yet or until there are couriers
			while(order.getCourier() == null && !currentList.isEmpty()) { 
				courier = currentList.get(0);
				courier.addNewOrder(order); // courier receives order
				courier.update("You have received a new order. "
						+ "Please respond whether you can carry out the order or not.");
				// courier decides randomly if he accepts or declines
				courier.replyRandom(); 
				currentList.remove(0);
			}

			if(currentList.isEmpty()) {
				order.getCustomer().update("All courriers are occupied. "
						+ "Your order could not be treated. Please try again later.");
			} else {
				order.setProfitFinal(order.getPrice()*this.markupPercentage + this.serviceFee - this.deliveryCost);
				order.setPriceFinal(order.getPriceInter() + this.serviceFee);
				savedOrders.add(order); // order is saved

				Restaurant order_restaurant = order.getRestaurant();
				List<Meal> order_meals = order.getMeals();
				List<Dish> order_dishes = order.getDishes();
				if(!order_meals.isEmpty()){
					for(int i=0; i < order_meals.size(); i++) // count of meals is updated
						addMealCount(order_meals.get(i), order.getQuantity().get(i), order_restaurant);
					order_restaurant.update("Please prepare the meal(s): " + order_meals 
							+ " to be picked up shortly by: " + courier.getName() + ".");

				} else{	
					for(int i=0; i < order_dishes.size(); i++) // count of dishes is updated
						addDishCount(order_dishes.get(i), order.getQuantity().get(i), order_restaurant);
					order_restaurant.update("Please prepare the dish(es): " + order_dishes 
							+ "to be picked up shortly by: " + courier.getName() + ".");
				}
				order.getCustomer().update("Your order has been accepted "
						+ "and will be carried out as soon as possible.");
			}
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
	
	
	/* Notifying users of special offers */
	// TODO
	
	/*********************************************************************/
	/* simulate profit related indicators*/
	
	/**
	 * Depending on the chosen tpPolicy a certain profit related quantity is calculated:
	 * @param	profit	wanted profit 
	 * @param	input1	depending on the Case (see below)
	 * @param	input2	depending on the Case (see below)
	 * 
	 *  <ul>
	 * 	<li> Case 1: tpPolicy = DeliveryCostProfit
	 * 
	 *  <ul>
	 * 	<li> @param	input1	markup percentage </li>
	 *  <li> @param	input2	service fee </li>
	 *  <li> @return delivery cost needed to achieve profit </li>
	 * </ul>
	 * 
	 * </li> <li> Case 2: tpPolicy = ServiceFeeProfit
	 *  
	 *  <ul>
	 * 	<li> @param	input1	markup percentage </li>
	 *  <li> @param	input2	delivery cost </li>
	 *  <li> @return service fee needed to achieve profit </li>
	 * </ul>
	 * 
	 *  </li> <li> Case 3: tpPolicy = MarkupProfit
	 * 
	 *  <ul>
	 * 	<li> @param	input1	delivery cost </li>
	 *  <li> @param	input2	service fee </li>
	 *  <li> @return markup percentage needed to achieve profit </li>
	 * </ul>
	 * 
	 * </ul>
	 * 
	 */
	public double simulateProfit(double profit, double input1, double input2){
		this.autoSetDateAfter();
		this.autoSetDateBeforeOneMonthAgo();
		return this.tpPolicy.howToTargetProfit(input1, input2, profit, this.savedOrders, this.dateBefore, this.dateAfter);
	}
	
	 
	
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
			if(order.getDate().compareTo(dateBefore) >= 0 && order.getDate().compareTo(dateAfter) <= 0) {
				sum += order.getPriceFinal();
			}
		return Order.round2(sum);
	}
	
	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * @return	sum		which is the total profit over a given Period of time 
	 */
	public double calcTotalProfit(){
		double sum = 0;
		for(Order order : this.savedOrders)
			if(order.getDate().after(dateBefore)&&order.getDate().before(dateAfter))
				sum += order.getProfitFinal();
		return Order.round2(sum);
	}
	
	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * //TODO @john --> look whether you think the function is good
	 * I thought a hashMap is good in this case because we don't have to use the function 
	 * contains all the time
	 * 
	 * @return	average income per customer over a given period of time 
	 */
	public double calcAverageProfit(){
		int nb_customers_who_ordered = 0;
		int temp_id = 0;
		boolean[] ordered_at_least_once = new boolean[customerList.get(customerList.size()-1).getID() + 1];
		for(Order o : savedOrders){
			temp_id = o.getCustomer().getID();
			if (!ordered_at_least_once[temp_id]){
				ordered_at_least_once[temp_id] = true;
				nb_customers_who_ordered++;
			}
		}
		return Order.round2(calcTotalProfit()/(double)nb_customers_who_ordered);
	}
	
	/*********************************************************************/
	/* Most/least selling restaurant and active courier */
	
	/**
	 * Returns the most or least selling <code>Restaurant</code> in terms of number of <code>Order</code>.
	 * @param 	most	a <code>boolean</code> whose value is true to get the most selling Restaurant,
	 * 					false to get the least selling one
	 * @return	the <code>Restaurant</code> following the criteria of the argument
	 */
	public Restaurant getMostOrLeastSellingRestaurant(boolean most){
		int[] nbOrders = new int[restaurantList.get(restaurantList.size()-1).getID() + 1];
		int max = 0; int min = savedOrders.size();
		Restaurant best_seller = null; 
		Restaurant least_seller = null;
		Restaurant current_r;
		for(Order o : savedOrders){
			current_r = o.getRestaurant();
			nbOrders[current_r.getID()]++;
			if (most && nbOrders[current_r.getID()] > max){
				max = nbOrders[current_r.getID()];
				best_seller = current_r;
			}
		}
		if (!most){
			for(Order o : savedOrders){
				current_r = o.getRestaurant();
				if (nbOrders[current_r.getID()] < min){
					min = nbOrders[current_r.getID()];
					least_seller = current_r;
				}
			}
		}
		return (most ? best_seller : least_seller);
	}
	
	/**
	 * Returns the most or least active <code>Courier</code> in terms of number of <code>Order</code>.
	 * Note that this method has a random factor in it (the courier accepting or not to deliver
	 * an offer) and isn't thus guaranteed to give the same output for the same argument.
	 * 
	 * @param 	most	a <code>boolean</code> whose value is true to get the most active Courier,
	 * 					false to get the least active one
	 * @return	the <code>Courier</code> following the criteria of the argument
	 */
	public Courier getMostOrLeastActiveCourier(boolean most){
		Courier most_active = null;
		Courier least_active = null;
		int max = 0; int min = savedOrders.size();
		for(Courier c : courierList){
			if (most && c.getNbOfDeliveredOrders() > max){
				max = c.getNbOfDeliveredOrders();
				most_active = c;
			}
			if (!most && c.getNbOfDeliveredOrders() > 0 && c.getNbOfDeliveredOrders() < min){
				min = c.getNbOfDeliveredOrders();
				least_active = c;
			}
			
		}
		return (most ? most_active : least_active);
	}
	
	/*********************************************************************/
	/* policy setters to respective policies */
	
	public void setDeliveryPolicyToFastDeliv(){
		this.dPolicy = new FastestDelivery();
	}
	
	public void setDeliveryPolicyToFairOcc(){
		this.dPolicy = new FairOccupationDelivery();
	}
	
	public void setTargetProfitPolicyToMarkup(){
		this.tpPolicy = new MarkupProfit();
	}
	
	public void setTargetProfitPolicyToDelivCostProf(){
		this.tpPolicy = new DeliveryCostProfit();
	}
	
	public void setTargetProfitPolicyToSerFeeProf(){
		this.tpPolicy = new ServiceFeeProfit();
	}
	
	public void setSortPolicyToMealSort(){
		this.sortPolicy = new MealSort();
	}
	
	public void setSortPolicyToDishSort(){
		this.sortPolicy = new DishSort();
	}

	/*********************************************************************/
	/* Getters and Setter */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/* Getters for policies */ 
	public SortPolicy getSoPolicy() {
		return sortPolicy;
	}

	public DeliveryPolicy getdPolicy() {
		return dPolicy;
	}

	public TargetProfitPolicy getTpPolicy() {
		return tpPolicy;
	}

	/* Getters and setters for the profit-related attributes (for the managers) */
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
	
	/* Getters and setters for the meal- and dish- sorting structures */
	public TreeSet<SortPolicy> getMealHeap() {
		return mealHeap;
	}
	
	public void setMealHeap(TreeSet<SortPolicy> mealHeap) {
		this.mealHeap = mealHeap;
	}
	
	public MealSort getMealSort() {
		return mealSort;
	}

	public void setMealSort(MealSort mealSort) {
		this.mealSort = mealSort;
	}
	
	public TreeSet<SortPolicy> getDishHeap() {
		return dishHeap;
	}
	
	public void setDishHeap(TreeSet<SortPolicy> dishHeap) {
		this.dishHeap = dishHeap;
	}

	public void setDishRestHeap(TreeSet<SortPolicy> dishRestHeap) {
		this.dishRestHeap = dishRestHeap;
	}

	public DishSort getDishSort() {
		return dishSort;
	}

	public void setDishSort(DishSort dishSort) {
		this.dishSort = dishSort;
	}
	
	public LinkedList<Order> getReceivedOrders() {
		return receivedOrders;
	}

	public void setReceivedOrders(LinkedList<Order> receivedOrders) {
		this.receivedOrders = receivedOrders;
	}
	public void setMarkupPercentage(double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}
	
	/* Getters and setters date */ 
	public Calendar getDateAfter() {
		return dateAfter;
	}

	public void setDateAfter(int year, int month, int date) {
		dateAfter.clear();
		dateAfter.set(year, month, date);
	}
	public void autoSetDateAfter(){
		dateAfter = Calendar.getInstance();
	}
	
	public void autoSetDateBeforeOneMonthAgo(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		dateBefore = cal;
	}
	

	public Calendar getDateBefore() {
		return dateBefore;
	}

	public void setDateBefore(int year, int month, int date) {
		dateBefore.clear();
		dateBefore.set(year, month, date);
	}
	
	/* Getters and Setter for user lists */
	public HashMap<String, User> getUsers() {
		return users;
	}
	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	public ArrayList<Courier> getCourierList() {
		return courierList;
	}
	public void setCourierList(ArrayList<Courier> courierList) {
		for (Courier e : courierList){
			activateUser(e);
		}
		this.courierList = courierList;
	}
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(ArrayList<Customer> customerList) {
		for (Customer e : customerList){
			activateUser(e);
		}
		this.customerList = customerList;
	}
	public ArrayList<Manager> getManagerList() {
		return managerList;
	}
	public void setManagerList(ArrayList<Manager> managerList) {
		for (Manager e : managerList){
			activateUser(e);
		}
		this.managerList = managerList;
	}
	public ArrayList<Restaurant> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
		for (Restaurant e : restaurantList){
			activateUser(e);
		}
		this.restaurantList = restaurantList;
	}
	/* Getters and Setter for users */
	public User getCurrent_user() {
		return current_user;
	}
	public void setCurrent_user(User current_user) {
		this.current_user = current_user;
	}
	public Courier getCurrent_courier() {
		return current_courier;
	}
	public void setCurrent_courier(Courier current_courier) {
		this.current_courier = current_courier;
	}
	public Customer getCurrent_customer() {
		return current_customer;
	}
	public void setCurrent_customer(Customer current_customer) {
		this.current_customer = current_customer;
	}
	public Manager getCurrent_manager() {
		return current_manager;
	}
	public void setCurrent_manager(Manager current_manager) {
		this.current_manager = current_manager;
	}
	public Restaurant getCurrent_restaurant() {
		return current_restaurant;
	}
	public void setCurrent_restaurant(Restaurant current_restaurant) {
		this.current_restaurant = current_restaurant;
	}
	
	/* MISC Getters and Setters */ 
	public ArrayList<Order> getSavedOrders() {
		return savedOrders;
	}
	public void setSavedOrders(ArrayList<Order> savedOrders) {
		this.savedOrders = savedOrders;
	}	

}
