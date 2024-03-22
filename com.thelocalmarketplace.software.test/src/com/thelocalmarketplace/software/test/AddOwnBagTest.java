package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.software.AddOwnBag;
import com.thelocalmarketplace.software.Order;

public class AddOwnBagTest {
	private Order order; 
	private AddOwnBag addOwnBag; //object under test 
	private mockScale scale;
	
	/**
	 * Initializing order, addOwnBag and scale for test set up. 
	 * @throws OverloadedDevice
	 */
	
	@Before
	public void setUp() throws OverloadedDevice { 
		order = new Order(scale); 
		addOwnBag = new AddOwnBag(order, scale); 
		scale = new mockScale(new Mass(40000000),new Mass(40000000));
		
	}

	@Test 
	public void testGetBagWeightBagAdded() {
		order.addTotalWeightInGrams(30);
		scale = new mockScale(new Mass(40000000), new Mass(40000000));
		AddOwnBag addOwnBag = new AddOwnBag(order, scale);
		double bagWeight = addOwnBag.getBagWeight(order, scale);
		assertEquals(10.0, bagWeight, 10.0);
	}
 
	@Test(expected = OverloadedDevice.class)
	public void testGetBagWeightNoBag() throws OverloadedDevice {
		order = new Order(scale);
		order.addTotalWeightInGrams(40); 
		scale = new mockScale(new Mass(40000000), new Mass(40000000));
		AddOwnBag addOwnBag = new AddOwnBag(order, scale);
		double bagWeight = addOwnBag.getBagWeight(order, scale);
	
	}
}

