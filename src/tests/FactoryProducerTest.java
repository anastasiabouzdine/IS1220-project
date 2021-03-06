package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import restaurantSetUp.AbstractFactory;
import restaurantSetUp.FactoryProducer;

public class FactoryProducerTest {

	@Test
	public void tryToGetADishFactory(){
		AbstractFactory af = FactoryProducer.getFactory("DISH");
		assertTrue(af instanceof AbstractFactory);
	}
	@Test
	public void tryToGetAMealFactory(){
		AbstractFactory af = FactoryProducer.getFactory("MEAL");
		assertTrue(af instanceof AbstractFactory);
	}
	@Test
	public void tryToGetACarFactory(){
		AbstractFactory af = FactoryProducer.getFactory("CAR");
		assertTrue(af == null);
	}

}
