package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
//import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
//import com.thelocalmarketplace.software.test.WeightDiscrepancyTest.MockItem;

public class AddOwnBag {

	// allow customer to add thier own bags to bagging area without causing a weight discrepancy
	
	
	// 1. customer decides to make use of thier own bags, activating an appropriate control to thier station
			// some sort of button, add listener 
	
	
	
	private ElectronicScaleListener electronicScaleListener;
	

	
	public AddOwnBag() {
		electronicScaleListener = new ElectronicScaleListener () {

			@Override
			public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
				// TODO Auto-generated method stub
				
			}
			
		};	
	}
	
	// now that customer has signaled they want to add thier own bags, pass in the weight of thier own bags
	public void ownbagweight(AbstractElectronicScale scale, Item weight_of_bag) throws OverloadedDevice {
		Mass prev_mass= scale.getCurrentMassOnTheScale();
		 
		scale.addAnItem(weight_of_bag);
		Mass after_mass = scale.getCurrentMassOnTheScale();	
		
		int result = after_mass.compareTo(prev_mass);
		
		
		if(result == 1) { // there is a weight descripancy
			
			SelfCheckoutStationSoftware.setStationBlock(false); // change to unblock and continue 
		
		}
	}
		
		
		
	
}