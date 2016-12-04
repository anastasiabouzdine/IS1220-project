// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package restaurantSetUp;

/************************************************************/
/**
 * 
 */
public class HalfMeal extends Meal {
	
	public HalfMeal(){
		super();
	}
	
	/**
	 * @param name = name of the meal f.e. "Lucky Luke" 
	 * @param mainDish = main dish of the meal
	 * @param secChoice = dessert or starter of the meal
	 * @param all three dishes have to be inserted either in the order "starter - main dish" or 
	 * main dish - dessert. if not, an error pops up
	 * 
	 * the constructor checks whether the added dishes are of they same type and names the meal either standard (different types)
	 * or of a type 
	 */
//	public HalfMeal(String name, Dish mainDish, Dish secChoice) {
//		super(name);
//		
//		String type = "standard";
//		
//		if(mainDish instanceof MainDish) {
//			MainDish main = (MainDish) mainDish;
//			getListOfDish().add(main);
//		}
//		
//		//TODO implement "else" for case that mainDish is not of type "MainDish"
//		
//		if(secChoice instanceof Dessert) {
//			Dessert des = (Dessert) secChoice;
//			getListOfDish().add(des);
//		}
//		
//		else if(secChoice instanceof Starter) {
//			Starter des = (Starter) secChoice;
//			getListOfDish().add(des);
//		}
//		
//		//TODO implement "else" for case that secChoice is of type "MainDish"
//		
//		if(mainDish.getType()==secChoice.getType())
//			type = mainDish.getType();
//		
//		setType(type);
//	}
	
	/**
	 * @param name = name of the meal f.e. "Lucky Luke" 
	 * @param mainDish = main dish of the meal
	 * @param secChoice = dessert or starter of the meal
	 * @param all three dishes have to be inserted either in the order "starter - main dish" or 
	 * main dish - dessert. if not, an error pops up
	 * 
	 * the constructor checks whether the added dishes are of they same type and names the meal either standard (different types)
	 * or of a type 
	 */
	public HalfMeal(String name, Dish mainDish, Dish secChoice) {
		super(name);
		
		String type = "standard";
		
		if (mainDish instanceof MainDish){
			MainDish main = (MainDish) mainDish;
			getListOfDish().add(main);
		} else {
			throw new Error(new ClassCastException());
		}
		
		if(secChoice instanceof Dessert){
			Dessert des = (Dessert) secChoice;
			getListOfDish().add(des);
		} else if (secChoice instanceof Starter){
			Starter star = (Starter) secChoice;
			getListOfDish().add(star);
		} else { 
			throw new Error(new ClassCastException());
		}
		
		if(mainDish.getType()==secChoice.getType())
			type = mainDish.getType();
		
		setType(type);
		
	}
	
};