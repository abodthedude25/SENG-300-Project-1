package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;

/**
 * Represents the Customer's order that different use cases can interact with.
 */
public class Order {
    private ArrayList<Item> order;
    private BigDecimal weight;
    private BigDecimal price;
    
    /**
     * Constructs an empty order.
     */
    public Order() {
        this.order = new ArrayList<Item>();
        this.weight = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
    }
    
    /**
     * Adds an item to the order via barcode scan
     */
    public void addItemViaBarcodeScan(BarcodedItem item) {
        // TODO implement this (group 2)
    }
    
    /**
     * Gets the total weight of the order.
     * 
     * @return The total weight of order.
     */
    public BigDecimal getWeight() {
        return this.weight;
    }
    
    /**
     * Gets the total price of the order
     * 
     * @return The total price of order.
     */
    public BigDecimal getPrice() {
        return this.price;
    }
}