package policies;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import core.Order;
import restaurantSetUp.Address;
import users.Courier;
import users.Customer;
import users.Restaurant;

public class DeliveryPolicyTest {
	
	private static Address address1 = new Address(11,11);
	private static Restaurant r1 = new Restaurant("Chez Andre", address1, "andre");
	
	private String name = "Chief";
	private String surname = "Foo";
	private String username = "iAmTheBoss";
	private String phoneNb = "+3333333";
	
	private Address address2 = new Address(6,9);
	private Address address3 = new Address(1,4);
	private Address address4 = new Address(6,2);
	private Address address5 = new Address(9,12);
	private Address address6 = new Address(0,2);
	
	private Courier man1 = new Courier(name, surname, address2, phoneNb, username);
	private Courier man2 = new Courier(name, surname, address3, phoneNb, username);
	private Courier man3 = new Courier(name, surname, address4, phoneNb, username);
	private Courier man4 = new Courier(name, surname, address5, phoneNb, username);
	private Courier man5 = new Courier(name, surname, address6, phoneNb, username);
	
	
	private String name2 = "John";
	private String surname2 = "Doe";
	private Address address7 = new Address(3, 3);
	private String phoneNumber = "+123 456 789";
	private String email = "john@doe.com";
	private String username2 = "johnDoe42";
	private Customer cust1 = new Customer(name2, surname2, address7, phoneNumber, email, username2);
	
	Order order1 = new Order(cust1, r1);
	
	
	private ArrayList<Courier> list = new ArrayList<Courier>();
	
	@Test
	public void fastestDelievery() {
		DeliveryPolicy dPolicy = new FastestDelivery();
		
		list.add(man1);
		list.add(man2);
		list.add(man3);
		list.add(man4);
		list.add(man5);
		
		assertTrue(dPolicy.howToDeliver(list, order1.getRestaurant().getAddress()).get(0).equals(man4));
	}
	
	@Test
	public void fairOccupationDelivery() {
		DeliveryPolicy dPolicy = new FairOccupationDelivery();
		
		man1.setNbOfDeliveredOrders(5);
		man2.setNbOfDeliveredOrders(2);
		man3.setNbOfDeliveredOrders(3);
		man4.setNbOfDeliveredOrders(4);
		man5.setNbOfDeliveredOrders(1);
		
		list.add(man1);
		list.add(man2);
		list.add(man3);
		list.add(man4);
		list.add(man5);
		
		assertTrue(dPolicy.howToDeliver(list, order1.getRestaurant().getAddress()).get(0).equals(man5));
	}

}
