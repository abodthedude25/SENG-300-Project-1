package com.thelocalmarketplace.software.test;
/*
 * Mahfuz Alam : 30142265
 */

import static org.junit.Assert.*;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.PaymentHandler;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserBronze;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;



public class PaymentHandlerTest {

    private PaymentHandler paymentHandler;
    //private SelfCheckoutStation checkoutStation;
    private ArrayList<Product> coinsList;
    private ArrayList<Product> allProducts;
    private Coin coin1, coin2;
    private BigDecimal totalCost;

    @Before
    public void setUp() throws Exception {
        // Mock SelfCheckoutStation and its components as needed
     
    	SelfCheckoutStation checkoutStation = new SelfCheckoutStation();
    	
        Numeral[] codeDigits = new Numeral[4];
        
        // Split the number 1234 into its individual digits and create Numeral instances
        codeDigits[0] = Numeral.one; // Assuming these are defined in Numeral
        codeDigits[1] = Numeral.two;
        codeDigits[2] = Numeral.three;
        codeDigits[3] = Numeral.four;
        
        // Create the Barcode object
        Barcode bananaBarcode = new Barcode(codeDigits);
    	
    	BarcodedProduct banana = new BarcodedProduct(bananaBarcode, "bannana", 12,10.00);
    	
    	
    	ArrayList<Product> allProducts = new ArrayList<Product>();
    	allProducts.add(banana);

    	
        paymentHandler = new PaymentHandler(checkoutStation, allProducts);
        
        
        
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
    public void processPaymentWithCoinsTestWithOverpayment() throws Exception {
        // Simulate sufficient payment
    	
    	ArrayList<Coin> coinsList = new ArrayList<Coin>();
    	Currency currency = Currency.getInstance("CAD");
    	
    	coin1 = new Coin(currency,new BigDecimal("10.00"));
        coin2 = new Coin(currency,new BigDecimal("12.00"));

        coinsList.add(coin1);
        coinsList.add(coin2);
    	
        //coinsList.remove(coin1); // Remove 1 dollar coin, leaving only 25 cents
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
        
        
    	
        //coinsList.remove(coin1); // Remove 1 dollar coin, leaving only 25 cents
        assertFalse(paymentHandler.processPaymentWithCoins(coinsList));
    }
    
    
    @Test
    public void testProcessPaymentWithExactAmount() throws Exception {
        ArrayList<Coin> coinsList = new ArrayList<>();
        coinsList.add(new Coin(new BigDecimal("10.00"))); // Adding a coin of $10
        assertTrue("Payment should succeed with exact amount", paymentHandler.processPaymentWithCoins(coinsList));
    }

    @Test
    public void testProcessPaymentWithOverpayment() throws Exception {
        ArrayList<Coin> coinsList = new ArrayList<>();
        coinsList.add(new Coin(new BigDecimal("15.00"))); // Overpaying by $5
        assertTrue("Payment should succeed and dispense change", paymentHandler.processPaymentWithCoins(coinsList));
    }

    @Test
    public void testProcessPaymentWithUnderpayment() throws Exception {
        ArrayList<Coin> coinsList = new ArrayList<>();
        coinsList.add(new Coin(new BigDecimal("5.00"))); // Underpaying by $5
        assertFalse("Payment should fail with underpayment", paymentHandler.processPaymentWithCoins(coinsList));
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

    @Test
    public void loadCoinDispenser_ValidCoins_NoExceptionThrown() throws Exception {
        // Load coins into the dispenser
        paymentHandler.loadCoinDispenser(coin1, coin2);
        // No specific assertion needed, just verifying no exception is thrown
    }

    @Test
    public void emptyCoinStorage_Successful() {
        // Simply calling the method to ensure no exceptions are thrown
        paymentHandler.emptyCoinStorage();
        // No exceptions expected, so no specific assertions are necessary
    }
}

/// tthis is a comment 
