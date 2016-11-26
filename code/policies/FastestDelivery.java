package policies;

public class FastestDelivery implements DeliveryPolicy {

	@Override
	public int howToDeliver() {
		return 1;
	}

}
