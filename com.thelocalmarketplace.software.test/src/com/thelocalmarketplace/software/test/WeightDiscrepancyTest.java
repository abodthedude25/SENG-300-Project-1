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
    public void foo() {
    	
    }
  
    
    
    

    
    
    
    
  
}