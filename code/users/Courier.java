package users;

import java.util.LinkedList;
import java.util.List;

import core.Order;
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
	
	//TODO discuss with John whether a static attribute of the core is a good idea or not
	
	private String name;
	private String surname;
	private int ID;
	private Address position;
	private String phoneNumber;
	private int nbOfDeliveredOrders;
	private String username;
	private static int counter;
	private boolean state; //true = on duty; false = off duty
	
	private LinkedList<Order> listOfReceivedOrders; 
	
	
	/**
	 * Constructor 
	 * 
	 * @param	name 	of courier
	 * @param	surname	of courier
	 * @param	position	of courier
	 * @param	phonenumber	of courier 
	 * @param	username	of courier to log into the system
	 * @param	id	unique id of courier
	 * @param	nbOfDeliveredOrders	amount of orders delivered by courier
	 * @param	state	states whether courier is on duty (true = yes)
	 */	
	public Courier(String name, String surname, Address position, String phoneNumber, String username){
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.ID = ++counter;
		this.nbOfDeliveredOrders = 0;
		this.state = true;
		listOfReceivedOrders = new LinkedList<Order>();
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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setNbOfDeliveredOrders(int nbOfDeliveredOrders) {
		this.nbOfDeliveredOrders = nbOfDeliveredOrders;
	}
	
	
	public List<Order> getListOfReceivedOrders() {
		return listOfReceivedOrders;
	}

	public void setListOfReceivedOrders(LinkedList<Order> listOfReceivedOrders) {
		this.listOfReceivedOrders = listOfReceivedOrders;
	}

	/*********************************************************************/
	
	/**
	 * the function <code>acceptOrder</code> accepts the order by 
	 * setting the courier of that order to this courier
	 * @return	order	that was given to the courier
	 */
	public Order acceptOrder(){
		Order order = this.listOfReceivedOrders.remove(); // get first element of queue
		order.setCourier(this);
		//TODO Design --> to discuss with John
		return order; 
	}
	
	/**
	 * the function <code>declineOrder</code> accepts the order by 
	 * setting the courier of that order null
	 * @return	order	that was given to the courier
	 */
	public Order declineOrder(){
		Order order = this.listOfReceivedOrders.remove(); // get first element of queue
		order.setCourier(null);
		//TODO Design --> to discuss with John
		return order; 
	}
	
	/**
	 * the function <code>replyRand</code> randomly either accepts or declines an order
	 * @return	order	that was given to the courier
	 */
	public Order replyRand(){
		if(Math.random() <= 0.7)
			return acceptOrder();
		else
			return declineOrder();
	}
	
	/**
	 * @param	message	of which this user is going to be notified
	 */
	public void update(String message) {
		System.out.println(message);
	}

	@Override
	public String toString() {
		return "Courier [name=" + name + ", surname=" + surname + ", position=" + position + "]";
	}
	
	
	
}
