package users;

import restaurants.Address;
import restaurants.Meal;
import restaurants.Restaurant;

/**
 * The class <code>Customer</code> allows to create a Customer which will be able to
 * <ul>
 * 	<li>place an <code>Order</code></li>
 *  <li>register/unregister to/from a <code>FidelityCardPlan</code></li>
 *  <li>access information related to their account</li>
 *  <li>give/remove consensus to be notified of a new special offer set by any <code>Restaurant</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class Customer {

	private String name;
	private String surname;
	private int ID;
	private Address address;
	private String email;
	private String phoneNumber;
	private String username;
	private static int counter;
	private boolean beNotified = true;
	
	public Customer(){
		super();
	}
		
	public Customer(String name, String surname, Address address, String phoneNumber,
			String email, String username){
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.ID = ++counter;
	}
	
	public void update(Meal specialMealOfTheWeek, Restaurant restaurant){
		if (beNotified){
			double mealPrice = specialMealOfTheWeek.getPrice()*restaurant.getSpecDiscFact();
			System.out.println(name + " " + surname + " has been notified"
					+ " that " + restaurant.getName() + " has put the meal "
					+ specialMealOfTheWeek.getName() + " at a price of " 
					+ mealPrice);
		}
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
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
	public static int getCounter() {
		return counter;
	}
	public boolean isBeNotified() {
		return beNotified;
	}
	public void setBeNotified(boolean beNotified) {
		this.beNotified = beNotified;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", surname=" + surname + ", username=" + username + "]";
//		return "Customer [name=" + name + ", surname=" + surname + ", ID=" + ID + ", address=" + address + ", email="
//				+ email + ", phoneNumber=" + phoneNumber + ", username=" + username + ", beNotified=" + beNotified
//				+ "]";
	}
	
};
