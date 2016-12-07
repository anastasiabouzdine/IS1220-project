package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.ParseCustomers;
import restaurantSetUp.FidCardPlan;
import restaurantSetUp.FidCardPlanBasic;
import restaurantSetUp.FidCardPlanLottery;
import restaurantSetUp.FidCardPlanPoints;
import users.Customer;

public class CustomerTest {

	private ArrayList<Customer> list_customer = ParseCustomers.parseCustomers("src/txtFILES/customersList.txt");

	
	@Test
	public void verifyTwoCustomersHaveDifferentIds(){
		Customer cust1 = list_customer.get(0);
		Customer cust2 = list_customer.get(1);
		assertTrue(cust1.getID() != cust2.getID());
	}

	@Test
	public void verifySetFidCardPlanBasicSame(){
		Customer cust1 =  list_customer.get(0);
		FidCardPlan fidCardPlan1 = new FidCardPlanBasic();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(cust1.getFidCardPlan() != fidCardPlan1);
	}
	
	@Test
	public void verifySetFidCardPlanPointsDifferent(){
		Customer cust1 = list_customer.get(0);
		FidCardPlan fidCardPlan1 = new FidCardPlanPoints();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(cust1.getFidCardPlan() == fidCardPlan1);
	}
	
	@Test
	public void verifySetFidCardPlanLotterybetween0and1(){
		Customer cust1 = list_customer.get(0);
		FidCardPlan fidCardPlan1 = new FidCardPlanLottery();
		cust1.setFidCardPlan(fidCardPlan1);
		assertTrue(0 <= fidCardPlan1.applyReduction());
	}
	
	@Test
	public void verifyCustomerCanChangeHisNotificationSystem(){
		Customer cust1 = list_customer.get(0);
		assertTrue(cust1.isBeNotified());
		cust1.changeNotifyConsensus();
		assertTrue(!cust1.isBeNotified());
	}
	
	
}