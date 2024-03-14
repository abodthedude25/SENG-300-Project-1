package com.thelocalmarketplace.demo;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.BaggingAreaListener;
import com.thelocalmarketplace.software.ElectronicScaleWrapper;
import com.thelocalmarketplace.software.Order;

public class BaggingAreaListenerDemo {
    public static void main(String[] args) {
    	
    	// Create an order for demonstration
        Order order = new Order();

        // Create a wrapper for the electronic scale
        ElectronicScaleWrapper scaleWrapper = new ElectronicScaleWrapper();

        // Create a bagging area listener
        BaggingAreaListener baggingAreaListener = new BaggingAreaListener(order);

        // Add the bagging area listener to the scale wrapper
        scaleWrapper.addElectronicScaleListener(baggingAreaListener);

        // Simulate changes in the bagging area mass
        simulateMassChange(scaleWrapper);
    }

    // Simulate changes in the bagging area mass
    private static void simulateMassChange(IElectronicScale scale) {
        // Simulate mass change
        Mass mass1 = new Mass(5.0); // Initial mass
        Mass mass2 = new Mass(7.0); // Changed mass

        // Signal mass change
        System.out.println("Initial mass on the scale: " + mass1);
        ((ElectronicScaleWrapper) scale).theMassOnTheScaleHasChanged(mass1);

        // Signal changed mass
        System.out.println("Changed mass on the scale: " + mass2);
        ((ElectronicScaleWrapper) scale).theMassOnTheScaleHasChanged(mass2);
    }
}

