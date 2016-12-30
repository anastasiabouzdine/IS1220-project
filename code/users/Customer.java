package users;

import core.Order;
import policies.FidCardPlan;
import policies.FidCardPlanBasic;
import policies.FidCardPlanLottery;
import policies.FidCardPlanPoints;
import restaurantSetUp.Meal;

/**
 * The class <code>Customer</code> allows to create a customer which will be able to
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

	private String surname;
	private Address address;
	private String email;
	private String phoneNumber;
	private boolean beNotified = true;

	private FidCardPlan fidCardPlan;

	/**
	 * Class constructor with password. 
	 * 	
	 * @param name			 	name of customer
	 * @param surname		 	surname of customer
	 * @param address 	 		an Address object containing the address of the customer 
	 * @param phoneNumber		phone number of customer
	 * @param email			 	email of customer
	 * @param username 	 		log-in name of customer
	 * @param password 	 		password of customer
	 */
	public Customer(String name, String surname, Address address, String phoneNumber,
			String email, String username, String password){
		super(name, username, password);
		this.surname = surname;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fidCardPlan = new FidCardPlanBasic();
	}

	/*********************************************************************/
	/* Fidelity card plans */

	/**
	 * This functions sets the fidelity card plan and
	 * checks whether the class of new fidCardPlan is the same as the old one,
	 * if so, nothing will be changed.
	 * @param fidCardPlan 	a FidCardPlan object representing the FidCardPlan
	 * 						to be set
	 */
	public void setFidCardPlan(FidCardPlan fidCardPlan) {
		if(!(fidCardPlan.getClass().equals(this.fidCardPlan.getClass())))
			this.fidCardPlan = fidCardPlan;
	}
	public FidCardPlan getFidCardPlan() {
		return fidCardPlan;
	}

	public void setFidCardToBasic() {
		FidCardPlanBasic basic = new FidCardPlanBasic();
		setFidCardPlan(basic);
	}
	public void setFidCardToPoints() {
		FidCardPlanPoints points = new FidCardPlanPoints();
		setFidCardPlan(points);
	}
	public void setFidCardToLottery() {
		FidCardPlanLottery lottery = new FidCardPlanLottery();
		setFidCardPlan(lottery);
	}

	/**
	 * Returns the number of fidelity points if the customer is subscribed to the FidelityPoints plan,
	 * 0 if not.
	 * @return an int corresponding to the number of points for the FidelityPoints plan
	 */
	public int getNumberOfFidelityPoints() {
		if (this.fidCardPlan instanceof FidCardPlanPoints){
			FidCardPlanPoints temp_fid = (FidCardPlanPoints) this.fidCardPlan;
			return temp_fid.getPoints();
		} 
		return 0;
	}

	/**
	 * Add fidelity points if the customer is subscribed to the FidelityPoints plan.
	 * @param n an int representing the number of fidelity points to add
	 * 
	 */
	public void addFidelityPoints(int n) {
		if (this.fidCardPlan instanceof FidCardPlanPoints){
			FidCardPlanPoints temp_fid = (FidCardPlanPoints) this.fidCardPlan;
			temp_fid.setPoints(temp_fid.getPoints() + n);
		} 
	}

	public void changeNotifyConsensus(){
		setBeNotified(!beNotified);
	}

	/*********************************************************************/

	public void update(Restaurant restaurant){
		if (beNotified){
			Meal specialMealOfTheWeek = restaurant.getSpecialMeal();
			double mealPrice = Order.round2(specialMealOfTheWeek.getPrice()*(1-restaurant.getSpecDiscFact()));
			String info = restaurant.getName() + " has put the meal "
					+ specialMealOfTheWeek.getName() + " at a price of " 
					+ mealPrice;
			this.update(info);
			System.out.println("[Customer UPDATE] " + getUsername() + " has been notified that " + info);
		}
	}

	@Override
	public String toString() {
		return "Customer [name=" + getName() + ", surname=" + surname + ", username=" + getUsername() + "]";
	}


	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !

	/**
	 * @return the beNotified
	 */
	public boolean isBeNotified() {
		return beNotified;
	}

	/**
	 * @param beNotified the beNotified to set
	 */
	public void setBeNotified(boolean beNotified) {
		this.beNotified = beNotified;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


};
