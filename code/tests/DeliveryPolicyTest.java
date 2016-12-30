package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import parsers.*;
import policies.DeliveryPolicy;
import policies.FairOccupationDelivery;
import policies.FastestDelivery;
import core.Core;
import core.Order;
import restaurantSetUp.Dessert;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Courier;
import users.Customer;
import users.Restaurant;

public class DeliveryPolicyTest {
	
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
	
	
	@Test
	public void fastestDelievery() {
		DeliveryPolicy dPolicy = new FastestDelivery();
		
		
		assertTrue(dPolicy.howToDeliver(list_courier, list_orders.get(0).getRestaurant().getAddress()).get(0).getName().equals("Paul"));
	}
	
	@Test
	public void fairOccupationDelivery() {
		DeliveryPolicy dPolicy = new FairOccupationDelivery();
		
		list_courier.get(0).setNbOfDeliveredOrders(5);
		list_courier.get(1).setNbOfDeliveredOrders(2);
		list_courier.get(2).setNbOfDeliveredOrders(3);
		list_courier.get(3).setNbOfDeliveredOrders(4);
		list_courier.get(4).setNbOfDeliveredOrders(6);
		list_courier.get(5).setNbOfDeliveredOrders(1);
		
		assertTrue(dPolicy.howToDeliver(list_courier, list_orders.get(0).getRestaurant().getAddress()).get(0).getName().equals("kobec"));
	}

}
