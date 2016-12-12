package policies;

/**
 * The class <code>FidCardPlanLottery</code> implements the interface <code>FidCardPlan</code> and presents the Lottery Fidelity Card Plan.
 *  
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class FidCardPlanLottery implements FidCardPlan {

	/**
	 * Constructor of Basic Fidelity Plan
	 */
	public FidCardPlanLottery() {
		super();
	}

	/**
	 * The function that is used if the Fidelity plan is set to FidCardPlanLottery.
	 * @return 1 if customer wan the lottery and 0 if he did not 
	 * (depending on the random number).
	 */
	@Override
	public double applyReduction() {
		if(Math.random() >= 0.99)
			return 0;
		
		return 1;
	}

}
