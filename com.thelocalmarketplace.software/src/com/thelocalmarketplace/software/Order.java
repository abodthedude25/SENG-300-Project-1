package com.thelocalmarketplace.software;

import java.util.ArrayList;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

/**
 * Represents the Customer's order that different use cases can interact with.
 */
public class Order {
    private ArrayList<Item> order;
    private double totalWeight;
    private long totalPrice;
    private BarcodedItem barcodedItem;
    private Mass mass;
    private WeightDiscrepancy weightDiscrepancy;
    private ElectronicScale scale;
    
    /**
     * Constructs an empty order.
     * @throws OverloadedDevice 
     */
    public Order(ElectronicScale scale) throws OverloadedDevice {
        this.order = new ArrayList<Item>();
        this.totalWeight = 0; 
        this.totalPrice = 0;
        this.scale = scale; 
        this.weightDiscrepancy = new WeightDiscrepancy(this, scale); 
    }

    /**
     * Adds an item to the order.
     *
     * @param item The item to add to the order.
     */
    public void addItemToOrder(Item item) {
        this.order.add(item);
    }

    /**
     * Gets the order.
     *
     * @return The order.
     */
    public ArrayList<Item> getOrder() {
        return this.order;
    }

    /**
     * Gets the total weight of the order (in grams).
     * 
     * @return The total weight of order (in grams).
     */
    public double getTotalWeightInGrams() {
        return this.totalWeight;
    }
    
    /**
     * Gets the total price of the order
     * 
     * @return The total price of order.
     */
    public long getTotalPrice() {
        return this.totalPrice;
    }
    
    /**
     * Updates the total weight of the order (in grams)
     */
    public void addTotalWeightInGrams(double weight) {
    	this.totalWeight += weight;
    }
    
    /**
     * Updates the total price of the order
     */
    public void addTotalPrice(long price) {
    	this.totalPrice += price;
    }

    /**
     * Adds an item to the order via barcode scan
     */
    public void addItemViaBarcodeScan(Barcode barcode) {
    	// Get the product from the database
        BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);

        //To make sure the barcode scanned is available in the database
        if (product != null) {
            double productWeight = product.getExpectedWeight(); // Gets products weight from barcode
            long productPrice = product.getPrice();

            addTotalWeightInGrams(productWeight); // Adds the weight of the product to the total weight of the order
            addTotalPrice(productPrice); // Adds the price of the product to the total price of the order

            mass = new Mass(productWeight); // Converts the weight of the product to a mass
            barcodedItem = new BarcodedItem(barcode, mass); // Adds the product to the order
            addItemToOrder(barcodedItem); // Adds the product to the order
            
            // Signal to the customer to place the scanned item in the bagging area
            System.out.println("Please place item in the bagging area.");
            
            weightDiscrepancy.unBlock(); // Checks for a weight discrepancy, if none, it unblocks the system
        }
    }
}
