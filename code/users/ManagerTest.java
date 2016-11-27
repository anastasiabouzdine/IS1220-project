package users;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurants.Address;

public class ManagerTest {

	private String name = "Chief";
	private String surname = "Foo";
	private String username = "iAmTheBoss";
	
	@Test
	public void createManager(){
		Manager man1 = new Manager(name, surname, username);
		assertTrue(man1 != null);
	}
	
	@Test
	public void verifyTwoCustomersHaveDifferentIds(){
		Manager man1 = new Manager(name, surname, username);
		Manager man2 = new Manager("Roland", "Stat", "rStat2");
		assertTrue(man1.getID() != man2.getID());
	}
}
