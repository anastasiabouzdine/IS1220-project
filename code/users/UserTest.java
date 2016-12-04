package users;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void verifyTwoUsersHaveDifferentIds(){
		User usr1 = new User("Antoine", "Ant42");
		User usr2 = new User("Baudouin", "Baudouin26");
		assertTrue(usr1.getID() != usr2.getID());
	}

}
