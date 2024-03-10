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

	/**
	 * When a barcode is scanned, the item is added to the order
	 * @param barcodeScanner
	 * @param barcode
	 */
	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		// if the software is not blocked, block it.
		if(!software.isBlocked()) {
			software.setStationBlock(true);
		}

		// add the item to the order, the software will be blocked at this point
		order.addItemViaBarcodeScan(barcode);

		// unblock the software
		software.setStationBlock(false);
	}
}