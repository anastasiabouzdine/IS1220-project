package clui;

import java.util.ArrayList;

import core.Core;
import core.Order;
import exceptions.AlreadyUsedUsernameException;
import parsers.ParseCouriers;
import parsers.ParseCustomers;
import parsers.ParseDishes;
import parsers.ParseManagers;
import parsers.ParseMeals;
import parsers.ParseOrders;
import parsers.ParseRestaurants;
import restaurantSetUp.AbstractFactory;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.DishFactory;
import restaurantSetUp.FactoryProducer;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
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

	/*
	 * ArrayList<FullMeal> list_fmeal =
	 * ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	 * ArrayList<HalfMeal> list_hmeal =
	 * ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	 * ArrayList<Starter> list_starter =
	 * ParseDishes.parseStarter("src/txtFILES/starters.txt");
	 * ArrayList<MainDish> list_mainDish =
	 * ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	 * ArrayList<Dessert> list_dessert =
	 * ParseDishes.parseDessert("src/txtFILES/desserts.txt"); ArrayList<Order>
	 * list_orders = ParseOrders.parseOrders();
	 */

	/**************************************************/
	/* Singleton pattern */
	private CommandProcessor() {
		core = new Core();

		core.logIn("root"); // login with manager to add lists
		core.setCustomerList(ParseCustomers.parseCustomers("src/txtFILES/customersList.txt"));
		core.setCourierList(ParseCouriers.parseCouriers("src/txtFILES/courierList.txt"));
		core.setManagerList(ParseManagers.parseManagers("src/txtFILES/managersList.txt"));
		core.setRestaurantList(ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt"));
		core.logOut();
	}

	private static final CommandProcessor INSTANCE = new CommandProcessor();

	public static CommandProcessor getInstance() {
		return INSTANCE;
	}

	public void processCmd(Command cmd) {
		current_name = cmd.getName();
		current_args = cmd.getArgs();
		if (current_name.equals("login")) {
			login();
		} else if (current_name.equals("login")) {
			login();
		} else if (current_name.equals("createMeal")) {
			createMeal();
		} else if (current_name.equals("addDish2Meal")) {
			addDish2Meal();
		} else if (current_name.equals("showMeal")) {
			showMeal();
		} else if (current_name.equals("saveMeal")) {
			saveMeal();
		} else if (current_name.equals("setMealPrice")) {
			setMealPrice();
		} else if (current_name.equals("setSpecialOffer")) {
			setSpecialOffer();
		} else if (current_name.equals("removeFromSpecialOffer")) {
			removeFromSpecialOffer();
		} else if (current_name.equals("addDish")) {
			addDish();
		} else if (current_name.equals("addMeal2Order")) {
			addMeal2Order();
		} else if (current_name.equals("endOrder")) {
			endOrder();
		} else if (current_name.equals("registerClient")) {
			registerClient();
		} else if (current_name.equals("registerCourier")) {
			registerCourier();
		} else if (current_name.equals("onDuty")) {
			onDuty();
		} else if (current_name.equals("offDuty")) {
			offDuty();
		} else if (current_name.equals("addContactInfo")) {
			addContactInfo();
		} else if (current_name.equals("associateCard")) {
			associateCard();
		} else if (current_name.equals("associateAgreement")) {
			associateAgreement();
		} else if (current_name.equals("registerRestaurant")) {
			registerRestaurant();
		} else if (current_name.equals("notifySpecialOffer")) {
			notifySpecialOffer();
		} else if (current_name.equals("showMeals")) {
			showMeals();
		} else if (current_name.equals("logout")) {
			logout();
		} 
	}

	/**************************************************/
	/* Apply commands to core */

	public void login() {
		String username = current_args[0];
		String info = core.logIn(username);
		System.out.println(info);
	}

	// WAIT FOR NEW REQ. WITH HALF- AND FULLMEALS
	public void createMeal() {
		String mealName = current_args[0];

//		Meal m = meal_factory.getMeal("fullmeal", mealName);
	}

	// WAIT FOR REQ. WITH HALF- AND FULLMEALS
	public void addDish2Meal() {
		String dishName = current_args[0];
		String mealName = current_args[1];

		Restaurant r = core.getCurrent_restaurant();
		Dish d = r.getDishByName(dishName);
		Meal m = r.getMealByName(mealName);
	}

	/**
	 * Show dishes in a meal with given name.
	 */
	public void showMeal() {
		String mealName = current_args[0];

		Meal meal = core.getCurrent_restaurant().getMealByName(mealName);
		if (meal != null) {
			System.out.println(meal.toString());
			;
		} else {
			System.out.println("! This meal does not exist !");
		}
	}

	public void saveMeal() {
		String mealName = current_args[0];
	}

	public void setMealPrice() {
		String mealName = current_args[0];
	}

	public void setSpecialOffer() {
		String mealName = current_args[0];

		Meal meal = core.getCurrent_restaurant().getMealByName(mealName);
		if (meal != null) {
			core.setSpecialMeal(meal);
		} else {
			System.out.println("! This meal does not exist !");
		}
	}

	// WAIT FOR NEW REQ. (should not have an argument)
	public void removeFromSpecialOffer() {
		String mealName = current_args[0];

		core.getCurrent_restaurant().setSpecMeal(null);
	}

	// WAIT FOR NEW REQ. (category means standard/vegetarian OR
	// starter/maindish/dessert)
	public void addDish() {
		String dishName = current_args[0];
		String dishCategory = current_args[1];
		double unitPrice = Double.parseDouble(current_args[2]);
	}

	// WAIT FOR NEW REQ. (there will probably be a startOrder function to
	// initialize order)
	public void addMeal2Order() {
		String mealName = current_args[0];
	}

	// WAIT FOR NEW REQ. (see above function)
	public void endOrder() {
	}

	// WAIT FOR NEW REQUIREMENTS (command might change)
	public void registerClient() {
		String firstName = current_args[0];
		String lastName = current_args[1];
		String username = current_args[2].trim();
		String address = current_args[3];
		String password = current_args[4];

		Customer c = new Customer(firstName, lastName, new Address(address), "00000000", "null@null.null", username);
		try {
			core.register(c);
		} catch (AlreadyUsedUsernameException e) {
			System.out.println("! This username is already taken ! ");
		}
	}

	// WAIT FOR NEW REQUIREMENTS (command might change)
	public void registerCourier() {
		String firstName = current_args[0];
		String lastName = current_args[1];
		String username = current_args[2];
		String position = current_args[3];
		String password = current_args[4];

	}

	/**
	 * Set the state of a courier with given username as on-duty.
	 */
	public void onDuty() {
		String username = current_args[0];

		core.logIn(username);
		Courier c = core.getCurrent_courier();
		if (c != null) {
			c.setAvailable(true);
			System.out.println(username + " has been set on duty.");
		} else {
			System.out.println("! This username is not associated with a courier !");
		}
		core.logOut();
	}

	/**
	 * Set the state of a courier with given username as off-duty.
	 */
	public void offDuty() {
		String username = current_args[0];

		core.logIn(username);
		core.getCurrent_courier().setAvailable(false);
		core.logOut();
	}

	// WAIT FOR NEW REQUIREMENTS
	public void addContactInfo() {
		String contactInfo = current_args[0];
	}

	/**
	 * Associates a fidelity card plan to a customer with given username.
	 */
	public void associateCard() {
		String username = current_args[0];
		String cardType = current_args[1];

		core.logIn(username);
		Customer c = core.getCurrent_customer();
		if (c != null) {
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
			System.out.println("! This username is not associated with a customer !");
		}
		core.logOut();
	}

	/**
	 * To associate an agreement (true or false) to a user.
	 */
	public void associateAgreement() {
		String username = current_args[0];
		boolean agreement = Boolean.parseBoolean(current_args[1]);

		core.logIn(username);
		Customer c = core.getCurrent_customer();
		if (c != null) {
			c.setBeNotified(agreement);
			System.out.println("Agreement has been set for " + username + ".");
		} else {
			System.out.println("! This username is not associated with a customer !");
		}
		core.logOut();
	}

	/**
	 * Adds a restaurant to the system with given name, username, address and
	 * password, recalling that the address must have the syntax "xCoord,yCoord".
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
	 * @deprecated this function is of no use for our program as we
	 *             automatically notify registered customers whenever a
	 *             restaurant sets a new special meal of the week offer
	 */
	public void notifySpecialOffer() {
		String message = current_args[0];
		String mealName = current_args[1];
		double specialPrice = Double.parseDouble(current_args[2]);
	}

	// WAIT FOR NEW REQUIREMENTS (difference between call from manager and
	// restaurant)
	public void showMeals() {
		String orderingCriteria = current_args[0];
	}

	/**
	 * Performs logout.
	 */
	public void logout() {
		core.logOut();
	}


}
