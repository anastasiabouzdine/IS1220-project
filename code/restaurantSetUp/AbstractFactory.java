package restaurantSetUp;

/**
 * The abstract class <code>AbstractFactory</code> is used to get <code>DishFactory</code>
 * and <code>MealFactory</code>.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public abstract class AbstractFactory {
	
	public abstract Dish getDish(String dishType, String dishName, double dishPrice);
	
	public abstract Dish getDish(String dishType, String dishName, double price, String foodType);
	
	public abstract Meal getMeal(String mealType, String mealName);


}
