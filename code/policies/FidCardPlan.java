package policies;

/**
 * The interface <code>FidCardPlan</code> is used to implement the
 * fidelity card plan and forces the classes to implement the 
 * method <code>applyReduction</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public interface FidCardPlan {

	public double applyReduction();
	
}
