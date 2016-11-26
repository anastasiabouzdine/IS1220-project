package restaurants;

/**
 * The class <code>DishFactory</code> produces a concrete dish factory.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class DishFactory extends AbstractFactory {

	
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
	public Meal getMeal(String mealType){
		return null;
	}
	
}
