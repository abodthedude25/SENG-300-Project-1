package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.WeightDiscrepancy;



public class WeightDiscrepancyTest {

	private WeightDiscrepancy weightDiscrepancy;
	private mockOrder mockOrder;
	private mockScale mockScale;
	
	@Before
	public void setUp() throws OverloadedDevice {
        mockOrder = new MockOrder();
        mockScale = new MockElectronicScale();
        weightDiscrepancy = new WeightDiscrepancy(mockOrder, mockScale);
        testsoftware = new SelfCheckoutStationSoftware();
        orderr = new Order();

    }
	
	
	class MockItem extends Item {
        public MockItem(Mass mass) {
            super(mass);
        }
    }
	
}

