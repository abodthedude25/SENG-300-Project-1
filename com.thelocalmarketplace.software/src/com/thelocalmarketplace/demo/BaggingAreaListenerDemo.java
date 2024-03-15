/**
 * Yotam Rojnov (UCID: 30173949)
 * Duncan McKay (UCID: 30177857)
 * Mahfuz Alam (UCID:30142265)
 * Luis Trigueros Granillo (UCID: 30167989)
 * Lilia Skumatova (UCID: 30187339)
 * Abdelrahman Abbas (UCID: 30110374)
 * Talaal Irtija (UCID: 30169780)
 * Alejandro Cardona (UCID: 30178941)
 * Alexandre Duteau (UCID: 30192082)
 * Grace Johnson (UCID: 30149693)
 * Abil Momin (UCID: 30154771)
 * Tara Ghasemi M. Rad (UCID: 30171212)
 * Izabella Mawani (UCID: 30179738)
 * Binish Khalid (UCID: 30061367)
 * Fatima Khalid (UCID: 30140757)
 * Lucas Kasdorf (UCID: 30173922)
 * Emily Garcia-Volk (UCID: 30140791)
 * Yuinikoru Futamata (UCID: 30173228)
 * Joseph Tandyo (UCID: 30182561)
 * Syed Haider (UCID: 30143096)
 * Nami Marwah (UCID: 30178528)
 */

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

