package policies;

/**
 * The interface <code>SortOrderPolicy</code> allow restaurantSetUp and managers
 * to sort the shipped orders according to different criteria.
 * 
 * The classes implementing this interface are
 * <ul>
 * 	<li><code>HalfMealSorted</code></li>
 *  <li><code>ALaCarteSorted</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */

public interface SortOrderPolicy {
	
	//TODO think about whether this class is necessary
	
	public boolean howToSortOrder(boolean order);

}
