package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import users.Manager;

public class ManagerTest {

	private String name = "Chief";
	private String surname = "Foo";
	private String username = "iAmTheBoss";
	
	@Test
	public void createManager(){
		Manager man1 = new Manager(name, surname, username, "password");
		assertNotNull(man1);
	}
	
	@Test
	public void verifyTwoManagersHaveDifferentIds(){
		Manager man1 = new Manager(name, surname, username, "password");
		Manager man2 = new Manager("Roland", "Stat", "rStat2", "password");
		assertNotEquals(man1.getID(), man2.getID());
	}
}
