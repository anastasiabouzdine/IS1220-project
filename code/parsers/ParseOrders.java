package parsers;

import java.util.ArrayList;

import core.Order;
import users.Customer;
import users.Restaurant;


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
	
	public static void main(String[] args) {
		ArrayList<Order> order_list = parseOrders();
		for(Order c : order_list){
			System.out.println(c.toString());
		}
	}

}
