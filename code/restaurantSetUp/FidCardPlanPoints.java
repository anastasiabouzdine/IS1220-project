package restaurantSetUp;

/**
 * The class <code>FidCardPlanPoints</code> allows to create a FidCardPlanPoints which 
 * <ul>
 * 	<li>will apply a reduction according to the points collected by <code>Customer</code></li>
 *  <li> is inherited from <code>FidelityCardPlan</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public class FidCardPlanPoints implements FidCardPlan{
	
	private int points;
	private double reduc;
	
	/**
	 * @param points default is set to 0 (new plan = 0 points)
	 * @param reduc default is set to 10 %
	 */
	public FidCardPlanPoints() {
		super();
		this.points = 0;
		this.reduc = 0.1;
	}

	/**
	 * The function that is used if the Fidelity plan is set to FidCardPlanPoints.
	 * @return 0 if customer has more than 100 points or (1 - reduc) if customer has more than 100 points
	 */
	@Override
	public double applyReduction() {
		
		if(points >= 100) {
			points -= 100;
			return (1 - reduc);
		}
			
		else
			return 1;
	}
	
	/*********************************************************************/
	/* Getters and Setter */ // no setter for the points!
	
	public double getReduc() {
		return reduc;
	}
	public void setReduc(double reduc) {
		this.reduc = reduc;
	}
	public int getPoints() {
		return points;
	}
	
}
