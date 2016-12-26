package restaurantSetUp;

/**
 * Dessert extends the <code>Dish</code> class and
 * represent a dessert with name, price and price.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Dessert extends Dish {
	
	/**
	 * Constructor for a dessert with given type.
	 * 
	 * @param name	 name of the Dessert f.e. "watermelon"
	 * @param price	 price of the Dessert
	 * @param type   type of the Dessert (ie. standard/vegetarian/glutenfree)
	 * 
	 */
	public Dessert(String name, double price, String type) {
		super(name, price, type);
	}
	
	/**
	 * Constructor for a dessert that has no special type and thus will be named "standard".
	 * 
	 * @param name = name of the Dessert f.e. "watermelon"
	 * @param price = price of the Dessert
	 * 
	 */
	public Dessert(String name, double price) {
		super(name, price, "standard");
	}

	@Override
	public String toString() {
		return "Dessert [" + getName() + ", " + getPrice() + ", " + getType() + "]";
	}
	
};
