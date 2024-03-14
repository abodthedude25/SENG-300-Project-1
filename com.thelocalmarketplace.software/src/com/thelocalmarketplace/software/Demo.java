package com.thelocalmarketplace.software;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;

public class Demo {
    public static void main(String[] args) {
        // Mock objects for demonstration
        ElectronicScale scale = new ElectronicScale(); // Assuming ElectronicScale class exists and can be instantiated
        
        // Demo for Order class
        demonstrateOrder(scale);
        
        // Demo for WeightDiscrepancy class
        demonstrateWeightDiscrepancy(scale);
        
        // You can add more demonstrations for other classes here
    }
    
    public static void demonstrateOrder(ElectronicScale scale) {
        try {
            Order order = new Order(scale);
            
            // Add some items to the order
            order.addItemToOrder(new Item("Item 1", 100)); // Assuming Item class exists
            order.addItemToOrder(new Item("Item 2", 150)); // Assuming Item class exists
            
            // Simulate barcode scan to add an item to the order
            Barcode barcode = new Barcode("123456789"); // Assuming Barcode class exists
            order.addItemViaBarcodeScan(barcode);
            
            // Demonstrate getting order details
            System.out.println("Order details:");
            System.out.println("Items: " + order.getOrder());
            System.out.println("Total weight: " + order.getTotalWeightInGrams());
            System.out.println("Total price: " + order.getTotalPrice());
            
            // Simulate checking for weight discrepancy
            order.checkForDiscrepancy(scale);
        } catch (OverloadedDevice e) {
            // Handle overloaded device exception
            e.printStackTrace();
        }
    }
    
    public static void demonstrateWeightDiscrepancy(ElectronicScale scale) {
        try {
            Order order = new Order(scale);
            
            // Add some items to the order
            order.addItemToOrder(new Item("Item 1", 100)); // Assuming Item class exists
            order.addItemToOrder(new Item("Item 2", 150)); // Assuming Item class exists
            
            WeightDiscrepancy discrepancy = new WeightDiscrepancy(order, scale);
            
            // Demonstrate each method
            discrepancy.updateMass();
            discrepancy.checkDiscrepancy();
            discrepancy.unBlock();
            boolean removalDetected = discrepancy.checkRemoval();
            boolean additionDetected = discrepancy.checkBaggageAddition();
            boolean weightChanged = discrepancy.checkWeightChange();
            
            // Print results
            System.out.println("Removal detected: " + removalDetected);
            System.out.println("Addition detected: " + additionDetected);
            System.out.println("Weight change detected: " + weightChanged);
        } catch (OverloadedDevice e) {
            // Handle overloaded device exception
            e.printStackTrace();
        }
    }
}
