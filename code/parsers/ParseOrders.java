package parsers;

import java.util.ArrayList;

import core.Core;
import core.Order;
import restaurantSetUp.FullMeal;
import restaurantSetUp.Meal;
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
* @see #parseOrders(ArrayList, Core)
*/
public class ParseOrders {
	
	public static ArrayList<Order> parseOrders(ArrayList<Customer> customerList, Core core) {
		
		ArrayList<Order> order_list = new ArrayList<Order>();
		int i = 0;	
		
		ArrayList<Restaurant> restaurant_list = ParseRestaurants.parseRestaurants("src/txtFILES/restaurantList.txt");
		ArrayList<Customer> customer_list = customerList;
		ArrayList<FullMeal> fullMeals = ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt");
		
			for(Restaurant restaurant : restaurant_list){
				for(Meal meal : fullMeals)
				restaurant.addMeal(meal);
			}
		
			while (i < restaurant_list.size() && i < customer_list.size()){
				Order order = new Order(customer_list.get(i), restaurant_list.get(i));
				order.addMeal(fullMeals.get(0), 1);
				order.getPrice();
				order.setPriceFinal(order.getPriceInter() + core.getServiceFee());
				order.setProfitFinal(order.getPriceFinal()*core.getMarkupPercentage() + core.getServiceFee() - core.getDeliveryCost());
				order_list.add(order);
				i++;
			}
		return order_list;
	}
	
	
}
