package users;

import java.util.Stack;


/**
 * The class <code>User</code> allows to create different users
 * for the MyFoodora system.
 * It can be used by any user needing a <code>name</code>,
 * a <code>username</code> and an <code>ID</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public abstract class User {

	private String name;
	private String surname;
	private String emailAddress;
	private String phoneNumb;
	private String username;
	private String password;
	private Address address;
	private int ID;
	private static int counter;
	private Stack<String> messageBox = new Stack<String>(); 
	
	/**
	 * Class constructor. 
	 * 	
	 * @param name			 	name of user
	 * @param username 	 		log-in name of user
	 */
	protected User(String name, String username, String surname, Address address, String phoneNumb, String emailAddress){
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.address = address;
		this.phoneNumb = phoneNumb;
		this.emailAddress = emailAddress;
		this.ID = ++counter;
	}
	
	/**
	 * Class constructor with password. 
	 * 	
	 * @param name			 	name of user
	 * @param username 	 		log-in name of user
	 * @param password 	 		password of user
	 */
	protected User(String name, String username, String surname, Address address, String phoneNumb, String emailAddress, String password) {
		this(name, username, surname, address, phoneNumb, emailAddress);
		this.password = password;
	}
	
	/**
	 * This function pushes a new message to the message box
	 * @param	message	new message
	 *  
	 */
	public void update(String message){ 
		messageBox.push(message);
	}
	
	/**
	 * This function allows to read new messages as soon as the user is logged in
	 *  
	 */
	public void checkMessages(){ 
		int amount = messageBox.size();
		while(!messageBox.isEmpty()){
			String message = messageBox.pop();
			System.out.println(amount - messageBox.size() + ". message: " + message);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ID != other.ID)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the messageBox
	 */
	public Stack<String> getMessageBox() {
		return messageBox;
	}

	/**
	 * @param messageBox the messageBox to set
	 */
	public void setMessageBox(Stack<String> messageBox) {
		this.messageBox = messageBox;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the counter
	 */
	public static int getCounter() {
		return counter;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the phoneNumb
	 */
	public String getPhoneNumb() {
		return phoneNumb;
	}

	/**
	 * @param phoneNumb the phoneNumb to set
	 */
	public void setPhoneNumb(String phoneNumb) {
		this.phoneNumb = phoneNumb;
	}


	
	
	
	

	
	

		
}
