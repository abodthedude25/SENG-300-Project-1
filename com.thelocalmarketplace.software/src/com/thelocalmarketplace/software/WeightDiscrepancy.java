package com.thelocalmarketplace.software;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.List; 

public class WeightDiscrepancy {
	
	private List<Item> items;
	private ElectronicScale scale;
	private boolean blocked;
	private Order order;
	private BigDecimal weightAtBlock;
	
    /**
     * Records weight at time of discrepancy before block
     * 
     */
	private void getWeightAtBlock() {
		weightAtBlock = order.getWeight();
	}
	
	/**
	 * Constructor for order
	 */
	public WeightDiscrepancy(Order order, ElectronicScale scale) {
		this.order = order;
		this.items = order.getOrder();
		this.scale = (ElectronicScale) scale;
		getWeightAtBlock();
	}
	
	/**
	 * Updates the scale mass every time an item is added to or removed from the order
	 */
	/**
	 * Records weight at time of discrepancy before block 
	 */
	public void updateMass() {
		try {
			Mass currMass = scale.getCurrentMassOnTheScale();
			currMass = Mass.ZERO;
			for (Item item : items) {
				scale.addAnItem(item);
			}
		} catch (OverloadedDevice e) {
			blocked = true;
		}
	}
	
	public void setBlocked() {
		Mass actual;
		BigDecimal expected;
		try {
			actual = scale.getCurrentMassOnTheScale();
			expected = order.getWeight();
			
			blocked = !expected.equals(actual);
		} catch (OverloadedDevice e) {
			blocked = true;
		}
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
    /**
     * Compares weight at block to current getWeight to check if an item has been removed.
     * 
     * @return True if item has been removed (new weight is less). False otherwise.
     */
	public boolean checkRemoval() {
		BigDecimal currentWeight = order.getWeight();
		if (currentWeight.compareTo(weightAtBlock) < 0) {
			return true;
		} else {
			return false;
		}
	}
}
