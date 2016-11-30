package policies;

import java.util.ArrayList;


import users.Courier;



public class FairOccupationDelivery implements DeliveryPolicy {
	
	private ArrayList<Courier> listCourier;

	@Override
	public <G> ArrayList<Courier> howToDeliver(ArrayList<Courier> list, G g) {
		listCourier = list;
		listCourier.sort((o1, o2) -> ((Integer) o1.getNbOfDeliveredOrders()).compareTo(((Integer) o2.getNbOfDeliveredOrders())));
		
		return listCourier;
	}

	

}
