package parsers;

import java.util.ArrayList;

import core.Order;
import users.Customer;
import users.Restaurant;

/**
* ParseOrders allows to generate an ArrayList of <code>Order</code> from a text file
* given as input the <code>parseOrders</code> method.
* This class is a tool to parse orders from a restaurant and a customer txt file.
* This is especially useful when making test needing more than
* one order and when the thing to test is something else
* than the actual creation of a <code>Order</code> object.
*  
* @author John de Wasseige
* @author Patrick von Platen
* 
* @see #parseOrders()
*/
public class ParseOrders {
	
	public static ArrayList<Order> parseOrders() {
		
		ArrayList<Order> order_list = new ArrayList<Order>();
		int i = 0;	
		
		ArrayList<Restaurant> restaurant_list = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
		ArrayList<Customer> customer_list = ParseCustomers.parseCustomers("src/txtFILES/customersList.txt");
		
			while (i < restaurant_list.size() && i < customer_list.size()){
				
				order_list.add(new Order(customer_list.get(i), restaurant_list.get(i)));
				i++;
			}
		return order_list;
	}
	
	
}
