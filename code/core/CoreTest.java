package core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.AlreadyUsedUsernameException;
import parsers.*;
import policies.ALaCarteSorted;
import policies.DeliveryPolicy;
import policies.FairOccupationDelivery;
import restaurantSetUp.Address;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Courier;
import users.Customer;
import users.Restaurant;

public class CoreTest {
	
	//TODO
	
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

	@Before
	public void setUserLists() {
		mf1.setCourierList(list_courier);
		mf1.setCustomerList(list_customer);
		mf1.setRestaurantList(list_restaurant);
	}
	
	@Test
	public void removeAndAddUser() {
		Courier e = list_courier.get(0);
		assertTrue(mf1.logIn(e.getUsername()) != null);
		mf1.removeUser(e);
		assertTrue(mf1.logIn(e.getUsername()) == null);
		assertTrue(!mf1.getCourierList().contains(e));
		try {
			mf1.addUser(e);
		} catch (AlreadyUsedUsernameException e1) {
			e1.printStackTrace();
		}
		assertTrue(mf1.logIn(e.getUsername()) != null);
		assertTrue(mf1.getCourierList().contains(e));
	}
	
	@Test(expected=AlreadyUsedUsernameException.class)
	public void addAlreadyUsedUsername() throws AlreadyUsedUsernameException  {
		mf1.addUser(list_restaurant.get(0));
	}
	
	@Test
	public void logInWithUnknownUser() {
		assertTrue(mf1.logIn("unknown user 42") == null);
		System.out.println("TEST logInWithUnknownUser : DONE\n");
	}
	
	@Test
	public void logInWithKnownCustomer() {
		assertTrue(mf1.getCurrent_customer() == null);
		assertTrue(mf1.logIn(list_customer.get(0).getUsername()) != null);
		assertTrue(mf1.getCurrent_customer() != null);
		System.out.println("TEST logInWithKnownCustomer : DONE\n");
	}
	
	@Test
	public void logInAndThenLogOut() {
		assertTrue(mf1.getCurrent_user() == null);
		mf1.logIn(list_customer.get(0).getUsername());
		assertTrue(mf1.getCurrent_user() != null);
		mf1.logOut();
		assertTrue(mf1.getCurrent_user() == null);
		System.out.println("TEST logInAndThenLogOut : DONE\n");
	}
	
	@Test
	public void addAndPrintListOfMealsByCount() {
		HalfMeal hm1 = list_hmeal.get(0);
		HalfMeal hm2 = list_hmeal.get(1);
		HalfMeal hm3 = list_hmeal.get(2);
		HalfMeal hm4 = list_hmeal.get(3);
		Dish d1 = list_starter.get(0);
		Dish d2 = list_starter.get(1);
		Dish d3 = list_starter.get(2);
		Dish d4 = list_starter.get(3);
		Dish d5 = list_starter.get(4);
		
		Restaurant rest1 = list_restaurant.get(0);
		Restaurant rest2 = list_restaurant.get(1);
				
		mf1.addMealCount(hm3, 4, rest1);
		mf1.addMealCount(hm2, 1, rest2);
		mf1.addMealCount(hm1, 4, rest1);
		mf1.addMealCount(hm1, 3, rest1);
		mf1.addMealCount(hm4, 3, rest2);
		
		mf1.addDishCount(d1, 4, rest1);
		mf1.addDishCount(d2, 1, rest2);
		mf1.addDishCount(d3, 4, rest1);
		mf1.addDishCount(d4, 5, rest1);
		mf1.addDishCount(d5, 9, rest2);
		mf1.addDishCount(d5, 9, rest1);
		
		assertTrue(7 == mf1.getSortedList(true).first().getCount());
		assertTrue(3 == mf1.getSortedList(rest2, true).first().getCount());
		assertTrue(1 == mf1.getSortedList(rest2, false).first().getCount());
		
		mf1.setSort(mf1.getDishSort());
		
		System.out.println(mf1.getSortedList(true));
		System.out.println("TEST addAndPrintListOfMealsByCount : DONE\n");
	}
	
	@Test
	public void treatOrder() {		
		Restaurant r1 = list_restaurant.get(0);
		
		r1.addMeal(list_hmeal.get(0));
		r1.addMeal(list_hmeal.get(1));
		r1.setSpecMeal(list_hmeal.get(1));
		
		Order order1 = mf1.createNewOrder(list_customer.get(0), r1);
		order1.addMeal(list_hmeal.get(0), 3);
		mf1.placeNewOrder(order1);
		mf1.treatNewOrders();
		
		DeliveryPolicy fPolicy = new FairOccupationDelivery();
		mf1.setdPolicy(fPolicy);
		Order order2 = mf1.createNewOrder(list_customer.get(0), list_restaurant.get(0));
		order2.addMeal(list_hmeal.get(0), 3);
		mf1.placeNewOrder(order2);
		mf1.treatNewOrders();
		
		System.out.println("TEST treatOrder : DONE\n");
	}

}
