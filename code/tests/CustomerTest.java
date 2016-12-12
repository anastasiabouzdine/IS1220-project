package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.ParseCustomers;
import policies.FidCardPlanBasic;
import policies.FidCardPlanLottery;
import policies.FidCardPlanPoints;
import users.Customer;

public class CustomerTest {

	private ArrayList<Customer> list_customer = ParseCustomers.parseCustomers("src/txtFILES/customersList.txt");
	Customer cust1 =  list_customer.get(0);
	
	@Test
	public void verifyTwoCustomersHaveDifferentIds(){
		Customer cust2 = list_customer.get(1);
		assertNotEquals(cust1.getID(), cust2.getID());
	}

	@Test
	public void verifySetFidCardPlanBasic(){
		cust1.setFidCardToBasic();
		assertTrue(cust1.getFidCardPlan() instanceof FidCardPlanBasic);
	}
	
	@Test
	public void verifySetFidCardPlanPoints(){
		cust1.setFidCardToPoints();
		assertTrue(cust1.getFidCardPlan() instanceof FidCardPlanPoints);
	}
	
	@Test
	public void verifySetFidCardPlanLottery(){
		cust1.setFidCardToLottery();
		assertTrue(cust1.getFidCardPlan() instanceof FidCardPlanLottery);
	}
	
	@Test
	public void verifyCreatedCustomerHasBasicFidPlan(){
		Customer cust2 = list_customer.get(3);
		assertTrue(cust2.getFidCardPlan() instanceof FidCardPlanBasic);
	}
	
	@Test
	public void verifySetFidCardPlanLotterybetween0and1(){
		cust1.setFidCardToLottery();
		assertTrue(0 <= cust1.getFidCardPlan().applyReduction());
		assertTrue(1 >= cust1.getFidCardPlan().applyReduction());
	}
	
	@Test
	public void verifyCustomerCanChangeHisNotificationSystem(){
		Customer cust1 = list_customer.get(0);
		assertTrue(cust1.isBeNotified());
		cust1.changeNotifyConsensus();
		assertFalse(cust1.isBeNotified());
	}
	
	
}
