/**
 * Yotam Rojnov (UCID: 30173949)
 * Duncan McKay (UCID: 30177857)
 * Mahfuz Alam (UCID:30142265)
 * Luis Trigueros Granillo (UCID: 30167989)
 * Lilia Skumatova (UCID: 30187339)
 * Abdelrahman Abbas (UCID: 30110374)
 * Talaal Irtija (UCID: 30169780)
 * Alejandro Cardona (UCID: 30178941)
 * Alexandre Duteau (UCID: 30192082)
 * Grace Johnson (UCID: 30149693)
 * Abil Momin (UCID: 30154771)
 * Tara Ghasemi M. Rad (UCID: 30171212)
 * Izabella Mawani (UCID: 30179738)
 * Binish Khalid (UCID: 30061367)
 * Fatima Khalid (UCID: 30140757)
 * Lucas Kasdorf (UCID: 30173922)
 * Emily Garcia-Volk (UCID: 30140791)
 * Yuinikoru Futamata (UCID: 30173228)
 * Joseph Tandyo (UCID: 30182561)
 * Syed Haider (UCID: 30143096)
 * Nami Marwah (UCID: 30178528)
 */

package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.PaymentHandler;
import com.thelocalmarketplace.software.OutOfInkException;
import com.thelocalmarketplace.software.OutOfPaperException;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

public class PaymentHandlerTest {
	private SelfCheckoutStation checkoutStation;
    private ArrayList<Product> coinsList;
    private ArrayList<Product> allProducts;
    private Coin coin1, coin2;
    private BigDecimal totalCost;
    private PaymentHandler paymentHandler;

    @Before
    public void setUp() {
        // Mock SelfCheckoutStation and its components as needed
    	BigDecimal[] denominations = {new BigDecimal("0.25"),new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("4")};
    	SelfCheckoutStation.configureCoinDenominations(denominations);
    	checkoutStation = new SelfCheckoutStation();
    	

    			
    			
        Numeral[] codeDigits = new Numeral[4];
        
        // Split the number 1234 into its individual digits and create Numeral instances
        codeDigits[0] = Numeral.one; // Assuming these are defined in Numeral
        codeDigits[1] = Numeral.two;
        codeDigits[2] = Numeral.three;
        codeDigits[3] = Numeral.four;
        
        // Create the Barcode object
        Barcode bananaBarcode = new Barcode(codeDigits);
    	
    	BarcodedProduct banana = new BarcodedProduct(bananaBarcode, "bannana", 12, 10.00);
    	
    	// Add some product for testing
    	allProducts = new ArrayList<Product>();
    	allProducts.add(banana);
         
        paymentHandler = new PaymentHandler(checkoutStation, allProducts);

	paymentHandler.coinStorage.connect(PowerGrid.instance());
        paymentHandler.coinStorage.activate();
	PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();
    }
   
    
    
    
    @Test
    public void testReceiptPrinter() throws Exception{
        // Mocking System.out for testing output 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Add some product for testing
        allProducts.add(new PLUCodedProduct(new PriceLookUpCode("0001"), "Product B", (long) 15.00));
        paymentHandler = new PaymentHandler(checkoutStation, allProducts);
        paymentHandler.amountSpent = BigDecimal.valueOf(27); // Set amount spent for testing
        paymentHandler.changeRemaining = BigDecimal.valueOf(0); // Set change remaining for testing

        paymentHandler.receiptPrinter();
        
        // Check if the receipt contains correct information
        assertTrue(outContent.toString().contains("Product B $15.00"));
        assertTrue(outContent.toString().contains("Total: $27.00"));
        assertTrue(outContent.toString().contains("Paid: $27.00"));
        assertTrue(outContent.toString().contains("Change: $0.00"));

        allProducts.remove(1);
        // Reset System.out
        System.setOut(System.out);
    }

    
    
    
    @Test(expected = NullPointerException.class)
    public void testReceiptPrinterIncorrectProduct() throws Exception{
        // Mocking System.out for testing output 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Add some product for testing
        allProducts.add(new emptyProdcutStub( (long) 4.00, false) );
        paymentHandler = new PaymentHandler(checkoutStation, allProducts);

        paymentHandler.receiptPrinter();
	    assertTrue(outContent.toString().contains("This product is not a supported product, can not be registered for a price"));

        // Reset System.out
        System.setOut(System.out);
    }
    
    @Test(expected = OutOfPaperException.class)
    public void testReceiptPrinterOutOfPaperException() throws Exception{
    	// Mocking System.out for testing output 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        paymentHandler.paperSpaceCounter = 0;
        paymentHandler.inkCounter = 10;

	    // Check if the out of ink exception is thrown
	    paymentHandler.receiptPrinter(); // Should throw outOfInkException
	    assertTrue(outContent.toString().contains("The printer is out of Paper currently, needs maintenance."));
	    
	    // Reset System.out
	    System.setOut(System.out);
    }
    
    @Test(expected = OutOfInkException.class)
    public void testReceiptPrinterOutOfInkException() throws Exception{
    	// Mocking System.out for testing output 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        paymentHandler.inkCounter = 0;
        paymentHandler.paperSpaceCounter = 100;

	    // Check if the out of ink exception is thrown
	    paymentHandler.receiptPrinter(); // Should throw outOfInkException
	    assertTrue(outContent.toString().contains("The printer is out of Ink currently, needs maintenance."));
	    
	    // Reset System.out
	    System.setOut(System.out);
    }
 
    @Test(expected = NullPointerException.class)
    public void constructor_NullStation_ThrowsException() {
        new PaymentHandler(null, new ArrayList<>());
    }

    @Test
    public void getChangeRemiaingTest() throws Exception {
        // Simulate exact payment
        assertEquals(paymentHandler.getChangeRemaining(), BigDecimal.ZERO);
    }
    
    
    
    
    @Test
    public void processPaymentWithCoinsTestWithOverpayment() throws Exception { //there is a probelm here 
        // Simulate sufficient payment
    	
    	ArrayList<Coin> coinsList = new ArrayList<Coin>();
    	Currency currency = Currency.getInstance("CAD");
    	
    	coin1 = new Coin(currency,new BigDecimal("10.00"));
        coin2 = new Coin(currency,new BigDecimal("12.00"));

        coinsList.add(coin1);
        coinsList.add(coin2);
        
        assertTrue(paymentHandler.processPaymentWithCoins(coinsList));
    }
    
    

    
    
    @Test
    public void processPaymentWithCoinsTestWithUnderpayment() throws Exception {
        // Simulate insufficient payment
    	
    	ArrayList<Coin> coinsList = new ArrayList<Coin>();
    	Currency currency = Currency.getInstance("CAD");
    	
    	coin1 = new Coin(currency,new BigDecimal("1.00"));
        coin2 = new Coin(currency,new BigDecimal("2.00"));

        coinsList.add(coin1);
        coinsList.add(coin2);
        
        
        assertFalse(paymentHandler.processPaymentWithCoins(coinsList));
    }
    
    
    @Test
    public void testProcessPaymentWithExactAmount() throws Exception {
    	ArrayList<Coin> coinsList = new ArrayList<Coin>();
    	Currency currency = Currency.getInstance("CAD");
    	
    	coin1 = new Coin(currency,new BigDecimal("10.00"));
        coin2 = new Coin(currency,new BigDecimal("2.00"));

        coinsList.add(coin1);
        coinsList.add(coin2);
        assertTrue("Payment should succeed with exact amount", paymentHandler.processPaymentWithCoins(coinsList));
    }

  


    @Test(expected = NullPointerException.class)
    public void testProcessPaymentWithNullCoinsList() throws Exception {
        paymentHandler.processPaymentWithCoins(null); // This should throw NullPointerException
    }
   // @Test
//    public void dispenseAccurateChange_Overpayment_EmitsChange() throws Exception {
//        // Overpay and expect change
//        coinsList.add(coin1); // Add another 1 dollar coin to simulate overpayment
//        assertTrue(paymentHandler.processPaymentWithCoins(coinsList));
//        verify(checkoutStation.coinDispensers.get(new BigDecimal("1.00")), times(1)).emit();
//    }

	
   /**
     * Checks if coins are actually loaded in the coin dispenser
     * @throws CashOverloadException
     */
    @Test
    public void testLoadCoinDispenser() throws CashOverloadException {
        Currency currency = Currency.getInstance("CAD");

        coin1 = new Coin(currency, new BigDecimal("1.00"));
        coin2 = new Coin(currency, new BigDecimal("2.00"));

        paymentHandler.loadCoinDispenser(coin1, coin2);
        assertTrue(paymentHandler.coinDispensers.containsValue(coin1));
        assertTrue(paymentHandler.coinDispensers.containsValue(coin2));

    }
    
    
    
    
    /**
     * Test for CashOverloadException
     * @throws CashOverloadException
     */
    @Test
    public void testLoadCoinDispenserOverload() throws CashOverloadException {
    	boolean thrown = false;
    	Currency currency = Currency.getInstance("CAD");
        coin1 = new Coin(currency, new BigDecimal("1.00"));
        int capacity = 2;
    	paymentHandler.configureCoinDispenserCapacity(capacity);
    	
    	try{
        	for(int i = 0; i < capacity + 5; i++) {
        		Coin c = new Coin(Currency.getInstance("CAD"), new BigDecimal(0.10));
        		paymentHandler.loadCoinDispenser(c);
        	}
    	}catch (CashOverloadException e) {
    		thrown = true;
    	}
    	
    	assertTrue(thrown);
    }
    
    
    /**
     * Test that the coin storage is empty
     * @throws SimulationException
     * @throws CashOverloadException
     */
    @Test
    public void testEmptyCoinStorage() throws SimulationException, CashOverloadException {
        // Add coins to the coin storage unit before calling emptyCoinStorage()
    	Currency currency = Currency.getInstance("CAD");
        coin1 = new Coin(currency, new BigDecimal("1.00"));
        paymentHandler.coinStorage.load(coin1);
        paymentHandler.coinStorage.unload();
    	
    }

    @Test (expected = NullPointerSimulationException.class)
    public void loadCoinDispenserTestWithNull() throws NullPointerSimulationException, CashOverloadException {
        // Add coins to the coin storage unit before calling emptyCoinStorage()
     
        paymentHandler.loadCoinDispenser(null);
   
    	
    }
    
    @Test (expected = NullPointerSimulationException.class)
    public void loadCoinDispenserTestWithNullCoin() throws NullPointerSimulationException, CashOverloadException {
        // Add coins to the coin storage unit before calling emptyCoinStorage()
    	Currency currency = Currency.getInstance("CAD");
        coin1 = new Coin(currency, new BigDecimal("1.00"));
        coin2 = new Coin(currency, new BigDecimal("2.00"));
        Coin coin3 = null;
        paymentHandler.loadCoinDispenser(coin3,coin1,coin2);
    }
   
    @Test 
    public void loadCoinDispenserTest() throws NullPointerSimulationException, CashOverloadException {
        // Add coins to the coin storage unit before calling emptyCoinStorage()
    	Currency currency = Currency.getInstance("CAD");
        coin1 = new Coin(currency, new BigDecimal("1.00"));
        coin2 = new Coin(currency, new BigDecimal("2.00"));
        checkoutStation.plugIn(PowerGrid.instance());
        checkoutStation.turnOn();
        
        paymentHandler.loadCoinDispenser(coin1,coin2);
    }

	
    
}
