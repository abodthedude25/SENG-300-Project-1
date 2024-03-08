package com.thelocalmarketplace.software;

import java.math.BigDecimal;

public class WeightDiscrepancy {
	
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
	public WeightDiscrepancy(Order order) {
		this.order = order;
		getWeightAtBlock();
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
