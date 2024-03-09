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
		weightAtBlock = order.getCurrentMassOnTheScale();
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







