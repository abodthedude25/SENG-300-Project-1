package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
import com.thelocalmarketplace.software.WeightDiscrepancy;

import powerutility.PowerGrid;


public class WeightDiscrepancyTest {   
	 
	private WeightDiscrepancy weightDiscrepancy;
    private mockOrder mockOrder;
    private mockScale mockScale;
    
    @Before
    public void setUp() throws OverloadedDevice {
        mockOrder = new mockOrder();
        mockScale = new mockScale();
        weightDiscrepancy = new WeightDiscrepancy(mockOrder, mockScale);
        PowerGrid powerGrid = PowerGrid.instance();
        
        weightDiscrepancy.plugIn(powerGrid);
        weightDiscrepancy.turnOn();
        weightDiscrepancy.enable();
        
        
    }
    
    


    // Define MockItem class
    class MockItem extends Item {
        public MockItem(Mass mass) {
            super(mass);
        }
    }


    
//    @Test
//    public void testNotifyMassChanged_WhenNoDiscrepancy() throws OverloadedDevice {
//       
//        Mass mass1 = new Mass(500);
//        MockItem item1 = new MockItem(mass1); 
//        weightDiscrepancy.notifyMassChanged();
//        mockScale.addAnItem(item1);
//        mockScale.
//        // Assertion
//        assertFalse(SelfCheckoutStationSoftware.isBlocked()); // Assuming isStationBlocked() returns false initially
//    }

    @Test
    public void checkWeightChangeTestTrue(){
       
        Mass mass1 = new Mass(500);
        MockItem item1 = new MockItem(mass1); 
        mockOrder.addItemToOrder(item1);
        mockOrder.addTotalWeightInGrams(5);
        assertTrue(weightDiscrepancy.checkWeightChange());    
    }
    
	@Test
    public void checkWeightChangeTestFalse() throws Exception{
       
    	mockOrder.addTotalWeightInGrams(0.5);  //order.getTotalWeightInGrams();
    	
        Mass mass1 = new Mass(500000);
        MockItem item1 = new MockItem(mass1); 
        mockScale.addAnItem(item1); // scale.getCurrentMassOnTheScale() = 500 000 mcg
        assertFalse(weightDiscrepancy.checkWeightChange());    
//        assertEquals(mockScale.getCurrentMassOnTheScale(), mockOrder.getTotalWeightInGrams());
        
    } 


    
}