package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.*;
import restaurantSetUp.Address;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Starter;
import users.Restaurant;

public class RestaurantTest {
	
	ArrayList<FullMeal> list_fmeal = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> list_hmeal = ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt");
	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/dessert.txt");
	ArrayList<Restaurant> list_restaurant = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
	
	
	
	@Test
	public void createAnAddress() {
		@SuppressWarnings("unused")
		Address address1 = new Address(3,2);
	}
	@SuppressWarnings("unused")
	@Test
	public void createRestaurant() {
		Restaurant r1 = list_restaurant.get(0);
	}
	
	@Test
	public void checkIfIDFunctionWorks(){
		Restaurant r1 = list_restaurant.get(0);
		Restaurant r2 = list_restaurant.get(1);
		assertTrue(r1.getID() != r2.getID());
	}
 
	@Test
	public void checkIfDiscountFactorDefaultWorks(){
		Restaurant r2 = list_restaurant.get(1);
		assertEquals(0.05, r2.getDiscountFactor(), 0);
	}
	
	@Test
	public void checkIfRestaurantGetPriceWorks(){
		
		Restaurant r2 = list_restaurant.get(1);
		
		Meal meal1 = list_fmeal.get(0);
		r2.addMeal(meal1);
		double a = 0.95*(4.3+8.3+4.3);
		
		assertEquals(a, r2.getPrice(meal1), 0.01);
	}
	
	@Test
	public void createFullMeal(){
		Starter dish1 = list_starter.get(0);
		MainDish dish2 = list_mainDish.get(0);
		Dessert dish3 = list_dessert.get(0);
		
		Meal meal1 = new FullMeal("Meal1", dish1, dish2, dish3);
		assertTrue(meal1.getListOfDish().contains(dish1));
		assertTrue(meal1.getListOfDish().contains(dish2));
		assertTrue(meal1.getListOfDish().contains(dish3));
	}
	
	@Test
	public void checkEqualDish(){
		Dish dish1 = new Starter("Salade", 3.99, "vegeterian");		
		Dish dish2 = new Starter("Salade", 3.99, "vegeterian");
		assertTrue(dish1.equals(dish2));
	}
	
	@Test
	public void checkEqualMeal(){
		Starter dish1 = new Starter("Salade", 3.99, "vegeterian");		
		MainDish dish2 = new MainDish("Spaghetti", 6.99, "vegeterian");
		Dessert dish3 = new Dessert("IceCream", 2.99, "vegeterian");
		
		Meal meal1 = new FullMeal("MealNew", dish1, dish2, dish3);
		Meal meal2 = new FullMeal("MealNew", dish1, dish2, dish3);
		
		assertTrue(meal1.equals(meal2));
	}
	
}
