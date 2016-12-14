package restaurantSetUp;

/**
 * FullMeal extends the <code>Meal</code> class and is represented
 * by a name, a <code>Starter</code>, <code>MainDish</code> and <code>Dessert</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FullMeal extends Meal{
	
	
	/**
	 * Class constructor.
	 */
	public FullMeal(String name){
		super(name);
	}
	
	
	/**
	 * Class constructor which checks whether the added dishes are of they same type 
	 * and names the meal either standard (different types) or of a type.
	 * Note that this constructor will only be used for the tests,
	 * as the other fullmeals will be made by the <code>MealFactory</code>.
	 * 
	 * @param name 		 name of the fullmeal
	 * @param starter 	 a <code>Starter</code>
	 * @param mainDish 	 a <code>MainDish</code>
	 * @param dessert    a <code>Dessert</code>
	 * 
	 */
	public FullMeal(String name, Starter starter, MainDish mainDish, Dessert dessert) {
		super(name);
		
		getListOfDish().add(starter);
		getListOfDish().add(mainDish);
		getListOfDish().add(dessert);
		
		String type = "standard";
			
		if((starter.getType() == mainDish.getType()) && (mainDish.getType()==dessert.getType()))
			type = mainDish.getType();
		setType(type);

	}
	
	
	/**
	 * SetFullMeals method which checks whether the added dishes are of they same type 
	 * and names the meal either standard (different types) or of a type.
	 * 
	 * @param name 		 name of the fullmeal
	 * @param starter 	 a <code>Starter</code>
	 * @param mainDish 	 a <code>MainDish</code>
	 * @param dessert    a <code>Dessert</code>
	 * 
	 */
	public void setFullMeal(Starter starter, MainDish mainDish, Dessert dessert) {
		
		getListOfDish().add(starter);
		getListOfDish().add(mainDish);
		getListOfDish().add(dessert);
		
		String type = "standard";
			
		if((starter.getType() == mainDish.getType()) && (mainDish.getType()==dessert.getType()))
			type = mainDish.getType();
		setType(type);
	}
	
	
};
