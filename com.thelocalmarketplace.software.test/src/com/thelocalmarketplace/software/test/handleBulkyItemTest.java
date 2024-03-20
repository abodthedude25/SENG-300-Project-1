package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import com.thelocalmarketplace.software.test.WeightDiscrepancyTest.MockItem;

import powerutility.PowerGrid;


public class handleBulkyItemTest {

	private WeightDiscrepancy weightDiscrepancy;
	private Order order;
	private ElectronicScale scale;
	
	@Before
	public void setUp() throws OverloadedDevice {
		scale = new ElectronicScale();
        PowerGrid grid = PowerGrid.instance();
        scale.plugIn(grid);
        scale.turnOn();
        scale.enable();
        order = new Order(scale);
        weightDiscrepancy = new WeightDiscrepancy(order, scale);             
	}

    class MockItem extends Item {
        public MockItem(Mass mass) {
            super(mass);
        }
    }   
	
    // if creating and item requires a mass, why is there a separate addTotalWeightInGrams
    
    /*
     * Create test for handleBulkyItem, by creating an order and adding items + weights
     * Call handle bulky item, it should remove the weight of the second item
     * 
     * Should we check the scale-using methods work with the updated TotalWeight?
     *  
     */
    @Test
  	public void testHandleBulkyItem_finalWeight () throws OverloadedDevice {
        MockItem item1 = new MockItem(new Mass(10));
        MockItem item2 = new MockItem(new Mass(60));
		
        order.addItemToOrder(item1); 
        scale.addAnItem(item1); // irrelevant?
        order.addTotalWeightInGrams(10);
        order.addItemToOrder(item2); 
        order.addTotalWeightInGrams(60);
       
             
        weightDiscrepancy.handleBulkyItem(order, 60);
               
        
        double expectedTotalWeight = 10;
        assertEquals(expectedTotalWeight, order.getTotalWeightInGrams(), 0);
        

  	}
}
