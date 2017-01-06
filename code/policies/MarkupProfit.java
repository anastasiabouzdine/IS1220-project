package policies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import core.Order;

/**
 * The class <code>MarkupProfit</code> allows 
 * to create the policy MarkupProfit to calculate 
 * the service fee needed to achieve a certain profit.
 * 
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 * 
 * @see #howToTargetProfit(double, double, double, ArrayList, Calendar, Calendar)
 */

public class MarkupProfit implements TargetProfitPolicy, Serializable {

	private static final long serialVersionUID = -2086741253308027391L;

	/**
	 * Constructor
	 */
	public MarkupProfit(){
		super();
	}
	
	/**
	 * The method howToTargetProfit of <code>MarkupProfit</code> allows 
	 * 
	 * to calculate the markup profit that is needed to reach a 
	 * certain profit by taking ancient orders of a certain period of time,
	 * the delivery cost and the service fee as given.
	 * 
	 * @param	input1	the delivery cost fee
	 * @param	input2	service fee
	 * @param	profit	the expected profit
	 * @param	orders	the list of all orders
	 * @param	dateBefore	the beginning of the period 
	 * @param	dateAfter	the end of the period 	
	 * 
	 * @return	markupFee	the markup fee that has to be set to achieve the wanted profit
	 */
	@Override
	public double howToTargetProfit(double input1, double input2, double profit, ArrayList<Order> orders, Calendar dateBefore, Calendar dateAfter) {
		
		double markupFee=0;
		int amountOrders = orders.size();
		double sum=0;
		for(Order order: orders){
			if(order.getDate().compareTo(dateBefore) >= 0 && order.getDate().compareTo(dateAfter) <= 0){
				sum += order.getPriceInter();
			}
		}
		markupFee = (profit - input2*amountOrders + input1*amountOrders)/Order.round2(sum);
		return Order.round4(markupFee);
	}


}
