package restaurantSetUp;

import java.io.Serializable;

/**
 * The class <code>FactoryProducer</code> produces concrete factories based on given input.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FactoryProducer implements Serializable {
	
	private static final long serialVersionUID = -6084842873502016401L;

	/**
	 * Returns an AbstractFactory of specified type.
	 * 
	 * @param 	choice	a String specifying the type of AbstractFactory to return
	 * @return 	an AbstractFactory of given type (Meal or Dish)
	 */
	public static AbstractFactory getFactory(String choice){
		if (choice.equalsIgnoreCase("DISH")){
			return new DishFactory();
		} else if (choice.equalsIgnoreCase("MEAL")){
			return new MealFactory();
		}
		return null;
	}

}
