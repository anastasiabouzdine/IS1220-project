package policies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import restaurantSetUp.Address;
import users.Courier;

public class FastestDelivery implements DeliveryPolicy {

	private ArrayList<Courier> courierListSorted;
	private ArrayList<Double> courierDistanceList;
	
	
	
	public FastestDelivery() {
		super();
		courierListSorted = new ArrayList<Courier>();
		courierDistanceList = new ArrayList<Double>();
	}

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
	
	public void getDistance(ArrayList<Courier> courierList, Address address) {
		
		courierDistanceList.clear();
		for(Courier courier: courierList) {
		double distance = courier.getPosition().calculateDistance(address);
		courierDistanceList.add(distance);
		}
	}

	

}
