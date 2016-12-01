package policies;

import users.Restaurant;

/**
 * The abstract class <code>Sort</code> allow restaurants and managers
 * to sort the shipped orders according to different criteria.
 * 
 * The classes implementing this interface are
 * <ul>
 * 	<li><code>MealSort</code></li>
 *  <li><code>DishSort</code></li>
 * </ul>
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public abstract class Sort implements Comparable<Sort> {
	
	
	private int count;
	private Restaurant rest;
	
	protected Sort(int count, Restaurant rest) {
		super();
		this.count = count;
		this.rest = rest;
	}

	protected Sort() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Restaurant getRest() {
		return rest;
	}

	public void setRest(Restaurant rest) {
		this.rest = rest;
	}
	
	
	public abstract boolean howToSortOrder(boolean order);
	
	@Override
	public int compareTo(Sort o) {
		return (this.count <= o.getCount()) ? 1 : -1;
	}
	
}
