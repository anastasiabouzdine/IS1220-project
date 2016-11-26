package policies;

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
	
	public int howToTargetProfit();

}
