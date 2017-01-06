package restaurantSetUp;

import java.io.Serializable;

/**
 * The class <code>MealFactory</code> produces a concrete meal factory.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class MealFactory extends AbstractFactory implements Serializable {

	private static final long serialVersionUID = -775359798482177866L;


	/**
	 * Returns a meal of specified type (starter, maindish, dessert).
	 * 
	 * @param mealType	  a string containing the type of the <code>Meal</code>
	 * 
	 */
	public Meal getMeal(String mealType, String mealName){
		if (mealType.equalsIgnoreCase("FULLMEAL")){
			return new FullMeal(mealName);
		} else if (mealType.equalsIgnoreCase("HALFMEAL")){
			return new HalfMeal(mealName);
		} 
		return null;		
	}
	
	
	
	/**
	 * NOT TO USE METHOD, SEE <code>DishFactory</code> to get a Dish,
	 * this method returns null as we have to extend the <code>AbstractFactory</code> class.
	 * 
	 */
	public Dish getDish(String dishType, String dishName, double dishPrice){
		return null;
	}


	/**
	 * NOT TO USE METHOD, SEE <code>DishFactory</code> to get a Dish,
	 * this method returns null as we have to extend the <code>AbstractFactory</code> class.
	 * 
	 */
	@Override
	public Dish getDish(String dishType, String dishName, double price, String foodType) {
		return null;
	}
	
}
