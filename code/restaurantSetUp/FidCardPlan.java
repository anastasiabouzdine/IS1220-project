package restaurantSetUp;

public interface FidCardPlan {
	
	/**
	 * The interface <code>FidCardPlan</code> forces the classes to implement the 
	 * method <code>applyReduction</code>
	 * 
	 * @author John de Wasseige
	 * @author Patrick von Platen
	 */
	
	
	
	/**
	 * The method <code>applyReduction</code> differs from each type of FidPlan and returns a reduction as a double 
	 */
	public double applyReduction();
}
