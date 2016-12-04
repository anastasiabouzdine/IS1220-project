package users;

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
	
	public User(String name, String username){
		this.name = name;
		this.username = username;
		this.ID = ++counter;
	}
	
	/*********************************************************************/
	/* Getters and Setter */ // no setter for the ID, nor for the COUNTER !
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getCounter() {
		return counter;
	}

	public int getID() {
		return ID;
	}
		
}
