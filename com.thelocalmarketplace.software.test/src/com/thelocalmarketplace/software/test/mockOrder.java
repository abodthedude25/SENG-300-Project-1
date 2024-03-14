package com.thelocalmarketplace.software.test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.WeightDiscrepancy;

import java.math.BigInteger;
import java.util.ArrayList;

public class mockOrder extends Order {
	   private ArrayList<Item> order;
	    private double totalWeight;
	    private long totalPrice;
	    private BarcodedItem barcodedItem;
	    private Mass mass;
	    private WeightDiscrepancy weightDiscrepancy;
	    
	    /**
	     * Constructs an empty order.
	     */
	    public mockOrder() {
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
}
