package com.thelocalmarketplace.software;

import java.util.List;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale; 

public class WeightDiscrepancy extends ElectronicScale{
	
	private List<Item> items;
	private ElectronicScale scale;
	private Mass weightAtBlock;
	private double value;
	
    /**
     // Records weight at time of discrepancy before block
     // Added weightAtBlcok to constructor instead
   
	private void getWeightAtBlock() {
		weightAtBlock = scale.getCurrentMassOnTheScale();
	}

    */
	
	
	/**
	 * Constructor for order
	 * @throws OverloadedDevice 
	 */
	public WeightDiscrepancy(Order order, ElectronicScale scale) throws OverloadedDevice {
		this.items = order.getOrder();
		this.scale = scale;
		this.weightAtBlock = scale.getCurrentMassOnTheScale();
		this.value = order.getTotalWeightInGrams() * 1_000_000;
	}
	
	/**
	 * Updates the scale mass every time an item is added to or removed from the order
	 */
	/**
	 * Records weight at time of discrepancy before block 
	 */
	public void updateMass() {
		for (Item item : items) {
			scale.addAnItem(item);
		}
	}
	
	/**
	 * Checks for a weight discrepancy 
	 * Will change the value of blocked as needed
	 */
	public void checkDiscrepancy() {
		boolean block;
		Mass actual;
		Mass expected;
		try {
			actual = scale.getCurrentMassOnTheScale();
			expected = new Mass(value);
			
			block = !expected.equals(actual);
			SelfCheckoutStationSoftware.setStationBlock(block);
		} catch (OverloadedDevice e) {
			SelfCheckoutStationSoftware.setStationBlock(true);
		}
	}
	
	public static void unBlock() {
		Mass actual;
		Mass expected;
		try {
			actual = scale.getCurrentMassOnTheScale();
			expected = new Mass(value);
			
			if (expected.equals(actual)) {
				SelfCheckoutStationSoftware.setStationBlock(false);
			}
		} catch (OverloadedDevice e) {
			SelfCheckoutStationSoftware.setStationBlock(true);
		}
	}
	
    /**
     * Compares weight at block to current getWeight to check if an item has been removed.
     * 
     * @return True if item has been removed (new weight is less). False otherwise.
     */
	public boolean checkRemoval() {
		Mass currentWeight = new Mass(value);
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
		Mass currentWeight = new Mass(value);
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
		Mass currentWeight = new Mass(value);
		if(currentWeight != weightAtBlock) {
			return true;
		} else {
			return false; 
		}
	}
	
	/**
	 * Called when the mass on the scale changes
	 * If blocked, will check if correct item was added/removed using setBlocked
	 * Will unblock if weight change fixes the weight discrepancy
	 */
	@Override
	public void notifyMassChanged() {
		super.notifyMassChanged();
		checkDiscrepancy();
	}
}







