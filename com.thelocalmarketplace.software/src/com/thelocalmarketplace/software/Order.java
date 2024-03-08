package com.thelocalmarketplace.software;

import java.math.BigInteger;
import java.util.ArrayList;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.Barcode;

/**
 * Represents the Customer's order that different use cases can interact with.
 */
public class Order {
    private ArrayList<Item> order;
    private BigInteger weight;
    private long price;
    
    /**
     * Constructs an empty order.
     */
    public Order() {
        this.order = new ArrayList<Item>();
        this.weight = BigInteger.ZERO;
        this.price = 0;
    }
    
    /**
     * Adds an item to the order via barcode scan
     */
    public void addItemViaBarcodeScan(Barcode barcode) {    	
    	// Determines the characteristics (weight and cost) of the product associated with the barcode (ALEX)
    	// Updates the expected weight from the bagging area.(ALSO ALEX)
    	
    	// Signals to the Customer to place the scanned item in the bagging area.
    	
    	// Signals to the System that the weight has changed.
    }
    
    /**
     * Gets the total weight of the order (in grams).
     * 
     * @return The total weight of order (in grams).
     */
    public BigInteger getWeightInGrams() {
        return this.weight;
    }
    
    /**
     * Gets the total price of the order
     * 
     * @return The total price of order.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * Updates the total weight of the order (in grams)
     */
    public void setWeightInGrams(BigInteger weight) {
    	this.weight = weight;
    }
    
    /**
     * Updates the total price of the order
     */
    public void setPrice(long price) {
    	this.price = price;
    }
}