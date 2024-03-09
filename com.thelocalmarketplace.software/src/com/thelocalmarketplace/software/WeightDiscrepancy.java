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
	
	/**
	 * Compares the weight of the bagging area to the weight of the bagging area at block time. If the weight difference of 
	 * currentWeight and weightAtBlock is positive, this indicates the item has been added to the bagging area. 
	 * @return True if the item has been added to bagging area. 
	 * @return False if a weight increase has not been detected, therefore item not added to bagging area. 
	 */
	public boolean checkBaggageAddition() {
		BigDecimal currentWeight = order.getWeight(); 
		if(currentWeight.compareTo(weightAtBlock) > 0) {
			return true;
		} else {
			return false; 
		}
	}
}







