package com.thelocalmarketplace.software;

import java.math.BigInteger;
import java.util.ArrayList;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

// hello secret message
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
    
    /**
     * Constructs an empty order.
     */
    public Order() {
        this.order = new ArrayList<Item>();
        this.totalWeight = 0;
        this.totalPrice = 0;
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
        // Determines the characteristics (weight and cost) of the product associated with the barcode (ALEX)
        // Updates the expected weight from the bagging area.(ALSO ALEX)

        // Signals to the Customer to place the scanned item in the bagging area.

        // Signals to the System that the weight has changed.
        BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);

        //To make sure the barcode scanned is avaiaiable in the database
        if (product != null) {
            double productWeight = product.getExpectedWeight(); // Gets products weight from barcode
            long productPrice = product.getPrice();

            // Products weight conversion from double (as it is in the
            // BarcodedProduct.java to bigInteger

            addTotalWeightInGrams(productWeight); // Adds the weight of the product to the total weight of the order
            addTotalPrice(productPrice); // Adds the price of the product to the total price of the order

            mass = new Mass(productWeight); // Converts the weight of the product to a mass
            barcodedItem = new BarcodedItem(barcode, mass); // Adds the product to the order
            addItemToOrder(barcodedItem); // Adds the product to the order
            
            System.out.println("Please place item in the bagging area.");
            
            weightDiscrepancy.checkDiscrepancy(); // Checks for a weight discrepancy
        }
    }
}
