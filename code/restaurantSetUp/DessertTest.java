package restaurantSetUp;

import org.junit.Test;

public class DessertTest {

	@Test
	public void createDessertWithNoType() {
		Dessert d = new Dessert("Apple Pie", 8.0);
		System.out.println(d.toString());
	}
	
	@Test
	public void createDessertWithType() {
		Dessert d = new Dessert("Vanilla Ice cream", 6.0, "glutenfree");
		System.out.println(d.toString());
	}
}
