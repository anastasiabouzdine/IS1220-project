package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parsers.ParseCouriers;
import users.Courier;


public class CourierTest {

	private ArrayList<Courier> list_courier = ParseCouriers.parseCouriers("src/txtFILES/courierList.txt");
	
	
	@Test
	public void createCourier(){
		Courier man1 = list_courier.get(0);
		assertTrue(man1 != null);
	}
	
	@Test
	public void verifyNewCourierHasNbOfDeliveredOrderCounterEqualZero(){
		Courier man1 = list_courier.get(0);
		assertTrue(man1.getNbOfDeliveredOrders() == 0);
	}
	
	@Test
	public void verifyTwoCouriersHaveDifferentIds(){
		Courier man1 = list_courier.get(0);
		Courier man2 = list_courier.get(1);
		assertTrue(man1.getID() != man2.getID());
	}

}
