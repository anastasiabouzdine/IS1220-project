package users;

import java.util.LinkedList;

import core.Order;

/**
 * The class <code>Courier</code> allows to create a courier which will be able to
 * <ul>
 * 	<li>register/unregister their Account to the MyFoodora system</li>
 *  <li>set their available as on-duty or off-duty</li>
 *  <li>change their position</li>
 *  <li>accept/refuse a delivery call (received from the MyFoodora system)</li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 * 
 * @see #replyRandom()
 */

public class Courier extends User {

	private String surname;
	private Address position;
	private String phoneNumber;
	private int nbOfDeliveredOrders;
	private boolean available; //true = on duty; false = off duty
	private LinkedList<Order> listOfReceivedOrders; 


	/**
	 * Class constructor with password. 
	 * 
	 * @param	name 		a String containing the name
	 * @param	surname		a String containing the surname
	 * @param	position	an Address object a String containing the position
	 * @param	phoneNumber a String containing the phonenumber
	 * @param	username	a String containing the username
	 * @param	password	a String containing the password
	 */	
	public Courier(String name, String surname, Address position, String phoneNumber, String username, String password){
		super(name, username, password);
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
		this.nbOfDeliveredOrders = 0;
		this.available = true;
		listOfReceivedOrders = new LinkedList<Order>();
	}


	/*********************************************************************/

	/**
	 * The function <code>acceptOrder</code> accepts the order by 
	 * setting the courier of that order to this courier.
	 */
	public void acceptOrder(){
		Order order = this.listOfReceivedOrders.remove(); // rm first element of linkedlist
		order.setCourier(this);
		nbOfDeliveredOrders++;
	}

	/**
	 * The function <code>declineOrder</code> declines the order by 
	 * setting the courier of that order null.
	 */
	public void declineOrder(){
		this.listOfReceivedOrders.remove();  // rm first element of linkedlist
	}

	/**
	 * The function <code>replyRand</code> randomly either accepts or declines an order.
	 */
	public void replyRandom(){
		if(Math.random() <= 0.9)
			acceptOrder();
		else
			declineOrder();
	}

	/**
	 * @param	order	that is to be added to the list of received orders
	 */
	public void addNewOrder(Order order) {
		this.listOfReceivedOrders.add(order);
	}

	@Override
	public String toString() {
		return "Courier [getUsername()=" + getUsername() + ", getName()=" + getName() + "]";
	}


	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !

	/**
	 * @return the nbOfDeliveredOrders
	 */
	public int getNbOfDeliveredOrders() {
		return nbOfDeliveredOrders;
	}


	/**
	 * @param nbOfDeliveredOrders the nbOfDeliveredOrders to set
	 */
	public void setNbOfDeliveredOrders(int nbOfDeliveredOrders) {
		this.nbOfDeliveredOrders = nbOfDeliveredOrders;
	}


	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}


	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}


	/**
	 * @return the listOfReceivedOrders
	 */
	public LinkedList<Order> getListOfReceivedOrders() {
		return listOfReceivedOrders;
	}


	/**
	 * @param listOfReceivedOrders the listOfReceivedOrders to set
	 */
	public void setListOfReceivedOrders(LinkedList<Order> listOfReceivedOrders) {
		this.listOfReceivedOrders = listOfReceivedOrders;
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
	 * @return the position
	 */
	public Address getAddress() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setAddress(Address position) {
		this.position = position;
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



}
