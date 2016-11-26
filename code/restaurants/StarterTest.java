package restaurants;

import org.junit.Test;

public class StarterTest {

	@Test
	public void createStarterWithNoType() {
		Starter d = new Starter("Bread and salami", 8.0);
		System.out.println(d.toString());
	}
	
	@Test
	public void createStarterWithType() {
		Starter d = new Starter("Toast and mozarella", 6.0, "vegetarian");
		System.out.println(d.toString());
	}

}
