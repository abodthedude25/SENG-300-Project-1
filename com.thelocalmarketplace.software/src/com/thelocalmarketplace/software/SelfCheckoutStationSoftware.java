/** Talaal Irtija (UCID: 30169780)
 * Yotam Rojnov (UCID: 30173949)
 * Joseph Tandyo (UCID: 30182561) */



package com.thelocalmarketplace.software;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import ca.ucalgary.seng300.powerutility.NoPowerException;


public class SelfCheckoutStationSoftware {

	/** Boolean variable that is used to track whether user interaction is blocked */
	private boolean blocked = false;

	/** Boolean variable to track if a current session is active or not. */
	private boolean active;

	/** Boolean variable to track whether the self checkout station is powered on. */
	private boolean enabled;

	/** Set function to change the blocked variable value
	 * @param value The new value for station block status*/
	public void setStationBlock(boolean value) {
		this.blocked = value;
	}

	/** Get function to get the blocked station status*/
	public boolean getStationBlock() {
		return this.blocked;
	}

	/** Function to start a session for self checkout machine*/
	public void startSession() {
		if (this.active) {
			throw new InvalidStateSimulationException("Session already started.");	// might add more exceptions after not sure yet
		}

		this.active = true;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Touch Anywhere to Start.");
		scanner.nextLine();
	}

	/** This function handles the user choice once a session has been started.
	 * User choice includes adding items and paying (ending a session) */
	public void handleUserChoice() {
		String input;
		while (true) {
			System.out.println("Enter '1' to add an item!");
			System.out.println("Enter '2' to pay!");

			switch (input = scanner.readLine()) {
				case("1") {

				}
			}
				case("2") {

				}
			}
	}

}
