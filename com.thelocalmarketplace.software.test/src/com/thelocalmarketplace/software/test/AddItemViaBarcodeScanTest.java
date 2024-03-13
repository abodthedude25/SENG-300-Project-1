// Joseph Tandyo
// Alexandre Duteau
// Syed Haider 
// Yuinikoru Futamata 30173228
// Alejandro Cardona 30178941

package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AddItemViaBarcodeScan;
import com.thelocalmarketplace.software.BaggingAreaListener;
import com.thelocalmarketplace.software.Order;
import powerutility.PowerGrid;

public class AddItemViaBarcodeScanTest {
	PowerGrid grid;
	BarcodeScanner scanner;
	ElectronicScale baggingArea;
	BarcodedItem barcodedItem;
	BarcodedProduct barcodedProduct;
	AddItemViaBarcodeScan testBarcodeItemAdder;
	Order testOrder;
	BaggingAreaListener testBaggingAreaListener;
	
	@Before
	public void setUp() {
		// Make a power grid for hardware to connect to
		grid = PowerGrid.instance();
		
		// Initializing hardware objects
		scanner = new BarcodeScanner();
		baggingArea = new ElectronicScale();
		
		// Power up and enable hardware objects
		scanner.plugIn(grid);
		scanner.turnOn();
		scanner.enable();
		baggingArea.plugIn(grid);
		baggingArea.turnOn();
		baggingArea.enable();
		
		// Initializing mock barcoded item
        Numeral[] barcodeDigits = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five};
        Barcode barcode = new Barcode(barcodeDigits);
		Mass itemMass = new Mass(1000000000); // 1kg in micrograms
		barcodedItem = new BarcodedItem(barcode, itemMass);
		
		// Initializing mock product (using same barcode as the barcoded item)
		String productDescription = "test product";
		long productPrice = 5;
		double productWeightInGrams = 1000;
		barcodedProduct = new BarcodedProduct(barcode, productDescription, productPrice, productWeightInGrams);
		
		// Adding mock product into product database
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, barcodedProduct);
		
		// Initializing testOrder
		testOrder = new Order();
		
		// Initializing testBarcodeItemAdder and making it listen to the scanner object
		testBarcodeItemAdder = new AddItemViaBarcodeScan(testOrder);
		scanner.register(testBarcodeItemAdder);
		
		// Initializing testBaggingAreaListener and making it listen to the scale object
		testBaggingAreaListener = new BaggingAreaListener();
		baggingArea.register(testBaggingAreaListener);
	}
	
	@Test
    public void testAddingItem() {
        baggingArea.addAnItem(barcodedItem);
    }
	
	@After
    public void tearDown() {
        // de-register listeners 
        scanner.deregister(testBarcodeItemAdder);
        baggingArea.deregister(testBaggingAreaListener);
        
        // Disable, turn off and un-plug hardware objects
        scanner.disable();
        scanner.turnOff();
        scanner.unplug();
        baggingArea.disable();
        baggingArea.turnOff();
        baggingArea.unplug();
    }
}