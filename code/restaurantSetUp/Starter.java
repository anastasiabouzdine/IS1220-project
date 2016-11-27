package restaurantSetUp;



/**
 * Starter is a Dish and takes over all functionality of Dash
 * @author Patrick
 */
public class Starter extends Dish {

	/**
	 * Constructor for a Starter with given type.
	 * 
	 * @param name = name of the Starter f.e. "Brushetta"
	 * @param price = price of the Starter
	 * @param type = type of the Starter (ie. standard/vegetarian/glutenfree)
	 * 
	 */
	public Starter(String name, double price, String type) {
		super(name, price, type);
	}
	
	/**
	 * Constructor for a Starter with given type.
	 * 
	 * @param name = name of the Starter f.e. "Brushetta"
	 * @param price = price of the Starter
	 * 
	 */
	public Starter(String name, double price) {
		super(name, price, "standard");
	}

	@Override
	public String toString() {
		return "Starter [getName()=" + getName() + ", getType()=" + getType() + ", getPrice()=" + getPrice() + "]";
	}
}
