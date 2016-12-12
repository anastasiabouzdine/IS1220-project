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
		Manager man1 = new Manager(name, surname, username);
		assertNotNull(man1);
	}
	
	@Test
	public void verifyTwoManagersHaveDifferentIds(){
		Manager man1 = new Manager(name, surname, username);
		Manager man2 = new Manager("Roland", "Stat", "rStat2");
		assertNotEquals(man1.getID(), man2.getID());
	}
}
