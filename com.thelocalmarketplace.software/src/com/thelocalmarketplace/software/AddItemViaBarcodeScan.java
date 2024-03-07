package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;

public class AddItemViaBarcodeScan implements BarcodeScannerListener {
	// THIS IS A LISTENER, basically when the scan event happens, it calls the
	// Order.java class' method to addItemViaBarcodeScan();
	
	private Order order;
	private SelfCheckoutStationSoftware software;
	
	/**
	 * Constructor for order
	 */
	public AddItemViaBarcodeScan(Order order) {
		this.order = order;
	}
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
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		// this is the method where we should call the method in Order.java addItemViaBarcodeScan()
		// and do all the checks or whatever
		
		// detects barcode
		if(!software.getStationBlock()) {
			// block it?
			software.setStationBlock(true);
		};
		
		// do the other stuff
		
		if(software.getStationBlock()) {
			software.setStationBlock(false);
		}
	}
}