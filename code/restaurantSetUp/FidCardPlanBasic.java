package restaurantSetUp;

/**
 * The class <code>FidCardPlanBasic</code> implements the interface <code>FidCardPlan</code> and presents the Basic Fidelity Card Plan.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FidCardPlanBasic implements FidCardPlan {

	/**
	 * Constructor of Basic Fidelity Plan
	 */
	public FidCardPlanBasic() {
		super();
	}

	/**
	 * The function is not used since the function <code>Order.setPrice</code> checks if special Meal is selected.
	 * @return 1 
	 */
	@Override
	public double applyReduction() {
		return 1;
	}

}
