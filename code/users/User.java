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
