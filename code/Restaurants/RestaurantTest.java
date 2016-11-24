package restaurants;

import static org.junit.Assert.*;

import org.junit.Test;

public class RestaurantTest {
	
	// public Restaurant(String name, Address adress, String username, double discountFactor, double specDiscFact) 
	
	private static Address address1 = null;
	private static Restaurant r1 = null;
	private static Restaurant r2 = null;
	
	@Test
	public void createAnAddress() {
		address1 = new Address(3,2);
	}
	@Test
	public void createRestaurant() {
		r1 = new Restaurant("Chez Andre", address1, "andre");
	}
	
	@Test
	public void checkIfIDFunctionWorks(){
		Address address2 = new Address(10,2);
		r2 = new Restaurant("Chez Joseph", address2, "andre");
		assertTrue(r1.getId() != r2.getId());
	}
 
	@Test
	public void checkIfDiscountFactorDefaultWorks(){
		Address address2 = new Address(10,2);
		r2 = new Restaurant("Chez Joseph", address2, "andre");
		assertEquals(0.05, r2.getDiscountFactor(), 0);
	}
	
	@Test
	public void checkIfRestaurantGetPriceWorks(){
		
		Address address2 = new Address(10,2);
		r2 = new Restaurant("Chez Joseph", address2, "andre");
		
		Starter dish1 = new Starter("Salade", 3.99, "vegeterian");		
		MainDish dish2 = new MainDish("Spaghetti", 6.99, "vegeterian");
		Dessert dish3 = new Dessert("IceCream", 2.99, "vegeterian");
		
		Meal meal1 = new FullMeal("Meal1", dish1, dish2, dish3);
		r2.addMeal(meal1);
		double a = 0.95*(3.99+6.99+2.99);
			
		assertEquals(a, r2.getPrice(meal1), 0.01);
	}

	@Test
	public void createDish(){
		Dish dish1 = new Dish("Spaghetti", 6.99, "vegeterian");
		assertEquals(6.99, dish1.getPrice(), 0);
		//TODO Why is it possible to create with a protected constructor???
		
		Dish dish2 = new MainDish("Spaghetti", 6.99, "vegeterian");
		assertEquals(6.99, dish2.getPrice(), 0);
	}
	

	
	
	@Test
	public void createFullMeal(){
		Starter dish1 = new Starter("Salade", 3.99, "vegeterian");		
		MainDish dish2 = new MainDish("Spaghetti", 6.99, "vegeterian");
		Dessert dish3 = new Dessert("IceCream", 2.99, "vegeterian");
		
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
