// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package restaurantSetUp;

import java.io.Serializable;
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
public abstract class Meal implements Serializable {

	private static final long serialVersionUID = -4876430174939851276L;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listOfDish == null) ? 0 : listOfDish.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meal other = (Meal) obj;
		if (listOfDish == null) {
			if (other.listOfDish != null)
				return false;
		} else if (!listOfDish.equals(other.listOfDish))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
};
