package users;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurantSetUp.Address;
import restaurantSetUp.FidCardPlan;
import restaurantSetUp.FidCardPlanBasic;
import restaurantSetUp.FidCardPlanLottery;
import restaurantSetUp.FidCardPlanPoints;

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

	@Test
	public void verifySetFidCardPlanBasicSame(){
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		FidCardPlan fidCardPlan1 = new FidCardPlanBasic();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(cust1.getFidCardPlan() != fidCardPlan1);
	}
	
	@Test
	public void verifySetFidCardPlanPointsDifferent(){
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		FidCardPlan fidCardPlan1 = new FidCardPlanPoints();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(cust1.getFidCardPlan() == fidCardPlan1);
	}
	
	@Test
	public void verifySetFidCardPlanLotterybetween0and1(){
		Customer cust1 = new Customer(name, surname, address, phoneNumber, email, username);
		FidCardPlan fidCardPlan1 = new FidCardPlanLottery();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(0 <= fidCardPlan1.applyReduction());
	}
	
	
}
