
/*
 * Mahfuz Alam : 30142265
 * Lilia Skumatova : 30187339
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.PaymentHandler;
import com.thelocalmarketplace.software.outOfInkException;
import com.thelocalmarketplace.software.outOfPaperException;
import com.thelocalmarketplace.software.test.emptyProdcutStub;

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
    	checkoutStation = new SelfCheckoutStation();
    	
        Numeral[] codeDigits = new Numeral[4];
        
        // Split the number 1234 into its individual digits and create Numeral instances
        codeDigits[0] = Numeral.one; // Assuming these are defined in Numeral
        codeDigits[1] = Numeral.two;
        codeDigits[2] = Numeral.three;
        codeDigits[3] = Numeral.four;
        
        // Create the Barcode object
        Barcode bananaBarcode = new Barcode(codeDigits);
    	
    	BarcodedProduct banana = new BarcodedProduct(bananaBarcode, "bannana", 12, (long) 10.00);
    	
    	// Add some product for testing
    	allProducts = new ArrayList<Product>();
    	allProducts.add(banana);
         
        paymentHandler = new PaymentHandler(checkoutStation, allProducts);
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
    
    @Test(expected = outOfPaperException.class)
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
    
    @Test(expected = outOfInkException.class)
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

     /**
     * Checks if coins are actually loaded in the coin dispenser
     * @throws CashOverloadException
     */
    @Test
    public void testLoadCoinDispenser() throws CashOverloadException {
        Currency currency = Currency.getInstance("CAD");

        coin1 = new Coin(currency, new BigDecimal("1.00"));
        coin2 = new Coin(currency, new BigDecimal("2.00"));

        try {
            paymentHandler.loadCoinDispenser(coin1, coin2);
            assertTrue(paymentHandler.coinDispensers.get(coin1).size() > 0);
            
        } catch (Exception e) {
            System.err.println("Test failed: Exception occurred - " + e.getMessage());
           
        }
    }

    /**
     * Test for CashOverloadException
     * @throws CashOverloadException
     */
    @Test (expected = SimulationException.class)
    public void testLoadCoinDispenserOverload() throws CashOverloadException {
    	try{
    		int capacity = paymentHandler.coinDispensers.get(new BigDecimal(0.10)).getCapacity();
        	for(int i = 0; i < capacity + 5; i++) {
        		Coin c = new Coin(Currency.getInstance("CAD"), new BigDecimal(0.10));
        		paymentHandler.loadCoinDispenser(c);
        	}
        	fail("Exception not thrown");
    	}catch (UnsupportedOperationException e) {
    		assertEquals("Operation not Supported", e.getMessage());
    	}
    	
    }


     /**
     * Test that loadCoinDispenser throws NullException when coin is null
     */
    @Test
    public void testLoadCoinDispenserNullCoins() {
        assertThrows(NullPointerException.class, () -> paymentHandler.loadCoinDispenser(null));
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
        paymentHandler.emptyCoinStorage();
        assertTrue(paymentHandler.coinStorage.getCoinCount() == 0);
    }
    
    
    
}

/// tthis is a comment 
