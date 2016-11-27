package users;

import restaurantSetUp.Meal;

/**
 * The interface <code>Observer</code> allows to implement the <b>Strategy Pattern</b>.
 * Contrary to the usual Observer interface, we won't need <code>addObserver</code>
 * or <code>removeObserver</code> methods as each <code>Customer</code> 
 * has a boolean <code>beNotified</code> attribute.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public interface Observer {
	
	public void update(Meal specialMealOfTheWeek, Restaurant restaurant);

}
