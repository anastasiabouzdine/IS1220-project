package users;

import restaurantSetUp.Address;

/**
 * The class <code>Courier</code> allows to create a Courier which will be able to
 * <ul>
 * 	<li>register/unregister their Account to the MyFoodora system</li>
 *  <li>set their state as on-duty or off-duty</li>
 *  <li>change their position</li>
 *  <li>accept/refuse a delivery call (received from the MyFoodora system)</li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class Courier {
	
	private String name;
	private String surname;
	private int ID;
	private Address position;
	private String phoneNumber;
	private int nbOfDeliveredOrders;
	private String username;
	private static int counter;
		
	public Courier(String name, String surname, Address position, String phoneNumber, String username){
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
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
	public Address getPosition() {
		return position;
	}
	public void setPosition(Address position) {
		this.position = position;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getID() {
		return ID;
	}
	public int getNbOfDeliveredOrders() {
		return nbOfDeliveredOrders;
	}
	public static int getCounter() {
		return counter;
	}
	
}
