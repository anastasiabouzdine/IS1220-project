package restaurantSetUp;

/************************************************************/
/**
 * Class that represent a dish/ class is inherited by other 
 * classes (starter, mainDish, dessert).
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public abstract class Dish {
	
	private String name;
	private double price;
	private String type;
	
	/**
	 * Constructor of type Dish that cannot be created from outside and is therefore protected.
	 * 
	 * @param name = name of the dish f.e. "spaghetti"
	 * @param price = price of the dish
	 * @param type = each dish can have a different type f.e. "vegetarian"
	 * 
	 */
	protected Dish(String name, double price, String type) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
	}


	/***********************************************************/

	@Override
	public String toString() {
		return "Dish [name=" + name + ", price=" + price + ", type=" + type + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Dish){
			Dish dish = (Dish) obj;
			if(dish.getName().equalsIgnoreCase(name)
					&& dish.getType().equalsIgnoreCase(type) && dish.getPrice() == price){
				return true;
			}
			return false;
		}
		return false;
	}

	/************************************************************/
	/* Getters and Setters */


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
};
