package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.Meal;
import restaurantSetUp.MealFactory;

public class MealFactoryTest {

	
	@Test
	public void createMealFactory(){
		MealFactory mf = new MealFactory();
		assertTrue(mf != null);
	}
	@Test
	public void getFullMealFromFactory() {
		MealFactory mf = new MealFactory();
		Meal fMeal = mf.getMeal("FULLMEAL");
		assertTrue(fMeal instanceof Meal);
		assertTrue(fMeal instanceof FullMeal);
	}
	@Test
	public void getHalfMealFromFactory() {
		MealFactory mf = new MealFactory();
		Meal hMeal = mf.getMeal("HALFMEAL");
		assertTrue(hMeal instanceof Meal);
		assertTrue(hMeal instanceof HalfMeal);
	}

}
