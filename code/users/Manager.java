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
	
	//TODO discuss with John whether a static attribute of the core is a good idea or not
	
	//private String name;
	private String surname;
		
	public Manager(String name, String surname, String username){
		super(name, username);
		this.surname = surname;
	}
	
	/*********************************************************************/
	/* Getters and Setter */

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Manager [getUsername()=" + getUsername() + ", getName()=" + getName() + "]";
	}
	
}
