package com.thelocalmarketplace.hardware.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.ComponentFailure;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenser;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

public class CoinDispenserTest {
	
	private CoinDispenserObserverStub observerStub;
    private CoinDispenser coinDispenser;
    private PowerGrid powerGrid;
    private Coin coin1;
    private Coin coin2;
    private Coin coin3;
    private Coin coin4;
	
	@Before
	public void setup() {
		observerStub = new CoinDispenserObserverStub();
        coinDispenser = new CoinDispenser(3); // Create a CoinDispenser with a capacity of 3
        powerGrid = PowerGrid.instance();
        PowerGrid.engageUninterruptiblePowerSource();
	    PowerGrid.instance().forcePowerRestore();
        Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
        
        // Test data
    	BigDecimal number = new BigDecimal("3.0");
        coin1 = new Coin(number);
        coin2 = new Coin(number);
        coin3  = new Coin(number);
    	coin4  = new Coin(number);
    	
        coinDispenser.attach(observerStub);
        coinDispenser.attach(new CoinDispenserObserverStub());
        
	}

    @Test(expected = NoPowerException.class)
    public void testReceiveWithNoPowerAndDeactivatedAndEnabled() throws CashOverloadException, DisabledException {
		coinDispenser.receive(coin1); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testReceiveWithPowerAndDeactivatedAndEnabled() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	
		coinDispenser.receive(coin1); // Test receiving a coin
    }

    @Test
    public void testReceiveWithPowerAndActivatedAndEnabled() throws DisabledException, CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();

		coinDispenser.receive(coin1); // Test receiving a coin
        assertEquals(1, coinDispenser.size()); // Assert that the size of the dispenser is 1 after receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testReceiveWithNoPowerAndActivatedAndEnabled() throws DisabledException, CashOverloadException {
    	//activate the component
    	coinDispenser.activate();

		coinDispenser.receive(coin1); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testReceiveWithNoPowerAndDeactivatedAndDisabled() throws CashOverloadException, DisabledException {
    	//disable
    	coinDispenser.disable();
    	
    	coinDispenser.receive(coin1); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testReceiveWithPowerAndDeactivatedAndDisabled() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//disable
    	coinDispenser.disable();
    	
		coinDispenser.receive(coin1); // Test receiving a coin
    }

    @Test(expected = DisabledException.class)
    public void testReceiveWithPowerAndActivatedAndDisabled() throws DisabledException, CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	//disable
    	coinDispenser.disable();

		coinDispenser.receive(coin1); // Test receiving a coin
        assertEquals(1, coinDispenser.size()); // Assert that the size of the dispenser is 1 after receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testReceiveWithNoPowerAndActivatedAndDisabled() throws DisabledException, CashOverloadException {
    	//activate the component
    	coinDispenser.activate();
    	//disable
    	coinDispenser.disable();
    	
		coinDispenser.receive(coin1); // Test receiving a coin
    }

    @Test(expected = NullPointerSimulationException.class)
    public void testReceiveWithCorruptedCoin() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
		coinDispenser.receive(null); // Test receiving a coin
    }
    
    @Test(expected = CashOverloadException.class)
    public void testReceiveOverCapacity() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
  
		coinDispenser.receive(coin1);
		coinDispenser.receive(coin2);
		coinDispenser.receive(coin3); 
		coinDispenser.receive(coin4); // limit is 3, this should overflow
    }
    
    @Test(expected = NoPowerException.class)
    public void testHasSpaceWithNoPower() {
        coinDispenser.hasSpace(); // Test if the dispenser has space initially
    }
    
    @Test
    public void testHasSpaceWithPower() {
    	coinDispenser.activate();
        assertTrue(coinDispenser.hasSpace()); // Test if the dispenser has space initially
    }
    
  	@Test
    public void testLoadWithPowerAndActivated() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
        coinDispenser.load(coin1, coin2); // Test loading coins
        
        // Assert that the size of the dispenser is 2 after loading coins
        assertEquals(2, coinDispenser.size());
    }
  	
  	@Test(expected = CashOverloadException.class)
    public void testLoadWithPowerAndActivatedOverCapacity() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
        coinDispenser.load(coin1, coin2, coin3, coin4); // Test loading coins
    }
    
    @Test(expected = NullPointerSimulationException.class)
    public void testLoadWithPowerAndActivatedAndCorruptedCoin() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
        coinDispenser.load(coin1, null); // Test loading coins
    }
    
    @Test(expected = NoPowerException.class)
    public void testLoadWithNoPowerAndActivated() throws CashOverloadException {
    	//activate the component
    	coinDispenser.activate();

        coinDispenser.load(coin1, coin2); // Test loading coins
    }
    
    @Test(expected = NoPowerException.class)
    public void testLoadWithPowerAndNotActivated() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	
        coinDispenser.load(coin1, coin2); // Test loading coins
    }
    
    @Test(expected = NoPowerException.class)
    public void testLoadWithNoPowerAndNotActivated() throws CashOverloadException {
        coinDispenser.load(coin1, coin2); // Test loading coins
    }
    
    
    @Test
    public void testUnloadWithPowerAndActivated() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	        
        coinDispenser.load(coin1, coin2); // Load coins for testing unloading
        List<Coin> result = coinDispenser.unload();
        assertEquals(Arrays.asList(coin1, coin2), result); // Assert that unloading returns the correct list of coins
    }
    
    @Test(expected = NoPowerException.class)
    public void testUnloadWithNoPowerAndActivated() throws CashOverloadException {
    	//activate the component
    	coinDispenser.activate();
    	
        List<Coin> result = coinDispenser.unload();
    }
    
    @Test(expected = NoPowerException.class)
    public void testUnloadWithPowerAndNotActivated() throws CashOverloadException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);

        List<Coin> result = coinDispenser.unload();
    }
    
    @Test(expected = NoPowerException.class)
    public void testUnloadWithNoPowerAndNotActivated() throws CashOverloadException {
        List<Coin> result = coinDispenser.unload();
    }
////////////////////////////////////
    
    @Test
    public void testEmitWithPowerAndActivatedAndEnabled() throws DisabledException, CashOverloadException, NoCashAvailableException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	coinDispenser.sink = new SinkStub();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
        assertEquals(1, coinDispenser.size()); // Assert that the size of the dispenser is 1 after receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithNoPowerAndActivatedAndEnabled() throws DisabledException, CashOverloadException, NoCashAvailableException {
    	//activate the component
    	coinDispenser.activate();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithPowerAndDeactivatedAndEnabled() throws CashOverloadException, DisabledException, NoCashAvailableException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component for load 
    	coinDispenser.activate();
    	coinDispenser.sink = new SinkStub();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
    	//activate the component
    	coinDispenser.disactivate();
    	
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithNoPowerAndDeactivatedAndEnabled() throws DisabledException, CashOverloadException, NoCashAvailableException {
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithPowerAndDeactivatedAndDisabled() throws CashOverloadException, DisabledException, NoCashAvailableException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//disable
    	coinDispenser.disable();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = DisabledException.class)
    public void testEmitWithPowerAndActivatedAndDisabled() throws CashOverloadException, DisabledException, NoCashAvailableException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	//disable
    	coinDispenser.disable();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithNoPowerAndDeactivatedAndDisabled() throws CashOverloadException, DisabledException, NoCashAvailableException {
    	//disable
    	coinDispenser.disable();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testEmitWithNoPowerAndActivatedAndDisabled() throws CashOverloadException, DisabledException, NoCashAvailableException { 
    	//activate the component
    	coinDispenser.activate();
    	//disable
    	coinDispenser.disable();
    	
    	coinDispenser.load(coin1, coin2); //load to prepare
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test(expected = NoCashAvailableException.class)
    public void testEmitWhenActivatedAndNoCoins() throws CashOverloadException, DisabledException, NoCashAvailableException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid); 
    	//activate the component
    	coinDispenser.activate();
    	
		coinDispenser.emit(); // Test receiving a coin
    }
    
    @Test
    public void testEmitNotifyCoinsEmpty() throws DisabledException, CashOverloadException, NoCashAvailableException {
    	coinDispenser.connect(powerGrid);
    	coinDispenser.activate();
    	coinDispenser.emit();
		Assert.assertTrue(observerStub.isCoinsEmpty);
	}
    
///////////////////////////////////////////
    @Test(expected = InvalidArgumentSimulationException.class)
    public void testCoinDispenserContructorWithBadCapacity() {
    	CoinDispenser bad = new CoinDispenser(-5);
    }
    
    @Test(expected = NoPowerException.class)
    public void testSizeWithNoPowerAndActivated() {
    	//activate the component
    	coinDispenser.activate();
    	coinDispenser.size();
    }
    
    @Test
    public void testSizeWithPowerAndActivated() {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	assertEquals(0, coinDispenser.size());
    }
    
    @Test(expected = NoPowerException.class)
    public void testSizeWithNoPowerAndDeactivated() {
    	coinDispenser.size();
    }
    
    @Test(expected = NoPowerException.class)
    public void testSizeWithPowerAndDeactivated() {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	coinDispenser.size();
    }
    
    @Test
    public void testGetCapacity() {
    	assertEquals(3, coinDispenser.getCapacity());
    }
    
    ////////////////////////////
    
    @Test(expected = NoPowerException.class)
    public void testRejectWithNoPowerAndActivated() throws CashOverloadException, DisabledException {
    	//activate the component
    	coinDispenser.activate();
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testRejectWithNoPowerAndDeactivated() throws CashOverloadException, DisabledException {
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testRejectWithPowerAndDeactivated() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = ComponentFailure.class)
    public void testRejectWithPowerAndActivated() throws CashOverloadException, DisabledException {
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    ///////
    
    @Test(expected = NoPowerException.class)
    public void testRejectAbstractWithNoPowerAndActivated() throws CashOverloadException, DisabledException {
    	AbstractCoinDispenserStub coinDispenser = new AbstractCoinDispenserStub(3); 
    	//activate the component
    	coinDispenser.activate();
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testRejectAbstractWithNoPowerAndDeactivated() throws CashOverloadException, DisabledException {
    	AbstractCoinDispenserStub coinDispenser = new AbstractCoinDispenserStub(3); 
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = NoPowerException.class)
    public void testRejectAbstractWithPowerAndDeactivated() throws CashOverloadException, DisabledException {
    	AbstractCoinDispenserStub coinDispenser = new AbstractCoinDispenserStub(3); 
    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
    
    @Test(expected = ComponentFailure.class)
    public void testRejectAbstractWithPowerAndActivated() throws CashOverloadException, DisabledException {
    	AbstractCoinDispenserStub coinDispenser = new AbstractCoinDispenserStub(3); 

    	// as mentioned in the documentation of receive, the function requires power
    	coinDispenser.connect(powerGrid);
    	//activate the component
    	coinDispenser.activate();
    	
    	// Stub a PassiveSource for testing
        PassiveSourceStub source = new PassiveSourceStub();
        coinDispenser.source = source;
        
        coinDispenser.reject(coin1); // Test rejecting a coin
    }
}

