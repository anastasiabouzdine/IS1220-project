package users;

import restaurantSetUp.Address;

import restaurantSetUp.FidCardPlan;
import restaurantSetUp.FidCardPlanBasic;
import restaurantSetUp.Meal;

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
public class Customer extends User implements Observer{
	
	//TODO discuss with John whether a static attribute of the core is a good idea or not

	private String surname;
	private Address address;
	private String email;
	private String phoneNumber;
	private boolean beNotified = true;
	
	private FidCardPlan fidCardPlan;
	
	public Customer(String name, String surname, Address address, String phoneNumber,
			String email, String username){
		super(name, username);
		this.surname = surname;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fidCardPlan = new FidCardPlanBasic();
	}
	
	
	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public boolean isBeNotified() {
		return beNotified;
	}
	public void setBeNotified(boolean beNotified) {
		this.beNotified = beNotified;
	}
	
	public FidCardPlan getFidCardPlan() {
		return fidCardPlan;
	}

	/**
	 *this functions checks whether the class of new fidCardPlan is the same as the old one. If so, nothing will be changed.
	 */
	public void setFidCardPlan(FidCardPlan fidCardPlan) {
		
		if(!(fidCardPlan.getClass().equals(this.fidCardPlan.getClass())))
			this.fidCardPlan = fidCardPlan;
	}
	
	/*********************************************************************/
	
	/**
	 * // TODO
	 */
	public void update(Meal specialMealOfTheWeek, Restaurant restaurant){
		if (beNotified){
			double mealPrice = specialMealOfTheWeek.getPrice()*restaurant.getSpecDiscFact();
			System.out.println("[Customer UPDATE] " + getName() + " " + surname + " has been notified"
					+ " that " + restaurant.getName() + " has put the meal "
					+ specialMealOfTheWeek.getName() + " at a price of " 
					+ mealPrice);
		}
	}
	
//	/**
//	 * @param	message	of which this user is going to be notified
//	 */
//	public void update(String message){
//		System.out.println("[Customer UPDATE] " + message);
//	}

	

	@Override
	public String toString() {
		return "Customer [name=" + getName() + ", surname=" + surname + ", username=" + getUsername() + "]";
//		return "Customer [name=" + name + ", surname=" + surname + ", ID=" + ID + ", address=" + address + ", email="
//				+ email + ", phoneNumber=" + phoneNumber + ", username=" + username + ", beNotified=" + beNotified
//				+ "]";
	}

	
};
