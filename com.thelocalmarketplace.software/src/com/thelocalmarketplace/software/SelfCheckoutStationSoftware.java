package com.thelocalmarketplace.software;

public class SelfCheckoutStationSoftware {
	private boolean blocked = false;
	
	public void setStationBlock(boolean value) {
		this.blocked = value;
	}
	
	public boolean getStationBlock() {
		return this.blocked;
	}
	
	public void startSession() {
		
		displayLandingScreenSession();  // welcome screen display
		
		waitForCustomerInteraction();   // Simulate customer wait interaction
		
		// Once clicked on the system screen, prompt a message for success
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
		System.out.println("Language selected... English ");
	}
	
	private void selectAudioIO() {
		
		
		System.out.println("Select your preferred audio input/output: ");  // Use scanner properties to ask for user input either through touch sense (i.e button) or auido I/O
		System.out.println("1. Verbal interaction");
		System.out.println("2. visual interaction");
		
		int selectedAudioPrompt = 1;
		System.out.println("Selected type: Verbal interaction");
		
		
		// For this method, we should also consider understanding how can we make the process user friendly (i.e
		// For a disabled customer, if they have visual disability, how can they start the session without any physical touch to the screen
	}
}
