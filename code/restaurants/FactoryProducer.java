package restaurants;


/**
 * The class <code>FactoryProducer</code> produces concrete factories based on given information.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FactoryProducer {
	
	/**
	 * Returns an AbstractFactory of specified type
	 * @param 	choice	a String specifying the type of AbstractFactory to return
	 * @return 			an AbstractFactory of given type
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
