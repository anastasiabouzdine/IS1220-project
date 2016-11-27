package restaurantSetUp;

public class FidCardPlanLottery implements FidCardPlan {

	public FidCardPlanLottery() {
		super();
	}

	/**
	 * @return 1 if customer won the lottery and 0 if he did not
	 */
	@Override
	public double applyReduction() {
		if(Math.random() >= 0.99)
			return 0;
		
		return 1;
	}

}
