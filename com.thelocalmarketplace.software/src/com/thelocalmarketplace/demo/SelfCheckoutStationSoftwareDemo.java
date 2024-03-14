package com.thelocalmarketplace.demo;

import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;

public class SelfCheckoutStationSoftwareDemo {
    public static void main(String[] args) {
        SelfCheckoutStationSoftware selfCheckout = new SelfCheckoutStationSoftware();

        // Demonstrate starting a session
        try {
            selfCheckout.startSession();
            System.out.println("Session started successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Demonstrate getting and setting station block status
        System.out.println("Station block status: " + SelfCheckoutStationSoftware.getStationBlock());
        SelfCheckoutStationSoftware.setStationBlock(true);
        System.out.println("Station block status after setting: " + SelfCheckoutStationSoftware.getStationBlock());

        // Demonstrate getting and setting station active status
        System.out.println("Station active status: " + SelfCheckoutStationSoftware.getStationActive());
        SelfCheckoutStationSoftware.setStationActive();
        System.out.println("Station active status after setting: " + SelfCheckoutStationSoftware.getStationActive());
    }
}
