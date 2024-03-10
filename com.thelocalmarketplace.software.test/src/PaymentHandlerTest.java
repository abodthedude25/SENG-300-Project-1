/*
 * Mahfuz Alam : 30142265
 */

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.PaymentHandler;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.tdc.hardware.CoinDispenser;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentHandlerTest {

    private PaymentHandler paymentHandler;
    private SelfCheckoutStation checkoutStation;
    private ArrayList<Coin> coinsList;
    private Coin coin1, coin2;
    private BigDecimal totalCost;

    @Before
    public void setUp() throws Exception {
        // Mock SelfCheckoutStation and its components as needed
        checkoutStation = mock(SelfCheckoutStation.class);
        coinsList = new ArrayList<>();

        // Assuming coin denomination values for simplicity
        coin1 = new Coin(new BigDecimal("1.00"));
        coin2 = new Coin(new BigDecimal("0.25"));

        coinsList.add(coin1);
        coinsList.add(coin2);

        // Setup for totalCost and changeRemaining logic
        totalCost = new BigDecimal("1.00");

        // Mocking the necessary parts of SelfCheckoutStation
        HashMap<BigDecimal, CoinDispenser> coinDispensers = new HashMap<>();
        coinDispensers.put(new BigDecimal("1.00"), mock(CoinDispenser.class));
        coinDispensers.put(new BigDecimal("0.25"), mock(CoinDispenser.class));
        when(checkoutStation.coinDispensers).thenReturn(coinDispensers);

        List<BigDecimal> coinDenominations = new ArrayList<>();
        coinDenominations.add(new BigDecimal("1.00"));
        coinDenominations.add(new BigDecimal("0.25"));
        when(checkoutStation.coinDenominations).thenReturn(coinDenominations);

        paymentHandler = new PaymentHandler(checkoutStation, new ArrayList<>());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_NullStation_ThrowsException() {
        new PaymentHandler(null, new ArrayList<>());
    }

    @Test
    public void processPaymentWithCoins_ExactAmount_ReturnsTrue() throws Exception {
        // Simulate exact payment
        assertTrue(paymentHandler.processPaymentWithCoins(coinsList));
    }

    @Test
    public void processPaymentWithCoins_InsufficientAmount_ReturnsFalse() throws Exception {
        // Simulate insufficient payment
        coinsList.remove(coin1); // Remove 1 dollar coin, leaving only 25 cents
        assertFalse(paymentHandler.processPaymentWithCoins(coinsList));
    }

    @Test
    public void dispenseAccurateChange_Overpayment_EmitsChange() throws Exception {
        // Overpay and expect change
        coinsList.add(coin1); // Add another 1 dollar coin to simulate overpayment
        assertTrue(paymentHandler.processPaymentWithCoins(coinsList));
        verify(checkoutStation.coinDispensers.get(new BigDecimal("1.00")), times(1)).emit();
    }

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
