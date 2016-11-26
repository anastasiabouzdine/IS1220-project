package restaurants;

import static org.junit.Assert.*;

import org.junit.Test;

public class DishFactoryTest {

	@Test
	public void createDishFactory(){
		DishFactory df = new DishFactory();
		assertTrue(df != null);
	}
	@Test
	public void getStarterFromFactory() {
		DishFactory df = new DishFactory();
		Dish starter = df.getDish("STARTER", "Bruschetta", 8.0);
		assertTrue(starter instanceof Dish);
		assertTrue(starter instanceof Starter);
	}
	@Test
	public void getMainDishFromFactory() {
		DishFactory df = new DishFactory();
		Dish mainDish = df.getDish("MAINDISH", "Bacon with fries", 16.0);
		assertTrue(mainDish instanceof Dish);
		assertTrue(mainDish instanceof MainDish);
	}
	@Test
	public void getDessertFromFactory() {
		DishFactory df = new DishFactory();
		Dish dessert = df.getDish("DESSERT", "Fruit salad", 4.0);
		assertTrue(dessert instanceof Dish);
		assertTrue(dessert instanceof Dessert);
	}

}
