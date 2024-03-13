/**
 * Talaal Irtija (UCID: 30169780)
 * Yotam Rojnov (UCID: 30173949)
 * Joseph Tandyo (UCID: 30182561)
 */

package com.thelocalmarketplace.software;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import static com.thelocalmarketplace.hardware.SelfCheckoutStation.resetConfigurationToDefaults;
import java.util.Scanner;

public class SelfCheckoutStationSoftware {

	/**
	 * Boolean variable that is used to track whether user interaction is blocked
	 */
	private boolean blocked = false;

	/**
	 * Boolean variable to track if a current session is active or not. 
	 */
	private boolean active;

	/**
	 * Set function to change the blocked variable value.
	 * @param value The new value for station block status
	 */
	public void setStationBlock(boolean value) {
		this.blocked = value;
	}

	/**
	 * Get function to get the blocked station status.
	 */
	public boolean getStationBlock() {
		return this.blocked;
	}
	
	/**
	 * Set function to change the active variable value.
	 */
	public void setStationActive() {
		this.active = true;
	}

	/**
	 * Function to start a session for self-checkout machine
	 * @throws InvalidStateSimulationException If a session is already active.
	 */
	public void startSession() {

		if (this.active) {
			throw new InvalidStateSimulationException("Session already started.");
		}

		// Reset all self-checkout station configurations to default.
		resetConfigurationToDefaults();
		
		// Create a scanner object to read user input.
		Scanner scanner = new Scanner(System.in);
		
		// Prompt the user to touch anywhere to start and wait for an input.
		System.out.println("Enter Anything to Start");
		scanner.nextLine();

		// Set the current session to active.
		this.active = true;

	}
}
