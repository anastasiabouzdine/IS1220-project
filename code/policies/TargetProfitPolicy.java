package policies;

import java.util.ArrayList;
import java.util.Calendar;

import core.Order;

/**
 * The interface <code>TargetProfitPolicy</code> allow managers 
 * for reasoning about different ways of meeting a given target profit.
 * 
 * The classes implementing this interface are
 * <ul>
 * 	<li><code>DeliveryCostProfit</code></li>
 *  <li><code>ServiceFeeProfit</code></li>
 *  <li><code>MarkupProfit</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public interface TargetProfitPolicy {
	
	public double howToTargetProfit(double input1, double input2, double profit, ArrayList<Order> orders, Calendar dateBefore, Calendar dateAfter);

}
