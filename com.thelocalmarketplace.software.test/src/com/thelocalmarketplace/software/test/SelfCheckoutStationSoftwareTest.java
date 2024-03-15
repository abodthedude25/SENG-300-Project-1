/**
 * Talaal Irtija (UCID: 30169780)
 * Yotam Rojnov (UCID: 30173949)
 * Joseph Tandyo (UCID: 30182561)
 * Lucas Kasdorf (UCID: 30173922)
 * Abil Momin (UCID: 30154771)
 */

package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;


public class SelfCheckoutStationSoftwareTest {
	
	private SelfCheckoutStationSoftware software;
	
	@Before
	public void setup() {
		software = new SelfCheckoutStationSoftware();
	}
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testStartSessionActiveUsingSetter() {
		software.setStationActive();
		software.startSession();
	}
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testStartSessionTwice() {
		software.startSession();
		software.startSession();
	}

	@Test
	public void testStartSessionNotActive() {
		software.startSession();
		assertFalse(SelfCheckoutStationSoftware.getStationActive());
	}
	
	@Test
	public void testStartSessionBlockGetter() {
		software.startSession();
		assertFalse(SelfCheckoutStationSoftware.getStationBlock());
		SelfCheckoutStationSoftware.setStationBlock(true);
		assertTrue(SelfCheckoutStationSoftware.getStationBlock());
		SelfCheckoutStationSoftware.setStationBlock(false);
		assertFalse(SelfCheckoutStationSoftware.getStationBlock());
	}
}
