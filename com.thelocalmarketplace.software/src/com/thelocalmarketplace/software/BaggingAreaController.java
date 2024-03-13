// Yuinikoru Futamata UCID:30173228
package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

/**
 * Represents a BaggingAreaContoller. Controls the bagging area scale of a self checkout station.
 */
public class BaggingAreaController {

	SelfCheckoutStation selfCheckoutStation;
	BaggingAreaListener listener;

	/**
	 * Constructor for the BagginAreaController
	 * 
	 * @param station
	 *              The self checkout station
	 */
	public void BaggingAreaContoller(SelfCheckoutStation station) {
		this.selfCheckoutStation = station;
	}

	/**
	 * Register the bagging area listener
	 */
	public void registerListener() {
		listener = new BaggingAreaListener();
		selfCheckoutStation.baggingArea.register(listener);
	}

	/**
	 * De-register the bagging area listener
	 */
	public void deregisterListener() {
		selfCheckoutStation.baggingArea.deregister(listener);
	}

	/**
	 * This is a listener for the bagging area scale
	 */
	public class BaggingAreaListener implements ElectronicScaleListener {
		// This is a listener for the bagging area

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
			// This will be for the use case Add item by Barcode Scan

			// Scenario:
			// 6. Signals to the system that the weight has changed

			// temporary print statement, will later be made to signal to the system the weight has changed
			System.out.println("Weight changed in the bagging area: " + mass);

		}
		@Override
		public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
			// TODO Auto-generated method stub

		}
		@Override
		public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
			// TODO Auto-generated method stub

		}
	}
}