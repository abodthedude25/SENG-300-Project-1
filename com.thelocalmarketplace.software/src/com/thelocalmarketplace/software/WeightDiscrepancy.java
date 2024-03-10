package com.thelocalmarketplace.software;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.List;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale; 

public class WeightDiscrepancy {
	
	private List<Item> items;
	private ElectronicScale scale;
	private boolean blocked;
	private Order order;
	private BigDecimal weightAtBlock;
	
    /**
     // Records weight at time of discrepancy before block
     // Added weightAtBlcok to constructor instead
   
	private void getWeightAtBlock() {
		weightAtBlock = scale.getCurrentMassOnTheScale();
	}

    */
	
	
	/**
	 * Constructor for order
	 */
	public WeightDiscrepancy(Order order, ElectronicScale scale) {
		this.order = order;
		this.items = order.getOrder();
		this.scale = (ElectronicScale) scale;
		weightAtBlock = getCurrentMassOnTheScale();
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
	
	/**
	 * @return True if system is blocked, false otherwise
	 * */
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
	
	/**
	 * If we decide to combine removal and addition for simplicity. Can remove if we decide. 
	 * Compared the weight of bagging area to the weight at block time. If the current weight 
	 * is not equal to the weight at block time this confirms the item has been added or removed. 
	 * @return True if item has been added or removed 
	 * @return False if the weight has not changed since block time 
	 */
	public boolean checkWeightChange() {
		BigDecimal currentWeight = order.getWeight(); 
		if(currentWeight != weightAtBlock) {
			return true;
		} else {
			return false; 
		}
	}
}







