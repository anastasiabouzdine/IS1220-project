package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.Core;
import core.Order;
import exceptions.AlreadyUsedUsernameException;
import parsers.*;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Starter;
import users.Courier;
import users.Customer;
import users.Manager;
import users.Restaurant;

public class CoreTest {
	
	//TODO update CoreTest so that it will be accessible
	
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
	
	}
	
	/*********************************************************************/
	/* Add, remove, log in, log out users */ 
	@Test
	public void removeAndAddUser() throws AlreadyUsedUsernameException {
		Courier e = list_courier.get(0);
		mf1.addUser(e);
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
		System.out.println("TEST removeAndAddUser : DONE\n");
	}
	
	@Test(expected=AlreadyUsedUsernameException.class)
	public void addAlreadyUsedUsername() throws AlreadyUsedUsernameException  {
		mf1.addUser(list_restaurant.get(0));
		System.out.println("TEST addAlreadyUsedUsername : DONE\n");
	}

	@Test
	public void logInWithUnknownUser() {
		assertTrue(mf1.logIn("unknown user 42") == null);
		System.out.println("TEST logInWithUnknownUser : DONE\n");
	}
	
	@Test
	public void logInWithKnownCustomer() {
		mf1.logOut();
		assertTrue(mf1.getCurrent_customer() == null);
		assertTrue(mf1.logIn(list_customer.get(0).getUsername()) != null);
		assertTrue(mf1.getCurrent_customer() != null);
		System.out.println("TEST logInWithKnownCustomer : DONE\n");
	}
	
	@Test
	public void logInAndThenLogOut() {
		mf1.logOut();
		assertTrue(mf1.getCurrent_user() == null);
		mf1.logIn(list_customer.get(0).getUsername());
		assertTrue(mf1.getCurrent_user() != null);
		mf1.logOut();
		assertTrue(mf1.getCurrent_user() == null);
		System.out.println("TEST logInAndThenLogOut : DONE\n");
	}
	
	/*********************************************************************/
	/* Setting a special meal of the week offer and notifying customers */ 
	
	@Test
	public void logInAndAddSpecMealAndCheckIfCustomersAreNotified() {
		mf1.logIn(rest1.getUsername());
		Meal fm1 = list_fmeal.get(0);
		mf1.getCurrent_restaurant().addMeal(fm1);
		Meal fm2 = list_fmeal.get(1);
		mf1.getCurrent_restaurant().addMeal(fm2);
		mf1.setSpecialMeal(fm1);
		mf1.setSpecialMeal(fm2);
		mf1.logOut();
		assertTrue(list_customer.get(0).getMessageBox().size() == 2);
		
		System.out.println("TEST logInAndAddSpecMealAndCheckIfCustomersAreNotified : DONE\n");
	}
	
	/*********************************************************************/
	/* Counting meals and treating orders */ 
	
	@Test
	public void addAndPrintListOfMealsByCount() {
		make2orders5HalfMeals();
		assertTrue(7 == mf1.getSortedList(true).first().getCount());
		assertTrue(3 == mf1.getSortedList(rest2, true).first().getCount());
		assertTrue(1 == mf1.getSortedList(rest2, false).first().getCount());
		
		mf1.setSortPolicyToDishSort();
		
		make2orders6Dishes();
			
		assertTrue(18 == mf1.getSortedList(true).first().getCount());
		assertTrue(9 == mf1.getSortedList(rest1, true).first().getCount());
		assertTrue(4 == mf1.getSortedList(rest1, false).first().getCount());
		
		System.out.println("TEST addAndPrintListOfMealsByCount : DONE\n");
	}
	
	
	@Test
	public void treatOrder() {
		mf1.setCourierList(list_courier);
		Restaurant rest1 = list_restaurant.get(0);
		
		rest1.addMeal(list_hmeal.get(0));
		rest1.addMeal(list_hmeal.get(1));
		rest1.setSpecMeal(list_hmeal.get(1));
		
		Order order1 = mf1.createNewOrder(list_customer.get(0), rest1);
		order1.addMeal(list_hmeal.get(0), 3);
		mf1.placeNewOrder(order1);
		mf1.treatNewOrders();
		
		
		mf1.setDeliveryPolicyToFairOcc();
		Order order2 = mf1.createNewOrder(list_customer.get(0), list_restaurant.get(0));
		order2.addMeal(list_hmeal.get(0), 3);
		mf1.placeNewOrder(order2);
		mf1.treatNewOrders();
		
		System.out.println("TEST treatOrder : DONE\n");
	}
	
	/*********************************************************************/
	/* Computing profit, income and average profit */ 
	
	@Test
	public void checkIfCalcTotalIncomeWorks() {
		make3orders();
		mf1.autoSetDateAfter();
		double totalIn = mf1.calcTotalIncome();
		//   3xdish1 : 3x8.3 = 24.9
		// + 2xdish2 : 2x6.35 = 12.7
		// + 1xdish3 : 1x16.85 = 16.85
		// + 3x(serviceFee + deliveryCost) : 3x2.5 = 7.5
		// ------------------------------------------------------
		// = 61.95
		
		assertTrue(equalsDouble(totalIn,61.95));
		System.out.println("TEST chekcIfCalcTotalIncomeWorks : DONE\n");
	}
	
	@Test
	public void chekcIfCalcTotalProfitWorks() {
		make3orders();
		mf1.autoSetDateAfter();
		double totalProfit = mf1.calcTotalProfit();
		//   markup : 0.05*(54.45) = 2.72
		// + 3xserviceFee : 3x2.5 = 7.5
		// - 3xdeliveryCost : 3x4 = 12
		// ------------------------------------------------------
		// = -1.78
		double trueTotalProfit = -1.78D;
		assertTrue(equalsDouble(totalProfit,trueTotalProfit));
		
		System.out.println("TEST checkIfCalcTotalProfitWorks : DONE\n");
	}
	
	@Test
	public void chekcIfCalcAverageProfitWorks() {
		make3orders();
		mf1.autoSetDateAfter();
		double avgProfit = mf1.calcAverageProfit();
		// = -1.78 /3
		double trueAvg = Order.round2(-1.78D/3);
		assertTrue(equalsDouble(avgProfit, trueAvg));
		
		System.out.println("TEST checkIfCalcAverageProfitWorks : DONE\n");
	}
	
	
//	/*********************************************************************/
//	/* Most/least selling restaurants and active couriers */ 
	
	@Test
	public void mostAndLeastSellingRestaurant() {
		make3orders();
		Restaurant temp_restaurant;
		System.out.println(mf1.getMostOrLeastSellingRestaurant(true));
		// Most selling
		temp_restaurant = mf1.getMostOrLeastSellingRestaurant(true);
		assertTrue(temp_restaurant.equals(rest1));
		// Least selling
		temp_restaurant = mf1.getMostOrLeastSellingRestaurant(false);
		assertTrue(temp_restaurant.equals(rest3));
		
		System.out.println("TEST mostAndLeastSellingRestaurant : DONE\n");
	}
	
	/* /!\ Note that we can only test if the most/least active
	 * courier method works but not if the returned courier is the
	 * good one as there is a random factor that plays when the 
	 * courier decides to accept an offer or not. */
	@Test
	public void mostOrLeastActiveCourier() {
		make3orders();
		
		Courier temp_courier;
		// Most active
		temp_courier = mf1.getMostOrLeastActiveCourier(true);
		assertTrue(temp_courier != null);
		// Least active
		temp_courier = mf1.getMostOrLeastActiveCourier(false);
		assertTrue(temp_courier != null);

		System.out.println("TEST mostOrLeastActiveCourier : DONE\n");
	}
	
	/*********************************************************************/
	/* calculate Target profit quantities */ 
	
	@Test
	public void simulateMarkupPerc() {
		make3orders();
		
		double serviceFee = 5;
		double deliveryFee = 3;
		double profit = 9;
		
		double sum = 0;
		double placeHolder = 0;
		
		// profit for one order = order price * markupPercentage + service fee - delivery cost
		
		//there are three orders so we'll calculate placeHolder as the left 
		// side of the equation and sum as the rigth side of the function 
		// so that placeHolder = markupPercentage * sum
		for(Order order : mf1.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit - 3*serviceFee + 3*deliveryFee;
		double expected = Order.round4((double)placeHolder/Order.round2(sum));
		double value = mf1.simulateProfit(profit, deliveryFee, serviceFee);
		
		assertTrue(equalsDouble(expected, value));
		System.out.println("TEST calculateProfit() : DONE\n");
	}
	
	@Test
	public void simulateDeliveryCost() {
		make3orders();
		
		mf1.setTargetProfitPolicyToDelivCostProf();;
		
		double serviceFee = 5;
		double markupProfit = 0.05;
		double profit = 9;
		
		double sum = 0;
		double placeHolder = 0;
		
		// profit for one order = order price * markupPercentage + service fee - delivery cost
		
		//there are three orders so we'll calculate placeHolder as the left 
		// side of the equation and sum as the rigth side of the function 
		// so that placeHolder = markupPercentage * sum
		for(Order order : mf1.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit - 3*serviceFee - sum*markupProfit;
		
		assertTrue(Order.round2(-placeHolder/3)==mf1.simulateProfit(profit, markupProfit, serviceFee));
		System.out.println("TEST calculateProfit() : DONE\n");
	}
	
	@Test
	public void simulateServiceFee() {
		make3orders();
		
		mf1.setTargetProfitPolicyToSerFeeProf();
		
		double deliveryFee = 5;
		double markupProfit = 0.05;
		double profit = 9;
		
		double sum = 0;
		double placeHolder = 0;
		
		// profit for one order = order price * markupPercentage + service fee - delivery cost
		
		//there are three orders so we'll calculate placeHolder as the left 
		// side of the equation and sum as the rigth side of the function 
		// so that placeHolder = markupPercentage * sum
		for(Order order : mf1.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit + 3*deliveryFee - sum*markupProfit;
		
		assertTrue(Order.round2(placeHolder/3)==mf1.simulateProfit(profit, markupProfit, deliveryFee));
		System.out.println("TEST calculateProfit() : DONE\n");
	}
	
	/**************************************************************************************************/
	/* Get customer history */
	
	@Test
	public void assertNoHistoryIsGivenWhenNoCustomerLoggedIn() {
		make2orders5HalfMeals();
		
		assertTrue(mf1.getHistoryOfOrders() == null);

		System.out.println("TEST assertNoHistoryIsGivenWhenNoCustomerLoggedIn : DONE\n");
	}
	
	@Test
	public void checkIfHistoryOfOrdersIsCorrectWhenCustomerLoggedIn() {
		make2orders5HalfMeals();
		Customer cust_at_test = list_customer.get(2);
		
		mf1.logIn(cust_at_test.getUsername());
		assertTrue(mf1.getHistoryOfOrders().size() == 1);

		System.out.println("TEST checkIfHistoryOfOrdersIsCorrectWhenCustomerLoggedIn : DONE\n");
	}
	
	/**************************************************************************************************/
	/* Helpers */
	
	
	public void make3orders() {
		mf1.setCourierList(list_courier);

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
	
	public void make2orders5HalfMeals() {
		mf1.setCourierList(list_courier);

		HalfMeal hm1 = list_hmeal.get(0);
		HalfMeal hm2 = list_hmeal.get(1);
		HalfMeal hm3 = list_hmeal.get(2);
		HalfMeal hm4 = list_hmeal.get(3);
		
		rest1.addMeal(hm3);
		rest1.addMeal(hm1);
		
		rest2.addMeal(hm2);
		rest2.addMeal(hm4);
		
		System.out.println("Making 2 orders...");
		/* Make sure that all parameters are in accordance with tests */
		mf1.setMarkup_percentage(0.05);
		mf1.setDeliveryCost(4.0);
		mf1.setServiceFee(2.5);

		Order order1 = mf1.createNewOrder(list_customer.get(2), rest1);
		order1.addMeal(hm3, 4);
		order1.addMeal(hm1, 4);
		order1.addMeal(hm1, 3);
		mf1.placeNewOrder(order1);

		Order order2 = mf1.createNewOrder(list_customer.get(3), rest2);
		order2.addMeal(hm2, 1);
		order2.addMeal(hm4, 3);
		mf1.placeNewOrder(order2);

		// Treat the three placed orders : two for rest1 and one for rest2
		mf1.treatNewOrders();
		System.out.println("Done with the 2 meal orders !");
	}
	
	/**
	 * Compares with flexibility if two doubles are equals up to 2 decimal places.
	 * @param expected
	 * @param value
	 * @return
	 */
	public static boolean equalsDouble(double expected, double value){
		return (Math.abs(value-expected) < 0.01);
	}
	
	public void make2orders6Dishes() {
		
		Dish d1 = list_starter.get(0);
		Dish d2 = list_starter.get(1);
		Dish d3 = list_starter.get(2);
		Dish d4 = list_starter.get(3);
		Dish d5 = list_starter.get(4);
		
		rest1.addStarter((Starter) d1);
		rest1.addStarter((Starter) d3);
		rest1.addStarter((Starter) d4);
		rest1.addStarter((Starter) d5);
		
		rest2.addStarter((Starter) d2);
		rest2.addStarter((Starter) d5);
		
		System.out.println("Making 2 orders...");
		/* Make sure that all parameters are in accordance with tests */
		mf1.setMarkup_percentage(0.05);
		mf1.setDeliveryCost(4.0);
		mf1.setServiceFee(2.5);

		Order order1 = mf1.createNewOrder(list_customer.get(2), rest1);
		order1.addDish(d1, 4);
		order1.addDish(d3, 4);
		order1.addDish(d4, 5);
		order1.addDish(d5, 9);
		mf1.placeNewOrder(order1);

		Order order2 = mf1.createNewOrder(list_customer.get(3), rest2);
		order2.addDish(d2, 1);
		order2.addDish(d5, 9);
		mf1.placeNewOrder(order2);

		// Treat the three placed orders : two for rest1 and one for rest2
		mf1.treatNewOrders();
		System.out.println("Done with the 2 dish orders !");
	}
		
}
