package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.ParseDishes;
import restaurantSetUp.Dessert;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.MealFactory;
import restaurantSetUp.Starter;

public class MealFactoryTest {

	ArrayList<Starter> list_starter = ParseDishes.parseStarter("src/txtFILES/starters.txt");
	ArrayList<MainDish> list_mainDish = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
	ArrayList<Dessert> list_dessert = ParseDishes.parseDessert("src/txtFILES/desserts.txt");
	
	
	@Test
	public void createMealFactory(){
		MealFactory mf = new MealFactory();
		assertTrue(mf != null);
	}
	@Test
	public void getFullMealFromFactory() {
		MealFactory mf = new MealFactory();
		Meal fMeal = mf.getMeal("FULLMEAL", "Menu a la French");
		assertTrue(fMeal instanceof Meal);
		assertTrue(fMeal instanceof FullMeal);
	}
	@Test
	public void getHalfMealFromFactory() {
		MealFactory mf = new MealFactory();
		Meal hMeal = mf.getMeal("HALFMEAL", "Menu a la British");
		assertTrue(hMeal instanceof Meal);
		assertTrue(hMeal instanceof HalfMeal);
	}
	
	@Test
	public void addDishesToFullMeal() {
		MealFactory mf = new MealFactory();
		FullMeal fMeal = (FullMeal) mf.getMeal("FULLMEAL", "Menu a la French");
		fMeal.setFullMeal(list_starter.get(0), list_mainDish.get(0), list_dessert.get(0));
		assertTrue(fMeal.getListOfDish().size() == 3);
	}
	@Test
	public void addDishesToHalfMeal() {
		MealFactory mf = new MealFactory();
		HalfMeal hMeal = (HalfMeal) mf.getMeal("HALFMEAL", "Menu a la British");
		hMeal.setHalfMeal(list_mainDish.get(1), list_dessert.get(1));
		assertTrue(hMeal.getListOfDish().size() == 2);
	}
}
	
