/**
 * Talaal Irtija (UCID: 30169780)
 * Yotam Rojnov (UCID: 30173949)
 * Joseph Tandyo (UCID: 30182561)
 * Lucas Kasdorf (UCID: 30173922)
 * Abil Momin (UCID: 30154771)
 */

package com.thelocalmarketplace.software;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import static com.thelocalmarketplace.hardware.SelfCheckoutStation.resetConfigurationToDefaults;
// import java.util.Scanner;

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

		resetConfigurationToDefaults(); // Reset all self-checkout station configurations to default.
		
		// Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input.
		// scanner.nextLine(); // Ignore input for now.
		// scanner.close(); // Close the scanner.
		
		// Prompt the user to touch anywhere to start and wait for an input.
        displayLandingScreenSession();
		
        waitForCustomerInteraction();

		active = true; // Set the current session to active.
        System.out.println("Session started successfully");
	}
	
	private void displayLandingScreenSession() {
		// Touch sensation prompt success
		System.out.println("Welcome to The Local Marketplace. Touch anywhere to start.");
	}
	
	private void waitForCustomerInteraction() {
		System.out.println("Waiting for interaction to commence");
		
		// Simulation of language selection (i.e later iterations will develop language selection options
		selectLanguage();
		
		// Simulation of audio input and output by the use of hardware model of microphone and speaker
		selectAudioIO();
	}
	
	private void selectLanguage() {
		// Prompts to begin with, can later created a range of most spoken language in Canada (i.e English, French, Spanish, Hindi, etc.)
		System.out.println("Select your preferred language: ");  // Use scanner properties to ask for user input either through touch sense (i.e button) or auido I/O
		System.out.println("1. English");
		System.out.println("2. French");
		System.out.println("3. Spanish");
		System.out.println("4. Hindi");
		
		// after the scanner class has acquired the user input, depending on that the statement is produced
		int selectedLanguage = 1;
		System.out.println("Language selected... English");
	}
	
	private void selectAudioIO() {
		System.out.println("Select your preferred audio input/output: ");  // Use scanner properties to ask for user input either through touch sense (i.e button) or auido I/O
		System.out.println("1. Verbal interaction");
		System.out.println("2. Visual interaction");
		
		int selectedAudioPrompt = 1;
		System.out.println("Selected type: Verbal interaction");
		
		// For this method, we should also consider understanding how can we make the process user friendly (i.e
		// For a disabled customer, if they have visual disability, how can they start the session without any physical touch to the screen
	}
}