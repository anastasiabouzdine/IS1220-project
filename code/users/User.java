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

public class User {

	private String name;
	private String username;
	private int ID;
	private static int counter;
	private Stack<String> messageBox = new Stack<String>(); 
	
	protected User(String name, String username){
		this.name = name;
		this.username = username;
		this.ID = ++counter;
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

		
}
