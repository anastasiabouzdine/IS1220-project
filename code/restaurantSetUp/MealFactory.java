package restaurantSetUp;

/**
 * The class <code>MealFactory</code> produces a concrete meal factory.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class MealFactory extends AbstractFactory{

	public Meal getMeal(String mealType){
		if (mealType.equalsIgnoreCase("FULLMEAL")){
			return new FullMeal();
		} else if (mealType.equalsIgnoreCase("HALFMEAL")){
			return new HalfMeal();
		} 
		return null;		
	}
	public Dish getDish(String dishType, String dishName, double dishPrice){
		return null;
	}
	
	
	
	/**
	 * 
	 * @param starter starter of the Meal
	 * @param main main Dish of the Meal
	 * @param dessert dessert of the Meal
	 * @param name name of the FullMeal 
	 * 
	 * this function checks whether entries are correctly casted before execution
	 * 
	 * @return either a full Meal (if parameters are of correct type) or 'null' (if not)
	 */
	public static FullMeal getFullMeal(String name, Dish starter, Dish main, Dish dessert) {
		
		if(starter instanceof Starter && main instanceof MainDish && dessert instanceof Dessert){
			
			Starter start = (Starter) starter;
			MainDish mainD = (MainDish) main;
			Dessert des = (Dessert) dessert;
			
			return new FullMeal(name, start, mainD, des);
		}
		
		else {
			System.out.println("The full Meal: " + name + " was not created. Please verify that 'starter' is of type 'Starter', 'main' is of type 'MainDish' and dessert is of type 'Dessert'");
			return null;
		}
	}
	
	/**
	 * 
	 * @param main main Dish of the Meal
	 * @param secChoice dessert or starter of the Meal
	 * @param name name of the FullMeal 
	 * 
	 * this function checks whether entries are correctly casted before execution
	 * 
	 * @return either a half Meal (if parameters are of correct type) or 'null' (if not)
	 */
	public static HalfMeal getHalfMeal(String name, Dish main, Dish secChoice) {
		
		if(main instanceof MainDish && (secChoice instanceof Starter || secChoice instanceof Dessert))
			return new HalfMeal(name, main, secChoice);
		
		else {
			System.out.println("The half Meal: " + name + " was not created. Please verify that 'main' is of type 'MainDish' and secChoice is either of type 'Dessert' or 'Starter'");
			return null;
		}
	}
}
