package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.Core;
import core.Order;
import core.ParseCustomers;
import exceptions.AlreadyUsedUsernameException;
import parsers.ParseCouriers;
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
import users.Courier;
import users.Customer;
import users.Manager;
import users.Restaurant;

public class ComplexityTest {
	
Core mf1 = new Core("MyFoodora");
	
	ArrayList<FullMeal> list_fmeal = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> list_hmeal = ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/dessert.txt");
	ArrayList<Restaurant> list_restaurant = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
	ArrayList<Order> list_orders = ParseOrders.parseOrders();
	ArrayList<Courier> list_courier = ParseCouriers.parseCouriers("src/txtFILES/courierList.txt");
	ArrayList<Customer> list_customer = ParseCustomers.parseCustomers("src/txtFILES/customersList.txt");
	ArrayList<Manager> list_manager = ParseManagers.parseManagers("src/txtFILES/managersList.txt");
	
	Restaurant rest1 = list_restaurant.get(0);
	Restaurant rest2 = list_restaurant.get(1);
	Restaurant rest3 = list_restaurant.get(2);
	
	Courier cour1 = list_courier.get(0);
	Courier cour2 = list_courier.get(1);
	Courier cour3 = list_courier.get(2);

	@Before
	public void setUserLists() throws AlreadyUsedUsernameException {
		mf1.register(list_manager.get(0));
		mf1.logIn("johnBoss");
		
		mf1.setCustomerList(list_customer);
		mf1.setRestaurantList(list_restaurant);
		mf1.setCourierList(list_courier);
	}

	public void make3orders() {
		System.out.println("Making 3 orders...");
		/* Make sure that all parameters are in accordance with tests */
		mf1.setMarkup_percentage(0.05);
		mf1.setDeliveryCost(4.0);
		mf1.setServiceFee(2.5);

		Order order1 = mf1.createNewOrder(list_customer.get(2), rest1);
		order1.addDish(list_mainDish.get(0), 3);
		mf1.placeNewOrder(order1);

		Order order2 = mf1.createNewOrder(list_customer.get(3), rest3);
		order2.addDish(list_mainDish.get(1), 2);
		mf1.placeNewOrder(order2);

		Order order3 = mf1.createNewOrder(list_customer.get(4), rest1);
		order3.addDish(list_mainDish.get(2), 1);
		mf1.placeNewOrder(order3);
		// Treat the three placed orders : two for rest1 and one for rest2
		mf1.treatNewOrders();
		System.out.println("Done with the 3 orders !");
	}
	
	@Test
	public void printExecutionTime(){
		double startTime = System.currentTimeMillis();
		for(int i=0; i < 10000; i++){
			make3orders();
		}
		double endTime = System.currentTimeMillis();
		System.out.println("execution time = "+ (endTime - startTime)/1000 + "s");
	}
	

}
