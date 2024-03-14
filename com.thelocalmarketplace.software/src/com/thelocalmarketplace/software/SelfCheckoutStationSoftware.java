/**
 * Talaal Irtija (UCID: 30169780)
 * Yotam Rojnov (UCID: 30173949)
 * Joseph Tandyo (UCID: 30182561)
 * Lucas Kasdorf (UCID: 30173922)
 */

package com.thelocalmarketplace.software;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import static com.thelocalmarketplace.hardware.SelfCheckoutStation.resetConfigurationToDefaults;
import java.util.Scanner;

public class SelfCheckoutStationSoftware {

	/**
	 * Boolean variable that is used to track whether user interaction is blocked
	 */
	private static boolean blocked = false;

	/**
	 * Boolean variable to track if a current session is active or not. 
	 */
	private static boolean active;

	/**
	 * Set function to change the blocked variable value.
	 * @param value The new value for station block status
	 */
	public static void setStationBlock(boolean value) {
		blocked = value;
	}

	/**
	 * Get function to get the blocked station status.
	 */
	public static boolean getStationBlock() {
		return blocked;
	}
	
	/**
	 * Set function to change the active variable value.
	 */
	public static void setStationActive() {
		active = true;
	}

	/**
	 * Get function to get the blocked station status.
	 */
	public static boolean getStationActive() {
		return active;
	}
	
	/**
	 * Function to start a session for self-checkout machine
	 * @throws InvalidStateSimulationException If a session is already active.
	 */
	public void startSession() {

		if (active) {
			throw new InvalidStateSimulationException("Session already started.");
		}

		// Reset all self-checkout station configurations to default.
		resetConfigurationToDefaults();
		
		// Create a scanner object to read user input.
		Scanner scanner = new Scanner(System.in);
		
		// Prompt the user to touch anywhere to start and wait for an input.
		System.out.println("Enter Anything to Start");
		
		// Ignore input for now.
		scanner.nextLine();
		
		// Close the scanner.
		scanner.close();
		
		// Set the current session to active.
		active = true;

	}
}
