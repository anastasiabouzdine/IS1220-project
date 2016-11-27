package users;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurantSetUp.Address;

public class CourierTest {

	private String name = "Chief";
	private String surname = "Foo";
	private String username = "iAmTheBoss";
	private Address address = new Address(6,9);
	private String phoneNb = "+3333333";
	
	@Test
	public void createCourier(){
		Courier man1 = new Courier(name, surname, address, phoneNb, username);
		assertTrue(man1 != null);
	}
	
	@Test
	public void verifyNewCourierHasNbOfDeliveredOrderCounterEqualZero(){
		Courier man1 = new Courier(name, surname, address, phoneNb, username);
		assertTrue(man1.getNbOfDeliveredOrders() == 0);
	}
	
	@Test
	public void verifyTwoCouriersHaveDifferentIds(){
		Courier cour1 = new Courier(name, surname, address, phoneNb, username);
		Courier cour2 = new Courier("Sabine", "Dof", address, "+22222", "sabine34");
		assertTrue(cour1.getID() != cour2.getID());
	}

}
