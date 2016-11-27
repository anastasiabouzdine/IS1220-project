package core;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurants.Dessert;
import restaurants.Dish;
import restaurants.HalfMeal;
import restaurants.MainDish;
import restaurants.Restaurant;
import restaurants.Starter;
import users.Customer;

public class OrderTest {

	@Test
	public void createOrder() {
		Order order1 = new Order(new Customer(), new Restaurant());
		assertTrue(order1 != null);
	}
	
	@Test 
	public void addMealToOrder() {
		Order order1 = new Order(new Customer(), new Restaurant());
		HalfMeal hm1 = new HalfMeal("steak and ice", new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		order1.addMeal(hm1,3);
		assertTrue(order1.getMeals().size() == 1);
		assertTrue(order1.getDishes().size() == 0);
		assertTrue(order1.getQuantity().size() == 1);
	}
	
	@Test 
	public void addDishToOrder() {
		Order order1 = new Order(new Customer(), new Restaurant());
		Dish d1 = new Starter("Tuna salad", 4.0); 
		order1.addDish(d1,2);
		assertTrue(order1.getMeals().size() == 0);
		assertTrue(order1.getDishes().size() == 1);
		assertTrue(order1.getQuantity().size() == 1);
	}

}
