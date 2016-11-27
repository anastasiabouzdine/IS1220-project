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

public class Manager {
	
	private String name;
	private String surname;
	private int ID;
	private String username;
	private static int counter;
		
	public Manager(String name, String surname, String username){
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.ID = ++counter;
	}
	
	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getID() {
		return ID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public static int getCounter() {
		return counter;
	}

}
