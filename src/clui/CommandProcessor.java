package clui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import core.Core;
import core.Order;
import exceptions.AlreadyUsedUsernameException;
import restaurantSetUp.AbstractFactory;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FactoryProducer;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Starter;
import users.*;

/**
 * The <code>CommandProcessor</code> class allows to process a given command
 * and use the its core instance to perform related actions on it.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class CommandProcessor {
	private Core core;

	private String current_name;
	private String[] current_args;

	private AbstractFactory dish_factory = FactoryProducer.getFactory("dish");
	private AbstractFactory meal_factory = FactoryProducer.getFactory("meal");

	private Order current_order;

	private ArrayList<Meal> potential_meals = new ArrayList<Meal>();

	/**************************************************/
	/* Singleton pattern */
	
	/**
	 * The core object is taken from deserialization of the appropriated
	 * ser file containing all the background of the core.
	 */
	private CommandProcessor() {
		try {
			FileInputStream fileIn = new FileInputStream("./ser_files/clui_core.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			core = (Core) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			System.out.println("! Error while loading the file !");
			core = new Core();
		} catch (ClassNotFoundException e) {
			System.out.println("! Class not found !");		
		}
	}

	private static final CommandProcessor INSTANCE = new CommandProcessor();

	public static CommandProcessor getInstance() {
		return INSTANCE;
	}

	public void processCmd(Command cmd) {
		current_name = cmd.getName();
		current_args = cmd.getArgs();

		switch (current_name.toUpperCase()) {
		case "REGISTERCUSTOMER":
			registerCustomer();
			break;
		case "REGISTERCOURIER":
			registerCourier();
			break;
		case "REGISTERRESTAURANT":
			registerRestaurant();
			break;
		case "ADDDISHRESTAURANTMENU":
			addDishRestaurantMenu();
			break;
		case "LOGIN":
			login();
			break;
		case "CREATEMEAL":
			createMeal();
			break;
		case "ADDDISH2MEAL":
			addDish2Meal();
			break;
		case "SHOWMEAL":
			showMeal();
			break;
		case "SAVEMEAL":
			saveMeal();
			break;
		case "SETSPECIALOFFER":
			setSpecialOffer();
			break;
		case "REMOVEFROMSPECIALOFFER":
			removeFromSpecialOffer();
			break;
		case "CREATEORDER":
			createOrder();
			break;
		case "ADDITEM2ORDER":
			addItem2Order();
			break;
		case "ADDNBITEM2ORDER":
			addNbItem2Order();
			break;
		case "ENDORDER":
			endOrder();
			break;
		case "ONDUTY":
			onDuty();
			break;
		case "OFFDUTY":
			offDuty();
			break;
		case "SETDELIVERYPOLICY":
			setDeliveryPolicy();
			break;
		case "SETPROFITPOLICY":
			setProfitPolicy();
			break;
		case "ASSOCIATECARD":
			associateCard();
			break;
		case "SHOWCOURIERDELIVERIES":
			showCourierDeliveries();
			break;
		case "SHOWRESTAURANTTOP":
			showRestaurantTop();
			break;
		case "SHOWCUSTOMERS":
			showCustomers();
			break;
		case "SHOWRESTAURANTS":
			showRestaurants();
			break;
		case "SHOWMENUITEM":
			showMenuItem();
			break;
		case "SHOWTOTALPROFIT":
			showTotalProfit();
			break;
		case "SETSERVICEFEE":
			setServiceFee();
			break;
		case "SETMARKUPPERCENTAGE":
			setMarkupPercentage();
			break;
		case "SETDELIVERYCOST":
			setDeliveryCost();
			break;
		case "LOGOUT":
			logout();
			break;
		case "STOP":
			stopAndSerialize();
			break;
		case "RESET":
			resetCore();
			break;
		}
	}

	/**************************************************/
	/* Apply commands to core */

	/**
	 * Adds a restaurant to the system with given name, username, address and
	 * password, recalling that the address must have the syntax
	 * "xCoord,yCoord".
	 */
	public void registerRestaurant() {
		String name = current_args[0];
		String username = current_args[1];
		String address = current_args[2];
		String password = current_args[3];

		Restaurant r = new Restaurant(name, new Address(address), username, password);
		try {
			core.register(r);
		} catch (AlreadyUsedUsernameException e) {
			System.out.println("! This username is already taken ! ");
		}
	}

	/**
	 * Adds a customer to the system with given firstname, lastname, username,
	 * address and password, recalling that the address must have the syntax
	 * "xCoord,yCoord".
	 */
	public void registerCustomer() {
		String firstName = current_args[0];
		String lastName = current_args[1];
		String username = current_args[2].trim();
		String address = current_args[3];
		String password = current_args[4];

		Customer c = new Customer(firstName, lastName, new Address(address),
				"00000000", "null@null.null", username, password);
		try {
			core.register(c);
		} catch (AlreadyUsedUsernameException e) {
			System.out.println("! This username is already taken ! ");
		}
	}

	/**
	 * Adds a courier to the system with given firstname, lastname, username,
	 * address and password, recalling that the address must have the syntax
	 * "xCoord,yCoord".
	 */
	public void registerCourier() {
		String firstName = current_args[0];
		String lastName = current_args[1];
		String username = current_args[2];
		String position = current_args[3];
		String password = current_args[4];

		Courier c = new Courier(firstName, lastName, new Address(position), "00000000", username, password);
		try {
			core.register(c);
		} catch (AlreadyUsedUsernameException e) {
			System.out.println("! This username is already taken ! ");
		}
	}

	/**
	 * Adds a dish with given dishname, category, foodtype and price, to the
	 * menu of the current logged in restaurant.
	 */
	public void addDishRestaurantMenu() {
		String dishName = current_args[0];
		String dishCategory = current_args[1].toLowerCase();
		String foodCategory = current_args[2].toLowerCase();

		try {
			double unitPrice = Double.parseDouble(current_args[3]);
			if (dishCategory.equals("starter")) {
				core.getCurrent_restaurant()
				.addStarter((Starter) dish_factory.getDish("starter", dishName, unitPrice, foodCategory));
			} else if (dishCategory.equals("maindish")) {
				core.getCurrent_restaurant()
				.addMainDish((MainDish) dish_factory.getDish("maindish", dishName, unitPrice, foodCategory));
			} else if (dishCategory.equals("dessert")) {
				core.getCurrent_restaurant()
				.addDessert((Dessert) dish_factory.getDish("dessert", dishName, unitPrice, foodCategory));
			}
		} catch (NumberFormatException e) {
			System.out.println("! Please enter a valid double for the price !");
		}
	}

	/**
	 * Allows the a user to log in.
	 * Note that the potential meals list is cleared in order to avoid
	 * use of the latter by unwanted users.
	 */
	public void login() {
		String password = current_args[1];
		String username = current_args[0];
		
		String info = core.logIn(username, password);
		System.out.println(info);
		potential_meals.clear();
	}

	/**
	 * Potentially creates a meal with given name and given type to the proposed
	 * items of the current logged in restaurant.
	 */
	public void createMeal() {
		String mealName = current_args[0];
		String mealType = current_args[1];

		if (core.getCurrent_restaurant() != null) {
			potential_meals.add(meal_factory.getMeal(mealType, mealName));
		} else {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Adds a dish that already exists in the restaurant list of dishes to a
	 * meal that is already in the restaurant list of meals.
	 */
	public void addDish2Meal() {
		String dishName = current_args[0];
		String mealName = current_args[1];

		if (core.getCurrent_restaurant() != null) {
			boolean found = false;
			Meal m = null;
			for (int i = 0; i < potential_meals.size(); i++) {
				m = potential_meals.get(i);
				if (m.getName().equalsIgnoreCase(mealName)) {
					List<Dish> ld = m.getListOfDish();
					Dish input_dish = core.getCurrent_restaurant().getDishByName(dishName);
					if (input_dish != null) {
						ld.add(input_dish);
						m.setListOfDish(ld);
					} else {
						System.out.println("! Please insert a valid dishname !");
					}
					found = true;
				}
			}
			if (!found) {
				System.out
				.println("! This meal has first to be created, please do it using createMeal <mealName> <mealType> !");
			}
		} else {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Show dishes in a meal of the logged in restaurant with given name.
	 */
	public void showMeal() {
		String mealName = current_args[0];

		String meal_print = null;
		// look for the meal in his already saved meals
		Meal m_saved = core.getCurrent_restaurant().getMealByName(mealName);
		if (m_saved != null) {
			meal_print = m_saved.toString();
		} else {
			// look for the meal in the potential meals list
			for (Meal m : potential_meals) {
				if (m.getName().equalsIgnoreCase(mealName)) {
					meal_print = "Not saved yet : " + m.toString();
				}
			}
		}
		if (meal_print != null) {
			System.out.println(meal_print.toString());
			System.out.println("(Price may be lower than the sum of prices depending on the applied fidelity plan.)");
		} else {
			System.out.println("! This meal does not exist !");
		}
	}

	/**
	 * Allows the logged in restaurant to save one of the pending meals with given meal name to the
	 * currently logged in restaurant list of meals.
	 * Erasing the potential_meals list at each log in allows to only have a use of this
	 * function by restaurants.
	 */
	public void saveMeal() {
		String mealName = current_args[0];

		boolean found = false;
		Meal m = null;
		for (int i = 0; i < potential_meals.size(); i++) {
			m = potential_meals.get(i);
			if (m.getName().equalsIgnoreCase(mealName)) {
				found = true;
				if ((m instanceof HalfMeal && m.getListOfDish().size() == 2)
						|| (m instanceof FullMeal && m.getListOfDish().size() == 3)) {
					core.getCurrent_restaurant().addMeal(m);
					potential_meals.remove(i);
				} else {
					System.out.println("! Wrong number of dishes, please update meal to follow specifications ! (" + m.toString() + ")");
				}
			}
		}
		if (!found) {
			System.out.println("! No meal corresponds to the given meal name !");
		}
	}

	/**
	 * Sets the special offer meal of the week of the logged in restaurant to
	 * the given meal.
	 */
	public void setSpecialOffer() {
		String mealName = current_args[0];

		try {
			Meal meal = core.getCurrent_restaurant().getMealByName(mealName);
			if (meal != null) {
				core.setSpecialMeal(meal);
			} else {
				System.out.println("! This meal does not exist !");
			}
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Sets the special offer meal of the week of the logged in restaurant to
	 * null.
	 */
	public void removeFromSpecialOffer() {
		String mealName = current_args[0];

		try {
			Meal meal = core.getCurrent_restaurant().getMealByName(mealName);
			if (meal != null) {
				core.setSpecialMeal(null);
			} else {
				System.out.println("! This meal does not exist !");
			}
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		}
	}

	public void createOrder() {
		String restaurantName = current_args[0];

		try {
			current_order = core.createNewOrder(core.findRestaurantByName(restaurantName));
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Add the given item (meal or dish) to the current order.
	 */
	public void addItem2Order() {
		String itemName = current_args[0];

		try {
			Meal m = current_order.getRestaurant().getMealByName(itemName);
			if (m != null) {
				current_order.addMeal(m, 1);
			} else {
				Dish d = current_order.getRestaurant().getDishByName(itemName);
				if (d != null) {
					current_order.addDish(d, 1);
				} else {
					System.out.println("! No item corresponds to the given item name !");
				}
			}
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Add the given item (meal or dish) with given quantity to the current
	 * order.
	 */
	public void addNbItem2Order() {
		String itemName = current_args[0];
		try {
			int q = Integer.parseInt(current_args[1]);

			try {
				Meal m = current_order.getRestaurant().getMealByName(itemName);
				if (m != null) {
					current_order.addMeal(m, q);
				} else {
					Dish d = current_order.getRestaurant().getDishByName(itemName);
					if (d != null) {
						current_order.addDish(d, q);
					} else {
						System.out.println("! No item corresponds to the given item name !");
					}
				}
			} catch (NullPointerException e) {
				Core.unauthorizedCommand();
			}
		} catch (NumberFormatException e) {
			System.out.println("! Please insert a valid number for the quantity !");
		}
	}

	/**
	 * Saves and treat the current order for the currently logged on customer at
	 * a given date.
	 */
	public void endOrder() {
		String string_date = current_args[0];

		try {
			Calendar cal = parseDateFromString(string_date);
			current_order.setDate(cal);
			try {
				Courier deliverer = core.placeNewOrder(current_order);
				if (deliverer != null) {
					System.out.println("The courier in charge of your order is " + deliverer.toString());
				} else {
					System.out.println("No courier was found for your order.");
				}
				current_order = null;
			} catch (NullPointerException e) {
				Core.unauthorizedCommand();
			}
		} catch (ParseException e) {
			System.out.println("! Please enter the date with dd/mm/yyyy format !");
		}
	}

	/**
	 * Set the state of the logged in courier to on-duty.
	 */
	public void onDuty() {
		try {
			core.getCurrent_courier().setAvailable(true);
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		} finally {
			core.logOut();
		}
	}

	/**
	 * Set the state of the logged in courier to off-duty.
	 */
	public void offDuty() {
		try {
			core.getCurrent_courier().setAvailable(false);
		} catch (NullPointerException e) {
			Core.unauthorizedCommand();
		} finally {
			core.logOut();
		}
	}

	/**
	 * For the currently logged on manager to set the delivery policy
	 * of the system to that passed as argument.
	 */
	public void setDeliveryPolicy() {
		String dPolicy = current_args[0];

		switch (dPolicy.toLowerCase()) {
		case "fairoccupation":
			core.setDeliveryPolicyToFairOcc();
			break;
		case "fastest":
			core.setDeliveryPolicyToFastDeliv();
			break;
		default:
			System.out.println("! Please choose between \"fairOccution\" or \"fastest\" delivery policy !");
			break;
		}
	}

	/**
	 * For the currently logged on manager to set the profit policy
	 * of the system to that passed as argument.
	 */
	public void setProfitPolicy() {
		String pPolicy = current_args[0];

		switch (pPolicy.toLowerCase()) {
		case "deliverycost":
			core.setTargetProfitPolicyToDelivCostProf();
			break;
		case "markup":
			core.setTargetProfitPolicyToMarkup();
			break;
		case "servicefee":
			core.setTargetProfitPolicyToSerFeeProf();
			break;
		default:
			System.out.println("! Please choose between \"deliveryCost\", \"markup\""
					+ "or \"serviceFee\" profit policy !");
			break;
		}
	}

	/**
	 * Associates a fidelity card plan to a customer with given username.
	 */
	public void associateCard() {
		String username = current_args[0];
		String cardType = current_args[1];

		if (core.getCurrent_manager() != null) {
			Customer c = core.findCustomerByName(username);
			if (cardType.equalsIgnoreCase("basic")) {
				c.setFidCardToBasic();
			} else if (cardType.equalsIgnoreCase("points")) {
				c.setFidCardToPoints();
			} else if (cardType.equalsIgnoreCase("lottery")) {
				c.setFidCardToLottery();
			} else {
				System.out.println("! Not a valid fidelity card type !");
				System.out.println("Please choose between basic|points|lottery.");
			}
		} else {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * For the currently logged on myFoodora manager to display the list of
	 * couriers sorted in decreasing order w r t the number of completed
	 * deliveries.
	 */
	public void showCourierDeliveries() {
		System.out.println("Couriers sorted in decreasing order w.r.t. the number of completed deliveries");
		ArrayList<Courier> best_c = core.getMostOrLeastActiveCouriers(true);
		Courier c = null;
		for (int i = 0; i < best_c.size(); i++) {
			c = best_c.get(i);
			System.out.println(c.getNbOfDeliveredOrders() + " orders by " + c.toString());
		}
	}

	/**
	 * For the currently logged on myFoodora manager to display the list of
	 * restaurant sorted in decreasing order w r t the number of delivered
	 * orders.
	 */
	public void showRestaurantTop() {
		System.out.println("Restaurants sorted in decreasing order w.r.t. the number of completed orders");
		ArrayList<Restaurant> best_r = core.getMostOrLeastSellingRestaurants(true);
		Restaurant r = null;
		for (int i = 0; i < best_r.size(); i++) {
			r = best_r.get(i);
			System.out.println(r.getNbOfDeliveredOrders() + " orders by " + r.toString());
		}
	}

	/**
	 * For the currently logged on myFoodora manager to display the list of
	 * customers.
	 * Note that the <code>getCustomerList</code> method of the core
	 * can only be used when the current logged in user is a manager.
	 */
	public void showCustomers() {
		for (Customer c : core.getCustomerList())
			System.out.println(c.toString());
	}

	/**
	 * For the currently logged on myFoodora manager to display the list of
	 * restaurants.
	 */
	public void showRestaurants() {
		if (core.getCurrent_manager() != null) {
			for (Restaurant r : core.getRestaurantList())
				System.out.println(r.toString());
		} else {
			Core.unauthorizedCommand();
		}
	}

	/**
	 * Display the list of meals and dishes of a given restaurant.
	 */
	public void showMenuItem() {
		String restaurantName = current_args[0];

		Restaurant r = core.findRestaurantByName(restaurantName);
		if (r == null) {
			System.out.println("! This restaurant is not valid !");
		} else {
			System.out.println("+++ Menu of " + r.toString() + " +++");
			System.out.println("--- Meals ---");
			for (Meal m : r.getListOfMeal()) {
				System.out.println(m.toString());
			}
			System.out.println("--- Starters ---");
			for (Dish d : r.getMenu().getListOfStarter()) {
				System.out.println(d.toString());
			}
			System.out.println("--- Maindishes ---");
			for (Dish d : r.getMenu().getListOfMainDish()) {
				System.out.println(d.toString());
			}
			System.out.println("--- Desserts ---");
			for (Dish d : r.getMenu().getListOfDessert()) {
				System.out.println(d.toString());
			}
		}
	}

	/**
	 * For the currently logged on myFoodora manager to show the
	 * total profit of the system since creation if arguments are
	 * null or within a time interval if not.
	 */
	public void showTotalProfit() {
		String beginDate = current_args[0];
		String endDate = current_args[1];
		
		Calendar bd = null;
		Calendar ed = null;
		if (beginDate != null) {
			try {
				bd = parseDateFromString(beginDate);
			} catch (ParseException e) {
				System.out.println("! Please insert date in the format dd/mm/yyyy !");
			}
		} else {
			bd = core.getSavedOrders().get(0).getDate();
		}
		if (endDate != null) {
			try {
				ed = parseDateFromString(endDate);
			} catch (ParseException e) {
				System.out.println("! Please insert date in the format dd/mm/yyyy !");
			}
		} else {
			ed = core.getSavedOrders().get(core.getSavedOrders().size() - 1).getDate();
		}
		core.setDateBeforeWithCalObject(bd);
		core.setDateAfterWithCalObject(ed);
		
		double profit = core.calcTotalProfit();
		if (beginDate != null && endDate != null) {
			System.out.println("Total profit between " + beginDate + " and " + endDate + " = " + profit + ".");
		} else {
			System.out.println("Total profit since creation = " + profit + ".");
		}
	}
	
	/**
	 * For the currently logged in manager to set the service fee.
	 */
	public void setServiceFee() {
		try {
			Double serviceFee = Double.parseDouble(current_args[0]);
			core.setServiceFee(serviceFee);
		} catch (NumberFormatException e) {
			System.out.println("! Please enter a valid double for the service fee !");
		}
	}
	
	/**
	 * For the currently logged in manager to set the markup percentage.
	 */
	public void setMarkupPercentage() {
		try {
			Double markupPercentage = Double.parseDouble(current_args[0]);
			core.setServiceFee(markupPercentage);
		} catch (NumberFormatException e) {
			System.out.println("! Please enter a valid double for the service fee !");
		}
	}
	
	/**
	 * For the currently logged in manager to set the delivery cost.
	 */
	public void setDeliveryCost() {
		try {
			Double deliveryCost = Double.parseDouble(current_args[0]);
			core.setServiceFee(deliveryCost);
		} catch (NumberFormatException e) {
			System.out.println("! Please enter a valid double for the service fee !");
		}
	}

	/**
	 * Performs logout.
	 */
	public void logout() {
		core.logOut();
	}

	/**
	 * Method is called whenever the command line is stopped and will
	 * serialize the core and all its relevant attributes into the 
	 * clui_core[dot]ser file.
	 */
	public void stopAndSerialize() {
		logout();
		try {
			FileOutputStream fileOut = new FileOutputStream("./ser_files/clui_core.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(core);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in ./ser_files/clui_core.ser");
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * For the currently logged in manager to reset the whole core object
	 * (cleaning the database of all users and orders).
	 */
	public void resetCore() {
		if (core.getCurrent_manager() != null) {
			core = new Core();
			System.out.println("Database was successfully reset, note that you have been logged out");
		} else {
			Core.unauthorizedCommand();
		}
	}

	
	/******************************************************************/
	/* Static helpers */

	/**
	 * Returns a Calendar object parsed from input date in format dd/mm/yyyy.
	 * 
	 * @param string_date
	 *            a string containing the date to be parsed in the form
	 *            dd/mm/yyyy
	 * @return a Calendar object representing the parsed date
	 * @throws ParseException if the input string is not in the required format
	 */
	public static Calendar parseDateFromString(String string_date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date) formatter.parse(string_date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

}
