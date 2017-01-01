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

	Core core = new Core();
	ArrayList<FullMeal> list_fmeal = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> list_hmeal = ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/desserts.txt");
	ArrayList<Restaurant> list_restaurant = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
	ArrayList<Customer> list_customer = ParseCustomers.parseCustomers("src/txtFILES/customersList.txt");
	ArrayList<Order> list_orders = ParseOrders.parseOrders(list_customer, core);
	ArrayList<Courier> list_courier = ParseCouriers.parseCouriers("src/txtFILES/courierList.txt");
	ArrayList<Manager> list_manager = ParseManagers.parseManagers("src/txtFILES/managersList.txt");

	Restaurant rest1 = list_restaurant.get(0);
	Restaurant rest2 = list_restaurant.get(1);
	Restaurant rest3 = list_restaurant.get(2);

	Courier cour1 = list_courier.get(0);
	Courier cour2 = list_courier.get(1);
	Courier cour3 = list_courier.get(2);


	@Before
	public void setUserLists() throws AlreadyUsedUsernameException {
		core.register(list_manager.get(0));
		core.logIn("john45");
		core.logOut();
		core.register(list_customer.get(0));
		core.logIn("john45");
		core.setCustomerList(list_customer);
		core.setRestaurantList(list_restaurant);			
	}

	/*********************************************************************/
	/* Add, remove, log in, log out users */ 

	@Test
	public void removeAndAddUser() throws AlreadyUsedUsernameException {
		Courier e = list_courier.get(0);
		core.addUser(e);
		assertTrue(core.logIn(e.getUsername()) != null);
		core.removeUser(e);
		assertTrue(core.logIn(e.getUsername()).equals("Not a valid username!"));
		assertTrue(!core.getCourierList().contains(e));
		try {
			core.addUser(e);
		} catch (AlreadyUsedUsernameException e1) {
			e1.printStackTrace();
		}
		assertNotNull(core.logIn(e.getUsername()));
		assertTrue(core.getCourierList().contains(e));
		System.out.println("TEST removeAndAddUser : DONE\n");
	}

	@Test(expected=AlreadyUsedUsernameException.class)
	public void addAlreadyUsedUsername() throws AlreadyUsedUsernameException  {
		core.addUser(list_restaurant.get(0));
	}

	@Test
	public void logInWithUnknownUser() {
		assertTrue(core.logIn("unknown user 42").equals("Not a valid username!"));
		System.out.println("TEST logInWithUnknownUser : DONE\n");
	}

	@Test
	public void logInWithKnownCustomer() {
		core.logOut();
		assertTrue(core.getCurrent_customer() == null);
		assertTrue(core.logIn(list_customer.get(0).getUsername()) != null);
		assertTrue(core.getCurrent_customer() != null);
		System.out.println("TEST logInWithKnownCustomer : DONE\n");
	}

	@Test
	public void logInAndThenLogOut() {
		core.logOut();
		assertTrue(core.getCurrent_user() == null);
		core.logIn(list_customer.get(0).getUsername());
		assertTrue(core.getCurrent_user() != null);
		core.logOut();
		assertTrue(core.getCurrent_user() == null);
		System.out.println("TEST logInAndThenLogOut : DONE\n");
	}

	/*********************************************************************/
	/* Setting a special meal of the week offer and notifying customers */ 

	@Test
	public void logInAndAddSpecMealAndCheckIfCustomersAreNotified() {
		core.logIn(rest1.getUsername());
		Meal fm1 = list_fmeal.get(0);
		core.getCurrent_restaurant().addMeal(fm1);
		Meal fm2 = list_fmeal.get(1);
		core.getCurrent_restaurant().addMeal(fm2);
		core.setSpecialMeal(fm1);
		core.setSpecialMeal(fm2);
		core.logOut();
		assertTrue(list_customer.get(0).getMessageBox().size() == 2);

		System.out.println("TEST logInAndAddSpecMealAndCheckIfCustomersAreNotified : DONE\n");
	}

	/*********************************************************************/
	/* Counting meals and treating orders */ 

	@Test
	public void addAndPrintListOfMealsByCount() {
		make2orders5HalfMeals();
		assertTrue(7 == core.getSortedList(true).first().getCount());
		assertTrue(3 == core.getSortedList(rest2, true).first().getCount());
		assertTrue(1 == core.getSortedList(rest2, false).first().getCount());

		core.setSortPolicyToDishSort();
		make2orders6Dishes();

		assertTrue(18 == core.getSortedList(true).first().getCount());
		assertTrue(9 == core.getSortedList(rest1, true).first().getCount());
		assertTrue(4 == core.getSortedList(rest1, false).first().getCount());

		System.out.println("TEST addAndPrintListOfMealsByCount : DONE\n");
	}


	@Test
	public void treatOrder() {
		core.setCourierList(list_courier);
		Restaurant rest1 = list_restaurant.get(0);

		rest1.addMeal(list_hmeal.get(0));
		rest1.addMeal(list_hmeal.get(1));
		rest1.setSpecialMeal(list_hmeal.get(1));

		core.logOut();
		core.logIn("cuspvp23");

		Order order1 = core.createNewOrder(rest1);
		order1.addMeal(list_hmeal.get(0), 3);
		placeOrder(order1);

		core.logOut();
		core.logIn("cuspvp23");

		core.setDeliveryPolicyToFairOcc();
		Order order2 = core.createNewOrder(list_restaurant.get(0));
		order2.addMeal(list_hmeal.get(0), 3);
		placeOrder(order2);


		System.out.println("TEST treatOrder : DONE\n");
	}

	/*********************************************************************/
	/* Computing profit, income and average profit */ 

	@Test
	public void checkIfCalcTotalIncomeWorks() {
		make3orders();
		core.autoSetDateAfter();
		double totalIn = core.calcTotalIncome();
		//   3xdish1 : 3x8.3 = 24.9
		// + 2xdish2 : 2x6.35 = 12.7
		// + 1xdish3 : 1x16.85 = 16.85
		// + 3x(serviceFee + deliveryCost) : 3x2.5 = 7.5
		// ------------------------------------------------------
		// = 61.95

		assertEquals(totalIn, 61.95, 0.01);
		System.out.println("TEST chekcIfCalcTotalIncomeWorks : DONE\n");
	}

	@Test
	public void chekcIfCalcTotalProfitWorks() {
		make3orders();
		core.autoSetDateAfter();
		double totalProfit = core.calcTotalProfit();
		//   markup : 0.05*(54.45) = 2.72
		// + 3xserviceFee : 3x2.5 = 7.5
		// - 3xdeliveryCost : 3x4 = 12
		// ------------------------------------------------------
		// = -1.78
		double trueTotalProfit = -1.78D;
		assertEquals(totalProfit,trueTotalProfit, 0.01);

		System.out.println("TEST checkIfCalcTotalProfitWorks : DONE\n");
	}

	@Test
	public void chekcIfCalcAverageProfitWorks() {
		make3orders();
		core.autoSetDateAfter();
		double avgProfit = core.calcAverageProfit();
		// = -1.78 /3
		double trueAvg = Order.round2(-1.78D/3);
		assertEquals(avgProfit, trueAvg, 0.01);

		System.out.println("TEST checkIfCalcAverageProfitWorks : DONE\n");
	}

	/*********************************************************************/
	/* Fidelity plan with points */ 

	@Test
	public void checkFidPlan() {
		Customer cust = list_customer.get(4); // username : cuscatiad
		cust.setFidCardToPoints();
		assertEquals(cust.getNumberOfFidelityPoints(), 0);
		core.setCourierList(list_courier);
		/* Make sure that all parameters are in accordance with tests */
		core.setMarkup_percentage(0.05);
		core.setDeliveryCost(4.0);
		core.setServiceFee(2.5);

		core.logOut();
		core.logIn("cuscatiad");

		Order order1 = core.createNewOrder(rest1);
		order1.addDish(list_mainDish.get(0), 150);
		placeOrder(order1);
		// 150*8.3 = 1245 --> 124 points and reduction applied for next order
		assertEquals(cust.getNumberOfFidelityPoints(), 124);

		core.logOut();
		core.logIn("cuscatiad");

		Order order2 = core.createNewOrder(rest1);
		order2.addDish(list_mainDish.get(0), 40);
		// 40*8.3*90% = 332 - 33.2 = 298.8 --> 124 - 100 + 29 = 53
		placeOrder(order2);
		assertEquals(order2.getPriceInter(), 298.8, 0.01);
		// check if the number of points has been set to 24 points
		assertEquals(cust.getNumberOfFidelityPoints(), 53);

		System.out.println("TEST checkIfCalcAverageProfitWorks : DONE\n");
	}



	/*********************************************************************/
	/* Most/least selling restaurants and active couriers */ 

	@Test
	public void mostAndLeastSellingRestaurant() {
		make3orders();
		Restaurant temp_restaurant;
		System.out.println("###" + core.getMostOrLeastSellingRestaurants(false));
		// Most selling
		temp_restaurant = core.getMostOrLeastSellingRestaurants(true).get(0);
		assertTrue(temp_restaurant.equals(rest1));
		// Least selling can be all restaurants except rest1 and rest3 who sold at least an order
		temp_restaurant = core.getMostOrLeastSellingRestaurants(false).get(0);
		assertNotEquals(temp_restaurant, rest3);
		assertNotEquals(temp_restaurant, rest1);

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
		temp_courier = core.getMostOrLeastActiveCouriers(true).get(0);
		assertTrue(temp_courier != null);
		// Least active
		temp_courier = core.getMostOrLeastActiveCouriers(false).get(0);
		assertTrue(temp_courier != null);

		System.out.println("TEST mostOrLeastActiveCourier : DONE\n");
	}
	
	@Test
	public void checkIfRestaurantOrderCounterWorksWithTreatedOrders() {
		assertEquals(rest1.getNbOfDeliveredOrders(), 0);
		make3orders(); // 2 orders for rest1 !
		assertEquals(rest1.getNbOfDeliveredOrders(), 2);
	}

	/*********************************************************************/
	/* Calculate Target profit quantities */ 

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
		for(Order order : core.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit - 3*serviceFee + 3*deliveryFee;
		double expected = Order.round4((double)placeHolder/Order.round2(sum));
		double value = core.simulateProfit(profit, deliveryFee, serviceFee);

		assertEquals(expected, value, 0.0001);

		System.out.println("TEST calculateProfit() : DONE\n");
	}

	@Test
	public void simulateDeliveryCost() {
		make3orders();

		core.setTargetProfitPolicyToDelivCostProf();;

		double serviceFee = 5;
		double markupProfit = 0.05;
		double profit = 9;

		double sum = 0;
		double placeHolder = 0;

		// profit for one order = order price * markupPercentage + service fee - delivery cost

		//there are three orders so we'll calculate placeHolder as the left 
		// side of the equation and sum as the rigth side of the function 
		// so that placeHolder = markupPercentage * sum
		for(Order order : core.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit - 3*serviceFee - sum*markupProfit;

		assertEquals(-placeHolder/3D, core.simulateProfit(profit, markupProfit, serviceFee), 0.01);

		System.out.println("TEST calculateProfit() : DONE\n");
	}

	@Test
	public void simulateServiceFee() {
		make3orders();
		core.setTargetProfitPolicyToSerFeeProf();

		double deliveryFee = 5;
		double markupProfit = 0.05;
		double profit = 9;

		double sum = 0;
		double placeHolder = 0;

		// profit for one order = order price * markupPercentage + service fee - delivery cost

		// there are three orders so we'll calculate placeHolder as the left 
		// side of the equation and sum as the right side of the function 
		// so that placeHolder = markupPercentage * sum
		for(Order order : core.getSavedOrders()) {
			sum += order.getPriceInter();
		}
		placeHolder = profit + 3*deliveryFee - sum*markupProfit;

		assertEquals(placeHolder/3D, core.simulateProfit(profit, markupProfit, deliveryFee), 0.01);

		System.out.println("TEST calculateProfit() : DONE\n");
	}

	@Test
	public void testIfCourierIsDeactivated() throws AlreadyUsedUsernameException{
		core.logOut();
		for(Courier r: list_courier)
			core.register(r);
		core.logIn("john45");

		for(int i=0; i < 4; i++)
			list_courier.get(i).setAvailable(false);

		assertTrue(core.getCourierList().size() == 6);
		int sum = 0;
		for(Courier c : list_courier)
			if(c.isAvailable()==false)
				sum++;

		assertTrue(sum == 4);

		String[] string = {"couPvp23","coujohn42","couP23p23","coujayjay34"};
		make3orders();
		for(int i=0; i<core.getSavedOrders().size(); i++)
			for(int j = 0; j < 4; j++)
				assertTrue(!core.getSavedOrders().get(i).getCourier().getUsername().equals(string[j]));

		System.out.println("TEST testIfCourierIsDeactivated() : DONE\n");
	}

	/**************************************************************************************************/
	/* Get customer history */

	@Test
	public void assertNoHistoryIsGivenWhenNoCustomerLoggedIn() {
		make2orders5HalfMeals();
		assertTrue(core.getHistoryOfOrders() == null);
		System.out.println("TEST assertNoHistoryIsGivenWhenNoCustomerLoggedIn : DONE\n");
	}

	@Test
	public void checkIfHistoryOfOrdersIsCorrectWhenCustomerLoggedIn() {
		make2orders5HalfMeals();
		Customer cust_at_test = list_customer.get(2);

		core.logIn(cust_at_test.getUsername());
		assertTrue(core.getHistoryOfOrders().size() == 1);

		System.out.println("TEST checkIfHistoryOfOrdersIsCorrectWhenCustomerLoggedIn : DONE\n");
	}

	/**************************************************************************************************/
	/* Helpers */


	public void make3orders() {
		core.setCourierList(list_courier);

		System.out.println("Making 3 orders...");
		/* Make sure that all parameters are in accordance with tests */
		core.setMarkup_percentage(0.05);
		core.setDeliveryCost(4.0);
		core.setServiceFee(2.5);

		core.logOut();
		core.logIn("cusmi2"); // username of customer at index 2

		Order order1 = core.createNewOrder(rest1);
		order1.addDish(list_mainDish.get(0), 3);
		placeOrder(order1);

		core.logOut();
		core.logIn("cusjohoeu"); // username of customer at index 3

		Order order2 = core.createNewOrder(rest3);
		order2.addDish(list_mainDish.get(1), 2);
		placeOrder(order2);

		core.logOut();
		core.logIn("cuscatiad"); // username of customer at index 4

		Order order3 = core.createNewOrder(rest1);
		order3.addDish(list_mainDish.get(2), 1);
		placeOrder(order3);
		// Treat the three placed orders : two for rest1 and one for rest2
		System.out.println("Done with the 3 orders !");
	}

	public void make2orders5HalfMeals() {
		core.setCourierList(list_courier);

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
		core.setMarkup_percentage(0.05);
		core.setDeliveryCost(4.0);
		core.setServiceFee(2.5);

		core.logOut();
		core.logIn("cusmi2"); // username of customer at index 2

		Order order1 = core.createNewOrder(rest1);
		order1.addMeal(hm3, 4);
		order1.addMeal(hm1, 4);
		order1.addMeal(hm1, 3);

		placeOrder(order1);

		core.logOut();
		core.logIn("cusjohoeu"); // username of customer at index 3

		Order order2 = core.createNewOrder(rest2);
		order2.addMeal(hm2, 1);
		order2.addMeal(hm4, 3);

		placeOrder(order2);

		// Treat the three placed orders : two for rest1 and one for rest2

		System.out.println("Done with the 2 meal orders !");
	}


	public void make2orders6Dishes() {

		Starter d1 = list_starter.get(0);
		Dish d2 = list_starter.get(1);
		Dish d3 = list_mainDish.get(4);
		Dish d4 = list_dessert.get(5);
		Dish d5 = list_starter.get(4);

		rest1.addStarter((Starter) d1);
		rest1.addMainDish((MainDish) d3);
		rest1.addDessert((Dessert) d4);
		rest1.addStarter((Starter) d5);

		rest2.addStarter((Starter) d2);
		rest2.addStarter((Starter) d5);

		System.out.println("Making 2 orders...");
		/* Make sure that all parameters are in accordance with tests */
		core.setMarkup_percentage(0.05);
		core.setDeliveryCost(4.0);
		core.setServiceFee(2.5);

		core.logOut();
		core.logIn("cusmi2"); // username of customer at index 2

		Order order1 = core.createNewOrder(rest1);
		order1.addDish(d1, 4);
		order1.addDish(d3, 4);
		order1.addDish(d4, 5);
		order1.addDish(d5, 9);
		placeOrder(order1);

		core.logOut();
		core.logIn("cusjohoeu"); // username of customer at index 3

		Order order2 = core.createNewOrder(rest2);
		order2.addDish(d2, 1);
		order2.addDish(d5, 9);
		placeOrder(order2);

		System.out.println("Done with the 2 dish orders !");
	}

	public void placeOrder(Order order){
		core.logOut();
		core.logIn("cuspvp23");
		core.placeNewOrder(order);
		core.logOut();
		core.logIn("john45");

	}



}
