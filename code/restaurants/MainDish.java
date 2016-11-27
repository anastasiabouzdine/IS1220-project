// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package restaurants;

/************************************************************/

/**
 * MainDish is a Dish and takes over all functionality of Dash
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class MainDish extends Dish {
	
	/**
	 * Constructor for a MainDish with given type.
	 * 
	 * @param name = name of the MainDish f.e. "spaghetti"
	 * @param price = price of the MainDish
	 * @param type = type of the MainDish (ie. standard/vegetarian/glutenfree)
	 * 
	 */
	public MainDish(String name, double price, String type) {
		super(name, price, type);
	}
	
	/**
	 * Constructor for a MainDish that has no special type and thus will be named "standard".
	 * 
	 * @param name = name of the dish f.e. "spaghetti"
	 * @param price = price of the dish
	 * 
	 */
	public MainDish(String name, double price) {
		super(name, price, "standard");
	}
	
	@Override
	public String toString() {
		return "MainDish [getName()=" + getName() + ", getType()=" + getType() + ", getPrice()=" + getPrice() + "]";
	}

};