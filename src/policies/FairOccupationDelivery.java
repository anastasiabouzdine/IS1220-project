package policies;

import java.io.Serializable;
import java.util.ArrayList;


import users.Courier;


/**
 * The class <code>FairOccupationDelivery</code> allows to create a delivery policy
 * that makes sure that the system chooses the courier that has fulfilled the least orders 
 * so far as its first choice is to execute the order.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FairOccupationDelivery implements DeliveryPolicy, Serializable {
	
	private static final long serialVersionUID = -3578517789744313257L;
	private ArrayList<Courier> listCourier;
	
	
	/**
	 * Constructor of fair occupation.
	 */
	public FairOccupationDelivery() {
		super();
		listCourier = new ArrayList<Courier>();
	}



	/**
	 * @param	list		an ArrayList of <code>Courier</code>
	 * 						and get all active Couriers as an input
	 * @param	g	   		is in this class not used
	 * @return	listCourier	an ArrayList of <code>Courier</code> and is the sorted list according to the policy
	 */
	@Override
	public <G> ArrayList<Courier> howToDeliver(ArrayList<Courier> list, G g) {
		listCourier = list;
		listCourier.sort((o1, o2) -> ((Integer) o1.getNbOfDeliveredOrders()).compareTo(((Integer) o2.getNbOfDeliveredOrders())));
		
		return listCourier;
	}

	

}
