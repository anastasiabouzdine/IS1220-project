package policies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import restaurantSetUp.Address;
import users.Courier;

/**
 * The class <code>FairOccupationDelivery</code> allows to create a delivery policy that 
 * makes sure that the system chooses the courier that has to take the shortest part to 
 * the restaurant according to their respective positions.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FastestDelivery implements DeliveryPolicy {

	private ArrayList<Courier> courierListSorted;
	private ArrayList<Double> courierDistanceList;
	
	
	/**
	 * Constructor of fastest delivery.
	 */
	public FastestDelivery() {
		super();
		courierListSorted = new ArrayList<Courier>();
		courierDistanceList = new ArrayList<Double>();
	}

	/**
	 * This functions takes the List of all the couriers and the list of their respective distances 
	 * to the restaurant and orders the couriers according to their distances
	 * 
	 * @param	list	is of type ArrayList<Courier> and get all active Couriers as an input
	 * @param	g	is the address of the restaurant that was ordered from
	 * @return	the sorted courierlist
	 */
	@Override
	public <G> ArrayList<Courier> howToDeliver(ArrayList<Courier> list, G g) {
		
		if(courierDistanceList.isEmpty())
			courierDistanceList.clear();
		
		getDistance(list, (Address) g);
		
		courierListSorted = (ArrayList<Courier>) IntStream.range(0, courierDistanceList.size())
				.mapToObj(i -> new CourierDistance(list.get(i), this.courierDistanceList.get(i)))
			    .sorted(Comparator.comparingDouble(CouDist -> CouDist.getDistance()))
			    .map(CouDist -> CouDist.getCourier())
			    .collect(Collectors.toList());
				
		return courierListSorted;
	}
	
	/**
	 * This functions calculates the distances between each courier and the restaurant
	 * and add them to an ArrayList 
	 * 
	 * @param courierList	is the list of all active couriers given by the core system
	 * @param	address	is the address of the restaurant that was ordered from
	 */
	public void getDistance(ArrayList<Courier> courierList, Address address) {
		
		courierDistanceList.clear();
		for(Courier courier: courierList) {
			double distance = courier.getPosition().calculateDistance(address);
			courierDistanceList.add(distance);
		}
	}

	

}
