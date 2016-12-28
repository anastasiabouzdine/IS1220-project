package clui;

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
import parsers.ParseCouriers;
import parsers.ParseCustomers;
import parsers.ParseManagers;
import parsers.ParseRestaurants;
import restaurantSetUp.AbstractFactory;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FactoryProducer;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Starter;
import users.*;

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
	private CommandProcessor() {
		core = new Core();

		core.logIn("root"); // login with manager to add lists
		// core.setCustomerList(ParseCustomers.parseCustomers("src/txtFILES/customersList.txt"));
		// core.setCourierList(ParseCouriers.parseCouriers("src/txtFILES/courierList.txt"));
		// core.setManagerList(ParseManagers.parseManagers("src/txtFILES/managersList.txt"));
		// core.setRestaurantList(ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt"));
		core.logOut();
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
		case "LOGOUT":
			logout();
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
	 */
	public void login() {
		String password = current_args[1];
		String username = current_args[0];
		String info = core.logIn(username, password);
		System.out.println(info);
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
			System.out.println("! Command not available for this type of user !");
		}
	}

	/**
	 * Adds a dish that already exists in the restaurant list of dishes to a
	 * meal that is already in the restaurant list of meals.
	 */
	public void addDish2Meal() {
		String dishName = current_args[0];
		String mealName = current_args[1];

		try {
			boolean found = false;
			Meal m = null;
			for (int i = 0; i < potential_meals.size(); i++) {
				m = potential_meals.get(i);
				if (m.getName().equalsIgnoreCase(mealName)) {
					List<Dish> ld = m.getListOfDish();
					ld.add(core.getCurrent_restaurant().getDishByName(dishName));
					m.setListOfDish(ld);
					found = true;
				}
			}
			if (!found) {
				System.out
				.println("! This meal was not created, please do it using createMeal <mealName> <mealType> !");
			}
		} catch (NullPointerException e) {
			System.out.println("! Command not available for this type of user !");
		}
	}

	/**
	 * Show dishes in a meal of the logged in restaurant with given name.
	 */
	public void showMeal() {
		String mealName = current_args[0];

		try {
			String meal_print = null;
			// look for the meal in his already saved meals
			meal_print = core.getCurrent_restaurant().getMealByName(mealName).toString();
			// look for the meal in the potential meals list
			for (Meal m : potential_meals) {
				if (m.getName().equalsIgnoreCase(mealName)) {
					meal_print = "Not saved yet - " + m.toString();
				}
			}
			if (meal_print != null) {
				System.out.println(meal_print.toString());
				System.out.println("Note that the price will be lower depending on the applied fidelity plan.");
			} else {
				System.out.println("! This meal does not exist !");
			}
		} catch (NullPointerException e) {
			System.out.println("! Command not available for this type of user !");
		}
	}

	/**
	 * Allows to save one of the pending meals with given meal name to the
	 * currently logged in restaurant list of meals.
	 */
	public void saveMeal() {
		String mealName = current_args[0];

		try {
			boolean found = false;
			Meal m = null;
			for (int i = 0; i < potential_meals.size(); i++) {
				m = potential_meals.get(i);
				if (m.getName().equalsIgnoreCase(mealName)) {
					core.getCurrent_restaurant().addMeal(m);
					potential_meals.remove(i);
					found = true;
				}
			}
			if (!found) {
				System.out.println("! No meal corresponds to the given meal name !");
			}
		} catch (NullPointerException e) {
			System.out.println("! Command not available for this type of user !");
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
			System.out.println("! Command not available for this type of user !");
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
			System.out.println("! Command not available for this type of user !");
		}
	}

	public void createOrder() {
		String restaurantName = current_args[0];

		try {
			current_order = core.createNewOrder(core.findRestaurantByName(restaurantName));
		} catch (NullPointerException e) {
			System.out.println("! Command not available for this type of user !");
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
			System.out.println("! Command not available for this type of user !");
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
				System.out.println("! Command not available for this type of user !");
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
				core.placeNewOrder(current_order);
				current_order = null;
			} catch (NullPointerException e) {
				System.out.println("! Command not available for this type of user !");
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
			System.out.println("! Command not available for this type of user !");
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
			System.out.println("! Command not available for this type of user !");
		} finally {
			core.logOut();
		}
	}

	/**
	 * Associates a fidelity card plan to a customer with given username.
	 */
	public void associateCard() {
		String username = current_args[0];
		String cardType = current_args[1];

		try {
			core.logIn(username);
			Customer c = core.getCurrent_customer();
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
		} catch (NullPointerException e) {
			System.out.println("! Command not available for this type of user !");
		} finally {
			core.logOut();
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
	 */
	public void showCustomers() {
		if (core.getCurrent_manager() != null) {
			for (Customer c : core.getCustomerList())
				System.out.println(c.toString());
		} else {
			System.out.println("! Command not available for this type of user !");
		}
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
			System.out.println("! Command not available for this type of user !");
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

	// TODO
	public void showTotalProfit() {
		String beginDate = current_args[0];
		String endDate = current_args[1];
	}

	/**
	 * Performs logout.
	 */
	public void logout() {
		core.logOut();
	}

	/**
	 * Returns a Calendar object parsed from input date in format dd/mm/yyyy.
	 * 
	 * @param string_date
	 *            a string containing the date to be parsed in the form
	 *            dd/mm/yyyy
	 * @return a Calendar object representing the parsed date
	 * @throws ParseException
	 */
	public static Calendar parseDateFromString(String string_date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date) formatter.parse(string_date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

}
