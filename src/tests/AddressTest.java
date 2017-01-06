package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import users.Address;

public class AddressTest {

	Address address1;
	
	@Test
	public void createAnAddress() {
		address1 = new Address(3,2);
	}
	
	@Test
	public void setAndgetCoordinates() {
		address1 = new Address(3,2);
		address1.setxCoordinate(4);
		address1.setyCoordinate(1);
		assertEquals(address1.getxCoordinate(), 4);
		assertEquals(address1.getyCoordinate(), 1);
	}
	
	@Test
	public void computeDistanceBetweenTwoAddress() {
		address1 = new Address(3,2);
		Address address2 = new Address(6, 6);
		double dist = address1.calculateDistance(address2);
		assertEquals(dist, 5, 0.01);
	}

	@Test
	public void checkIfTwoSameAddressAreEqual() {
		Address address2 = new Address(6, 6);
		Address address3 = new Address(6, 6);
		assertTrue(address2.equals(address3));
	}
}
