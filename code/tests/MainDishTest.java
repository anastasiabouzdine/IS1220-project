package tests;

import org.junit.Test;

import restaurantSetUp.MainDish;

public class MainDishTest {

	@Test
	public void createMainDishWithNoType() {
		MainDish md = new MainDish("Steak with onions", 16.0);
		System.out.println(md.toString());
	}
	
	@Test
	public void createMainDishWithType() {
		MainDish md = new MainDish("Rice with onions", 12.0, "vegetarian");
		System.out.println(md.toString());
	}
	

}
