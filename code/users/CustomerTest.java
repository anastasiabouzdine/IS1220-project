package users;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurants.Address;

public class CustomerTest {

	private String name = "John";
	private String surname = "Doe";
	private Address address = new Address(2, 3);
	private String phoneNumber = "+123 456 789";
	private String email = "john@doe.com";
	private String username = "johnDoe42";
	
	@Test
	public void createCustomer(){
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		assertTrue(cust1 != null);
	}
	
	@Test
	public void verifyTwoCustomersHaveDifferentIds(){
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		Customer cust2 = new Customer("Antoine", surname, address, phoneNumber, email, username);
		assertTrue(cust1.getID() != cust2.getID());
	}

}
