package policies;

import java.util.ArrayList;
import java.util.Calendar;

import core.Order;

/**
 * The class <code>ServiceFeeProfit</code> allows 
 * to create the policy ServiceFeeProfit to calculate 
 * the service fee needed to achieve a certain profit
 * @see the method <code>howToTargetProfit</code>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class ServiceFeeProfit implements TargetProfitPolicy {

		/**
		 * Constructor
		 */
		public ServiceFeeProfit(){
			super();
		}
		
		/**
		 * The method howToTargetProfit of <code>ServiceFeeProfit</code> allows 
		 * 
		 * to calculate the service fee that is needed to reach a 
		 * certain profit by taking ancient orders of a certain period of time,
		 * the markup percentage and the delivery cost as given.
		 * 
		 * @param	input1	the markup percentage
		 * @param	input2	the	delivery cost fee
		 * @param	profit	the expected profit
		 * @param	order	the list of all orders
		 * @param	dateBefore	the beginning of the period 
		 * @param	dateAfter	the end of the period 	
		 * 
		 * @return	serviceFee	the service fee that has to be set to achieve the wanted profit
		 */
		@Override
		public double howToTargetProfit(double input1, double input2, double profit, ArrayList<Order> orders, Calendar dateBefore, Calendar dateAfter) {
			
			double serviceFee=0;
			int amountOrders = orders.size();
			double sum=0;
			
			for(Order order: orders)
				if(order.getDate().after(dateBefore)&&order.getDate().before(dateAfter))
					sum += order.getPriceInter();
			
			sum *= input1;
			sum -= (input2*amountOrders);
			
			serviceFee = (profit - sum) / amountOrders;

			return Order.round2(serviceFee);
		}

}
