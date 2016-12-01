package core;

import static org.junit.Assert.*;

import org.junit.Test;

import policies.ALaCarteSorted;
import restaurantSetUp.Address;
import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;
import users.Customer;
import users.Restaurant;

public class CoreTest {
	
	Core mf1 = new Core("MyFoodera");
	HalfMeal hm1 = new HalfMeal("1steak ice", new MainDish("steak", 7.0), new Dessert("iceeam", 4.0));
	HalfMeal hm2 = new HalfMeal("2steak and ice", new MainDish("eak", 4.0), new Dessert("icecream", 4.3));
	HalfMeal hm3 = new HalfMeal("3steakice", new MainDish("stak", 2.0), new Dessert("iccream", 4.1));
	HalfMeal hm4 = new HalfMeal("4steakice", new MainDish("stak", 2.0), new Dessert("iccream", 4.1));
	Dish d1 = new Starter("Dish1",3);
	Dish d2 = new Starter("Dish2",3);
	Dish d3 = new Starter("Dish3",3);
	Dish d4 = new Starter("Dish4",3);
	Dish d5 = new Starter("Dish5",3);
	
	Restaurant rest1 = new Restaurant();
	Restaurant rest2 = new Restaurant();
	
	Order order1 = new Order(cust1, r1);
	

	@Test
	public void addAndPrintListOfMealsByCount() {
		
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
		
		System.out.println(mf1.getSortedList(true));
		
		assertTrue(7 == mf1.getSortedList(true).first().getCount());
		assertTrue(3 == mf1.getSortedList(rest2, true).first().getCount());
		assertTrue(1 == mf1.getSortedList(rest2, false).first().getCount());
		
		mf1.setSort(mf1.getDishSort());
		
		System.out.println(mf1.getSortedList(true));
		
	}
	
	

}
