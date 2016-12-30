package policies;

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
	 * Class constructor.
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

	/************************************************************/
	/* Getters and Setters */ // no setter for the points!
	
	
	/**
	 * @return the reduc
	 */
	public double getReduc() {
		return reduc;
	}

	/**
	 * @param reduc the reduc to set
	 */
	public void setReduc(double reduc) {
		this.reduc = reduc;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	
}
