/**
 * Yotam Rojnov (UCID: 30173949)
 * Duncan McKay (UCID: 30177857)
 * Mahfuz Alam (UCID:30142265)
 * Luis Trigueros Granillo (UCID: 30167989)
 * Lilia Skumatova (UCID: 30187339)
 * Abdelrahman Abbas (UCID: 30110374)
 * Talaal Irtija (UCID: 30169780)
 * Alejandro Cardona (UCID: 30178941)
 * Alexandre Duteau (UCID: 30192082)
 * Grace Johnson (UCID: 30149693)
 * Abil Momin (UCID: 30154771)
 * Tara Ghasemi M. Rad (UCID: 30171212)
 * Izabella Mawani (UCID: 30179738)
 * Binish Khalid (UCID: 30061367)
 * Fatima Khalid (UCID: 30140757)
 * Lucas Kasdorf (UCID: 30173922)
 * Emily Garcia-Volk (UCID: 30140791)
 * Yuinikoru Futamata (UCID: 30173228)
 * Joseph Tandyo (UCID: 30182561)
 * Syed Haider (UCID: 30143096)
 * Nami Marwah (UCID: 30178528)
 */

package com.thelocalmarketplace.software;

import java.math.BigInteger;
import java.util.List;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale; 

public class WeightDiscrepancy {
	
	private List<Item> items;
	private static AbstractElectronicScale scale;
	private Mass weightAtBlock;
	private static double value;
	private static double weightAtBlockDouble;
	

	/**
	 * Constructor for order
	 * @throws OverloadedDevice 
	 */
	public WeightDiscrepancy(Order order, AbstractElectronicScale scale) {  // revised some code for the demo class 
		this.items = order.getOrder();
	    this.scale = scale;
	    try {
	        this.weightAtBlock = scale.getCurrentMassOnTheScale();
	        
	        BigInteger divisor = new BigInteger("1000000");
	        BigInteger weightAtBlockBig = weightAtBlock.inMicrograms().divide(divisor);
	        WeightDiscrepancy.weightAtBlockDouble = weightAtBlockBig.doubleValue();
	        WeightDiscrepancy.value = order.getTotalWeightInGrams();
	        
	    } catch (OverloadedDevice e) {
	        // Handle the exception accordingly, such as logging or throwing a runtime exception
	        throw new RuntimeException("Failed to initialize WeightDiscrepancy: " + e.getMessage());
	    }
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
		double actual;
		double expected;

		actual = weightAtBlockDouble;
		expected = value;	
		block = !(expected == actual);
		SelfCheckoutStationSoftware.setStationBlock(block);
		
	} 
	
	public void checkIfCanUnblock() {
	    Mass actual;
	    Mass expected;
	    long tolerance;

	    try {
	        actual = scale.getCurrentMassOnTheScale();
	        expected = new Mass(value);
	        tolerance = scale.getSensitivityLimit().inMicrograms().longValue() / 2;

	        long actualInMicrograms = actual.inMicrograms().longValue();
	        long expectedInMicrograms = expected.inMicrograms().longValue();

	        // Calculate the absolute difference and compare it with the tolerance
	        long difference = Math.abs(actualInMicrograms - expectedInMicrograms);

	        if (difference <= tolerance) {
	            SelfCheckoutStationSoftware.setStationBlock(false);
	        }
	    } catch (OverloadedDevice e) {
	        SelfCheckoutStationSoftware.setStationBlock(true);
	    }
	}

    /**
     * Compares weight at block to current getWeight to check if an item has been removed.
     * 
     * @return True if item has been removed (new weight is less). 
     * @return Negative if weight decrease has not been detected
     **/
	public boolean checkRemoval() {
		if (value<weightAtBlockDouble) {
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
		if(value>weightAtBlockDouble) {
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
		if(value != weightAtBlockDouble) {
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
//	@Override
//	public void notifyMassChanged() {
//		super.notifyMassChanged();
//		checkDiscrepancy();
//	}

	
	public static void setStationBlock(boolean b) {
		if (b == true) {
			SelfCheckoutStationSoftware.setStationBlock(true);
		}
		if (b == false) {
			SelfCheckoutStationSoftware.setStationBlock(false);
		}
	}
}







