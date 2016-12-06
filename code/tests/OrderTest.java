package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Order;
import core.ParseCustomers;
import parsers.*;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Customer;
import users.Restaurant;

public class OrderTest {
	
	ArrayList<FullMeal> list_fmeal = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> list_hmeal = ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/dessert.txt");
	ArrayList<Restaurant> list_restaurant = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
	ArrayList<Customer> list_customer = ParseCustomers.parseCustomers("src/txtFILES/courierList.txt");
	ArrayList<Order> list_order = ParseOrders.parseOrders();

	@Test
	public void createOrder() {
		Order order1 = list_order.get(0);
		assertTrue(order1 != null);
	}
	
	@Test 
	public void addMealToOrder() {
		Order order1 = list_order.get(0);
		assertTrue(order1 != null);
		HalfMeal hm1 = list_hmeal.get(0);
		order1.addMeal(hm1,3);
		assertTrue(order1.getMeals().size() == 1);
		assertTrue(order1.getDishes().size() == 0);
		assertTrue(order1.getQuantity().size() == 1);
	}
	
	@Test 
	public void addDishToOrder() {
		Order order1 = list_order.get(0);
		assertTrue(order1 != null);
		Dish d1 = list_starter.get(0);
		order1.addDish(d1,2);
		assertTrue(order1.getMeals().size() == 0);
		assertTrue(order1.getDishes().size() == 1);
		assertTrue(order1.getQuantity().size() == 1);
	}
	
	@Test 
	public void getPriceOfOrder() {
		Order order1 = list_order.get(0);
		
		assertTrue(order1 != null);
		
		FullMeal fm1 = list_fmeal.get(0);
		HalfMeal hm1 = list_hmeal.get(0);
		Restaurant r1 = order1.getRestaurant();
		
		r1.addMeal(hm1);
		r1.addMeal(fm1);
		
		order1.addMeal(hm1,3);
		order1.addMeal(fm1,3);
		
		System.out.println(order1);
		
		double a = 3*(4.3 + 8.3 + 4.3)*0.95 + 3*(8.3 + 4.3)*0.95;
		
		assertTrue(order1.getPrice() == a);
	}
	
	//TODO round 
	@Test 
	public void getPriceOfOrderWithSpecialMeal() {
		Order order1 = list_order.get(0);
		
		assertTrue(order1 != null);
		
		FullMeal fm1 = list_fmeal.get(0);
		HalfMeal hm1 = list_hmeal.get(0);
		Restaurant r1 = order1.getRestaurant();
		
		r1.addMeal(hm1);
		r1.addMeal(fm1);
		
		r1.setSpecMeal(fm1);
		
		System.out.println(order1.isFidCardPlanBasic());
		
		order1.addMeal(hm1,3);
		order1.addMeal(fm1,3);
		
		double a = Math.round(3*(4.3 + 8.3 + 4.3)*90 + 3*(8.3 + 4.3)*95)/100.0;
		
		System.out.println(a);
		System.out.println(order1.getPrice());
		
		assertTrue(order1.getPrice() == a);
	}

}
