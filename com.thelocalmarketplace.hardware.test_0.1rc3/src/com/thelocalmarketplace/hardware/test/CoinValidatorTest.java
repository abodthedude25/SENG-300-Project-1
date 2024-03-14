package com.thelocalmarketplace.hardware.test;
import org.junit.Test;
import com.tdc.ComponentFailure;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.Before;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;
import com.tdc.coin.AbstractCoinValidator;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;


@SuppressWarnings("javadoc")
public class CoinValidatorTest {
	private Currency currency = Currency.getInstance(Locale.CANADA);
	private AbstractCoinValidator validator;
	
	@Before
	public void setup() {
	    validator = new CoinValidator(currency, Arrays.asList(BigDecimal.ONE));
	    validator.connect(PowerGrid.instance());
	    validator.activate();
	    PowerGrid.engageUninterruptiblePowerSource();
	    PowerGrid.instance().forcePowerRestore();
        Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
	}
	
	@Test(expected = SimulationException.class)
	public void testBadCreateWithNullDenomination() {
	    new CoinValidator(currency, Arrays.asList((BigDecimal)null));
	}
	
	@Test(expected = SimulationException.class)
   public void testCurrencyNull() {
       new CoinValidator(null, Arrays.asList(BigDecimal.ONE));
   }
	
   @Test(expected = SimulationException.class)
   public void testBadDenominationNull() {
       new CoinValidator(currency, null);
   }
   
   @Test(expected = SimulationException.class)
   public void testBadDenominationEmpty() {
       new CoinValidator(currency, Arrays.asList());
   }
   
   @Test(expected = SimulationException.class)
   public void testBadDenominationNegative() {
       new CoinValidator(currency, Arrays.asList(BigDecimal.valueOf(-1)));
   }
   
   @Test(expected = SimulationException.class)
   public void testBadDenominationRepeat() {
       new CoinValidator(currency, Arrays.asList(BigDecimal.ONE, BigDecimal.ONE));
   }
   
   //////////////////////
   
   @Test(expected = SimulationException.class)
   public void testSetupWithNullRejectionSink() {
       Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
       map.put(BigDecimal.ONE, new StandardSinkStub() {
       });
       validator.setup(null, map, null);
   }
   
	@Test(expected = NullPointerSimulationException.class)
	public void testSetupWithNullOverflowSink() {
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	    map.put(BigDecimal.ONE, new StandardSinkStub());
	    validator.setup(new StandardSinkStub(), map, null);
	}
   
	@Test(expected = SimulationException.class)
	public void testSetupWithMismatchingDenominationsWithSinks() {
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		map.put(BigDecimal.ONE, new StandardSinkStub());
	    map.put(BigDecimal.valueOf(2), new StandardSinkStub());
	    validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
	}
	
	@Test(expected = SimulationException.class)
	public void testSetupWithNullStandrdSinks() {
	    StandardSinkStub sink = new StandardSinkStub();
	    validator.setup(sink, null, sink);
	}
	
	@Test(expected = SimulationException.class)
	public void testSetupNullSink() {
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		map.put(BigDecimal.ONE, null);
	    validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
	}
	
	@Test
	public void testSetupCorrectly() {
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		map.put(BigDecimal.ONE, new StandardSinkStub());
	    validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
	    assertEquals(validator.standardSinks.size(), 1);
	    assertNotEquals(null, validator.rejectionSink);
	    assertNotEquals(null, validator.overflowSink);
	}

   @Test(expected = SimulationException.class)
	public void testSetupWithRepeatedSink() {
	    validator = new CoinValidator(currency, Arrays.asList(BigDecimal.ONE, BigDecimal.TEN));
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	    StandardSinkStub sink = new StandardSinkStub();
	    map.put(BigDecimal.ONE, sink);
	    map.put(BigDecimal.TEN, sink);
	    validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
	}

	@Test(expected = SimulationException.class)
	public void testSetupWithOverflowAndStanrdSinksOverlap() {
	    StandardSinkStub sink = new StandardSinkStub();
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	    map.put(BigDecimal.ONE, sink);
	    validator.setup(new StandardSinkStub(), map, sink);
	}
	@Test(expected = SimulationException.class)
	public void testSetupWithRejectAndStanrdSinksOverlap() {
		StandardSinkStub sink = new StandardSinkStub();
	    Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	    map.put(BigDecimal.ONE, sink);
	    validator.setup(sink, map, new StandardSinkStub());
	}	

	@Test(expected = NoPowerException.class)
   public void testRejectAbstractWithNoPowerAndActivated() throws CashOverloadException, DisabledException {
	   Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	   AbstractCoinValidatorStub validator = new AbstractCoinValidatorStub(currency, map); 
   	
   	   validator.activate();
   	
   	   Coin coin1 = new Coin(new BigDecimal("3.0"));
       validator.reject(coin1); // Test rejecting a coin
   }
   
   @Test(expected = NoPowerException.class)
   public void testRejectAbstractWithNoPowerAndDeactivated() throws CashOverloadException, DisabledException {
	   Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	   AbstractCoinValidatorStub validator = new AbstractCoinValidatorStub(currency, map); 

   	   Coin coin1 = new Coin(new BigDecimal("3.0"));
       validator.reject(coin1); // Test rejecting a coin
   }
   
   @Test(expected = NoPowerException.class)
   public void testRejectAbstractWithPowerAndDeactivated() throws CashOverloadException, DisabledException {
	   Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	   AbstractCoinValidatorStub validator = new AbstractCoinValidatorStub(currency, map); 
   	
	   validator.connect(PowerGrid.instance());
	   
	   Coin coin1 = new Coin(new BigDecimal("3.0"));
       validator.reject(coin1); // Test rejecting a coin
   }
   
   @Test(expected = ComponentFailure.class)
   public void testRejectAbstractWithPowerAndActivated() throws CashOverloadException, DisabledException {
	   Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
	   AbstractCoinValidatorStub validator = new AbstractCoinValidatorStub(currency, map); 
   	
	   validator.connect(PowerGrid.instance());
	   validator.activate();
	   
	   Coin coin1 = new Coin(new BigDecimal("3.0"));
       validator.reject(coin1); // Test rejecting a coin
   }
	
   
	@Test(expected = NoPowerException.class)
	public void testHasSpaceNoPowerAndActivated() {
		validator.disconnect();
		validator.hasSpace();
	}
	
	@Test(expected = NoPowerException.class)
	public void testHasSpacePowerAndNotActivated() {
		validator.disactivate();
		validator.hasSpace();
	}
	
	@Test(expected = NoPowerException.class)
	public void testHasSpaceNoPowerAndDisactivated() {
		validator.disconnect();
		validator.disactivate();
		validator.hasSpace();
	}
	@Test
	public void testHasSpacePowerAndActivated() {
		assertEquals(true, validator.hasSpace()); //told to assume it is true in docs
	}
	
	/////////////////////
	
	@Test
	public void testReceiveValidCoin() throws DisabledException, CashOverloadException {
		Coin validCoin = new Coin(currency, BigDecimal.ONE);
		Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		map.put(BigDecimal.ONE, new StandardSinkStub());
		validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		validator.receive(validCoin);
		// Assuming observer updates and coin routing are properly handled,
		// you can add assertions to check the behavior after receiving a valid coin.
		// For example, check if the appropriate observer method was called.
	}
	
	@Test(expected = NoPowerException.class)
	public void testReceiveNoPower() throws DisabledException, CashOverloadException {
		 validator.disconnect();
		 Coin validCoin = new Coin(currency, BigDecimal.ONE);
		 Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		 map.put(BigDecimal.ONE, new StandardSinkStub());
		 validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		 validator.receive(validCoin);
	}
	
	@Test(expected = NoPowerException.class)
	public void testReceiveNotActivated() throws DisabledException, CashOverloadException {
		 validator.disactivate();
		 Coin validCoin = new Coin(currency, BigDecimal.ONE);
		 Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		 map.put(BigDecimal.ONE, new StandardSinkStub());
		 validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		 validator.receive(validCoin);
	}
	
	@Test(expected = DisabledException.class)
	public void testReceiveDisabled() throws DisabledException, CashOverloadException {
		 validator.disable();
		 Coin validCoin = new Coin(currency, BigDecimal.ONE);
		 Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		 map.put(BigDecimal.ONE, new StandardSinkStub());
		 validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		 validator.receive(validCoin);
	}
	
	@Test
	public void testNotifyValidCoinDetected() throws DisabledException, CashOverloadException {
		 CoinValidatorObserverStub observerStub = new CoinValidatorObserverStub();
		 validator.attach(observerStub);
		 Coin validCoin = new Coin(currency, BigDecimal.ONE);
		 Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		 map.put(BigDecimal.ONE, new StandardSinkStub());
		 validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		 validator.receive(validCoin);
		 assertEquals(true, observerStub.isValidCoinDetected);
	}
	
	@Test(expected = NullPointerSimulationException.class)
	public void testReceiveNullCoin() throws DisabledException, CashOverloadException {
		validator.receive(null);
	}
	
	@Test
	public void testReceiveInvalidCoin() throws DisabledException, CashOverloadException {
		CoinValidatorObserverStub observerStub = new CoinValidatorObserverStub();
		validator.attach(observerStub);
		
		Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		map.put(BigDecimal.ONE, new StandardSinkStub());
		validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		
        Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.UK);
		Coin coin1 = new Coin(new BigDecimal("3.0"));
		validator.receive(coin1);
		assertEquals(true, observerStub.isInvalidCoinDetected);
	}
	
	@Test
	public void testReceiveSinkHasSpace() throws DisabledException, CashOverloadException {
		Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		StandardSinkStub sink = new StandardSinkStub();
		map.put(BigDecimal.ONE, sink);
		sink.setHasSpace(true);
		validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		
		Coin coin1 = new Coin(BigDecimal.ONE);
		validator.receive(coin1);
	}
	
	@Test
	public void testReceiveSinkHasNoSpace() throws DisabledException, CashOverloadException {
		Map<BigDecimal, Sink<Coin>> map = new HashMap<>();
		StandardSinkStub sink = new StandardSinkStub();
		map.put(BigDecimal.ONE, sink);
		sink.setHasSpace(false);
		validator.setup(new StandardSinkStub(), map, new StandardSinkStub());
		
		Coin coin1 = new Coin(BigDecimal.ONE);
		validator.receive(coin1);
	}

}