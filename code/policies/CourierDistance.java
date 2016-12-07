package policies;

import users.Courier;

/**
 * The class <code>CourierDistance</code> is only used by the class <code>FastestDelivery</code>
 * to order the courier by their distance to a certain restaurant.
 * @see the method <code>howToTargetProfit</code>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class CourierDistance {
	
	private Courier courier;
	private double Distance;
	
	
	/**
	 * Constructor of the CourierDistance.
	 * @param	courier a courier that delivers the order
	 * @param	distance the distance between a courier and a certain address	
	 */
	public CourierDistance(Courier courier, double distance) {
		super();
		this.courier = courier;
		Distance = distance;
	}


	/*************************************************************/
	//Getters and Setters 
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	public double getDistance() {
		return Distance;
	}
	public void setDistance(double distance) {
		Distance = distance;
	}
	
	
}
