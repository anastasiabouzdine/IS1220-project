// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package restaurantSetUp;

import java.util.ArrayList;
import java.util.List;

import restaurantSetUp.Dish;

/************************************************************/
/**
 * Meal is an abstract class that is inherited by either a halfMeal or a FullMeal.
 * 
 * @author Patrick von Platen
 * @author John Wasseige
 */
public abstract class Meal {
	
	private String name;
	private List<Dish> listOfDish;
	private String type;
	
	
	/**
	 * for tests only
	 */
	public Meal(){
		super();
	}
	

	/**
	 * Constructor of <code>Meal</code> where the constructor is protected so that a class of type Dish can not be created from the outside the package.
	 * @param name = name of the meal f.e. "Lucky Luke" 
	 */
	protected Meal(String name) {
		this.name = name;
		listOfDish = new ArrayList<Dish>();
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
	 * @return the listOfDish
	 */
	public List<Dish> getListOfDish() {
		return listOfDish;
	}


	/**
	 * @param listOfDish the listOfDish to set
	 */
	public void setListOfDish(List<Dish> listOfDish) {
		this.listOfDish = listOfDish;
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
	
	
	/************************************************************/
	
	
	/**
	 * @return returns the price of the meal (adds up the prices of all its elements)
	 */
	public double getPrice() {
		
		double sum = 0;
		
		for(Dish dish : listOfDish)
			sum += dish.getPrice();
		
		return sum;
	}
	

	@Override
	public String toString() {
		return "Meal [" + name + ", " + listOfDish + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Meal){
			Meal meal = (Meal) obj;
			if(meal.getName().equals(name) && meal.getType().equals(type)){
					boolean isSameMeal = true;
				for(int i=0; i < meal.listOfDish.size(); i++)
					if(!meal.getListOfDish().get(i).equals(listOfDish.get(i)))
						isSameMeal = false;
				return isSameMeal;
			}
			else
				return false;				
		}
		else
			return false;			
	}
	
};
