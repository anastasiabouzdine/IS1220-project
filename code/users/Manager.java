package users;


/**
 * The class <code>Manager</code> allows to create a Manager which is in
 * charge of overseeing the MyFoodora system and is be able to
 * <ul>
 * 	<li>add/remove any kind of user (<code>Restaurant</code>,
 *  <code>Customers</code> and/or <code>Couriers</code>) to/from the system</li>
 *  <li>un-/register from/to a <code>FidelityCardPlan</code></li>
 *  <li>activate/disactivate any kind of user (<code>Restaurant</code>,
 *  <code>Customers</code> and/or <code>Couriers</code>) of the system</li>
 *  </ul>
 *  A <code>Manager</code> is also able to perform business performance related 
 *  operations (see implemented methods).
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Manager extends User {
	
	/**
	 * Class constructor. 
	 * 	
	 * @param name			 	name of manager
	 * @param surname	 		surname of manager
	 * @param username 	 		log-in name of manager
	 */
	public Manager(String name, String surname, String username){
		super(name, username, surname, null, null, null);
	}
	
	/**
	 * Class constructor with password. 
	 * 	
	 * @param name			 	name of manager
	 * @param surname	 		surname of manager
	 * @param username 	 		log-in name of manager
	 * @param password			password of manager
	 */
	public Manager(String name, String surname, String username, String password){
		super(name, username, surname, null, null, null, password);
	}
	
	

	
	@Override
	public String toString() {
		return "Manager [getUsername()=" + getUsername() + ", getName()=" + getName() + "]";
	}

	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !

	
	
	
}
