package projet_oop;

public class Starter extends Dish {

	public Starter(String name, int price, String type) {
		super(name, price, type);
	}
	
	public Starter(String name, int price) {
		super(name, price, "standard");
	}

}
