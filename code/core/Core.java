package core;

import java.util.*;

import exceptions.AlreadyUsedUsernameException;
import policies.*;
import restaurantSetUp.*;
import users.*;

/**
 * The class <code>Core</code> is the core class of the system that can be seen as the 
 * center amongst all other classes keeping track of all useful informations for all users.
 * 
 * A hashMap of username as key and user as value allows to have an efficient way of 
 * activating and deactivating <code>Users</code>.
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

		Manager root = new Manager("system", "admin", "root");
		users.put("root", root);
		managerList.add(root);

		this.mealHeap = new TreeSet<SortPolicy>();
		this.mealRestHeap = new TreeSet<SortPolicy>();
		this.dishHeap = new TreeSet<SortPolicy>();
		this.dishRestHeap = new TreeSet<SortPolicy>();

		this.receivedOrders = new LinkedList<Order>();
		this.savedOrders = new ArrayList<Order>();

		this.dateAfter = Calendar.getInstance();
		this.dateBefore = Calendar.getInstance();
	}
	
	/*********************************************************************/
	/**
	 * This method allows unregistered clients to register themselves.
	 * The method cannot be called if somebody is logged in.
	 * 
	 * @param user a newly created user
	 * @throws exceptions.AlreadyUsedUsernameException if the chosen username already exists
	 */
	public void register(User user) throws AlreadyUsedUsernameException {
		if(current_user == null){ 
			if (users.containsKey(user.getUsername())){
				throw new AlreadyUsedUsernameException();
			}
			if (user instanceof Courier){
				Courier courier_user = (Courier) user;
				this.courierList.add(courier_user);
			} else if (user instanceof Customer){
				Customer customer_user = (Customer) user;
				this.customerList.add(customer_user);
			} else if (user instanceof Restaurant){
				Restaurant restaurant_user = (Restaurant) user;
				this.restaurantList.add(restaurant_user);
			}
			users.put(user.getUsername(), user);
		}else{
			unauthorizedCommand();
		}
	}

	/**
	 * This method allows registered users to log in and changes the current user to the user 
	 * having completed the log in. New messages are printed out if user logs in.
	 * 
	 * @param username registered username that is saved in the system.
	 * @return a String containing a welcome message if the login was successful
	 * 		   or null if not
	 */
	public String logIn(String username){
		String output="Not a valid username!";
		if (users.containsKey(username)){
			current_user = users.get(username);
			if (current_user instanceof Courier){
				current_courier = (Courier) current_user;
				output = "Successfully logged in as a Courier !";
			} else if (current_user instanceof Customer){
				current_customer = (Customer) current_user;
				output = "Successfully logged in as a Customer !";
			} else if (current_user instanceof Manager){
				current_manager = (Manager) current_user;
				output = "Successfully logged in as a Manager !";
			} else if (current_user instanceof Restaurant){
				current_restaurant = (Restaurant) current_user;
				output = "Successfully logged in as a Restaurant !";
			}
			current_user.checkMessages(); 
		}
		return output;
	}

	/**
	 * This method allows logged in users to log out.
	 */
	public void logOut(){
		current_user = null;
		current_courier = null;
		current_customer = null;
		current_manager = null;
		current_restaurant = null;
	}

	/* Add, remove, activate and deactivate users */
	public void activateUser(User user){
		if(current_manager != null){
			users.put(user.getUsername(), user);
		}else{
			unauthorizedCommand();
		}
	}
	public void deactivateUser(User user){
		if(current_manager != null){
			users.remove(user.getUsername(), user);
		}else{
			unauthorizedCommand();
		}
	}
	public void addUser(User user) throws AlreadyUsedUsernameException {
		if(current_manager != null){ 
			if (users.containsKey(user.getUsername())){
				throw new AlreadyUsedUsernameException();
			}
			if (user instanceof Courier){
				Courier courier_user = (Courier) user;
				this.courierList.add(courier_user);
			} else if (user instanceof Customer){
				Customer customer_user = (Customer) user;
				this.customerList.add(customer_user);
			} else if (current_user instanceof Manager){
				Manager manager_user = (Manager) user;
				this.managerList.add(manager_user);
			} else if (current_user instanceof Restaurant){
				Restaurant restaurant_user = (Restaurant) user;
				this.restaurantList.add(restaurant_user);
			}
			activateUser(user);
		}else{
			unauthorizedCommand();
		}
	}
	public void removeUser(User e){
		if(current_manager != null){
			if (users.containsKey(e.getUsername())){
				deactivateUser(e);
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
		}else{
			unauthorizedCommand();
		}
	}

	/*********************************************************************/
	/* Restaurant functions */

	/**
	 * Sets the special meal of current_restaurant to the input <code>Meal</code>.
	 * Note that this function has to be here (and not only in the <code>Restaurant</code>
	 * class) because we need to notify all registered customers after this update.
	 * @param meal the new special meal of the week
	 */
	public void setSpecialMeal(Meal meal){
		if (current_restaurant != null) {
			current_restaurant.setSpecMeal(meal);
			notifyCustomersOfSpecialOffer();
		} else {
			unauthorizedCommand();
		}
	}

	/**
	 * Notifies all customers that are active of the new special meal offer.
	 * The fact a user wants to be notified or not is an attribute
	 * associated with each `Customer`.
	 */
	private void notifyCustomersOfSpecialOffer(){
		for(Customer cust : customerList){
			if (users.containsKey(cust.getUsername())){
				cust.update(current_restaurant);
			}
		}
	}

	/*********************************************************************/
	/* Methods for the sorting policy */

	/**
	 * @param rest is of type <code>Restaurant</code>
	 * @return mealRestHeap	is a TreeSet of all the orders of meals of a specific restaurant
	 */
	private TreeSet<SortPolicy> getMealRestHeap(Restaurant rest) {
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
	private TreeSet<SortPolicy> getDishRestHeap(Restaurant rest) {
		if(!dishRestHeap.isEmpty())
			dishRestHeap.clear();

		for(SortPolicy dCount : dishHeap)
			if(dCount.getRest().equals(rest)) 
				dishRestHeap.add(dCount);
		return dishRestHeap;
	}

	/**
	 *  This function is exclusively used by the core (=private).
	 * 
	 * @param meal	is of type <code>Meal</code>
	 * @return	mCount.getCount() or 0: either the Meal was already ordered then its count is returned
	 * or the Meal has not been ordered yet then its count is 0
	 */
	private int getMealSort(Meal meal){

		for(SortPolicy mCount : mealHeap)
			if(((MealSort) mCount).getMeal().equals(meal)) {
				setMealSort((MealSort) mCount);
				return mCount.getCount();
			}
		return 0;
	}

	/**
	 * This function is exclusively used by the core (=private).
	 * 
	 * This function adds a new meal count each time a meal was ordered 
	 * and delivered. It is only used by the function <code>treatNewOrders</code>
	 * once meals have been well ordered. 
	 * 
	 * @param	meal	is of type Meal
	 * @param	count	is the amount meal has already been ordered
	 * @param	rest	is of type restaurant
	 */
	private void addMealCount(Meal meal, int count, Restaurant rest){
		int holder = getMealSort(meal);
		if(holder != 0)
			mealHeap.remove(mealSort);	
		
		mealHeap.add(new MealSort(meal, count+holder, rest));
	}

	/**
	 * This function is exclusively used by the core (=private).
	 * 
	 * @param dish	is of type <code>Dish</code>
	 * @return	dCount.getCount() or 0: either the Dish was already ordered then its count is returned
	 * or the Dish has not been ordered yet then its count is 0
	 */
	private int getDishSort(Dish dish){
		for(SortPolicy dCount : dishHeap){
			if(((DishSort) dCount).getDish().equals(dish)) {
				setDishSort((DishSort) dCount);
				return dCount.getCount();
			}
		}
		return 0;
	}

	/**
	 * This function is exclusively used by the core (=private).
	 * 
	 * This function adds a new dish count each time a dish was ordered 
	 * and delivered. It is only used by the function <code>treatNewOrders</code>
	 * once dishes have been well ordered.
	 * 
	 * @param	dish	is of type Dish
	 * @param	count	is the amount dish has already been ordered
	 * @param	rest	is of type restaurant
	 */
	private void addDishCount(Dish dish, int count, Restaurant rest){
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
		if(current_manager != null || current_restaurant != null){
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
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @param rest	is of type restaurant
	 * @param order	is either true or false (true = descending order)
	 * @return	MealHeap or DishHeap according to the current policy established by the
	 * managers for the chosen restaurant. returned Heap can be in ascending or descending order according to the input
	 */
	public TreeSet<SortPolicy> getSortedList(Restaurant rest, boolean order){
		if(current_manager != null || current_restaurant != null){
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
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/*********************************************************************/
	/* Update functions */


	/**
	 * This function allows to leave a message for the system.
	 * If the current user is a manager, the message will be displayed 
	 * directly
	 * 
	 * @param message	message that is to be transfered to the system
	 */
	private void updateSystem(String message){
		if(this.current_user instanceof Manager)
			System.out.println(message);
		else
			for(Manager manager : this.managerList)
				manager.update(message);
	}

	/**
	 * This function allows to leave a message for a user.
	 * If the current user is the destination of the message, the message will be displayed 
	 * directly
	 * 
	 * @param	user	the user who is going to receive the message
	 * @param	message	message that is to be transfered to the user
	 */
	private void update(User user, String message){
		if(this.current_user.equals(user))
			System.out.println(message);
		else
			user.update(message);
	}

	/**
	 *  Treat new orders.
	 *	this is one of the most important functions in the core:
	 *	it treats new orders that were received by the system:
	 *	1) it takes the latest order from the queue (LIFO principle)
	 *	2) according to its policy a list of the given couriers is given
	 *	3) a loop is implemented that waits until a courier accepts or declines the order (the courier 
	 *	does that randomly in our case) 
	 *	4) either no courier has been found (then the order is disregarded) 
	 *	5) or the courier accepts and all the data is saved and updated respectively
	 *	This function is private because it is only used by the function placeOrder()
	 */ 
	private void treatNewOrders(){ 
		while (receivedOrders.size() != 0){
			Order order = this.receivedOrders.removeFirst();
			//get ordered list of couriers according to the chosen policy
			ArrayList<Courier> currentList = this.dPolicy.howToDeliver(courierList,order.getRestaurant().getAddress());
			Courier courier = null;
			// while no courier has been found yet or until there are couriers
			while(order.getCourier() == null && !currentList.isEmpty()) { 
				courier = currentList.get(0);
				if(courier.isAvailable() && users.containsKey(courier.getUsername())){
					courier.addNewOrder(order); // courier receives order
					update(courier, "[Order ID : "+ order.getID() + "] You have received a new order. "
							+ "Please respond whether you can carry out the order or not.");
					// courier decides randomly if he accepts or declines
					courier.replyRandom();
				} 
				currentList.remove(0);
			}

			if(currentList.isEmpty()) {
				update(order.getCustomer(), "[Order ID : "+ order.getID() + "] All courriers are occupied. "
						+ "Your order could not be treated. Please try again later.");
				updateSystem("No courier was available. Order has been deleted.");
			} else {
				order.setProfitFinal(order.getPrice()*this.markupPercentage + this.serviceFee - this.deliveryCost);
				order.setPriceFinal(order.getPriceInter() + this.serviceFee);
				savedOrders.add(order); // order is saved
				updateSystem("[Order ID : "+ order.getID() + "] " + courier + " will proceed with the order. Order has been saved.");
				
				Restaurant order_restaurant = order.getRestaurant();
				List<Meal> order_meals = order.getMeals();
				List<Dish> order_dishes = order.getDishes();
				if(!order_meals.isEmpty()){
					for(int i=0; i < order_meals.size(); i++) // count of meals is updated
						addMealCount(order_meals.get(i), order.getQuantity().get(i), order_restaurant);
					update(order_restaurant, "[Order ID : "+ order.getID() + "] Please prepare the meal(s): " + order_meals 
							+ " to be picked up shortly by: " + courier.getName() + ".");

				} else{	
					for(int i=0; i < order_dishes.size(); i++) // count of dishes is updated
						addDishCount(order_dishes.get(i), order.getQuantity().get(i), order_restaurant);
					update(order_restaurant,"[Order ID : "+ order.getID() + "] Please prepare the dish(es): " + order_dishes 
							+ "to be picked up shortly by: " + courier.getName() + ".");
				}
				Customer order_customer = order.getCustomer();
				order_customer.addFidelityPoints(Math.floorDiv((int)order.getPriceInter(), 10));
				update(order_customer,"[Order ID : "+ order.getID() + "] Your order has been accepted "
						+ "for the price of " + order.getPriceFinal() + " and will be carried out as soon as possible.");
			}
		}
	}

	/*********************************************************************/
	/* Methods for customers */

	/**
	 * Returns the history of orders of the current user, null if none is logged in.
	 * @return an ArrayList of orders containing the orders of the current user
	 */
	public ArrayList<Order> getHistoryOfOrders() {
		if (current_customer != null){
			int temp_ID = current_customer.getID();
			ArrayList<Order> temp_cust_orders = new ArrayList<Order>();
			for(Order o : savedOrders){
				if (temp_ID == o.getCustomer().getID()){
					temp_cust_orders.add(o);
				}
			}
			return temp_cust_orders;
		} else {
			unauthorizedCommand();
			return null;
		}
	}
	
	/**
	 * Creates a new order.
	 * @param	cust	as type of Customer	
	 * @param	rest	as type of Restaurant
	 * @return	new Order
	 */
	public Order createNewOrder(Customer cust, Restaurant rest){
		if (current_customer != null){
			return new Order(cust, rest);
		} else {
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * Places a new order in the queue of new orders.
	 * @param	order	of type order
	 */
	public void placeNewOrder(Order order){
	 
		if (current_customer != null){
			this.receivedOrders.add(order);
			update(this.current_customer,"Your order has succesfully been placed.");
			treatNewOrders();
		} else {
			unauthorizedCommand();
		}
	}




	/*********************************************************************/
	/* Simulate profit related indicators*/

	/**
	 * Depending on the chosen tpPolicy a certain profit related quantity is calculated:
	 * @param	profit	wanted profit 
	 * @param	input1 		service fee || markup percentage || delivery cost
	 * @param	input2 	markup percentage || delivery cost || service fee
	 * @return  delivery cost || service fee || markup percentage (all needed to achieve profit)
	 * 
	 *  <ul>
	 * 	<li> Case 1: tpPolicy = DeliveryCostProfit
	 * 
	 *  <ul>
	 * 	<li> input1	markup percentage </li>
	 *  <li> input2	service fee </li>
	 *  <li> delivery cost needed to achieve profit </li>
	 * </ul>
	 * 
	 * </li> <li> Case 2: tpPolicy = ServiceFeeProfit
	 *  
	 *  <ul>
	 * 	<li> input1	markup percentage </li>
	 *  <li> input2	delivery cost </li>
	 *  <li> service fee needed to achieve profit </li>
	 * </ul>
	 * 
	 *  </li> <li> Case 3: tpPolicy = MarkupProfit
	 * 
	 *  <ul>
	 * 	<li> input1	delivery cost </li>
	 *  <li> input2	service fee </li>
	 *  <li> markup percentage needed to achieve profit </li>
	 * </ul>
	 * 
	 * </ul>
	 */
	public double simulateProfit(double profit, double input1, double input2){
		if(current_manager != null){
			this.autoSetDateAfter();
			this.autoSetDateBeforeOneMonthAgo();
			return this.tpPolicy.howToTargetProfit(input1, input2, profit, this.savedOrders, this.dateBefore, this.dateAfter);
		}else{
			unauthorizedCommand();
			return 0;
		}
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
		if(current_manager != null){
			double sum = 0;
			for(Order order : this.savedOrders)
				if(order.getDate().compareTo(dateBefore) >= 0 && order.getDate().compareTo(dateAfter) <= 0) {
					sum += order.getPriceFinal();
				}
			return Order.round2(sum);
		}else{
			unauthorizedCommand();
			return 0;
		}

	}

	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * @return	sum		which is the total profit over a given Period of time 
	 */
	public double calcTotalProfit(){
		if(current_manager != null){
			double sum = 0.0D;
			for(Order order : this.savedOrders){
				System.out.println("CALCTOTAL order " + order.getID());
				if(order.getDate().compareTo(dateBefore) >= 0 && order.getDate().compareTo(dateAfter) <= 0){
					sum = Order.round2(sum + order.getProfitFinal());
				}
			}
			return Order.round2(sum);
		}else{
			unauthorizedCommand();
			return 0;
		}
	}

	/**
	 * @see dateBefore and dateAfter have to be set 
	 * before calling this function
	 * 
	 * I thought a hashMap is good in this case because we don't have to use the function 
	 * contains all the time
	 * 
	 * @return	average income per customer over a given period of time 
	 */
	public double calcAverageProfit(){
		if(current_manager != null){
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
		}else{
			unauthorizedCommand();
			return 0;
		}
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
		if(current_manager != null){
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
		}else{
			unauthorizedCommand();
			return null;
		}
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
		if(current_manager != null){
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
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/*********************************************************************/
	/* policy setters to respective policies */

    
	public void setDeliveryPolicyToFastDeliv(){
		if(current_manager != null){
			this.dPolicy = new FastestDelivery();
		}else{
			unauthorizedCommand();
		}
	}

	public void setDeliveryPolicyToFairOcc(){
		if(current_manager != null){
			this.dPolicy = new FairOccupationDelivery();
		}else{
			unauthorizedCommand();
		}
	}

	public void setTargetProfitPolicyToMarkup(){
		if(current_manager != null){
			this.tpPolicy = new MarkupProfit();
		}else{
			unauthorizedCommand();
		}
	}

	public void setTargetProfitPolicyToDelivCostProf(){
		if(current_manager != null){
			this.tpPolicy = new DeliveryCostProfit();
		}else{
			unauthorizedCommand();
		}
	}

	public void setTargetProfitPolicyToSerFeeProf(){
		if(current_manager != null){
			this.tpPolicy = new ServiceFeeProfit();
		}else{
			unauthorizedCommand();
		}
	}

	public void setSortPolicyToMealSort(){
		if(current_manager != null){
			this.sortPolicy = new MealSort();
		}else{
			unauthorizedCommand();
		}
	}

	public void setSortPolicyToDishSort(){
		if(current_manager != null){
			this.sortPolicy = new DishSort();
		}else{
			unauthorizedCommand();
		}
	}

	/*********************************************************************/
	/* Getters and Setter */
	
	/**
	 * @return the name
	 */
	public String getName() {
		if(current_manager != null){
			return name;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if(current_manager != null){
			this.name = name;
		}else{
			unauthorizedCommand();
		}
	}

	/* Getters for policies */ 
	/**
	 * @return the sortPolicy
	 */
	public SortPolicy getSoPolicy() {
		if(current_manager != null){
			return sortPolicy;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @return the dPolicy
	 */
	public DeliveryPolicy getdPolicy() {
		return dPolicy;
	}

	/**
	 * @return the tpPolicy
	 */
	public TargetProfitPolicy getTpPolicy() {
		return tpPolicy;
	}

	/* Getters and setters for the profit-related attributes (for the managers) */
	/**
	 * @return the serviceFee
	 */
	public double getServiceFee() {
		if(current_manager != null){
			return serviceFee;
		}else{
			unauthorizedCommand();
			return 0;
		}
	}

	/**
	 * @param service_fee the service_fee to set
	 */
	public void setServiceFee(double service_fee) {
		if(current_manager != null){
			this.serviceFee = service_fee;
		}else{
			unauthorizedCommand();
		}
	}

	/**
	 * @return the markupPercentage
	 */
	public double getMarkupPercentage() {
		if(current_manager != null){
			return markupPercentage;
		}else{
			unauthorizedCommand();
			return 0;
		}
	}

	/**
	 * @param markupPercentage the markupPercentage to set
	 */
	public void setMarkup_percentage(double markupPercentage) {
		if(current_manager != null){
			this.markupPercentage = markupPercentage;
		}else{
			unauthorizedCommand();
		}
	}

	/**
	 * @return the deliveryCost
	 */
	public double getDeliveryCost() {
		if(current_manager != null){
			return deliveryCost;
		}else{
			unauthorizedCommand();
			return 0;
		}
	}

	/**
	 * @param deliveryCost the deliveryCost to set
	 */
	public void setDeliveryCost(double deliveryCost) {
		if(current_manager != null){
			this.deliveryCost = deliveryCost;
		}else{
			unauthorizedCommand();
		}
	}

	/* Getters and setters for the meal- and dish- sorting structures */
	/**
	 * @return the mealHeap
	 */
	private TreeSet<SortPolicy> getMealHeap() {
		return mealHeap;
	}

	/**
	 * @param mealSort the mealSort to set
	 */
	private void setMealSort(MealSort mealSort) {
		this.mealSort = mealSort;
	}

	/**
	 * @return the dishHeap
	 */
	private TreeSet<SortPolicy> getDishHeap() {
		return dishHeap;
	}

	/**
	 * @param dishSort the dishSort to set
	 */
	private void setDishSort(DishSort dishSort) {
		this.dishSort = dishSort;
	}

	/**
	 * @return the receivedOrders
	 */
	public LinkedList<Order> getReceivedOrders() {
		if(current_manager != null){
			return receivedOrders;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @param receivedOrders the receivedOrders to set
	 */
	public void setReceivedOrders(LinkedList<Order> receivedOrders) {
		if(current_manager != null){
			this.receivedOrders = receivedOrders;
		}else{
			unauthorizedCommand();
		}
	}
    
	/**
	 * @param markupPercentage the markupPercentage to set
	 */
	public void setMarkupPercentage(double markupPercentage) {
		if(current_manager != null){
			this.markupPercentage = markupPercentage;
		}else{
			unauthorizedCommand();
		}
	}

	/* Getters and setters date */ 
	/**
	 * @return the dateAfter
	 */
	public Calendar getDateAfter() {
		if(current_manager != null){
			return dateAfter;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @param year the year to set
     * @param month the month to set
     * @param date the date to set
	 */
	public void setDateAfter(int year, int month, int date) {
		if(current_manager != null){
			dateAfter.clear();
			dateAfter.set(year, month, date);
		}else{
			unauthorizedCommand();
		}
	}
	public void autoSetDateAfter(){
		if(current_manager != null){
			dateAfter = Calendar.getInstance();
		}else{
			unauthorizedCommand();
		}
	}

	private void autoSetDateBeforeOneMonthAgo(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		dateBefore = cal;
	}


	/**
	 * @return the dateBefore
	 */
	public Calendar getDateBefore() {
		if(current_manager != null){
			return dateBefore;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @param year the year to set
     * @param month the month to set
     * @param date the date to set
	 */
	public void setDateBefore(int year, int month, int date) {
		dateBefore.clear();
		dateBefore.set(year, month, date);
	}

	/* Getters and Setter for user lists */
	/**
	 * @return the users
	 */
	public HashMap<String, User> getUsers() {
		if(current_manager != null){
			return users;
		}else{
			unauthorizedCommand();
			return null;
		}
	}
    
	/**
	 * @param users the users to set
	 */
	public void setUsers(HashMap<String, User> users) {
		if(current_manager != null){
			this.users = users;
		}else{
			unauthorizedCommand();
		}
	}
	/**
	 * @return the courierList
	 */
	public ArrayList<Courier> getCourierList() {
		if(current_manager != null){
			return courierList;
		}else{
			unauthorizedCommand();
			return null;
		}
	}
	/**
	 * @param courierList the courierList to set
	 */
	public void setCourierList(ArrayList<Courier> courierList) {
		if(current_manager != null){
			for (Courier courier : courierList){
				activateUser(courier);
			}
			this.courierList = courierList;
		}else{
			unauthorizedCommand();
		}
	}
	/**
	 * @return the customerList
	 */
	public ArrayList<Customer> getCustomerList() {
		if(current_manager != null){
			return customerList;
		}else{
			unauthorizedCommand();
			return null;
		}
	}
	/**
	 * @param customerList the customerList to set
	 */
	public void setCustomerList(ArrayList<Customer> customerList) {
		if(current_manager != null){
			for (Customer cust : customerList){
				activateUser(cust);
			}
			this.customerList = customerList;
		}else{
			unauthorizedCommand();
		}
	}
	/**
	 * @return the managerList
	 */
	public ArrayList<Manager> getManagerList() {
		if(current_manager != null){
			return managerList;
		}else{
			unauthorizedCommand();
			return null;
		}
	}
	/**
	 * @param managerList the managerList to set
	 */
	public void setManagerList(ArrayList<Manager> managerList) {
		if(current_manager != null){
			for (Manager manag : managerList){
				activateUser(manag);
			}
			this.managerList = managerList;
		}else{
			unauthorizedCommand();
		}
	}
	/**
	 * @return the restaurantList
	 */
	public ArrayList<Restaurant> getRestaurantList() {
		if(current_manager != null){
			return restaurantList;
		}else{
			unauthorizedCommand();
			return null;
		}
	}
	/**
	 * @param restaurantList the restaurantList to set
	 */
	public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
		if(current_manager != null){
			for (Restaurant rest : restaurantList){
				activateUser(rest);
			}
			this.restaurantList = restaurantList;
		}else{
			unauthorizedCommand();
		}
	}
	/* Getters and Setter for users */
	/**
	 * @return the current_user
	 */
	public User getCurrent_user() {
		return current_user;
	}

	/**
	 * @return the current_courier
	 */
	public Courier getCurrent_courier() {
		if(current_courier != null){
			return current_courier;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @return the current_customer
	 */
	public Customer getCurrent_customer() {
		if(current_customer != null){
			return current_customer;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @return the current_manager
	 */
	public Manager getCurrent_manager() {
		if(current_manager != null){
			return current_manager;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/**
	 * @return the current_restaurant
	 */
	public Restaurant getCurrent_restaurant() {
		if(current_restaurant != null){
			return current_restaurant;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/* MISC Getters and Setters */ 
	
	/**
	 * @return the savedOrders
	 */
	public ArrayList<Order> getSavedOrders() {
		if(current_manager != null){
			return savedOrders;
		}else{
			unauthorizedCommand();
			return null;
		}
	}

	/* static methods */
	private static void unauthorizedCommand(){
		System.out.println("This command is not valid! Please try again");
	}

}
