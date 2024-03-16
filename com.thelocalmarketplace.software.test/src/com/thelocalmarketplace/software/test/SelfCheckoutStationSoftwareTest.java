package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;


public class SelfCheckoutStationSoftwareTest {
	
	private SelfCheckoutStationSoftware software;
	private Scanner input;
	
	@Before
	public void setup() {
		software = new SelfCheckoutStationSoftware();
		input = new Scanner(System.in);
		SelfCheckoutStationSoftware.setStationActive(false);
	}
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testStartSessionActiveUsingSetter() {
		SelfCheckoutStationSoftware.setStationActive(true);
		String inputData = "user input data";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
		Scanner testInput = new Scanner(System.in);
		software.startSession(testInput);
	}
	
	@Test (expected = InvalidStateSimulationException.class)
	public void testStartSessionTwice() {
		String inputData = "user input data";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
		Scanner testInput = new Scanner(System.in);
		software.startSession(testInput);

		software.startSession(testInput);
	}

	@Test
	public void testStartSessionNotActive() {
		String inputData = "user input data";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
		Scanner testInput = new Scanner(System.in);
		software.startSession(testInput);

		assertTrue(SelfCheckoutStationSoftware.getStationActive());
	}
	
	@Test
	public void testStartSessionBlockGetter() {
		String inputData = "user input data";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
		Scanner testInput = new Scanner(System.in);
		software.startSession(testInput);

		assertFalse(SelfCheckoutStationSoftware.getStationBlock());
		SelfCheckoutStationSoftware.setStationBlock(true);
		assertTrue(SelfCheckoutStationSoftware.getStationBlock());
		SelfCheckoutStationSoftware.setStationBlock(false);
		assertFalse(SelfCheckoutStationSoftware.getStationBlock());
	}
}