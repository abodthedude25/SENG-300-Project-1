package com.thelocalmarketplace.hardware.test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;


public class CoinStorageUnitTest {
	private CoinStorageUnit coinStorageUnit;
	private PowerGrid pgrid;
	private Coin coina;
	private Coin coinb;
	private Coin coinc;
	private Coin coind;
	private CoinStorageUnitObserverStub observerStub;
	
	@Before
	public void setup() {
		observerStub = new CoinStorageUnitObserverStub();
		coinStorageUnit = new CoinStorageUnit(3); // create a storage unit of capacity 3
		pgrid = PowerGrid.instance();
		PowerGrid.engageUninterruptiblePowerSource();
	    PowerGrid.instance().forcePowerRestore();
		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
		BigDecimal value = new BigDecimal(1);
		coina = new Coin(value);
		coinb = new Coin(value);
		coinc = new Coin(value);
		coind = new Coin(value);
		coinStorageUnit.attach(observerStub);
	}
	
	@Test
	public void testGetCapacity() {
		assertEquals(3, coinStorageUnit.getCapacity());
	}
	
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testNegCapacity() {
		coinStorageUnit = new CoinStorageUnit(-1);
	}
	
	@Test
	public void testGetCapacityNotEmpty() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(coina);
		assertEquals(3, coinStorageUnit.getCapacity());
	}
	
	@Test(expected = NoPowerException.class)
	public void testGetCoinCountNoPower() {
		coinStorageUnit.activate();
		coinStorageUnit.getCoinCount();
	}
@Test(expected = NoPowerException.class)
	public void testGetCoinCountNoPowerNotActive() {
		coinStorageUnit.getCoinCount();
	}
	
	@Test(expected = NoPowerException.class)
	public void testGetCoinCountNotActive() {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.getCoinCount();
	}
	
	@Test
	public void testGetCoinCountEmpty() {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		Assert.assertEquals(0, coinStorageUnit.getCoinCount());
	}
	
	@Test
	public void testGetCoinCountNotEmpty() throws SimulationException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina);
		Assert.assertEquals(1, coinStorageUnit.getCoinCount());
	}
		
	@Test
	public void testLoadCoins() throws SimulationException, CashOverloadException{
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(coina, coinb);
		Assert.assertEquals(2, coinStorageUnit.getCoinCount()-1);
	}
	
	@Test(expected = NullPointerSimulationException.class)
	public void testLoadNullCoin() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coina = null;
		coinStorageUnit.load(coina);
	}
	
	@Test(expected = NullPointerSimulationException.class)
	public void testLoadNull() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(null);
	}
	
	@Test(expected = CashOverloadException.class)
	public void testOverLoad() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(coina, coinb, coinc, coind);
	}
	
	@Test(expected = NoPowerException.class)
	public void testLoadNoPower() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.load(coina);
	}
	
	@Test(expected = NoPowerException.class)
	public void testLoadNotActive() throws SimulationException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(coina);
	}
	
	@Test(expected = NoPowerException.class)
	public void testLoadNoPowerNotActive() throws SimulationException, CashOverloadException {
		coinStorageUnit.load(coina);
	}
	
	@Test
	public void testLoadNotifyCoinsLoaded() throws SimulationException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina, coinb);
		Assert.assertTrue(observerStub.isCoinsLoaded);
	}
	
	@Test
	public void testLoadDisabled() throws SimulationException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.disable();
		coinStorageUnit.load(coina);
		Assert.assertEquals(1, coinStorageUnit.getCoinCount()-1);
	}
		
	@Test(expected = NoPowerException.class)
	public void testUnloadNoPower() {
		coinStorageUnit.activate();
		coinStorageUnit.unload();
	}
	
	@Test(expected = NoPowerException.class)
	public void testUnloadNotActivated() {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.unload();
	}
	
	@Test
	public void testUnload() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.load(coina, coinb);
		List<Coin> resultCoins = coinStorageUnit.unload();
		Assert.assertEquals(Arrays.asList(coina, coinb, null), resultCoins);
	}
@Test
	public void testUnloadEmpty() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		List<Coin> resultCoins = coinStorageUnit.unload();
		Assert.assertNotEquals(null, resultCoins);
	}
	
	@Test
	public void testUnloadNotifyCoinsUnloaded() throws SimulationException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.unload();
		Assert.assertTrue(observerStub.isCoinsUnloaded);
	}
	
	@Test(expected = NoPowerException.class)
	public void testReceiveNoPowerDisabled() throws DisabledException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.disable();
		coinStorageUnit.receive(coina);
	}
	
	@Test(expected = DisabledException.class)
	public void testReceiveDisabled() throws DisabledException, CashOverloadException{
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.disable();
		coinStorageUnit.activate();
		coinStorageUnit.receive(coina);
	}
	
	@Test(expected = NoPowerException.class)
	public void testReceiveNoPower() throws DisabledException, CashOverloadException {
		coinStorageUnit.activate();
		coinStorageUnit.receive(coina);
	}
	
	@Test(expected = NoPowerException.class)
	public void testReceiveNoPowerNotActive() throws DisabledException, CashOverloadException {
		coinStorageUnit.receive(coina);
	}
	
	@Test
	public void testLoadThenReceive() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina);
		coinStorageUnit.receive(coinb);
		List<Coin>resultCoins = coinStorageUnit.unload();
		Assert.assertEquals(Arrays.asList(coina, coinb, null), resultCoins);
	}
	
	@Test
	public void testReceiveThenLoad() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.receive(coinb);
		coinStorageUnit.load(coina);
		List<Coin> resultCoins = coinStorageUnit.unload();
		Assert.assertEquals(Arrays.asList(coinb, coina, null), resultCoins);
	}
	
	@Test(expected = CashOverloadException.class)
	public void testReceiveFull() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina, coinb, coinc);
		coinStorageUnit.receive(coind);
	}
	
	@Test(expected = NullPointerSimulationException.class)
	public void testReceiveNull() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coina = null;
		coinStorageUnit.receive(coina);
	}
	
	@Test
	public void testReceiveNotifyCoinAdded() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.receive(coina);
		Assert.assertTrue(observerStub.isCoinAdded);
	}
	
	@Test
	public void testReceiveNotifyCoinsFull() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina, coinb);
		coinStorageUnit.receive(coinc);
		Assert.assertTrue(observerStub.isCoinsFull);
	}
	
	@Test(expected = NoPowerException.class)
	public void testHasSpaceNoPower() {
		coinStorageUnit.activate();
		coinStorageUnit.hasSpace();
	}
	
	@Test(expected = NoPowerException.class)
	public void testHasSpaceNotActivated() {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.hasSpace();
	}
	
	@Test(expected = NoPowerException.class)
	public void testHasSpaceNotActivatedNoPower() {
		coinStorageUnit.hasSpace();
	}
	
	@Test
	public void testHasSpaceEmpty() {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		Assert.assertTrue(coinStorageUnit.hasSpace());
	}
@Test
	public void testHasSpaceNotFull() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina, coinb);
		Assert.assertTrue(coinStorageUnit.hasSpace());
	}
	
	@Test
	public void testHasSpaceFull() throws DisabledException, CashOverloadException {
		coinStorageUnit.connect(pgrid);
		coinStorageUnit.activate();
		coinStorageUnit.load(coina, coinb, coinc);
		Assert.assertFalse(coinStorageUnit.hasSpace());
	}
}