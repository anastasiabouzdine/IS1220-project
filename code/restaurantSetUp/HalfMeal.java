package restaurantSetUp;

/**
 * HalfMeal extends the <code>Meal</code> class and is represented
 * by a name, a <code>MainDish</code> and a <code>Starter</code> or a<code>Dessert</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class HalfMeal extends Meal {
	
	public HalfMeal(String name){
		super(name);
	}
	
	/**
	 * Class constructor which checks whether the added dishes are of they same type 
	 * and names the meal either standard (different types) or of a type.
	 * Note that this constructor will only be used for the tests,
	 * as the other halfmeals will be made by the <code>MealFactory</code>.
	 * 
	 * @param name 		 name of the halfmeal
	 * @param mainDish 	 a <code>MainDish</code>
	 * @param secChoice  a <code>Dish</code> that can be a <code>Starter</code> or a <code>Dessert</code>
	 * 
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
		
		if(mainDish.getType().equals(secChoice.getType()))
			type = mainDish.getType();
		
		setType(type);
	}
	
	/**
	 * SetDishes method which checks whether the added dishes are of they same type 
	 * and names the meal either standard (different types) or of a type.
	 * 
	 * @param mainDish 	 a <code>MainDish</code>
	 * @param secChoice  a <code>Dish</code> that can be a <code>Starter</code> or a <code>Dessert</code>
	 * 
	 */
	public void setHalfMeal(MainDish mainDish, Dish secChoice){
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
		
		if(mainDish.getType().equals(secChoice.getType()))
			type = mainDish.getType();
		
		setType(type);
	}
	
};
