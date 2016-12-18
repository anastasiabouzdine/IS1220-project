package clui;

import java.util.ArrayList;
import java.util.Arrays;

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
import restaurantSetUp.Dessert;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Address;
import users.Customer;

public class CommandProcessor {
	private Core core;
	private String current_name;
	private String[] current_args;
	
	ArrayList<FullMeal> list_fmeal = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> list_hmeal = ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/desserts.txt");
	ArrayList<Order> list_orders = ParseOrders.parseOrders();
	
	/**************************************************/
	/* Singleton pattern */
	private CommandProcessor()
	{
		core = new Core();
		
		core.logIn("root"); // login with manager to add lists
		core.setCustomerList(ParseCustomers.parseCustomers("src/txtFILES/customersList.txt"));
		core.setCourierList(ParseCouriers.parseCouriers("src/txtFILES/courierList.txt"));
		core.setManagerList(ParseManagers.parseManagers("src/txtFILES/managersList.txt"));
		core.setRestaurantList(ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt"));
		core.logOut();
	}
	
	private static CommandProcessor INSTANCE = new CommandProcessor();
 
	public static CommandProcessor getInstance() {	
		return INSTANCE;
	}
	
	public void processCmd(Command cmd){
		current_name = cmd.getName();
		current_args = cmd.getArgs();
		if (current_name.equals("login")){
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
		}
	}
	
	/**************************************************/
	/* Apply commands to core */
	
	public void login(){
		String username = current_args[0];
		String info = core.logIn(username);
		System.out.println(info);
	}
	
	public void createMeal() {
	    String mealName = current_args[0];
	}
	public void addDish2Meal() {
	    String dishName = current_args[0];
	    String mealName = current_args[1];
	}
	public void showMeal() {
	    String mealName = current_args[0];
	}
	public void saveMeal() {
	    String mealName = current_args[0];
	}
	public void setMealPrice() {
	    String mealName = current_args[0];
	}
	public void setSpecialOffer() {
	    String mealName = current_args[0];
	}
	public void removeFromSpecialOffer() {
	    String mealName = current_args[0];
	}
	public void addDish() {
	    String dishName = current_args[0];
	    String dishCategory = current_args[1];
	    double unitPrice = Double.parseDouble(current_args[2]);
	}
	public void addMeal2Order() {
	    String mealName = current_args[0];
	}
	public void endOrder() {
	}
	// WAIT FOR NEW REQUIREMENTS (command might change)
	public void registerClient() {
	    String firstName = current_args[0];
	    String lastName = current_args[1];
	    String username = current_args[2].trim();
	    String address = current_args[3]; 
	    String password = current_args[4];
	    
	    // address has to have the syntax : [xCoord;yCoord]
	    String[] coord = address.trim().substring(1, address.length() - 2).split(";");
	    Customer c = new Customer(firstName, lastName,
	    		new Address(Integer.parseInt(coord[0]),Integer.parseInt(coord[1])),
	    		"00000000", "null@null.null", username);
	    
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
	public void onDuty() {
	    String username = current_args[0];
	}
	public void offDuty() {
	    String username = current_args[0];
	}
	public void addContactInfo() {
	    String contactInfo = current_args[0];
	}
	public void associateCard() {
	    String username = current_args[0];
	    String cardType = current_args[1];
	}
	public void associateAgreement() {
	    String username = current_args[0];
	    String agreement = current_args[1];
	}
	public void registerRestaurant() {
	    String name = current_args[0];
	    String username = current_args[1];
	    String address = current_args[2];
	    String password = current_args[3];
	}
	public void notifySpecialOffer() {
	    String message = current_args[0];
	    String mealName = current_args[1];
	    double specialPrice = Double.parseDouble(current_args[2]);
	}
	public void showMeals() {
	    String orderingCriteria = current_args[0];
	}
	
	

}
