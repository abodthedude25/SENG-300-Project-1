package com.thelocalmarketplace.demo;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.PaymentHandler;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {
        // Initialize the self-checkout station
        SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation();

        // Initialize an order
        Order order = new Order(selfCheckoutStation.getElectronicScale());

        // Add items to the order
        order.addItemToOrder(new com.thelocalmarketplace.software.Item("Item 1", 100));
        order.addItemToOrder(new com.thelocalmarketplace.software.Item("Item 2", 150));

        // Initialize the payment handler
        PaymentHandler paymentHandler = new PaymentHandler(selfCheckoutStation, order.getOrder());

        // Simulate coin insertion
        ArrayList<Coin> coinsList = new ArrayList<>();
        coinsList.add(new Coin(BigDecimal.valueOf(0.25))); // Quarter
        coinsList.add(new Coin(BigDecimal.valueOf(0.10))); // Dime
        coinsList.add(new Coin(BigDecimal.valueOf(0.05))); // Nickel
        coinsList.add(new Coin(BigDecimal.valueOf(0.01))); // Penny

        try {
            // Process payment with coins
            paymentHandler.processPaymentWithCoins(coinsList);
        } catch (DisabledException | NoCashAvailableException e) {
            e.printStackTrace();
        }
        
        // Demonstrate weight discrepancy handling
        try {
            // Initialize weight discrepancy with the order and scale
            WeightDiscrepancy weightDiscrepancy = new WeightDiscrepancy(order, selfCheckoutStation.getElectronicScale());
            
            // Update scale mass
            weightDiscrepancy.updateMass();
            
            // Check for discrepancy
            weightDiscrepancy.checkDiscrepancy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start session
        SelfCheckoutStationSoftware selfCheckoutStationSoftware = new SelfCheckoutStationSoftware();
        selfCheckoutStationSoftware.startSession();
    }
}
