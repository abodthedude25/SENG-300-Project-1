package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
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
	
	
	class MockItem extends Item {
        public MockItem(Mass mass) {
            super(mass);
        }
    }
	
}

