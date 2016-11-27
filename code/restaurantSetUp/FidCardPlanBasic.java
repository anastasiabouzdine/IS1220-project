package restaurantSetUp;

public class FidCardPlanBasic implements FidCardPlan {

	
	
	public FidCardPlanBasic() {
		super();
	}

	
	/**
	 * @return 1 since the function <code>Order.setPrice</code> checks before if special Meal is selected
	 */
	@Override
	public double applyReduction() {
		return 1;
	}

}
