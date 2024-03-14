package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
import com.thelocalmarketplace.software.WeightDiscrepancy;

import powerutility.NoPowerException;
import powerutility.PowerGrid;


public class WeightDiscrepancyTest {   
	
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
    
    
    @Test
    public void testUpdateMass_AddItemToOrder() throws OverloadedDevice {
       
        
        MockItem item1 = new MockItem(new Mass(100));
        MockItem item2 = new MockItem(new Mass(100));
       
        
        order.addItemToOrder(item1);
        order.addItemToOrder(item2);
        
        
 
        WeightDiscrepancy weightDiscrepancy2 = new WeightDiscrepancy(order,scale);
        
        weightDiscrepancy2.updateMass();

        Mass expectedMass = new Mass(200);
 
        
        assertEquals(expectedMass, scale.getCurrentMassOnTheScale()); } 
    
    
    
    @Test
    public void checkWeightChangeTestTrue(){
       
        Mass mass1 = new Mass(500);
        MockItem item1 = new MockItem(mass1); 
        order.addItemToOrder(item1);
        order.addTotalWeightInGrams(5);
        assertTrue(weightDiscrepancy.checkWeightChange());    
    }
    
	@Test
    public void checkWeightChangeTestFalse() throws Exception{
       
    	order.addTotalWeightInGrams(0.5);  //order.getTotalWeightInGrams();
    	
        Mass mass1 = new Mass(500000);
        MockItem item1 = new MockItem(mass1); 
       scale.addAnItem(item1); // scale.getCurrentMassOnTheScale() = 500 000 mcg
        assertFalse(weightDiscrepancy.checkWeightChange());    
//        assertEquals(mockScale.getCurrentMassOnTheScale(), mockOrder.getTotalWeightInGrams());
        
    } 
    
    

    
    
    
    
  
}