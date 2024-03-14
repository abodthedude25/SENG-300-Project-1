package com.thelocalmarketplace.demo;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import com.jjjwelectronics.OverloadedDevice;

import java.util.ArrayList;

public class WeightDiscrepancyDemo {
    public static void main(String[] args) {
        // Create a list of items for demonstration
        ArrayList<Item> items = new ArrayList<>();
        // Add some items to the list

        // Create a mock ElectronicScale
        ElectronicScale scale = new ElectronicScale();

        // Create a mock Order
        Order order = new Order(items);

        try {
            // Create a WeightDiscrepancy instance
            WeightDiscrepancy weightDiscrepancy = new WeightDiscrepancy(order, scale);

            // Update the mass on the scale
            weightDiscrepancy.updateMass();

            // Check for discrepancies
            weightDiscrepancy.checkDiscrepancy();

            // Check if an item has been removed
            boolean itemRemoved = weightDiscrepancy.checkRemoval();
            System.out.println("Item removed: " + itemRemoved);

            // Check if an item has been added to the bagging area
            boolean itemAddedToBaggingArea = weightDiscrepancy.checkBaggageAddition();
            System.out.println("Item added to bagging area: " + itemAddedToBaggingArea);

            // Check if there's a weight change
            boolean weightChanged = weightDiscrepancy.checkWeightChange();
            System.out.println("Weight changed: " + weightChanged);

            // Simulate unblocking
            WeightDiscrepancy.unBlock();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
