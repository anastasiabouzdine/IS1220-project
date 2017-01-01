package restaurantSetUp;

/**
 * The class <code>DishFactory</code> produces a concrete dish factory.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class DishFactory extends AbstractFactory {

	/**
	 * Returns a dish of specified type (starter, maindish, dessert),
	 * name and price.
	 * 
	 * @param dishType	  a string containing the type of the Dish
	 * @param dishName	  a string containing the name of the Dish
	 * @param dishPrice   a double for the price of the Dish
	 * 
	 */
	public Dish getDish(String dishType, String dishName, double dishPrice){
		if (dishType.equalsIgnoreCase("STARTER")){
			return new Starter(dishName, dishPrice);
		} else if (dishType.equalsIgnoreCase("MAINDISH")){
			return new MainDish(dishName, dishPrice);
		} else if (dishType.equalsIgnoreCase("DESSERT")){
			return new Dessert(dishName, dishPrice);
		}
		return null;		
	}
	
	/**
	 * Returns a dish of specified type (starter, maindish, dessert),
	 * name and price.
	 * 
	 * @param dishType	  a string containing the type of the Dish
	 * @param name	  a string containing the name of the Dish
	 * @param price   a double for the price of the Dish
	 * @param foodType    a string containing the type of food of the Dish
	 * 
	 */
	public Dish getDish(String dishType, String name, double price, String foodType){
		if (dishType.equalsIgnoreCase("STARTER")){
			return new Starter(name, price, foodType);
		} else if (dishType.equalsIgnoreCase("MAINDISH")){
			return new MainDish(name, price, foodType);
		} else if (dishType.equalsIgnoreCase("DESSERT")){
			return new Dessert(name, price, foodType);
		}
		return null;		
	}
	
	
	
	/**
	 * NOT TO USE METHOD, SEE <code>MealFactory</code> to get a Meal,
	 * this method returns null as we have to extend the <code>AbstractFactory</code> class.
	 * 
	 */
	public Meal getMeal(String mealType, String mealName){
		return null;
	}
	
}
