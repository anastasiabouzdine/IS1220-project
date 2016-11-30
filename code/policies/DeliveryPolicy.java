package policies;

import java.util.ArrayList;


import users.Courier;

/**
 * The interface <code>DeliveryPolicy</code> allows the MyFoodora system
 * to choose the policy for allocating a courier to an order to be delivered.
 * 
 * The classes implementing this interface are
 * <ul>
 * 	<li><code>FastestDelivery</code></li>
 *  <li><code>FairOccupationDelivery</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public interface DeliveryPolicy {
	
	public <G> ArrayList<Courier> howToDeliver(ArrayList<Courier> list, G g); 

}
