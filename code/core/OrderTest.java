package core;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurantSetUp.Address;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Customer;
import users.Restaurant;

public class OrderTest {
	
	private String name = "John";
	private String surname = "Doe";
	private Address address = new Address(2, 3);
	private String phoneNumber = "+123 456 789";
	private String email = "john@doe.com";
	private String username = "johnDoe42";
	
	private Address address1 = new Address(3,2);
	private Restaurant r1 = new Restaurant("Chez Andre", address1, "andre");

	@Test
	public void createOrder() {
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Order order1 = new Order(cust1, r1);
		assertTrue(order1 != null);
	}
	
	@Test 
	public void addMealToOrder() {
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Order order1 = new Order(cust1, r1);
		assertTrue(order1 != null);
		HalfMeal hm1 = new HalfMeal("steak and ice", new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		order1.addMeal(hm1,3);
		assertTrue(order1.getMeals().size() == 1);
		assertTrue(order1.getDishes().size() == 0);
		assertTrue(order1.getQuantity().size() == 1);
	}
	
	@Test 
	public void addDishToOrder() {
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Order order1 = new Order(cust1, r1);
		assertTrue(order1 != null);
		Dish d1 = new Starter("Tuna salad", 4.0); 
		order1.addDish(d1,2);
		assertTrue(order1.getMeals().size() == 0);
		assertTrue(order1.getDishes().size() == 1);
		assertTrue(order1.getQuantity().size() == 1);
	}
	
	@Test 
	public void getPriceOfOrder() {
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Order order1 = new Order(cust1, r1);
		
		assertTrue(order1 != null);
		
		FullMeal fm1 = new FullMeal("salad and steak and ice",new Starter("salad", 4.30), new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		HalfMeal hm1 = new HalfMeal("steak and ice", new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		
		r1.addMeal(hm1);
		r1.addMeal(fm1);
		
		order1.addMeal(hm1,3);
		order1.addMeal(fm1,3);
		
		double a = 3*(4.3 + 7 + 4)*0.95 + 3*(7 + 4)*0.95;
		
		assertTrue(order1.getPrice() == a);
	}
	
	@Test 
	public void getPriceOfOrderWithSpecialMeal() {
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Order order1 = new Order(cust1, r1);
		
		assertTrue(order1 != null);
		
		FullMeal fm1 = new FullMeal("salad and steak and ice",new Starter("salad", 4.30), new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		HalfMeal hm1 = new HalfMeal("steak and ice", new MainDish("steak", 7.0), new Dessert("icecream", 4.0)); 
		
		r1.addMeal(hm1);
		r1.addMeal(fm1);
		
		r1.setSpecMeal(fm1);
		
		System.out.println(order1.isFidCardPlanBasic());
		
		order1.addMeal(hm1,3);
		order1.addMeal(fm1,3);
		
		double a = Math.round(3*(4.3 + 7 + 4)*90 + 3*(7 + 4)*95)/100.0;
		
		System.out.println(a);
		System.out.println(order1.getPrice());
		
		assertTrue(order1.getPrice() == a);
	}

}
