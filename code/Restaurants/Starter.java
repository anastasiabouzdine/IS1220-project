package projet_oop;



/**
 * Starter is a Dish and takes over all functionality of Dash
 * @author Patrick
 */
public class Starter extends Dish {

	/**
	 * @param name = name of the dish f.e. "spaghetti"
	 * @param price = price of the dish
	 * @param type = each dish can have a different type f.e. "vegetarian"
	 * 
	 * the constructor is for a dish that has a special type f.e. "vegetarian".
	 */
	public Starter(String name, int price, String type) {
		super(name, price, type);
	}
	
	/**
	 * @param name = name of the dish f.e. "spaghetti"
	 * @param price = price of the dish
	 * @param type = each dish can have a different type f.e. "vegetarian"
	 * 
	 * the constructor is for a dish that has no special type and thus will be named "standard".
	 */
	public Starter(String name, int price) {
		super(name, price, "standard");
	}

}
