package policies;

import users.Courier;

public class CourierDistance {
	
	private Courier courier;
	private double Distance;
	
	
	public CourierDistance(Courier courier, double distance) {
		super();
		this.courier = courier;
		Distance = distance;
	}


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
