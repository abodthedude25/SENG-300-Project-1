/**
 * Lucas Kasdorf (UCID: 30173922)
 */

package com.thelocalmarketplace.software.test;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;


public class SelfCheckoutStationSoftwareTest {
	
	private SelfCheckoutStationSoftware software;
	
	@Before
	public void setup() {
		this.software = new SelfCheckoutStationSoftware();
	}
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testStartSessionActive() {
		software.setStationActive();
		software.startSession();
	}
	
	@Test
	public void testStartSessionNotActive() {
		software.startSession();
	}
}
